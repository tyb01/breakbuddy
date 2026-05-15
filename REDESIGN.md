# PawsUp — Production App Spec

> **Audience:** Hello Code agent. This is the single source of truth for building PawsUp. Build everything end-to-end, production-ready, Play Store publishable on day one.
>
> **This is a greenfield build.** No existing users. No legacy data. No migration. Build it right the first time.
>
> **How to work:** Execute sprint by sprint (Section 10). After each sprint, verify the acceptance criteria in Appendix E before proceeding.

---

## 0. What PawsUp is

PawsUp is a digital-wellbeing Android app. When you've been using a chosen app for too long, a cat comes to sit with you on your screen for a short break. No lectures. No punishment. Just a cat.

**Tagline:** *A tiny cat café for your phone.*

**Emotional core:** The user isn't being blocked — they're being visited. The break happens *with* them, not *to* them.

**Business model:** One free cat (Miso, the orange tabby) forever. Four premium cats via monthly or annual subscription. One bundle subscription for all cats. Seasonal one-time cats in the future.

**Privacy position:** No accounts. No backend. No analytics. Nothing leaves the device. This is both a product value and a Play Store publishing advantage.

---

## 1. Tech stack

Use these and nothing else unless this spec explicitly adds something:

- **Kotlin** (latest stable)
- **Jetpack Compose** + **Material 3**
- **Hilt** for dependency injection
- **DataStore (Preferences)** for all local persistence
- **ExoPlayer (Media3)** for video playback
- **Google Play Billing Library 8.0.0** for subscriptions
- **Android UsageStatsManager** for foreground app detection
- **Foreground Service** (`foregroundServiceType="dataSync"`) + **WorkManager watchdog** + **BroadcastReceiver** for boot resilience
- Min SDK: **24** (Android 7.0)
- Target SDK: **35** (Android 15)
- Build output: **Android App Bundle (.aab)** with Play App Signing

No Firebase. No analytics SDK. No crash reporting SDK. No ads SDK. No sign-in library.

---

## 2. Package layout

Single-module app under `com.pawsup`:

```
com.pawsup/
  application/
    PawsUpApplication.kt
  monitoring/
    MonitoringService.kt
    MonitoringRepository.kt
    BootReceiver.kt
    WatchdogWorker.kt
  apppicker/
    AppPickerActivity.kt
    AppPickerViewModel.kt
    InstalledAppsRepository.kt
    InstalledApp.kt
  cats/
    Cat.kt
    CatRegistry.kt
    CatAssetResolver.kt
    CatVoiceProvider.kt
  billing/
    BillingClientWrapper.kt
    BillingRepository.kt
    Entitlements.kt
    ProductIds.kt
  break_experience/
    BreakOverlayActivity.kt
    BreakOverlayViewModel.kt
    BreakState.kt
    components/
      VideoPlayerCompositor.kt
      BreakTimerDisplay.kt
      CatDialogueBubble.kt
      PetInteraction.kt
      LongPressEscape.kt
      GuestVisitOverlay.kt
  paywall/
    PaywallActivity.kt
    PaywallViewModel.kt
  share/
    BreakRecapGenerator.kt
    ShareCardComposable.kt
  settings/
    SettingsActivity.kt
    SettingsViewModel.kt
  onboarding/
    OnboardingActivity.kt
  data/
    DataStoreKeys.kt
    UserPreferences.kt
```

---

## 3. Data model

### 3.1 DataStore keys

```kotlin
object DataStoreKeys {
    val MONITORED_PACKAGES              = stringSetPreferencesKey("monitored_packages")
    val MAX_MINUTES_PER_VISIT           = intPreferencesKey("max_minutes_per_visit")      // default 15
    val BREAK_DURATION_MINUTES          = intPreferencesKey("break_duration_minutes")     // default 2
    val ACTIVE_CAT_ID                   = stringPreferencesKey("active_cat_id")           // default "miso"
    val OWNED_CAT_IDS                   = stringSetPreferencesKey("owned_cat_ids")        // always has "miso"
    val HAS_CAFE_BUNDLE                 = booleanPreferencesKey("has_cafe_bundle")        // default false
    val ENTITLEMENTS_LAST_SYNCED        = longPreferencesKey("entitlements_last_synced")
    val TOTAL_BREAKS_COMPLETED          = intPreferencesKey("total_breaks_completed")
    val TOTAL_PETS_LIFETIME             = longPreferencesKey("total_pets_lifetime")
    val ONBOARDING_COMPLETED            = booleanPreferencesKey("onboarding_completed")
    val USE_GL_CHROMA_KEY               = booleanPreferencesKey("use_gl_chroma_key")
    val USAGE_PERMISSION_GRANTED        = booleanPreferencesKey("usage_permission_granted")
    val OVERLAY_PERMISSION_GRANTED      = booleanPreferencesKey("overlay_permission_granted")
    val NOTIFICATIONS_PERMISSION_GRANTED = booleanPreferencesKey("notifications_permission_granted")

    fun guestVisitLastShown(catId: String) = longPreferencesKey("guest_visit_last_shown_$catId")
}
```

### 3.2 Cat data class

```kotlin
data class Cat(
    val id: String,
    val displayName: String,
    val tier: CatTier,
    val backgroundColorHex: String,   // "#000000" or "#0D4F4F" for Shadow
    val keyColor: Int,                 // Color int for chroma key shader
    val voiceBucket: VoiceBucket,
    val personalityBlurb: String,
    val moodColorHex: String,          // for share card gradient
    val greetingPitchShift: Float = 1.0f
)

enum class CatTier { FREE, PREMIUM }
enum class VoiceBucket { MISO, YUKI, MOCHA, SHADOW, BISCUIT }
```

### 3.3 CatRegistry (Hilt singleton)

