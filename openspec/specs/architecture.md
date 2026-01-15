# Arquitectura

## Stack obligatorio
- Kotlin
- Android SDK 34+
- Gradle Kotlin DSL
- Version Catalog

## Arquitectura
- Clean Architecture
- MVVM

### Capas
- presentation (Compose + ViewModels)
- domain (UseCases + Domain Models)
- data (Repositories + API + DTOs)

### Reglas
- UI no conoce DTOs
- Dominio no conoce Retrofit
- Repositorios ocultan detalles de red

## State Management
- StateFlow
- UiState sellado:
    - Loading
    - Success
    - Error
    - Empty

- collectAsStateWithLifecycle
- Estado sobrevive a rotación

## Debug Networking
- Chucker MUST be included:
  - Enabled ONLY in debug builds
  - Disabled in release builds

