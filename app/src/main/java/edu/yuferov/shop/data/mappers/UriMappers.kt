package edu.yuferov.shop.data.mappers

import android.net.Uri
import com.google.gson.*
import java.lang.reflect.Type

class UriSerializer : JsonSerializer<Uri> {
    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ) = JsonPrimitive(src.toString())
}

class UriDeserializer : JsonDeserializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ) = Uri.parse(json!!.asJsonPrimitive!!.asString)
}