```kotlin
@Singleton
class CatRegistry @Inject constructor() {
    val all: List<Cat> = listOf(
        Cat("miso",    "Miso",    CatTier.FREE,    "#000000", Color.BLACK,            VoiceBucket.MISO,    "Your warm, slightly judgmental roommate. Free, always.",           "#E8A87C", 1.00f),
        Cat("yuki",    "Yuki",    CatTier.PREMIUM, "#000000", Color.BLACK,            VoiceBucket.YUKI,    "Soft, sleepy, maternal. She'll sit with you when you need calm.",  "#F5ECD7", 1.05f),
        Cat("mocha",   "Mocha",   CatTier.PREMIUM, "#000000", Color.BLACK,            VoiceBucket.MOCHA,   "A Siamese kitten with way too much to say. Excitable, loving.",    "#AED9E0", 1.25f),
        Cat("shadow",  "Shadow",  CatTier.PREMIUM, "#0D4F4F", Color(0xFF0D4F4F).toArgb(), VoiceBucket.SHADOW,  "Mysterious, zen, slightly witchy. Drops wisdom and leaves.",       "#062d2d", 0.85f),
        Cat("biscuit", "Biscuit", CatTier.PREMIUM, "#000000", Color.BLACK,            VoiceBucket.BISCUIT, "A cozy calico who reminds you to eat and breathe.",                "#E07A5F", 1.00f),
    )
    fun find(id: String): Cat = all.first { it.id == id }
    fun free(): Cat = all.first { it.tier == CatTier.FREE }
    fun premium(): List<Cat> = all.filter { it.tier == CatTier.PREMIUM }
}
```

---

## 4. Monitoring service

### 4.1 Architecture

- `MonitoringService`: foreground service polling `UsageStatsManager.queryUsageStats` every **2 seconds** on a coroutine.
- If the foreground package is in `MONITORED_PACKAGES` AND the current continuous session exceeds `MAX_MINUTES_PER_VISIT`, fire the break overlay.
- Session time resets when the user switches package or after a break completes.
- `WatchdogWorker`: WorkManager periodic task every 15 minutes; if service not running, restart it.
- `BootReceiver`: on `ACTION_BOOT_COMPLETED`, start `MonitoringService`.

### 4.2 Session tracking (pseudo-code)

```kotlin
var currentSessionPackage: String? = null
var sessionStartTime: Long = 0L
var breakFireable = true

fun onPoll() {
    val foreground = getForegroundPackage() ?: return
    if (foreground !in userPreferences.monitoredPackages) {
        currentSessionPackage = null; breakFireable = true; return
    }
    if (foreground != currentSessionPackage) {
        currentSessionPackage = foreground
        sessionStartTime = System.currentTimeMillis()
        breakFireable = true; return
    }
    val elapsed = (System.currentTimeMillis() - sessionStartTime) / 60_000
    if (elapsed >= userPreferences.maxMinutesPerVisit && breakFireable) {
        breakFireable = false
        fireBreakOverlay()
    }
}
```

### 4.3 Firing the overlay

```kotlin
fun fireBreakOverlay() {
    startActivity(Intent(this, BreakOverlayActivity::class.java)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        .putExtra("cat_id", userPreferences.activeCatId)
        .putExtra("break_duration_minutes", userPreferences.breakDurationMinutes))
}
```

---

## 5. App picker

### 5.1 How to enumerate installed apps (Play-safe)

```kotlin
fun loadInstalledApps(pm: PackageManager): List<InstalledApp> =
    pm.queryIntentActivities(
        Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER), 0
    )
    .filter { it.activityInfo.packageName != "com.pawsup" }
    .map { InstalledApp(it.activityInfo.packageName, it.loadLabel(pm).toString(), it.loadIcon(pm)) }
    .sortedBy { it.displayName.lowercase() }
```

**Do NOT use `QUERY_ALL_PACKAGES`** — Play Store rejects it. The `<queries>` manifest block handles API 30+ visibility.

Cache result 60 seconds. Background thread only. LRU icon bitmap cache (100 entries max).

### 5.2 UI

- M3 `SearchBar` at top, filters list real-time, case-insensitive.
- `LazyColumn`: `[40dp icon]  [app name]  [Switch]`.
- Section header *"Apps you're watching"* above monitored apps when any are selected.
- Full-row tap target flips toggle.
- Toggle writes to `MONITORED_PACKAGES` DataStore immediately.
- Empty search state: *"No apps match that name."*
- Loading state: `CircularProgressIndicator` centred.

---

## 6. Cat video compositing

### 6.1 Asset structure

```
assets/cats/
  miso/entrance.mp4   idle.mp4   outro.mp4   poster.webp
  yuki/entrance.mp4   idle.mp4   outro.mp4   poster.webp
  mocha/entrance.mp4  idle.mp4   outro.mp4   poster.webp
  shadow/entrance.mp4 idle.mp4   outro.mp4   poster.webp   ← #0D4F4F bg
  biscuit/entrance.mp4 idle.mp4  outro.mp4   poster.webp
assets/sounds/
  greeting.mp3        purr.mp3
res/font/
  crimson_text.ttf    crimson_text_italic.ttf
```

All videos: 9:16 portrait (720×1280 minimum), no audio track, 30fps.
- `entrance.mp4`: cat enters bottom-right, walks to center, settles. ~5s.
- `idle.mp4`: cat in settled pose, breathing/blinking loop. 6s. First frame = last frame exactly.
- `outro.mp4`: cat stretches, walks out bottom-left. ~5s.
- `poster.webp`: cat in settled pose. 1080×1920. Used in paywall and share card.

Shadow only: all four files use  `#0D4F4F` background instead of black.

### 6.2 Chroma-key compositor

**Primary: OpenGL ES 2.0 fragment shader**

```glsl
precision mediump float;
uniform sampler2D uTexture;
uniform vec3 uKeyColor;    // (0,0,0) black cats | (0.051, 0.310, 0.310) Shadow
uniform float uThreshold;  // 0.18
uniform float uSoftness;   // 0.08
varying vec2 vTexCoord;

void main() {
    vec4 color = texture2D(uTexture, vTexCoord);
    float dist = distance(color.rgb, uKeyColor);
    float alpha = smoothstep(uThreshold, uThreshold + uSoftness, dist);
    gl_FragColor = vec4(color.rgb, alpha);
}
```

