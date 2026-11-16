# DisneySolitaireAI

Fresh native Android project for a Klondike solitaire assistant/auto-player.

## Current state (0.1.0)
- Kotlin + Jetpack Compose app shell
- Basic status/settings UI
- Pure Kotlin card model and core Klondike placement predicates
- No API key embedded in source

## Not implemented yet
- MediaProjection live screen capture
- Card/tableau/stock/waste/foundation recognition (OpenCV/OCR)
- Full legal-move generator and BFS/DFS/heuristic solver
- AccessibilityService-based touch execution and floating controls
- Secure DeepSeek API key entry/storage and API requests
- End-screen detection, retry/rotation/resolution handling

The UI intentionally labels these capabilities as not connected. Do not treat this starter as a working auto-player.

## Build
Open this folder in Android Studio (JDK 17), allow Gradle sync, then Build > Build Bundle(s) / APK(s) > Build APK(s).

GitHub Actions also builds a debug APK on push to `main` or `master`.
