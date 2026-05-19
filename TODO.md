# PawsUp — Publishing Checklist

Follow every step in this exact order. Each section matches what you see on the Play Console dashboard.

> **Important:** Play Console locks the subscription creation screen until two things are done first:
> 1. At least one approved build is uploaded (even to Internal Testing)
> 2. Content rating and target audience forms are submitted
>
> Because of this, subscriptions are set up **after** the first internal build is uploaded and the required forms are filled — not before.

---

## ── PHASE 1: PREPARE ASSETS AND CODE ────────────────────────────

### STEP 1 — Create Cat Videos (15 files)

**Specs:** MP4, portrait 720×1280 min, 30fps, no audio.
Background: black `#000000` for Miso/Yuki/Mocha/Biscuit — teal `#0D4F4F` for Shadow.

| File | What it shows | Length |
|---|---|---|
| `entrance.mp4` | Cat walks in from bottom-right, settles center | ~5s |
| `idle.mp4` | Cat sits, breathes/blinks loop — first frame = last frame | 6s |
| `outro.mp4` | Cat stretches, walks out bottom-left | ~5s |

Place all files here:
```
app/src/main/assets/cats/miso/entrance.mp4
app/src/main/assets/cats/miso/idle.mp4
app/src/main/assets/cats/miso/outro.mp4
app/src/main/assets/cats/yuki/entrance.mp4
app/src/main/assets/cats/yuki/idle.mp4
app/src/main/assets/cats/yuki/outro.mp4
app/src/main/assets/cats/mocha/entrance.mp4
app/src/main/assets/cats/mocha/idle.mp4
app/src/main/assets/cats/mocha/outro.mp4
app/src/main/assets/cats/shadow/entrance.mp4
app/src/main/assets/cats/shadow/idle.mp4
app/src/main/assets/cats/shadow/outro.mp4
app/src/main/assets/cats/biscuit/entrance.mp4
app/src/main/assets/cats/biscuit/idle.mp4
app/src/main/assets/cats/biscuit/outro.mp4
```

- [ ] All 15 videos placed in the correct folders

---

### STEP 2 — Create Cat Poster Images (5 files)

**Specs:** WebP lossless, 1024×1024, transparent background, cat in settled pose.

```
app/src/main/assets/cats/miso/poster.webp
app/src/main/assets/cats/yuki/poster.webp
app/src/main/assets/cats/mocha/poster.webp
app/src/main/assets/cats/shadow/poster.webp
app/src/main/assets/cats/biscuit/poster.webp
```

- [ ] All 5 posters placed in the correct folders

---

### STEP 3 — Create Sound Files (2 files)

| File | Sound | Length |
|---|---|---|
| `greeting.mp3` | Soft mrrp — plays when cat arrives | ~1s |
| `purr.mp3` | Purring — plays when user taps cat | 1–3s |

```
app/src/main/assets/sounds/greeting.mp3
app/src/main/assets/sounds/purr.mp3
```

- [ ] Both sound files placed in the correct folder

---

### STEP 4 — Remove Debug Logging From Code

Open `app/src/main/java/com/pawsup/monitoring/MonitoringService.kt` and remove:
- Every line starting with `Log.d(TAG, ...)`
- The line `private const val TAG = "PawsUp"`
- The line `import android.util.Log`

Do the same in `WatchdogWorker.kt`, `BootReceiver.kt`, `KeepAliveReceiver.kt`.

- [ ] All Log.d lines removed from all monitoring files

---

### STEP 5 — Create Release Keystore

**This file signs your app forever. Never lose it. Never commit it to Git.**

Open PowerShell in your project folder:

```powershell
keytool -genkey -v -keystore pawsup-release.jks -alias pawsup -keyalg RSA -keysize 2048 -validity 10000
```

Write down the password. Move `pawsup-release.jks` somewhere safe **outside** the project folder.

- [ ] Keystore created and stored safely

---

### STEP 6 — Configure Release Signing in Code

Open `app/build.gradle.kts`. Inside `android { }`, add before `buildTypes`:

```kotlin
signingConfigs {
    create("release") {
        storeFile = file("/YOUR/SAFE/PATH/pawsup-release.jks")
        storePassword = "YOUR_KEYSTORE_PASSWORD"
        keyAlias = "pawsup"
        keyPassword = "YOUR_KEY_PASSWORD"
    }
}
```

Then inside the existing `release { }` block add:
```kotlin
signingConfig = signingConfigs.getByName("release")
```

- [ ] Signing config added to build.gradle.kts

---

### STEP 7 — Build the Release AAB

```powershell
.\gradlew.bat bundleRelease
```

Output file:
```
app/build/outputs/bundle/release/app-release.aab
```

- [ ] AAB built with no errors

---

### STEP 8 — Host Privacy Policy and Terms

