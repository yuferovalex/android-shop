package edu.yuferov.shop.data.mappers

import com.google.gson.*
import edu.yuferov.shop.domain.Percent
import java.lang.reflect.Type

class PercentSerializer : JsonSerializer<Percent> {
    override fun serialize(
        src: Percent?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement = if (src == null)
        JsonNull.INSTANCE
    else
        JsonPrimitive(src.value)
}

class PercentDeserializer : JsonDeserializer<Percent> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Percent = Percent(json!!.asJsonPrimitive!!.asDouble)
}
