# Disney Solitaire AI (Android starter project)

This is a buildable Android Studio starter project, not a finished/validated Disney Solitaire bot.
It includes:
- Kotlin Android app and control UI
- MediaProjection consent flow and foreground capture service scaffold
- AccessibilityService scaffold for user-authorized gestures
- Klondike card/state model and legal-move generator
- DeepSeek-compatible API client with key entered in-app (not embedded in source)

## Build
1. Install Android Studio (current stable) and Android SDK 35.
2. Open this folder in Android Studio.
3. Allow Gradle sync.
4. Build > Build Bundle(s) / APK(s) > Build APK(s).

The generated APK will be under `app/build/outputs/apk/debug/`.

## Important limitations
- Disney Solitaire screen layouts, card sprites, animation timing, and game-specific rules have not been calibrated against a real device screenshot.
- Card detection is currently a safe placeholder interface. It does not yet reliably recognize the actual game.
- The solver generates legal Klondike moves but is not a complete win-guaranteeing search engine.
- Accessibility gestures and capture require explicit Android user permission.
- Do not use the previously pasted API key. Revoke it and enter a newly generated key in the app.
- DeepSeek calls are optional; local move generation does not require the API.
