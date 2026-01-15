# MeliApp

A modern Android application for searching and browsing products from Mercado Libre's public API.

## Features

- **Product Search**: Search for products by text query
- **Product List**: Browse search results in a clean list
- **Product Details**: View detailed information about a product
- **Modern UI**: Built with Jetpack Compose and Material 3
- **Clean Architecture**: Organized with MVVM and Clean Architecture principles

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose + Material 3
- **Architecture**: Clean Architecture (Presentation, Domain, Data)
- **Networking**: Retrofit + OkHttp + Moshi
- **State Management**: StateFlow + UiState
- **Navigation**: Navigation Compose
- **Testing**: JUnit + MockK + Turbine

## Project Structure

```
app/src/main/java/com/example/meliapp/
├── data/
│   ├── api/          # Retrofit API interfaces
│   ├── dto/          # Data Transfer Objects
│   ├── repository/   # Repository implementations
│   └── exception/    # Custom exceptions
├── domain/
│   ├── model/        # Domain models
│   ├── usecase/      # Business logic use cases
│   └── mapper/       # DTO to Domain mappers
└── presentation/
    ├── ui/
    │   ├── common/   # Shared UI components
    │   ├── screen/   # Compose screens
    │   ├── theme/    # Theming
    │   └── navigation/ # Navigation setup
    └── viewmodel/    # ViewModels
```

## API Integration

This app integrates with Mercado Libre's public API:
- Search endpoint: `GET /sites/{site_id}/search?q={query}`
- Item details: `GET /items/{item_id}`

## Building and Running

1. Clone the repository
2. Open in Android Studio
3. Build and run on device/emulator

## Testing

Run unit tests:
```bash
./gradlew test
```

## CI/CD

GitHub Actions workflow runs on every PR:
- Build
- Unit tests
- Lint

## Requirements

- Android SDK 34+
- Min SDK 28
- Kotlin 2.0+

## License

This project is for educational purposes.