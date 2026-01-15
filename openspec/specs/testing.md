# Testing

## Unit Tests (obligatorios)
- UseCases
- ViewModels
- Mappers

## Tools
- JUnit
- MockK
- Turbine
- Coroutine Test

❌ NO implementar tests End-to-End
UI tests solo si es trivial

## Testing Requirements

### Mandatory
- Unit tests for:
    - UseCases
    - Repositories
    - ViewModels
    - Mappers

- UI tests for:
    - Main user flows (search, list, detail)

### Coverage
- Target coverage:
    - Domain: ~100%
    - ViewModels: high coverage
- No business logic without tests

## Testing Levels

### Unit Tests
- Domain logic tested with fakes
- No Android dependencies
- No Hilt required

### Integration-like Tests
- ViewModel + UseCase + FakeRepository
- No mocks unless strictly necessary

### Instrumentation Tests
- Optional
- Use Hilt only when needed
