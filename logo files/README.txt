PawsUp App Icon v2 — "Paw + Pause"
====================================

THE CONCEPT
-----------
A single bold symbol where a paw print silhouette doubles as a pause 
symbol (⏸). The two cream pads in the center read as both:
- The two paw pads of a cat
- The two bars of a universal pause icon

Above them: four toe beans confirming the paw reading.
Result: one symbol that says "cat" and "pause" simultaneously.

COLORS
------
Background: #2B1F2E  (warm dark plum — same as your in-app overlay bg)
Symbol:     #F5ECD7  (soft cream — same as your in-app dialogue text)

These match the colors already used inside the app, so the brand
identity is consistent from icon → break overlay → typography.


WHERE EACH FILE GOES
--------------------

INTO YOUR ANDROID PROJECT (app/src/main/):

  res/mipmap-mdpi/ic_launcher.png + ic_launcher_round.png       (48px)
  res/mipmap-hdpi/ic_launcher.png + ic_launcher_round.png       (72px)
  res/mipmap-xhdpi/ic_launcher.png + ic_launcher_round.png      (96px)
  res/mipmap-xxhdpi/ic_launcher.png + ic_launcher_round.png    (144px)
  res/mipmap-xxxhdpi/ic_launcher.png + ic_launcher_round.png   (192px)
  res/mipmap-xxxhdpi/ic_launcher_foreground.png   (1024×1024 adaptive)
  res/mipmap-anydpi-v26/ic_launcher.xml          (adaptive icon def)
  res/mipmap-anydpi-v26/ic_launcher_round.xml
  res/values/ic_launcher_background.xml          (the plum color)

INTO PLAY CONSOLE:

  play-store-listing/icon-512.png            → Store listing → App icon
  play-store-listing/feature-graphic-1024x500.png → Feature graphic


INSTALL STEPS
-------------
1. In Android Studio, switch Project view to "Project" mode 
   (top left dropdown)
2. Navigate to app/src/main/res/
3. Delete existing mipmap-* folders if they have the old icon
4. Copy the contents of this folder's res/ into app/src/main/res/
5. Verify res/values/colors.xml has the line:
     <color name="ic_launcher_background">#2B1F2E</color>
   (if it's not there, the standalone file I included will add it)
6. Build → Clean Project, then Build → Rebuild Project
7. Install on phone — if old icon still shows, uninstall first