`cat.keyColor` is passed as `uKeyColor` — no special casing needed for Shadow; it's the same shader with a different uniform.

**Fallback: Compose BlendMode.Screen**

Detect GLES capability on first launch. If unavailable, render video in a Compose `Box` with `Modifier.graphicsLayer { blendMode = BlendMode.Screen }` over a dark background. Store result in `USE_GL_CHROMA_KEY`. Slightly less clean edges but works on all devices.

### 6.3 Idle loop

`Player.REPEAT_MODE_ONE` handles looping. Because the first and last frames are identical the loop is visually seamless — no crossfade needed.

---

## 7. Break overlay experience

### 7.1 Activity setup

```kotlin
// In BreakOverlayActivity.onCreate()
window.addFlags(
    FLAG_SHOW_WHEN_LOCKED or FLAG_DISMISS_KEYGUARD or
    FLAG_KEEP_SCREEN_ON or FLAG_TURN_SCREEN_ON
)
WindowCompat.setDecorFitsSystemWindows(window, false)
```

```xml
<activity
    android:name=".break_experience.BreakOverlayActivity"
    android:launchMode="singleInstance"
    android:excludeFromRecents="true"
    android:showOnLockScreen="true"
    android:exported="false" />
```

### 7.2 State machine

```kotlin
sealed class BreakState {
    object Entrance : BreakState()
    object CompanionLoop : BreakState()
    data class GuestVisit(val guestCat: Cat) : BreakState()
    object Outro : BreakState()
    object Done : BreakState()
}
```

Flow:
```
Entrance (~5s, video length)
  → CompanionLoop (breakDuration − entrance − outro)
      → [optional GuestVisit 8s] → resume CompanionLoop
  → Outro (~5s, video length)
      → Done → dismiss overlay → show RecapToast
```

### 7.3 Background

```kotlin
Brush.radialGradient(
    colors = listOf(Color(0xFF2A1F1A), Color(0xFF0F0A08)),
    center = Offset(screenWidth / 2f, screenHeight * 0.75f)
)
```

Same for all cats. Cat composites over it via the shader.

### 7.4 Timer

- Top-center, below status bar.
- Font: `Crimson Text`, 52sp, `Color(0xFFF5ECD7)`.
- Format: *`"Miso staying ~3:42 longer"`* — cat display name + remaining time.
- Update every second with 300ms crossfade between values.
- Never use "locked", "blocked", "limit", or "exceeded".

### 7.5 Dialogue bubble

- Upper third, centered, below the timer.
- Font: `Crimson Text Italic`, 22sp, white 90% alpha.
- 25-second rotation. Transition: fade out 600ms → pause 200ms → fade in 600ms.
- First line: the cat's `entrance` line, shown at t=1s in `Entrance` state.
- During `Outro`: show the cat's `goodbye` line.
- Enforced max 12 words per line (debug assertion in `CatVoiceProvider`).

### 7.6 Pet interaction

- Invisible tap zone covering lower 50% width × lower 40% height of screen.
- On tap: play `purr.mp3` at `cat.greetingPitchShift` pitch. Scale cat sprite 1.0 → 1.02 → 1.0 over 400ms EaseInOutCubic.
- Increment `sessionPetCount` in ViewModel and `TOTAL_PETS_LIFETIME` in DataStore.
- *"Tap to pet"* hint fades in at t=10s, fades out after 5s. Once per break session.

### 7.7 Long-press escape

- 1500ms long-press anywhere on screen.
- Reveals: *"I really need to leave →"* (16sp, white 60% alpha, center-screen).
- Tap it → skip to last 1.5s of `outro.mp4` → proceed to Done.

### 7.8 Audio

| Event | Sound | When |
|---|---|---|
| Break starts | `greeting.mp3` at `cat.greetingPitchShift` | t=0 of Entrance |
| User taps pet | `purr.mp3` at `cat.greetingPitchShift` | each tap |

**That is the entire audio design.** No music. No ambient sounds. No other audio. Silence is the product.

Use `MediaPlayer` with `setPlaybackParams(PlaybackParams().setPitch(...))`. Release in `onDestroy`.

### 7.9 Recap toast

Shown 6 seconds after overlay dismisses. `WindowManager` overlay (not `android.widget.Toast`):

```
🐾 Miso visited for 5 minutes.
   3 pets given.          [Share this break]
```

Rounded card, `Color(0xFF2A1F1A)`. Auto-dismisses after 6 seconds.

---

## 8. Shareable recap card

On *"Share this break"* tap:

1. Render `ShareCardComposable` off-screen, capture to 1080×1920 `Bitmap`.
2. Card layout:
   - Background: gradient using `cat.moodColorHex` top → desaturated dark bottom.
   - Cat poster (`poster.webp`) centered at 60% card height.
   - Headline: *"I took a break with [CatName] today 🐾"*
   - Subline: *"5 minutes of quiet. 3 pets given."*
   - Footer: *"PawsUp — a cat café in your pocket"*
3. Save to `FileProvider` cache as `break_recap.png`.
4. Launch `Intent.ACTION_SEND` with `type = "image/png"` and text: *"I took a 5-minute break with Miso today 🐾 #PawsUp"*.

---

## 9. Subscriptions & monetization

### 9.1 Product catalog (set up in Play Console)

| Product ID | Base plan ID | Price | Unlocks |
|---|---|---|---|
| `pawsup_yuki` | `yuki-monthly` | $1.99/mo | Yuki |
| `pawsup_yuki` | `yuki-yearly` | $9.99/yr + 1-week free trial | Yuki |
| `pawsup_mocha` | `mocha-monthly` | $1.99/mo | Mocha |
| `pawsup_mocha` | `mocha-yearly` | $9.99/yr + 1-week free trial | Mocha |
| `pawsup_shadow` | `shadow-monthly` | $1.99/mo | Shadow |
| `pawsup_shadow` | `shadow-yearly` | $9.99/yr + 1-week free trial | Shadow |
| `pawsup_biscuit` | `biscuit-monthly` | $1.99/mo | Biscuit |
| `pawsup_biscuit` | `biscuit-yearly` | $9.99/yr + 1-week free trial | Biscuit |
| `pawsup_cafe_bundle` | `cafe-monthly` | $4.99/mo | All cats + future cats |
| `pawsup_cafe_bundle` | `cafe-yearly` | $24.99/yr + 1-week free trial | All cats + future cats |

