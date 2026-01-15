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
- **Dependency Injection**: Hilt
- **Networking**: Retrofit + OkHttp + Moshi
- **Local Database**: Room (SQLite)
- **State Management**: StateFlow + UiState
- **Navigation**: Navigation Compose
- **Testing**: JUnit + MockK + Turbine

## Project Structure

```
app/src/main/java/com/example/meliapp/
├── data/
│   ├── api/          # Retrofit API interfaces
│   ├── datasource/   # Remote and Local data sources
│   ├── dto/          # Data Transfer Objects
│   ├── local/        # Room Database and DAOs
│   └── repository/   # Repository implementations
├── di/               # Hilt Dependency Injection modules
├── domain/
│   ├── model/        # Domain models
│   ├── usecase/      # Business logic use cases
│   └── mapper/       # DTO to Domain mappers
└── presentation/
    ├── viewmodel/    # ViewModels
    └── ui/           # Compose screens and theme
```

## API Integration

This app integrates with Mercado Libre's public API:
- Search endpoint: `GET /sites/MLA/search?q={query}`
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

## Development

This project follows OpenSpec methodology for structured development and was built with assistance from Kilo Code AI and Trae AI.

## License

This project is for educational purposes.