# Code Documentation Rules

## Deprecated APIs

- Deprecated APIs MUST NOT be used.
- If a deprecated API appears:
    - Replace it with the recommended alternative
    - Or remove it if unnecessary


### ✔️ ADEMÁS
Agrega un task (ver punto tasks más abajo).

---

## 2️⃣ Errores y warnings de Sonar / Lint = 0

### 📍 DÓNDE VA
👉 **Spec de calidad + Task**

📄 `specs/code-quality.md` (mismo archivo anterior)

```md
## Static Analysis

- The project MUST compile with:
  - 0 Lint errors
  - 0 Lint warnings
- SonarLint issues MUST be fixed:
  - Code smells
  - Bugs
  - Deprecated APIs


## Requirements

- Every Kotlin file MUST include a file-level KDoc comment.
- The comment MUST explain:
    - The responsibility of the file
    - The layer it belongs to
    - How it should be used

### Example
```kotlin
/**
 * ProductRepositoryImpl
 *
 * Data layer implementation that fetches products from Mercado Libre API.
 * This class maps remote DTOs into domain models and handles error mapping.
 */