Create two web pages (GitHub Pages, Notion, or any free host):
- `https://pawsup.app/privacy`
- `https://pawsup.app/terms`

Minimum privacy policy content: app collects no data, everything stays on device, uninstalling removes all data, your contact email.

- [ ] Privacy policy URL is live
- [ ] Terms URL is live

---

## ── PHASE 2: INTERNAL TESTING (dashboard section 1) ─────────────

### STEP 9 — Select Testers for Internal Testing

Play Console → **Testing → Internal testing → Testers tab**

1. Click **Create email list**
2. Name it "Core testers"
3. Add your own Gmail address
4. Click **Save changes**

- [ ] Tester email list created

---

### STEP 10 — Create and Publish Internal Testing Release

Play Console → **Testing → Internal testing → Releases tab**

1. Click **Create new release**
2. Click **Upload** → select `app-release.aab`
3. Release name: `1.0.0`
4. Release notes: `Initial internal test`
5. Click **Next** → review summary → click **Save and publish**

- [ ] Internal testing release published
- [ ] Install the app on your phone using the internal test link — confirm it opens correctly

---

## ── PHASE 3: FINISH SETTING UP (dashboard section 2) ───────────

Complete all items in this phase. Each one is a circle checkbox on your dashboard.

---

### STEP 11 — Set Privacy Policy

Play Console → **Policy → App content → Privacy policy**

1. Paste: `https://pawsup.app/privacy`
2. Click **Save**

- [ ] Done ✓

---

### STEP 12 — App Access

Play Console → **Policy → App content → App access**

1. Select **All functionality is available without special access**
2. Click **Save**

- [ ] Done ✓

---

### STEP 13 — Ads

Play Console → **Policy → App content → Ads**

1. Select **No, my app does not contain ads**
2. Click **Save**

- [ ] Done ✓

---

### STEP 14 — Content Rating

Play Console → **Policy → App content → Content rating**

1. Click **Start questionnaire**
2. Enter your email
3. Category: **Utility**
4. Answer **No** to every question
5. Click **Calculate rating** → expected result: **Everyone**
6. Click **Apply rating**

- [ ] Done ✓ — rating shows "Everyone"

---

### STEP 15 — Target Audience

Play Console → **Policy → App content → Target audience**

1. Select **13 and over**
2. "Does your app appeal to children?" → **No**
3. Click **Save**

- [ ] Done ✓

---

### STEP 16 — Data Safety

Play Console → **Policy → Data safety**

1. "Does your app collect or share any user data?" → **No**
2. "Is all data encrypted in transit?" → **Yes**
3. "Do you provide a way for users to request data deletion?" → **Yes**
   - Explain: *Uninstalling the app removes all data.*
4. Click **Save** then **Submit**

- [ ] Done ✓

---

### STEP 17 — Government Apps

Play Console → **Policy → App content → Government apps**

1. Select **No** → Click **Save**

- [ ] Done ✓

---

### STEP 18 — Financial Features

Play Console → **Policy → App content → Financial features**

1. Select **No** → Click **Save**

- [ ] Done ✓

---

### STEP 19 — Health

Play Console → **Policy → App content → Health**

1. Select **No** → Click **Save**

- [ ] Done ✓

---

### STEP 20 — App Category and Contact Details

Play Console → **Store presence → App category**

1. App type: **App**
2. Category: **Health & Fitness**
3. Add your contact email address
4. Click **Save**

- [ ] Done ✓

---

### STEP 21 — Set Up Store Listing

Play Console → **Store presence → Main store listing**

| Field | Value |
|---|---|
| App name | `PawsUp` |
| Short description | `A tiny cat café for your phone. Gentle screen-time breaks with adorable cats.` |
| Full description | Copy from `REDESIGN.md` → Appendix C |
| App icon | 512×512 PNG |
| Feature graphic | 1024×500 PNG |

Screenshots (at least 4, minimum 1080×1920):
- Break overlay with a cat on screen
- Settings Home tab
- Café tab showing all 5 cats
- Paywall screen

Click **Save**

- [ ] Done ✓ — all fields saved

---

> All circles under "Finish setting up your app" should now be green.
> **Now you can set up subscriptions** — the screen is unlocked at this point.

---

## ── PHASE 4: SET UP SUBSCRIPTIONS (now unlocked) ────────────────

### STEP 22 — Create In-App Subscriptions

Play Console → your app → **Monetize → Subscriptions** → **Create subscription**

For each product:
1. Enter the Product ID exactly as shown
2. Add a name (e.g. "Yuki")
3. Click **Add base plan**, fill in plan ID, billing period, price
4. Add second base plan the same way
5. Click **Save** then **Activate**

