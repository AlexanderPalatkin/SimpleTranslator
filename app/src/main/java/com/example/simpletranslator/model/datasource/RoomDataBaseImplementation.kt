package com.example.simpletranslator.model.datasource

import com.example.simpletranslator.model.data.DataModel

class RoomDataBaseImplementation : DataSource<List<DataModel>> {
    override suspend fun getData(word: String): List<DataModel> {
        TODO("not implemented")
    }
}