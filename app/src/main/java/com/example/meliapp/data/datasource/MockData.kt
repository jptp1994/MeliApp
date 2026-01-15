package com.example.meliapp.data.datasource

import com.example.meliapp.data.dto.*

object MockData {
    val searchResults = listOf(
        ItemDto(
            id = "MOCK-1",
            title = "Samsung Galaxy S23 Ultra 5G 256 GB Phantom Black (Mock)",
            price = 1200.0,
            currencyID = "USD",
            images = listOf("https://http2.mlstatic.com/D_NQ_NP_813158-MLA54950942699_042023-V.jpg"),
            condition = "new",
            availableQuantity = 10,
            brand = "Samsung",
            status = "active",
            category = "MLA-CELLPHONES",
            quality = "standard",
            priority = "high",
            createdAt = "2023-01-01",
            lastUpdated = "2023-02-01",
            keywords = "Samsung, S23, Ultra",
            hasVariations = true
        ),
        ItemDto(
            id = "MOCK-2",
            title = "Sony PlayStation 5 Digital Edition (Mock)",
            price = 450.0,
            currencyID = "USD",
            images = listOf("https://http2.mlstatic.com/D_NQ_NP_843657-MLA45330365023_032021-V.jpg"),
            condition = "new",
            availableQuantity = 5,
            brand = "Sony",
            status = "active",
            category = "MLA-CONSOLES",
            quality = "high",
            priority = "medium",
            createdAt = "2023-01-15",
            lastUpdated = "2023-03-01",
            keywords = "Sony, PS5, Console",
            hasVariations = false
        ),
        ItemDto(
            id = "MOCK-3",
            title = "Apple iPhone 14 Pro Max 128 GB (Mock)",
            price = 1100.0,
            currencyID = "USD",
            images = listOf("https://http2.mlstatic.com/D_NQ_NP_969420-MLA51368962638_092022-V.jpg"),
            condition = "used",
            availableQuantity = 1,
            brand = "Apple",
            status = "paused",
            category = "MLA-CELLPHONES",
            quality = "premium",
            priority = "low",
            createdAt = "2022-09-01",
            lastUpdated = "2023-01-01",
            keywords = "Apple, iPhone, 14 Pro Max",
            hasVariations = true
        )
    )

    fun getItemDetail(itemId: String): ItemDetailDto {
        return ItemDetailDto(
            id = itemId,
            title = if (itemId == "MOCK-1") "Samsung Galaxy S23 Ultra 5G (Mock Detail)" else "Producto Mock Detalle",
            price = 1200.0,
            condition = "new",
            pictures = listOf(
                PictureDto("https://http2.mlstatic.com/D_NQ_NP_813158-MLA54950942699_042023-V.jpg"),
                PictureDto("https://http2.mlstatic.com/D_NQ_NP_688673-MLA54950942701_042023-V.jpg")
            ),
            availableQuantity = 10,
            attributes = listOf(
                AttributeDto("BRAND", "Marca Samsung"),
                AttributeDto("MODEL", "Modelo S23 Ultra"),
                AttributeDto("COLOR", "Color Phantom Black")
            ),
            warranty = "Garantía de fábrica: 12 meses",
            currencyId = "USD",
            soldQuantity = 123,
        )
    }
}
