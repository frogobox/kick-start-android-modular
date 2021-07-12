package com.frogobox.base.util

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * movie
 * Copyright (C) 16/11/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.base.util
 *
 */
class NullAdapter : TypeAdapter<Any>() {
    @Throws(IOException::class)
    override fun write(jsonWriter: JsonWriter, o: Any) {
        jsonWriter.beginObject()
        for (field in o.javaClass.declaredFields) {
            val fieldValue = runGetter(field, o)
            jsonWriter.name(field.name)
            if (fieldValue == null) {
                jsonWriter.value("")
            } else {
                jsonWriter.value(fieldValue.toString())
            }
        }
        jsonWriter.endObject()
    }

    @Throws(IOException::class)
    override fun read(jsonReader: JsonReader): Any? {
        /* Don't forget to add implementation here to have your Object back alive :) */
        return null
    }

    companion object {
        /**
         * A generic field accessor runner.
         * Run the right getter on the field to get its value.
         * @param field
         * @param o `Object`
         * @return
         */
        fun runGetter(field: Field, o: Any): Any? {
            // MZ: Find the correct method
            for (method in o.javaClass.methods) {
                if (method.name.startsWith("get") && method.name.length == field.name.length + 3) {
                    if (method.name.toLowerCase().endsWith(field.name.toLowerCase())) {
                        try {
                            return method.invoke(o)
                        } catch (e: IllegalAccessException) {
                            e.printStackTrace()
                        } catch (e: InvocationTargetException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
            return null
        }
    }
}