package com.example.meliapp.data.repository

import com.example.meliapp.data.dto.ItemDto
import com.example.meliapp.domain.model.SearchSource

data class SearchItemsResult(
    val items: List<ItemDto>,
    val source: SearchSource
)

