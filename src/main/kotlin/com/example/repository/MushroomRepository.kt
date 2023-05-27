package com.example.repository

import com.example.database.mushroom.MushroomDTO
import com.example.database.mushroom.MushroomTable
import com.example.models.Mushroom

object MushroomRepository {

    fun saveMushroom(mushroom: Mushroom) {
        MushroomTable.insertMushroom(mushroom.toMushroomDTO())
    }

    fun removeMushroom(mushroomId: Long) {
        MushroomTable.removeMushroom(mushroomId)
    }

    fun getAll(): List<MushroomDTO> {
        return MushroomTable.getAll()
    }

    fun getById(id: Long): MushroomDTO {
        return MushroomTable.getById(id)
    }
}