Free trial on yearly plans only. No trial on monthly.

### 9.2 ProductIds.kt

```kotlin
object ProductIds {
    val PER_CAT = mapOf(
        "yuki"    to PerCatSub("pawsup_yuki",    "yuki-monthly",    "yuki-yearly"),
        "mocha"   to PerCatSub("pawsup_mocha",   "mocha-monthly",   "mocha-yearly"),
        "shadow"  to PerCatSub("pawsup_shadow",  "shadow-monthly",  "shadow-yearly"),
        "biscuit" to PerCatSub("pawsup_biscuit", "biscuit-monthly", "biscuit-yearly"),
    )
    const val CAFE_PRODUCT = "pawsup_cafe_bundle"
    const val CAFE_MONTHLY = "cafe-monthly"
    const val CAFE_YEARLY  = "cafe-yearly"
}
data class PerCatSub(val productId: String, val monthlyPlan: String, val yearlyPlan: String)
```

### 9.3 BillingClientWrapper

- Initialize in `PawsUpApplication`, reconnect with exponential backoff.
- `queryProductDetailsAsync` at startup; cache results.
- `launchBillingFlow(activity, productDetails, basePlanId)` to start purchase.
- On `PURCHASED`: call `acknowledgePurchase` immediately (unacknowledged purchases auto-refund after 3 days).
- `queryPurchasesAsync` on every app foreground to refresh entitlements.
- `enablePendingPurchases(PendingPurchasesParams.newBuilder().enablePrepaidPlans().build())`.
- Emit `Flow<BillingEvent>` consumed by `BillingRepository`.
- `isSuspended() == true` → treat as unowned, surface a link to Play subscriptions center.

### 9.4 Entitlements

```kotlin
data class Entitlements(
    val ownedCatIds: Set<String>,
    val hasCafeBundle: Boolean,
    val lastSyncedAt: Long
) {
    fun owns(catId: String) = catId == "miso" || hasCafeBundle || catId in ownedCatIds

    companion object {
        val DEFAULT = Entitlements(setOf("miso"), false, 0L)
    }
}
```

Cached in DataStore. If Play unreachable for **14 consecutive days** → fall back to Miso-only.

### 9.5 Guest visit mechanic

During `CompanionLoop` for a user who owns only Miso:

- Eligible after `TOTAL_BREAKS_COMPLETED >= 3` (grace period for new users).
- 15% probability per break.
- Select random unowned cat with 7-day cooldown per cat (`guestVisitLastShown` key).
- Schedule the visit at a random point 20–40s into the loop.
- UI: fade in `poster.webp` of the guest cat at 80% opacity over the idle video, with text:

  > *"[Name] padded in for a moment, looked at you [adverb], and settled nearby. She'll come back if you adopt her."*
  > `[Meet [Name]]`  `[Maybe later]`

  Adverbs: Yuki = *sleepily*, Mocha = *eagerly*, Shadow = *mysteriously*, Biscuit = *warmly*.

- Auto-dismisses after 8 seconds. *"Meet [Name]"* opens paywall for that cat.
- Increment `TOTAL_BREAKS_COMPLETED` only at successful `Outro` completion.

### 9.6 Paywall

Opened from: (a) locked cat in Settings cat picker, (b) guest-visit CTA. Never opened automatically.

Layout top → bottom:
1. Cat poster — full-bleed top half, mood-color gradient overlay.
2. Cat name (32sp Crimson Text Bold) + personality blurb (18sp italic, grey).
3. Monthly/Yearly chip toggle. Yearly shows *"Save 58%"* badge.
4. Two purchase cards (M3 Card):
   - *"Adopt just [Name]"* — price for selected period.
   - *"Adopt the whole café"* — `$4.99/mo` or `$24.99/yr` — *"All 5 cats + every future cat"*.
5. *"Adopt"* button — launches billing flow for selected card + period.
6. *"Maybe later"* — text-only dismiss. No guilt.
7. Footer: *"Renews automatically. Cancel anytime in Play Store."* + Privacy Policy + Terms links.

Post-purchase: confetti, *"[Name] is yours now. She'll be there next time."*, return to cat picker with new cat selected.

### 9.7 Cat picker (Settings)

2-column grid of 5 cat cards. Each card: poster image, name, personality blurb, *"With you today"* badge if active, *"Adopt"* lock overlay if not owned. Tap owned → set active. Tap locked → open paywall.

---

## 10. Onboarding

Shown only when `ONBOARDING_COMPLETED == false`.

### Screen 1 — Welcome
- Miso `poster.webp` illustration, large.
- Title: *"A tiny cat café for your phone."*
- Body: *"When you scroll for too long, a cat comes to sit with you. Then you both rest a moment."*
- CTA: *"Let's go"*

### Screen 2 — Pick your apps
- Subtitle: *"Pick the apps that keep you scrolling. Miso will visit when you've been on one too long."*
- Embed `AppPickerActivity` inline.
- Require at least 1 app selected to enable CTA.
- CTA: *"Done, let's set limits"*

### Screen 3 — Set your limit
- Visit limit slider: 1–30 min, default 15. Live label: *"Miso will visit after 15 minutes."*
- Break duration slider: 1–15 min, default 5. Live label: *"Each visit lasts 5 minutes."*
- CTA: *"Almost there"*

### Screen 4 — Permissions
Walk through 3 permissions in order. Each: plain-English explanation + *"Grant"* button + checkmark when granted. All 3 must be granted to continue. If denied, show explanation + *"Try again"*.

