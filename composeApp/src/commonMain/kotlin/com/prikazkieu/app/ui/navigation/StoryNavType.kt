package com.prikazkieu.app.ui.navigation

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import com.prikazkieu.app.data.model.Story
import io.ktor.http.decodeURLQueryComponent
import io.ktor.http.encodeURLParameter
import kotlinx.serialization.json.Json

// Story isn't a scalar (String/Int/Boolean/enum), so type-safe Navigation can't auto-derive a
// NavType for it as a route argument - it needs to be told explicitly how to (de)serialize it.
val StoryNavType = object : NavType<Story>(isNullableAllowed = false) {

    override fun put(bundle: SavedState, key: String, value: Story) {
        bundle.write { putString(key, Json.encodeToString(value)) }
    }

    override fun get(bundle: SavedState, key: String): Story? =
        bundle.read { getString(key) }.let { Json.decodeFromString(it) }

    override fun parseValue(value: String): Story =
        Json.decodeFromString(value.decodeURLQueryComponent())

    override fun serializeAsValue(value: Story): String =
        Json.encodeToString(value).encodeURLParameter()
}