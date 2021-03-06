package com.raydev.shared.mapper

import com.raydev.shared.entity.AyatEntity
import com.raydev.shared.entity.SurahEntity
import com.raydev.shared.model.Ayat
import com.raydev.shared.model.Surah

object AyatMapper {
    fun mapResponseToEntities(input: List<Ayat>): List<AyatEntity>{
        val data = ArrayList<AyatEntity>()
        input.map {
            val surah = AyatEntity(
                id = it.indo,
                nomor = it.nomor,
                ar = it.arab,
            )
            data.add(surah)
        }
        return data
    }

    fun mapEntitiesToDomain(input: List<AyatEntity>): List<Ayat> =
        input.map {
            Ayat(
                nomor = it.nomor,
                indo = it.id,
                arab = it.ar,
            )
        }

    fun mapDomainToEntity(input: Ayat) = AyatEntity(
        nomor = input.nomor,
        id = input.indo,
        ar = input.arab,
    )
}