| Permission | Explanation |
|---|---|
| `PACKAGE_USAGE_STATS` | *"PawsUp needs to know when app is open so Miso knows when to visit. This information never leaves your phone."* |
| `SYSTEM_ALERT_WINDOW` | *"PawsUp needs to show the cat on top of your screen during a break."* |
| `POST_NOTIFICATIONS` | *"A small background notification keeps PawsUp running. You can hide it in notification settings after setup."* |

CTA when all granted: *"Meet Miso"* → set `ONBOARDING_COMPLETED = true`, start `MonitoringService`, navigate to Settings.

### Screen 5 — Meet Miso
- Play `miso/entrance.mp4` composited on screen.
- Text fade-in: *"Hey. I'm Miso. I'll come by when you need a break."*
- After video + 1s: *"See you soon."*
- Auto-advance to Settings after 2s.

---

## 11. Settings screen

Single scrollable screen. No tabs, no hamburger menu.

```
Your companion today
[Active cat card — poster + name + blurb, tappable → cat picker]

Visit limit           15 min  [edit]
Break time             5 min  [edit]

Monitored apps         3 apps [edit → AppPickerActivity]

The café (5 cats)             [view → cat picker grid]

Reliability & permissions     [view → diagnostics panel]
```

Reliability panel: shows status of all 3 permissions, battery optimization warning, OEM autostart guidance if relevant.

---

## 12. Build config (`app/build.gradle.kts`)

```kotlin
android {
    namespace = "com.pawsup"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.pawsup"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"
        resourceConfigurations += listOf("en")
    }
    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
        }
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
    bundle {
        language { enableSplit = false }
        density  { enableSplit = true  }
        abi      { enableSplit = true  }
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2025.04.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.media3:media3-exoplayer:1.4.1")
    implementation("androidx.media3:media3-ui:1.4.1")
    implementation("com.android.billingclient:billing-ktx:8.0.0")
    implementation("androidx.work:work-runtime-ktx:2.9.1")
    implementation("io.coil-kt:coil-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
}
```

---

## 13. AndroidManifest (key sections)

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-permission android:name="android.permission.PACKAGE_USAGE_STATS"
        tools:ignore="ProtectedPermissions" />
    <uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
    <uses-permission android:name="android.permission.FOREGROUND_SERVICE_DATA_SYNC" />
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
    <uses-permission android:name="android.permission.POST_NOTIFICATIONS" />
    <uses-permission android:name="com.android.vending.BILLING" />

    <!-- Enumerate launcher apps on API 30+ without QUERY_ALL_PACKAGES -->
    <queries>
        <intent>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent>
    </queries>

    <meta-data
        android:name="com.google.android.play.billingclient.version"
        android:value="8.0.0" />

    <application>
        <!-- foregroundServiceType="dataSync" avoids the elevated specialUse review -->
        <service
            android:name=".monitoring.MonitoringService"
            android:foregroundServiceType="dataSync"
            android:exported="false" />

        <receiver android:name=".monitoring.BootReceiver" android:exported="false">
            <intent-filter>
                <action android:name="android.intent.action.BOOT_COMPLETED" />
            </intent-filter>
        </receiver>

        <activity
            android:name=".break_experience.BreakOverlayActivity"
            android:launchMode="singleInstance"
            android:excludeFromRecents="true"
            android:showOnLockScreen="true"
            android:exported="false" />
    </application>
</manifest>
```

---

## 14. ProGuard rules

```
-keep class com.android.billingclient.api.** { *; }
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keepclasseswithmembernames class * {
    @javax.inject.Inject <fields>;
    @javax.inject.Inject <init>(...);
}
-keep class androidx.media3.** { *; }
-keepclassmembers class * extends androidx.datastore.preferences.protobuf.GeneratedMessageLite { <fields>; }
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keep class com.pawsup.cats.** { *; }
-keep class com.pawsup.billing.** { *; }
```

---

## 15. Play Store readiness

### 15.1 The easy-publish ruleset — do NOT change these

| Decision | This project's choice | What NOT to use |
|---|---|---|
| Foreground service type | `dataSync` | `specialUse` — triggers manual Play review |
| App enumeration | `<queries>` manifest element | `QUERY_ALL_PACKAGES` — rejected without strong justification |
| Accounts | None | Any sign-in → account deletion form + user data requirements |
| Analytics | None | Any analytics SDK → Data Safety disclosures |
| Ads | None | Any ads SDK → AdMob review + content rating recalc |
| Backend | None | Any server calls → Data Safety "Data shared" disclosures |
| Target audience | 13+ | "Children" → Designed-for-Families certification (2–4 extra weeks) |
| In-app purchases | Play Billing only | Alternative billing → eligibility application |
| Content rating | "Everyone" via IARC | Skipping IARC → blocked in major regions |

If a future feature request would break any of these: **stop and ask before implementing.**

### 15.2 Play Console listing assets

```
play-store-listing/
  icon-512.png                     512×512
  feature-graphic-1024x500.png     Play Store hero banner
  screenshots/phone/               1080×1920 minimum
    01-break-miso.png
    02-cat-roster.png
    03-paywall.png
    04-share-card.png
    05-onboarding.png
  short-description.txt            max 80 chars
  full-description.txt             max 4000 chars (see Appendix D)
  privacy-policy.html              host at https://pawsup.app/privacy
  terms.html                       host at https://pawsup.app/terms
