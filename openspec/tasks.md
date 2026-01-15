# Tasks

## Task 1 – Project setup
- Crear proyecto Android
- Gradle Kotlin DSL
- Version Catalog
- Estructura Clean Architecture
- [ ] Verify all dependencies use latest stable versions
- [ ] Add Chucker interceptor for debug builds only
## Task 2 – Networking
- Retrofit interfaces
- OkHttp
- DTOs según api-contract
- Manejo centralizado de errores

## Task 3 – Domain
- Domain models
- UseCases
- Mappers DTO → Domain

## Task 4 – Presentation
- ViewModels
- UiState
- Screens Compose
- Navigation Compose

## Task 5 – Testing
- Unit tests de UseCases
- Unit tests de ViewModels

## Task 6 – CI & Docs
- GitHub Actions
- README profesional
- 
## Task X – Code Quality
- [ ] Fix all Android Lint warnings and errors
- [ ] Resolve SonarLint issues
- [ ] Move all hardcoded UI strings to strings.xml
- [ ] Replace all deprecated APIs
- [ ] Improve unit test coverage across all layers
- [ ] Add Compose UI tests for main flows
- [ ] Extract all hardcoded dimensions into a centralized design system

## Task – Dependency Injection & Testing Improvements

- [ ] Refactor repositories to depend on abstractions only
- [ ] Introduce fake implementations for repositories and data sources
- [ ] Refactor ViewModel tests to use fakes instead of mocks
- [ ] Reduce Hilt usage in unit tests
- [ ] Keep Hilt limited to production and instrumentation
