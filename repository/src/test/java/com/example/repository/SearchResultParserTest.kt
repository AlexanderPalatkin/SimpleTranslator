package com.example.repository

import com.example.repository.room.HistoryEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class SearchResultParserTest {

    @Test
    fun mapHistoryEntityToSearchResult_CheckSize_ReturnEquals() {
        val historyEntities = listOf(
            HistoryEntity("Word1", "Meaning1", "ImageUrl1"),
            HistoryEntity("Word2", "Meaning2", "ImageUrl2")
        )

        val searchResult = mapHistoryEntityToSearchResult(historyEntities)

        assertEquals(historyEntities.size, searchResult.size)
    }

}