```

`short-description.txt`:
```
A tiny cat café for your phone. Gentle screen-time breaks with adorable cats.
```

### 15.3 Data Safety form

- Data collected: **None**
- Data shared: **None**
- User can request deletion: **Yes** — *"Uninstalling the app removes all data."*

### 15.4 Required Play Console forms

| Form | Answer |
|---|---|
| Sensitive permissions — `PACKAGE_USAGE_STATS` | Category: *Core app functionality*. Justification: *"PawsUp monitors which app is in the foreground to detect when a user-selected app has been used past their chosen time limit. This data never leaves the device."* |
| Foreground service | Select: *Data sync / background fetch* |
| Ads | No ads |
| App access | All functionality available without login |
| Account deletion | My app does not have accounts |
| Target audience | 13+ |
| Content rating (IARC) | Complete questionnaire → expected result: Everyone |
| Government / News / Health | No, No, No |

### 15.5 Testing process

1. Build signed release `.aab`.
2. Upload to **Internal Testing** track.
3. Confirm pre-launch report: zero crashes.
4. Test on 3 physical devices: API 24 (low-end), API 30 (mid), API 34+ (recent).
5. Add license tester emails in Play Console → Setup → License testing for free subscription testing.
6. Verify subscription purchase end-to-end with a license tester account.
7. Promote: Internal → Closed Testing (1 week soak optional) → Open Testing → Production.

---

## 16. Hard rules — never violate

These survive any refactor. Stop and flag if any of these is about to break:

- **No music or ambient audio.** Greeting mrrp + pet purr are the only sounds.
- **No backend.** Everything is local. Zero server calls.
- **No analytics SDK.** Not even Crashlytics. Use Play Console crash reporting.
- **No skip button.** Long-press escape is the only way out.
- **No nag notifications.** Only the monitoring foreground service notification exists.
- **No auto-triggered paywall.** Opens only on explicit user tap.
- **Miso is free forever.** Never locked. Never removed from free tier.
- **Cats don't lecture.** No mention of screen time, addiction, limits, or productivity.
- **One global time limit.** No per-app overrides — that is a v1.1 feature.

---

## Appendix A — strings.xml

```xml
<string name="app_name">PawsUp</string>

<!-- Onboarding -->
<string name="onboarding_welcome_title">A tiny cat café for your phone.</string>
<string name="onboarding_welcome_body">When you scroll for too long, a cat comes to sit with you. Then you both rest a moment.</string>
<string name="onboarding_cta_start">Let\'s go</string>
<string name="onboarding_apps_subtitle">Pick the apps that keep you scrolling. Miso will visit when you\'ve been on one too long.</string>
<string name="onboarding_apps_cta">Done, let\'s set limits</string>
<string name="onboarding_limits_cta">Almost there</string>
<string name="onboarding_visit_label">Miso will visit after %1$d minutes.</string>
<string name="onboarding_break_label">Each visit lasts %1$d minutes.</string>
<string name="onboarding_permission_usage_title">App usage access</string>
<string name="onboarding_permission_usage_body">PawsUp needs to know which app is open so Miso knows when to visit. This information never leaves your phone.</string>
<string name="onboarding_permission_overlay_title">Show on screen</string>
<string name="onboarding_permission_overlay_body">PawsUp needs to show the cat on top of your screen during a break.</string>
<string name="onboarding_permission_notification_title">Background notification</string>
<string name="onboarding_permission_notification_body">A small background notification keeps PawsUp running. You can hide it in notification settings after setup.</string>
<string name="onboarding_permission_grant">Grant</string>
<string name="onboarding_permission_try_again">Try again</string>
<string name="onboarding_all_done_cta">Meet Miso</string>
<string name="onboarding_miso_intro">Hey. I\'m Miso. I\'ll come by when you need a break.</string>
<string name="onboarding_miso_farewell">See you soon.</string>

<!-- App picker -->
<string name="app_picker_title">Choose your apps</string>
<string name="app_picker_search_hint">Search apps</string>
<string name="app_picker_section_monitored">Apps you\'re watching</string>
<string name="app_picker_empty_search">No apps match that name.</string>
<string name="app_picker_done">Done</string>

<!-- Settings -->
<string name="settings_companion_today">Your companion today</string>
<string name="settings_visit_limit">Visit limit</string>
<string name="settings_break_time">Break time</string>
<string name="settings_monitored_apps">Monitored apps</string>
<string name="settings_the_cafe">The café (5 cats)</string>
<string name="settings_reliability">Reliability &amp; permissions</string>
<string name="settings_min_format">%1$d min</string>
<string name="settings_apps_count">%1$d apps</string>

<!-- Break overlay -->
<string name="break_timer_format">%1$s staying ~%2$s longer</string>
<string name="break_pet_hint">Tap to pet</string>
<string name="break_escape">I really need to leave →</string>

<!-- Recap toast -->
<string name="recap_visited">🐾 %1$s visited for %2$s.</string>
<string name="recap_pets">%1$d pets given.</string>
<string name="recap_share">Share this break</string>

<!-- Share card -->
<string name="share_card_headline">I took a break with %1$s today 🐾</string>
<string name="share_card_subline">%1$s of quiet. %2$d pets given.</string>
<string name="share_card_watermark">PawsUp — a cat café in your pocket</string>
<string name="share_text">I took a %1$s break with %2$s today 🐾 #PawsUp</string>

<!-- Cat picker -->
<string name="cat_picker_title">The café</string>
<string name="cat_picker_active_badge">With you today</string>
<string name="cat_picker_locked_badge">Adopt</string>

<!-- Paywall -->
<string name="paywall_adopt_one">Adopt just %1$s</string>
<string name="paywall_adopt_all">Adopt the whole café</string>
<string name="paywall_adopt_all_subtitle">All 5 cats + every future cat</string>
<string name="paywall_monthly">Monthly</string>
<string name="paywall_yearly">Yearly</string>
<string name="paywall_yearly_badge">Save 58%</string>
<string name="paywall_cta">Adopt</string>
<string name="paywall_dismiss">Maybe later</string>
<string name="paywall_legal">Renews automatically. Cancel anytime in Play Store.</string>
<string name="paywall_success_title">%1$s is yours now.</string>
<string name="paywall_success_body">She\'ll be there next time.</string>

<!-- Monitoring notification -->
<string name="notification_monitoring_title">PawsUp</string>
<string name="notification_monitoring_body">Watching for break time.</string>
<string name="notification_monitoring_pause">Pause</string>

