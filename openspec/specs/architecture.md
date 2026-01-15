# Architecture

## Mandatory Stack
- Kotlin
- Android SDK 34+
- Gradle Kotlin DSL
- Version Catalog

## Architecture
- Clean Architecture
- MVVM

### Layers
- presentation (Compose + ViewModels)
- domain (UseCases + Domain Models)
- data (Repositories + API + DTOs)

### Rules
- UI does not know DTOs
- Domain does not know Retrofit
- Repositories hide network details

## State Management
- StateFlow
- Sealed UiState:
    - Loading
    - Success
    - Error
    - Empty

- collectAsStateWithLifecycle
- State survives rotation

## Debug Networking
- Chucker MUST be included:
  - Enabled ONLY in debug builds
  - Disabled in release builds

