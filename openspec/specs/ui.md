# UI & UX

## UI Stack
- Jetpack Compose
- Material 3
- Navigation Compose
- WindowSizeClass
- Compose Preview

## Pantallas
1. Search screen
2. Results list screen
3. Product detail screen

## UX States
- Loading
- Error (retry)
- Empty
- Success

## Text Handling

- NO hardcoded strings are allowed in Composables.
- All user-visible text MUST come from:
    - string resources
    - stringResource(id = ...)
    - 
## Reglas
- No lógica de negocio en Composables
- ViewModels livianos

## Dimensions

- No hardcoded dp values in Composables.
- All spacing, sizes and paddings MUST be defined in a central file.
- Use a design system approach (e.g. Spacing, Dimensions object).

