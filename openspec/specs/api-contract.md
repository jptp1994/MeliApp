# API Contract – Mercado Libre

## Documentación oficial
https://developers.mercadolibre.com.ar/es_ar/items-y-busquedas

---

## Endpoints permitidos

### 1. Search products
GET https://api.mercadolibre.com/sites/{site_id}/search?q={query}

#### Request
- site_id: String (usar "MLA" por defecto)
- q: String
- limit: Int (default 20)
- offset: Int (default 0)

❌ NO usar:
- filtros avanzados
- sorting
- categorías
- seller filters

#### Response (usar SOLO)
- id
- title
- price
- currency_id
- thumbnail
- condition
- available_quantity

---

### 2. Item detail
GET https://api.mercadolibre.com/items/{item_id}

#### Request
- item_id: String

❌ NO usar parámetros adicionales

#### Response (usar SOLO)
- id
- title
- price
- currency_id
- pictures[].url
- condition
- available_quantity
- sold_quantity
- warranty
- attributes[].name
- attributes[].value_name

---

## Reglas estrictas
- NO mapear toda la API
- DTOs solo con campos definidos arriba
- Dominio desacoplado de la API
