package com.example.meliapp.domain.usecase

import com.example.meliapp.data.dto.AttributeDto
import com.example.meliapp.data.dto.ItemDetailDto
import com.example.meliapp.data.dto.PictureDto
import com.example.meliapp.data.repository.ProductRepository
import com.example.meliapp.domain.model.Attribute
import com.example.meliapp.domain.model.ProductDetail
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetProductDetailUseCaseTest {

    private val repository: ProductRepository = mockk()
    private val useCase = GetProductDetailUseCase(repository)

    @Test
    fun `invoke returns mapped product detail from repository`() = runTest {
        // Given
        val mockItemDetailDto = ItemDetailDto(
            id = "1",
            title = "Product Detail",
            price = 150.0,
            currencyId = "ARS",
            pictures = listOf(PictureDto("url1")),
            condition = "new",
            availableQuantity = 10,
            soldQuantity = 5,
            warranty = "1 year",
            attributes = listOf(AttributeDto("Color", "Red"))
        )
        val expectedProductDetail = ProductDetail(
            id = "1",
            title = "Product Detail",
            price = 150.0,
            currency = "ARS",
            pictures = listOf("url1"),
            condition = "new",
            availableQuantity = 10,
            soldQuantity = 5,
            warranty = "1 year",
            attributes = listOf(Attribute("Color", "Red"))
        )
        coEvery { repository.getItemDetail("1") } returns mockItemDetailDto

        // When
        val result = useCase("1")

        // Then
        assertEquals(expectedProductDetail, result)
    }
}