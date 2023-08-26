package com.example.database.mushroom

import com.example.routing.mushroom.update.MushroomUpdateReq
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object MushroomTable : Table("mushrooms") {
    private val id = long(name = "id").autoIncrement()
    private val lat = double(name = "lat")
    private val lon = double(name = "lon")
    private val name = varchar(name = "name", length = 50)
    private val description = varchar(name = "description", length = 250)
    private val image = varchar(name = "image", length = 250)

    override val primaryKey = PrimaryKey(id)

    fun insertMushroom(mushroom: MushroomDTO) {
        transaction {
            MushroomTable.insert {
                it[lat] = mushroom.lat
                it[lon] = mushroom.lon
                it[name] = mushroom.name
                it[description] = mushroom.description
                it[image] = mushroom.image.orEmpty()
            }
        }
    }

    fun updateMushroom(mushroom: MushroomUpdateReq) {
        transaction{
            MushroomTable.update(where = { MushroomTable.id eq mushroom.id }) {
                mushroom.lat?.let { lat ->
                    it[MushroomTable.lat] = lat
                }
                mushroom.lon?.let { lon ->
                    it[MushroomTable.lon] = lon
                }
                mushroom.name?.let { name ->
                    it[MushroomTable.name] = name
                }
                mushroom.description?.let { description ->
                    it[MushroomTable.description] = description
                }
                mushroom.image?.let { image ->
                    it[MushroomTable.image] = image
                }
            } > 0
        }
    }

    fun removeMushroom(mushroomId: Long) {
        transaction {
            MushroomTable.deleteWhere { id eq mushroomId } > 0
        }
    }

    fun getAll(): List<MushroomDTO> {
        return transaction {
            MushroomTable.selectAll().map {
                MushroomDTO(
                    id = it[MushroomTable.id],
                    lat = it[lat],
                    lon = it[lon],
                    name = it[name],
                    description = it[description],
                    image = it[image]
                )
            }
        }
    }

    fun getById(id: Long): MushroomDTO {
        return transaction {
            val result = MushroomTable.select { MushroomTable.id.eq(id) }.single()
            MushroomDTO(
                id = result[MushroomTable.id],
                lat = result[lat],
                lon = result[lon],
                name = result[name],
                description = result[description],
                image = result[image]
            )
        }
    }
}