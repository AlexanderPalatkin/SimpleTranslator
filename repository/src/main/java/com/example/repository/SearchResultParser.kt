package com.example.repository

import com.example.model.AppState
import com.example.model.data.DataModel
import com.example.model.data.Meaning
import com.example.model.data.TranslatedMeaning
import com.example.model.dto.MeaningsDto
import com.example.model.dto.SearchResultDto
import com.example.model.dto.TranslationDto
import com.example.repository.room.HistoryEntity

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<SearchResultDto> {
    val searchResult = ArrayList<SearchResultDto>()

    if (list.isNotEmpty()) {
        for (entity in list) {
            val meanings = listOf(
                MeaningsDto(
                    TranslationDto(
                        entity.description
                    ), entity.imageUrl
                )
            )

            searchResult.add(SearchResultDto(entity.word, meanings))
        }
    }

    return searchResult
}

fun convertDataModelSuccessToEntity(appState: AppState): HistoryEntity? {
    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.data

            if (searchResult.isNullOrEmpty() || searchResult[0].text.isEmpty()) {
                null
            } else {
                HistoryEntity(
                    searchResult[0].text,
                    convertMeaningsToSingleString(searchResult[0].meanings),
                    searchResult[0].meanings[0].imageUrl
                )
            }
        }
        else -> null
    }
}

fun parseLocalSearchResults(data: AppState): AppState {
    return AppState.Success(mapResult(data, false))
}

fun mapSearchResultToResult(searchResults: List<SearchResultDto>): List<DataModel> {
    return searchResults.map { searchResult ->
        var meanings: List<Meaning> = listOf()
        searchResult.meanings?.let {
            meanings = it.map { meaningsDto ->
                Meaning(
                    TranslatedMeaning(meaningsDto.translation?.translation ?: ""),
                    meaningsDto.imageUrl ?: ""
                )
            }
        }
        DataModel(searchResult.text ?: "", meanings)
    }
}

fun parseOnlineSearchResults(data: AppState): AppState {
    return AppState.Success(mapResult(data, true))
}

private fun mapResult(
    data: AppState,
    isOnline: Boolean
): List<DataModel> {
    val newSearchResults = arrayListOf<DataModel>()
    when (data) {
        is AppState.Success -> {
            getSuccessResultData(data, isOnline, newSearchResults)
        }

        else -> {}
    }
    return newSearchResults
}

private fun getSuccessResultData(
    data: AppState.Success,
    isOnline: Boolean,
    newSearchDataModels: ArrayList<DataModel>
) {
    val searchDataModels: List<DataModel> = data.data as List<DataModel>
    if (searchDataModels.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in searchDataModels) {
                parseOnlineResult(searchResult, newSearchDataModels)
            }
        } else {
            for (searchResult in searchDataModels) {
                newSearchDataModels.add(
                    DataModel(
                        searchResult.text,
                        arrayListOf()
                    )
                )
            }
        }
    }
}

private fun parseOnlineResult(
    searchDataModel: DataModel,
    newSearchDataModels: ArrayList<DataModel>
) {
    if (searchDataModel.text.isNotBlank() && searchDataModel.meanings.isNotEmpty()) {
        val newMeanings = arrayListOf<Meaning>()
        newMeanings.addAll(searchDataModel.meanings.filter { it.translatedMeaning.translatedMeaning.isNotBlank() })
        if (newMeanings.isNotEmpty()) {
            newSearchDataModels.add(
                DataModel(
                    searchDataModel.text,
                    newMeanings
                )
            )
        }
    }
}

fun convertMeaningsToSingleString(meanings: List<Meaning>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translatedMeaning.translatedMeaning, ", ")
        } else {
            meaning.translatedMeaning.translatedMeaning
        }
    }
    return meaningsSeparatedByComma
}