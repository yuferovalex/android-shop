package edu.yuferov.shop.data.mappers

import com.google.gson.*
import edu.yuferov.shop.domain.Price
import java.lang.reflect.Type

class PriceSerializer : JsonSerializer<Price> {
    override fun serialize(
        src: Price?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ) = JsonPrimitive(src?.value)
}

class PriceDeserializer : JsonDeserializer<Price> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ) = Price(json!!.asJsonPrimitive!!.asDouble)
}