<!-- Guest visit -->
<string name="guest_visit_body">%1$s padded in for a moment, looked at you %2$s, and settled nearby. She\'ll come back if you adopt her.</string>
<string name="guest_visit_cta">Meet %1$s</string>
<string name="guest_visit_dismiss">Maybe later</string>
```

---

## Appendix B — Voice scripts (all 5 cats)

### Miso — free cat, dry warmth

**Entrance:** *"Hey. Came to sit with you."*

**Loop:** "You smell tired. Just an observation." / "I'm not judging. (I am a little.)" / "Your thumb's been busy. Give it a minute."  / "Stay. The view from here is fine." / "You're allowed to do nothing." / "Hands down. Both of them." / "I came all the way here, you know."  / "Look at me instead. I'm better." / "Breath in. Breath out. There you go." / "Whatever it was, it'll still be there."  / "We don't have to talk. I prefer it actually." / "Pet me if you're nervous. I'll allow it." / "You're doing fine. Probably." / "This is the cat appointment. Mandatory." / "Soft moment. Don't ruin it." / "Take up less space in your head." / "I came to remind you you're a person." / "Eyes off the rectangle for a second." / "You didn't miss anything important." / "We're just resting. That's the whole plan." / "Slow down. I dare you." / "Look out a window after this." / "You blink less than you should." / "I'll wait. I have nothing else on." / "This is the part where you exhale." / "I love you. Don't make it weird."

**Goodbye:** *"Okay. Go gently."*

---

### Yuki — premium, soft maternal

**Entrance:** *"There you are. Come rest a moment, sweetheart."*

**Loop:**  "Breathe with me. Slowly. Like this." / "Have you had water today? Real water?" / "There's no rush. Truly." /"I'll stay until you feel like yourself again." / "Close your eyes for a moment. Just a moment." / "You're allowed to slow down." / "Whatever it is, it can wait for you." / "I came because I missed you." / "Soft breath in. Soft breath out." / "I think you're doing your best." / "Unclench your hands, darling." / "We have all the time we need." / "You looked tired. So I came." / "Step away from your day for a minute." / "Nothing urgent will happen in five minutes." / "Your body is asking for stillness." / "Slow your eyes. Look at one thing." / "Tell yourself one kind thing." / "Stretch your back when I leave." / "You're loved. Just so you know." / "Pet my fur. It'll help us both." / "Sit a little longer with me." / "There. That's better already." / "You came back. That's enough." / "Rest now. I'm here."

**Goodbye:** *"Sleep well later. I mean it."*

---

### Mocha — premium, kitten energy

**Entrance:** *"HI HI HI you're here!"*

**Loop:**  "Okay okay okay sit sit sit." / "I have so much to tell you. I forgot it." / "Watch my tail. WATCH IT." / "You smell like outside. Were you outside?" / "I just remembered something! Now I forgot." / "Wiggle. Wiggle wiggle wiggle." / "I'm doing the loaf now. The loaf!" / "Pet me pet me pet me." / "Hi again. Did you miss me? Of course." / "I am very small but very important." / "What are we doing? Are we doing it?" / "I love being here. I love being here." / "Look at my paws! Tiny!" / "Boop. (I booped you.)" / "Are you my friend now? Yes? Yes." / "I'm purring. Can you hear it? Listen." / "I will protect you from the air." / "You have a nose. I have a nose too!" / "This is the best break. The best."  / "I can do a thing. Look. Watching?" / "Slow blink at you. Slow. Blink." / "I'm a tiny mighty cat. Mighty!" / "Stay stay stay. Five more minutes.hehe!" / "Are you having fun? I'm having fun." / "Yip! Just a tiny one. Sorry." / "You found me! Well, I found you." / "Best human. Confirmed. Best."

**Goodbye:** *"Bye!! Come back soon!! Bye!!"*

---

### Shadow — premium, witchy zen

**Entrance:** *"The scroll is a river. Step out a moment."*

**Loop:** "Nothing important is happening on that feed." / "Be where your paws are." / "The mind that is not racing arrives sooner." / "Stillness is a skill. You're practicing." / "Notice your breath. Don't change it. Just notice." / "The hurry is a story. It isn't true." / "You are not what you scrolled." / "Watch the air around your hands." / "The world will continue without your attention." / "Empty mind is full of room." / "Soften the place behind your eyes." / "The body knows. Ask it." / "You don't owe the screen anything." / "I sit. The room sits. You sit." / "Time is wider than you think." / "The wave passes. So does the urge." / "Your thoughts are not your home." / "Breath in: here. Breath out: now." / "The cat does not check the clock." / "What if you simply rest." / "Stop looking. Start noticing." / "The quiet has been waiting." / "Where are your shoulders right now." / "Even the river rests in pools." / "You are enough without doing." / "The eye that is rested sees better." / "Listen past the noise." / "Return to the breath. Return again." / "Soft attention. Soft."

**Goodbye:** *"We meet again at the next bend."*

---

### Biscuit — premium, cozy comfort

**Entrance:** *"Oh good, you're here. Sit, sit."*

**Loop:**  "Let's pretend this is a tea break." / "Stretch your back. I'll wait." / "Have a snack after this. Promise me." / "Your hands are tight. Open them." / "Five minutes is a perfectly good amount." / "You worked hard. I noticed😁." / "Take up some space. Lean back." / "Warm drink later? Yes, warm drink." / "Soft is a feeling. Try some." / "I made room. It's for you." / "Has anyone told you you're doing fine?" / "Soup tonight, maybe. Just a thought." / "Your eyes need a longer blink." / "Roll your wrists. Both ways. There." / "You're allowed to enjoy this." / "I packed extra coziness. We can share." / "Even cats nap. Don't fight it." / "Step outside later. Just a little." / "Tell your shoulders the day is done." / "I'm proud of you. Don't argue." / "Want a snack? I want a snack." / "Soft sweater energy. Match me." / "The good news is: you can rest." / "Five deep breaths before you go." / "We made it to right now together." / "Be gentle with the next hour." / "I love your face. Just saying." / "Cozy is a verb. Do it now."

**Goodbye:** *"Go have a snack. That's an order."*

---

## Appendix C — Play Store full description

```
PawsUp is a tiny cat café for your phone.

When you've been scrolling for too long, a cat comes to sit with
you. Not to lecture. Not to lock your phone. Just to be there,
quietly, while you take a small breath.

