---
name: calme-code-hygiene
description: Keep CalMe Kotlin source imports clean and formatting consistent after code changes. Use for Kotlin edits in this Compose Multiplatform project.
---

# CalMe code hygiene

## Architecture

CalMe uses MVVM with MVI-style state management:

1. Put screen state and UI actions in the screen's ViewModel; expose immutable UI state.
2. Model user actions as intents and reduce them into the next UI state.
3. Keep composables stateless where practical: render state and emit intents. Do not put business logic in reusable components.
4. Keep platform-specific work outside shared UI so the same state and screens work on Android and iOS.

## Dependencies

Declare every external dependency and its version in `libs.versions.toml`. Reference it from a module's `module.yaml` through its `$libs` alias; do not add inline Maven coordinates or versions to `module.yaml`.

After every Kotlin source edit:

1. Run `scripts/ktlint-compose -F` for every changed `.kt` file. This reformats code, optimizes imports, and applies the Jetpack Compose ruleset.
2. Build the affected target before considering the change complete.

Do not reformat generated code, resources, Xcode project files, or unrelated source files.
