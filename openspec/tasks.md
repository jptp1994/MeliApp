# Tasks

## Task 1 – Project setup
- [x] Create Android project
- [x] Gradle Kotlin DSL
- [x] Version Catalog
- [x] Clean Architecture structure
- [x] Verify all dependencies use latest stable versions
- [x] Add Chucker interceptor for debug builds only

## Task 2 – Networking
- [x] Retrofit interfaces
- [x] OkHttp
- [x] DTOs according to api-contract
- [x] Centralized error handling

## Task 3 – Domain
- [x] Domain models
- [x] UseCases
- [x] Mappers DTO → Domain

## Task 4 – Presentation
- [x] ViewModels
- [x] UiState
- [x] Screens Compose
- [x] Navigation Compose

## Task 5 – Testing
- [x] Unit tests for UseCases
- [x] Unit tests for ViewModels

## Task 6 – CI & Docs
- [ ] GitHub Actions
- [x] Professional README

## Task 7 – Code Quality
- [x] Fix all Android Lint warnings and errors
- [ ] Resolve SonarLint issues (e.g., remove unused Room files causing compilation errors)
- [x] Move all hardcoded UI strings to strings.xml
- [x] Replace all deprecated APIs
- [x] Improve unit test coverage across all layers
- [x] Add Compose UI tests for main flows
- [x] Extract all hardcoded dimensions into a centralized design system

## Task 8– Dependency Injection & Testing Improvements
- [x] Refactor repositories to depend on abstractions only
- [x] Introduce fake implementations for repositories and data sources
- [x] Refactor ViewModel tests to use fakes instead of mocks
- [x] Reduce Hilt usage in unit tests
- [x] Keep Hilt limited to production and instrumentation

