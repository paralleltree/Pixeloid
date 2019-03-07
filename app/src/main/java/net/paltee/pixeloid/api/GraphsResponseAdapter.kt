package net.paltee.pixeloid.api

import com.squareup.moshi.*
import net.paltee.pixeloid.model.Graph
import java.lang.reflect.Type

class GraphsResponseAdapter(val moshi: Moshi) : JsonAdapter<List<Graph>>() {
    override fun fromJson(reader: JsonReader): List<Graph>? {
        reader.beginObject()
        if (reader.nextName() != "graphs") return null
        reader.beginArray()

        val list = arrayListOf<Graph>()
        val adapter = moshi.adapter(Graph::class.java)
        while (reader.hasNext()) list.add(adapter.fromJson(reader)!!)

        reader.endArray()
        reader.endObject()
        return list
    }

    override fun toJson(writer: JsonWriter, value: List<Graph>?) {
        throw NotImplementedError()
    }

    companion object {
        val FACTORY: Factory = object : Factory {
            override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
                val listType = Types.newParameterizedType(List::class.java, Graph::class.java)
                return if (type == listType) GraphsResponseAdapter(moshi) else null
            }
        }
    }
}
