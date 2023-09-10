package com.example.repository

import com.example.model.AppState
import com.example.model.data.DataModel
import com.example.model.data.Meanings
import com.example.repository.room.HistoryEntity

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<DataModel> {
    val searchResult = ArrayList<DataModel>()

    if (list.isNotEmpty()) {
        for (entity in list) {
            val meanings = listOf(
                Meanings(
                    com.example.model.data.Translation(
                        entity.description
                    ), entity.imageUrl
                )
            )

            searchResult.add(DataModel(entity.word, meanings))
        }
    }

    return searchResult
}

fun convertDataModelSuccessToEntity(appState: AppState): HistoryEntity? {
    return when (appState) {
        is AppState.Success -> {
            val searchResult = appState.data

            if (searchResult.isNullOrEmpty() || searchResult[0].text.isNullOrEmpty()) {
                null
            } else {
                HistoryEntity(
                    searchResult[0].text!!,
                    convertMeaningsToString(searchResult[0].meanings!!),
                    searchResult[0].meanings?.get(0)?.imageUrl
                )
            }
        }
        else -> null
    }
}

fun parseLocalSearchResults(appState: AppState): AppState {
    return AppState.Success(mapResult(appState, false))
}

fun parseOnlineSearchResults(appState: AppState): AppState {
    return AppState.Success(mapResult(appState, true))
}

private fun mapResult(
    appState: AppState,
    isOnline: Boolean
): List<DataModel> {
    val newSearchResults = arrayListOf<DataModel>()

    when (appState) {
        is AppState.Success -> {
            getSuccessResultData(appState, isOnline, newSearchResults)
        }
        else -> {
            // Nothing to do
        }
    }

    return newSearchResults
}

private fun getSuccessResultData(
    appState: AppState.Success,
    isOnline: Boolean,
    newDataModels: ArrayList<DataModel>
) {
    val dataModels: List<DataModel> =
        appState.data as List<DataModel>
    if (dataModels.isNotEmpty()) {
        if (isOnline) {
            for (searchResult in dataModels) {
                parseOnlineResult(searchResult, newDataModels)
            }
        } else {
            for (searchResult in dataModels) {
                newDataModels.add(
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
    dataModel: DataModel,
    newDataModels: ArrayList<DataModel>
) {
    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()

        dataModel.meanings?.let {
            for (meaning in dataModel.meanings!!) {
                if (meaning.translation != null && !meaning.translation!!.translation.isNullOrBlank()) {
                    newMeanings.add(
                        Meanings(
                            meaning.translation,
                            meaning.imageUrl
                        )
                    )
                }
            }
        }

        if (newMeanings.isNotEmpty()) {
            newDataModels.add(DataModel(dataModel.text, newMeanings))
        }
    }
}

fun parseSearchResults(state: AppState): AppState {
    val newSearchResults = arrayListOf<DataModel>()
    when (state) {
        is AppState.Success -> {
            val searchResults = state.data
            if (!searchResults.isNullOrEmpty()) {
                for (searchResult in searchResults) {
                    parseResult(searchResult, newSearchResults)
                }
            }
        }
        is AppState.Error -> state.error
        is AppState.Loading -> state.progress
    }

    return AppState.Success(newSearchResults)
}

private fun parseResult(
    dataModel: DataModel,
    newDataModels: ArrayList<DataModel>
) {
    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()

        dataModel.meanings?.let {
            for (meaning in dataModel.meanings!!) {
                if (meaning.translation != null && !meaning.translation!!.translation.isNullOrBlank()) {
                    newMeanings.add(
                        Meanings(
                            meaning.translation,
                            meaning.imageUrl
                        )
                    )
                }
            }
        }

        if (newMeanings.isNotEmpty()) {
            newDataModels.add(DataModel(dataModel.text, newMeanings))
        }
    }
}

fun convertMeaningsToString(meanings: List<Meanings>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation?.translation, ", ")
        } else {
            meaning.translation?.translation
        }
    }

    return meaningsSeparatedByComma
}