🐾 WORKS WITH ANY APP
Choose any app on your phone — the ones that keep you scrolling.
Set a per-visit time that feels right. PawsUp watches quietly
in the background and waits.

🐾 A CAT COMES TO VISIT
When you cross your limit, your cat walks onto the screen and
settles in for a quiet moment with you. No music. No alarms.
No anxious warnings. Just a soft companion and a slow few
minutes of silence.

🐾 ADOPT A WHOLE CAFÉ
Miso, your orange tabby roommate, is free forever. When you're
ready, you can also adopt:
  • Yuki — a soft Persian who whispers like a calm parent
  • Mocha — a Siamese kitten with too much to say
  • Shadow — a wise black cat with zen one-liners
  • Biscuit — a cozy calico who reminds you to eat
Each cat has a personality, a voice, and a way of sitting with you.

🐾 BUILT FOR YOUR PRIVACY
No accounts. No servers. No analytics. Nothing leaves your
device — ever. We can't see what apps you use, how long you
scroll, or how many times you pet a cat. That's between you
and your cat.

🐾 SUBSCRIPTION DETAILS
Miso is free, always. Premium cats are $1.99/month each, or
adopt the whole café for $4.99/month. Annual plans available
with a 1-week free trial. Cancel anytime through Google Play.
Subscriptions renew automatically until cancelled.

Privacy: https://pawsup.app/privacy
Terms:   https://pawsup.app/terms
```

---

## Appendix D — Acceptance criteria

**Sprint 1 — Core infrastructure**
- [ ] Project builds cleanly with all dependencies resolved
- [ ] Hilt injection works end-to-end
- [ ] DataStore reads/writes for all defined keys
- [ ] `CatRegistry` returns correct 5 cats with correct metadata
- [ ] `BillingClientWrapper` initializes and connects to Play without error
- [ ] `Entitlements.owns("miso")` returns `true` by default
- [ ] 14-day offline fallback logic passes unit tests

**Sprint 2 — Monitoring service**
- [ ] `MonitoringService` starts and shows persistent notification
- [ ] `BootReceiver` restarts service after simulated reboot
- [ ] `WatchdogWorker` restarts service after `adb shell am kill`
- [ ] Time limit crossed on monitored app fires `BreakOverlayActivity`
- [ ] Session timer resets on app switch
- [ ] Break does not re-fire immediately after completing

**Sprint 3 — App picker**
- [ ] All user-launchable apps appear in list
- [ ] PawsUp itself does not appear
- [ ] System services without launcher icons do not appear
- [ ] Search filters correctly, case-insensitive
- [ ] Toggle persists after app kill and reopen
- [ ] List loads in under 1 second on device with 80+ apps
- [ ] Monitoring service triggers on a newly-toggled app

**Sprint 4 — Break overlay**
- [ ] Overlay appears above any app including lock screen
- [ ] Miso entrance video plays from bottom-right to center
- [ ] Black background keys out cleanly with no grey fringe (Pixel 6+)
- [ ] Idle loop is seamless with no visible cut
- [ ] Timer counts down accurately every second with smooth transition
- [ ] Dialogue lines rotate every 25 seconds without flash
- [ ] Greeting sound plays once at entrance, pitch-shifted correctly
- [ ] Pet tap plays purr and scales sprite
- [ ] Long-press 1500ms reveals escape text
- [ ] Escape exits via fast-forward outro
- [ ] Outro plays with goodbye line
- [ ] Zero music or background audio at any point
- [ ] Recap toast appears after overlay dismisses
- [ ] All 5 cats render correctly including Shadow with #0D4F4F key

**Sprint 5 — Subscriptions & paywall**
- [ ] Paywall opens from locked cat in picker
- [ ] Paywall opens from guest visit CTA only — never auto-triggered
- [ ] Monthly/yearly toggle switches prices correctly
- [ ] Test purchase via Play Console license tester completes
- [ ] `Entitlements` updates within 2 seconds of purchase
- [ ] Purchased cat selectable in cat picker
- [ ] Entitlements survive app kill and reopen
- [ ] Purchases acknowledged immediately (not left pending)
- [ ] Guest visit fires within 15% of breaks for Miso-only users
- [ ] Guest visit does not fire in first 3 breaks
- [ ] Guest visit respects 7-day cooldown per cat
- [ ] Café bundle unlocks all 4 premium cats

**Sprint 6 — Onboarding**
- [ ] Onboarding shows only on first launch
- [ ] Cannot advance past app picker without selecting at least 1 app
- [ ] All 3 permissions granted before onboarding completes
- [ ] "Try again" re-prompts individual permissions
- [ ] Miso intro video plays at end of onboarding
- [ ] `MonitoringService` starts immediately after onboarding
- [ ] Second launch goes directly to Settings screen

**Sprint 7 — Sharing & polish**
- [ ] Share card generates and saves correctly
- [ ] Share intent opens OS chooser with correct image and prefilled text
- [ ] Share card is visually polished on a real device
- [ ] All animations are smooth at 60fps on mid-range device
- [ ] No voice line exceeds 12 words (debug assertion fires if violated)

**Sprint 8 — Play Store readiness**
- [ ] Release `.aab` builds cleanly with R8 enabled
- [ ] Zero crashes in Play Console pre-launch report
- [ ] App works correctly on Android 7.0 (API 24) test device
- [ ] Privacy policy and Terms live and reachable at their URLs
- [ ] Data Safety form: all categories marked "Not collected"
- [ ] `PACKAGE_USAGE_STATS` Permission Declaration submitted under "Core app functionality"
- [ ] Foreground service type is `dataSync` in the final release manifest
- [ ] No `QUERY_ALL_PACKAGES` in final manifest
- [ ] App icon, feature graphic, and 5 screenshots uploaded
- [ ] Short and full descriptions on the listing
- [ ] IARC content rating completed, result is "Everyone"
- [ ] Subscription test purchase works on Internal Testing track
- [ ] All Play Console policy forms completed
