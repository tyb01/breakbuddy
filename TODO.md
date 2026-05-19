# PawsUp — What's Left To Do

Everything below must be done before the app can be published on the Play Store.
Steps are in the exact order you should do them.

---

## STEP 1 — Create Your Cat Videos

You need 3 videos per cat × 5 cats = 15 videos total.

**Specs for every video**
- Format: MP4
- Resolution: 720×1280 minimum (portrait, 9:16)
- Frame rate: 30fps
- No audio track in the video file
- Background color: black (#000000) for Miso, Yuki, Mocha, Biscuit — teal (#0D4F4F) for Shadow only

**The 3 videos each cat needs**

| File name | What happens | Length |
|---|---|---|
| `entrance.mp4` | Cat walks in from bottom-right, settles in center | ~5 seconds |
| `idle.mp4` | Cat sits, breathing/blinking loop. First frame = last frame exactly (seamless loop) | 6 seconds |
| `outro.mp4` | Cat stretches and walks out bottom-left | ~5 seconds |

**Where to put them**
```
app/src/main/assets/cats/miso/entrance.mp4
app/src/main/assets/cats/miso/idle.mp4
app/src/main/assets/cats/miso/outro.mp4
app/src/main/assets/cats/yuki/entrance.mp4
... (same pattern for yuki, mocha, shadow, biscuit)
```

---

## STEP 2 — Create Your Cat Posters

You need 1 poster image per cat = 5 images total.

**Specs**
- Format: WebP (lossless, supports transparency)
- Size: 1024×1024 pixels (square)
- Background: fully transparent (the app removes backgrounds automatically, but starting transparent gives cleaner edges)
- Content: cat in a settled/resting pose

**Where to put them**
```
app/src/main/assets/cats/miso/poster.webp
app/src/main/assets/cats/yuki/poster.webp
app/src/main/assets/cats/mocha/poster.webp
app/src/main/assets/cats/shadow/poster.webp
app/src/main/assets/cats/biscuit/poster.webp
```

---

## STEP 3 — Create Your Sound Files

You need 2 audio files.

| File | What it is | Notes |
|---|---|---|
| `greeting.mp3` | A soft "mrrp" sound — plays when the cat arrives | Keep it short (~1 second) |
| `purr.mp3` | A purring sound — plays each time the user taps the cat | 1–3 seconds |

**Where to put them**
```
app/src/main/assets/sounds/greeting.mp3
app/src/main/assets/sounds/purr.mp3
```

---

## STEP 4 — Create a Google Play Console Account

1. Go to https://play.google.com/console
2. Sign in with a Google account
3. Pay the one-time $25 registration fee
4. Fill in your developer name and contact details
5. Accept the Developer Distribution Agreement

---

## STEP 5 — Create the App in Play Console

1. In Play Console, click **"Create app"**
2. App name: `PawsUp`
3. Default language: English
4. App or Game: App
5. Free or Paid: Free
6. Accept the declarations and click **Create app**

---

## STEP 6 — Set Up In-App Subscriptions

In Play Console → your app → **Monetize → Subscriptions**

Create these 5 subscription products one by one:

**Product 1**
- Product ID: `pawsup_yuki`
- Add base plan: `yuki-monthly` — $1.99/month
- Add base plan: `yuki-yearly` — $9.99/year, with 1-week free trial

**Product 2**
- Product ID: `pawsup_mocha`
- Add base plan: `mocha-monthly` — $1.99/month
- Add base plan: `mocha-yearly` — $9.99/year, with 1-week free trial

**Product 3**
- Product ID: `pawsup_shadow`
- Add base plan: `shadow-monthly` — $1.99/month
- Add base plan: `shadow-yearly` — $9.99/year, with 1-week free trial

**Product 4**
- Product ID: `pawsup_biscuit`
- Add base plan: `biscuit-monthly` — $1.99/month
- Add base plan: `biscuit-yearly` — $9.99/year, with 1-week free trial

**Product 5**
- Product ID: `pawsup_cafe_bundle`
- Add base plan: `cafe-monthly` — $4.99/month
- Add base plan: `cafe-yearly` — $24.99/year, with 1-week free trial

---

## STEP 7 — Add License Testers

This lets you test purchases without real charges.

1. Play Console → **Setup → License testing**
2. Add your own Gmail address (and any testers' addresses)
3. Set license response to **RESPOND_NORMALLY**
4. Save

---

## STEP 8 — Create a Release Keystore

This is the file that signs your app. **Keep it safe — you can never change it after publishing.**

Open a terminal in your project folder and run:

```bash
keytool -genkey -v -keystore pawsup-release.jks \
  -alias pawsup -keyalg RSA -keysize 2048 -validity 10000
```

You will be asked for:
- A keystore password (remember this)
- Your name, organisation, city, country
- A key password (can be same as keystore password)

**Store this file somewhere safe outside the project. Never commit it to Git.**

---

## STEP 9 — Configure the Release Build

Open `app/build.gradle.kts` and add your signing info inside the `android { }` block:

```kotlin
signingConfigs {
    create("release") {
        storeFile = file("../pawsup-release.jks")   // path to your keystore
        storePassword = "YOUR_KEYSTORE_PASSWORD"
        keyAlias = "pawsup"
        keyPassword = "YOUR_KEY_PASSWORD"
    }
}

buildTypes {
    release {
        isMinifyEnabled = true
        isShrinkResources = true
        proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        signingConfig = signingConfigs.getByName("release")  // add this line
    }
}
```

> Tip: Move passwords to a `local.properties` file and read them from there — don't hardcode them in `build.gradle.kts`.

---

## STEP 10 — Remove Debug Logging

Before building the release, remove the debug log lines from the monitoring service.

Open `app/src/main/java/com/pawsup/monitoring/MonitoringService.kt` and delete every line that starts with `Log.d(TAG, ...)`. You can also delete the `private const val TAG = "PawsUp"` line and the `import android.util.Log` import.

---

## STEP 11 — Build the Release AAB

In your terminal, in the project folder:

```bash
./gradlew bundleRelease
```

The output file will be at:
```
app/build/outputs/bundle/release/app-release.aab
```

---

## STEP 12 — Upload to Internal Testing

1. Play Console → your app → **Release → Testing → Internal testing**
2. Click **Create new release**
3. Under "App bundles", upload `app-release.aab`
4. Write release notes (e.g. "Initial internal test")
5. Click **Save and publish**

---

## STEP 13 — Test the Full Flow on a Real Device

Install the internal test build on your phone via the Play Store (search for it using the internal test link Play Console gives you).

Check each of these:
- [ ] App opens and onboarding shows
- [ ] All 3 permissions can be granted
- [ ] Monitoring starts and the notification appears
- [ ] An app you're watching triggers Miso after the visit limit
- [ ] Entrance → idle → outro plays without any blink or gap
- [ ] Tapping the cat plays the purr sound
- [ ] The break ends and the recap toast shows
- [ ] Settings shows all 3 tabs (Home, Café, Reliability)
- [ ] Battery optimization fix button works in Reliability tab
- [ ] Monitor Me toggle stops and restarts monitoring
- [ ] Subscription purchase works (use your license tester account)
- [ ] After purchase, the cat unlocks and is selectable in Café tab
- [ ] Swipe app from recents — monitoring keeps running
- [ ] Reboot phone — monitoring restarts automatically

---

## STEP 14 — Fill in the Play Store Listing

In Play Console → **Store presence → Main store listing**

**App name:** PawsUp

**Short description** (copy exactly):
```
A tiny cat café for your phone. Gentle screen-time breaks with adorable cats.
```

**Full description** — copy from `REDESIGN.md` → Appendix C

**App icon:** Upload a 512×512 PNG

**Feature graphic:** Upload a 1024×500 PNG (banner shown at top of Play Store page)

**Screenshots:** Upload at least 4 screenshots from the app (1080×1920):
- The break overlay with a cat on screen
- The Settings home tab
- The Café tab showing all 5 cats
- The paywall

---

## STEP 15 — Complete the Data Safety Form

Play Console → **Policy → Data safety**

- Data collected: **No data collected**
- Data shared: **No data shared**
- Data deletion: select "Users can request deletion" → write: *"Uninstalling the app removes all data."*

Save and submit.

---

## STEP 16 — Submit the Sensitive Permissions Declaration

Play Console → **Policy → App content → Sensitive permissions**

For `PACKAGE_USAGE_STATS`:
- Category: **Core app functionality**
- Justification: *"PawsUp monitors which app is in the foreground to detect when a user-selected app has been used past their chosen time limit. This data never leaves the device."*

For **Foreground service**:
- Select: **Data sync / background fetch**

---

## STEP 17 — Complete the IARC Content Rating

Play Console → **Policy → App content → Content rating**

- Click **Start questionnaire**
- App type: Utility
- Answer No to everything (no violence, no sexual content, no user interaction, etc.)
- Expected result: **Everyone**

---

## STEP 18 — Complete All Other App Content Forms

Play Console → **Policy → App content**

Go through each section and fill in:
- **Ads:** No ads
- **App access:** All functionality available without login
- **Account deletion:** My app does not have accounts
- **Target audience:** 13+
- **Government / News / Health:** No, No, No

---

## STEP 19 — Host Privacy Policy and Terms

The app links to these URLs. You need them live before submitting for review.

Create simple web pages at:
- `https://pawsup.app/privacy` — Privacy Policy
- `https://pawsup.app/terms` — Terms of Service

Minimum privacy policy content:
- What data the app collects (none)
- That everything stays on-device
- Contact email for questions

Add the privacy policy URL in Play Console → **Policy → App content → Privacy policy**

---

## STEP 20 — Promote to Production

Once internal testing passes with zero crashes:

1. Play Console → **Testing → Internal testing → Promote release → Production**
2. Set rollout percentage to **20%** for the first few days (safer than 100%)
3. Monitor crash reports in Play Console → **Android vitals**
4. Once stable, increase to 100%

---

## Quick Reference — File Locations

```
app/src/main/assets/
  cats/
    miso/     entrance.mp4  idle.mp4  outro.mp4  poster.webp
    yuki/     entrance.mp4  idle.mp4  outro.mp4  poster.webp
    mocha/    entrance.mp4  idle.mp4  outro.mp4  poster.webp
    shadow/   entrance.mp4  idle.mp4  outro.mp4  poster.webp
    biscuit/  entrance.mp4  idle.mp4  outro.mp4  poster.webp
  sounds/
    greeting.mp3
    purr.mp3

pawsup-release.jks   ← keep this safe, outside the project folder
```
