# CalMe-Demo

CalMe-Demo is a meal logging and health goal tracking app for Android and iOS. It shares its UI and application logic through Kotlin Multiplatform and Compose Multiplatform.

## Current features

- **Today:** View logged calories and protein; add, edit, and delete meals.
- **Reports:** Switch between daily, weekly, and monthly views.
- **Goals:** Enter weight, height, calorie, and protein goals, then view an updated BMI assessment.
- **Adaptive layout:** Use `WindowSizeClass` to choose navigation and page layouts for phones, tablets, and foldables.

> Data currently lives in ViewModels and resets when the app is closed. Reports display sample values, and the notification switch does not schedule notifications. Goals entered on the Goals page do not yet change the calculations on the Today page.

## Project structure

| Path | Purpose |
| --- | --- |
| `androidApp/` | Android app entry point and resources |
| `iosApp/` | iOS app entry point and Xcode project |
| `shared/src/` | Shared UI, ViewModels, state, and logic |
| `shared/src/screen/` | Today, Reports, and Goals screens |
| `shared/src/component/` | Shared components and `BaseNavDisplay` |
| `shared/src/theme/` | App colors and typography |
| `shared/composeResources/` | Fonts and icons |

The project uses [Kotlin Toolchain (Amper)](https://kotlin-toolchain.org/dev/) for module and dependency configuration in `project.yaml`, `*/module.yaml`, and `libs.versions.toml`. It uses Navigation 3, Material 3 Adaptive, Koin, Noto Sans Thai, and DM Sans.

The Android application ID and iOS bundle ID are both `com.eb1xnr007.calme.demo`.

## Getting started

Install the Android SDK to build Android and Xcode to build iOS. The `kotlin` script in the project root bootstraps the pinned Kotlin Toolchain on first use.

```sh
cd CalMe-Demo
./kotlin build -m androidApp
./kotlin run -m androidApp
```

On macOS, point `DEVELOPER_DIR` at the full Xcode installation when building or running iOS, especially if `xcode-select` points to Command Line Tools:

```sh
DEVELOPER_DIR=/Applications/Xcode.app/Contents/Developer ./kotlin build -m iosApp
DEVELOPER_DIR=/Applications/Xcode.app/Contents/Developer ./kotlin run -m iosApp
```

You can also open the project in Android Studio and use its run configurations.

## Build checks

```sh
./kotlin build -m androidApp
DEVELOPER_DIR=/Applications/Xcode.app/Contents/Developer ./kotlin build -m iosApp
```

Both targets have been built successfully on macOS. The foldable layout has not yet been tested on physical hardware.
