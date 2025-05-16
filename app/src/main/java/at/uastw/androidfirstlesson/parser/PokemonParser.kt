package at.uastw.androidfirstlesson.parser

import at.uastw.androidfirstlesson.dto.PokemonDto
import at.uastw.androidfirstlesson.dto.PokemonTypeDto
import org.json.JSONObject

class PokemonParser {

    fun parseJson(jsonString: String): PokemonDto {
        val root = JSONObject(jsonString)
        val id = root.optInt("id", 0)
        val name = root.optString("name", "<unknown>")
        val height = root.optInt("height", 0)
        val weight = root.optInt("weight", 0)


        val typesArray = root.optJSONArray("types")
        val typeList = mutableListOf<PokemonTypeDto>()
        for (i in 0 until (typesArray?.length() ?: 0)) {
            val typeOuterObject = typesArray.getJSONObject(i)
            val typeInnerObject = typeOuterObject.getJSONObject("type")
            val typeName = typeInnerObject.optString("name")
            typeList.add(PokemonTypeDto(typeName))
        }
        val dto = PokemonDto(
            id = id,
            name = name,
            height = height,
            weight = weight,
            types = typeList
        )
        return dto
    }

}