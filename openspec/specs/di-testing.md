# Dependency Injection & Testing Strategy

## Core Principle
Dependency Injection must allow:
- Easy replacement of implementations
- Tests without Android framework
- Real interaction between layers using fakes

Hilt is considered an infrastructure detail, not a core dependency.

---

## Abstraction Rules

- Domain layer MUST NOT depend on:
    - Hilt
    - Android framework
    - Retrofit
    - Room

- Domain depends only on interfaces.

---

## Repository Design

Each repository MUST depend on abstractions:

- ProductRepository (interface)
    - Implementations:
        - ProductRepositoryImpl (production)
        - FakeProductRepository (tests)

Repositories must NOT:
- Contain Android-specific logic
- Be tightly coupled to Retrofit or Room

---

## Data Sources

Use explicit data sources:

- RemoteDataSource
- LocalDataSource (optional)

Each data source MUST have:
- Real implementation
- Fake implementation for tests

Example:
- ProductRemoteDataSource
- FakeProductRemoteDataSource

---

## Testing Strategy

### Mocks
Use mocks ONLY when:
- Testing edge cases
- Testing error paths
- Verifying interactions

### Fakes
Use fakes when:
- Validating interaction between layers
- Testing business logic end-to-end (domain → data)
- Testing ViewModels with real logic

Fakes MUST:
- Be deterministic
- Run in memory
- Avoid network and disk

---

## ViewModel Testing

ViewModels SHOULD be tested with:
- Real UseCases
- Fake repositories or fake data sources
- Minimal mocking

---

## Hilt Usage

- Hilt modules MUST be thin:
    - Bind interfaces to implementations
    - No business logic

- Tests SHOULD NOT rely on Hilt unless:
    - Instrumentation tests
    - Integration tests

Unit tests SHOULD construct dependencies manually.

---

## Goal

The architecture must allow:
- Replacing production code with fakes in tests
- Testing multiple layers together
- Senior-level test scenarios
