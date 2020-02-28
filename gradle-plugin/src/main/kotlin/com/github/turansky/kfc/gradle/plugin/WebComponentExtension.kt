package com.github.turansky.kfc.gradle.plugin

import com.github.turansky.kfc.gradle.plugin.WebComponent.Property
import com.github.turansky.kfc.gradle.plugin.WebComponent.Property.Type
import com.github.turansky.kfc.gradle.plugin.WebComponent.Property.Type.*

open class WebComponentExtension {
    lateinit var id: String
    lateinit var source: String

    private val _properties: MutableList<Property> = mutableListOf()
    private val _events: MutableList<String> = mutableListOf()

    fun property(name: String) {
        addProperty(name, RW)
    }

    fun getter(name: String) {
        addProperty(name, RO)
    }

    fun setter(name: String) {
        addProperty(name, WO)
    }

    private fun addProperty(name: String, type: Type) {
        _properties += Property(name, type)
    }

    fun events(vararg events: String) {
        _events += events
    }

    fun build(): WebComponent =
        WebComponent(
            id = id,
            properties = _properties.toList(),
            events = _events.toList(),
            source = source
        )
}