| Product ID | Base plan ID | Billing | Price | Trial |
|---|---|---|---|---|
| `pawsup_yuki` | `yuki-monthly` | Monthly | $1.99 | None |
| `pawsup_yuki` | `yuki-yearly` | Yearly | $9.99 | 7 days |
| `pawsup_mocha` | `mocha-monthly` | Monthly | $1.99 | None |
| `pawsup_mocha` | `mocha-yearly` | Yearly | $9.99 | 7 days |
| `pawsup_shadow` | `shadow-monthly` | Monthly | $1.99 | None |
| `pawsup_shadow` | `shadow-yearly` | Yearly | $9.99 | 7 days |
| `pawsup_biscuit` | `biscuit-monthly` | Monthly | $1.99 | None |
| `pawsup_biscuit` | `biscuit-yearly` | Yearly | $9.99 | 7 days |
| `pawsup_cafe_bundle` | `cafe-monthly` | Monthly | $4.99 | None |
| `pawsup_cafe_bundle` | `cafe-yearly` | Yearly | $24.99 | 7 days |

- [ ] All 5 products created and activated

---

### STEP 23 — Add License Testers

Play Console → **Setup → License testing**

1. Click **Add license testers**
2. Add your own Gmail address
3. Set response to **RESPOND_NORMALLY**
4. Click **Save**

- [ ] Your Gmail added as license tester

---

### STEP 24 — Test Subscription Purchase

Install the internal test build (already published in Step 10) and:

1. Open the app → go to Café tab → tap a locked cat
2. The paywall opens
3. Tap **Adopt** — the Play billing sheet appears
4. Complete the purchase (no real charge because you are a license tester)
5. Cat should unlock immediately in the Café tab

- [ ] Purchase flow works end to end with no errors

---

## ── PHASE 5: CLOSED TESTING (dashboard section 3) ──────────────

### STEP 25 — Select Countries and Regions

Play Console → **Testing → Closed testing** → **Create track** → name it `Alpha`

Inside the Alpha track:
1. Click **Countries / Regions** → **Add countries / regions**
2. Select at minimum your own country
3. Click **Save**

- [ ] Countries selected

---

### STEP 26 — Select Testers for Closed Testing

Inside the Alpha track → **Testers tab**

1. Click **Create email list**
2. Add at least **12 Gmail addresses** of real people who will test the app
3. Send them the opt-in link that Play Console shows
4. Each person must click the link and opt in from their phone

> Google requires 12 testers actively opted-in before production is unlocked.

- [ ] 12+ testers added to the email list
- [ ] Opt-in link sent to all testers
- [ ] At least 12 testers have opted in (check the counter on the dashboard)

---

### STEP 27 — Create and Publish Closed Testing Release

Play Console → **Testing → Closed testing → Alpha → Releases tab**

1. Click **Create new release**
2. Upload the same `app-release.aab`
3. Release name: `1.0.0`
4. Click **Next** → **Save and publish**

- [ ] Closed testing release published

---

### STEP 28 — Send Release to Google for Review

After publishing the closed test release:

1. Click **Send for review**
2. Google will review — usually 1–3 days
3. You will receive an email when approved

- [ ] Submitted for Google review
- [ ] Review passed

---

### STEP 29 — Run Closed Test for 14 Days

After Google approves, your testers can install from the Play Store and use the app normally.

- [ ] At least 12 testers are opted-in
- [ ] Closed test has been running for at least **14 days**

> The dashboard shows a live counter. You cannot apply for production until both boxes are met.

---

## ── PHASE 6: PRODUCTION (dashboard section 4) ───────────────────

### STEP 30 — Apply for Production Access

Once the 14-day / 12-tester requirement is met, the **"Apply for production access"** button on the dashboard becomes active.

1. Play Console → **Production → Apply for production access**
2. Answer any final questions
3. Submit — Google approves within a few days

- [ ] Applied for production access
- [ ] Google approved

---

### STEP 31 — Publish to Production

After approval:

1. Play Console → **Production → Create new release**
2. Upload `app-release.aab`
3. Click **Next**
4. Set rollout to **20%** (safer than 100% on day one)
5. Click **Start rollout to production**

- [ ] Published at 20% rollout
- [ ] Monitor **Android vitals** for crashes over the first few days
- [ ] Increase rollout to 100% once stable

---

## Quick Reference — All Asset File Locations

```
app/src/main/assets/
  cats/
    miso/     entrance.mp4   idle.mp4   outro.mp4   poster.webp
    yuki/     entrance.mp4   idle.mp4   outro.mp4   poster.webp
    mocha/    entrance.mp4   idle.mp4   outro.mp4   poster.webp
    shadow/   entrance.mp4   idle.mp4   outro.mp4   poster.webp
    biscuit/  entrance.mp4   idle.mp4   outro.mp4   poster.webp
  sounds/
    greeting.mp3
    purr.mp3

pawsup-release.jks  ← outside the project folder, never in Git
```
