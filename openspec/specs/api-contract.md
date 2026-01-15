# API Contract – Mercado Libre

## Official Documentation
https://developers.mercadolibre.com.ar/es_ar/items-y-busquedas

---

## Allowed Endpoints

### 1. Search products (Catalog API)
GET https://api.mercadolibre.com/products/search?q={query}&status=active&site_id={site_id}

#### Request
- q: String
- status: String (default "active")
- site_id: String (use "MLA" by default)
- limit: Int (default 20)
- offset: Int (default 0)

❌ DO NOT use:
- advanced filters
- sorting
- categories
- seller filters

#### Response (use ONLY)
- id
- name (mapped to title)
- pictures[].url (first one as thumbnail)

Note: Catalog API does not provide price, currency, condition, or available_quantity. These are set to default/empty values in the app.

---

### 2. Item detail
GET https://api.mercadolibre.com/items/{item_id}

#### Request
- item_id: String

❌ DO NOT use additional parameters

#### Response (use ONLY)
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

## Strict Rules
- DO NOT map the entire API
- DTOs only with fields defined above
- Domain decoupled from the API
