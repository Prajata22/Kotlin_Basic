package com.applex.kotlin_basic.utils

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

import java.io.IOException

class IntegerTypeAdapter : TypeAdapter<Int>() {

    @Throws(IOException::class)
    override fun read(reader: JsonReader): Int? {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return null
        }
        val stringValue = reader.nextString()
        return try {
            Integer.valueOf(stringValue)
        } catch (e: NumberFormatException) {
            0
        }
    }

    @Throws(IOException::class)
    override fun write(writer: JsonWriter, value: Int?) {
        if (value == null) {
            writer.nullValue()
            return
        }
        writer.value(value)
    }
}