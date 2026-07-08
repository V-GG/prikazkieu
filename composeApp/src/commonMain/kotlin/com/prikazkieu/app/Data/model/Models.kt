package com.prikazkieu.app.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Story(
    val title: String,
    val thumbnail: String,
    val readTimeMinutes: Int,
    val ageGroup: Int,
    val url: String,
    val type: Type?,
    val format: Format,
    val kingdom: String?,
    val author: String?,
    val album: String?,
    val isLatest: Boolean,
) {
    @Serializable
    enum class Type(val displayName: String) {
        FAIRY_TALES("Вълшебни приказки"),
        FOLK_TALES("Битови приказки"),
        MYTHS_AND_LEGENDS("Митове и Легенди"),
        FABLES("Басни и Истории за животни"),
        POEMS_AND_SONGS("Стихове и Песни"),
        RIDDLES("Гатанки и залъгванки"),
        DID_YOU_KNOW("Искам да знам")
    }

    @Serializable
    enum class Format(val displayName: String) {
        text("text"),
        audio("audio"),
        video("video"),
    }
}

data class Kingdom(
    val name: String,
    val image: String?,
    val moreInfo: String? = null,
    val storyCount: Int,
)

data class Author(
    val name: String,
    val image: String?,
    val lived: String?,
    val origin: String?,
    val moreInfo: String? = null,
)

data class Album(
    val name: String,
    val image: String?,
    val description: String,
    val storyCount: Int,
)

enum class Filter(val mask: Int) {
    READ_MINUTES_2(1 shl 0),
    READ_MINUTES_10(1 shl 1),
    READ_MINUTES_30(1 shl 2),
    READ_MINUTES_99(1 shl 3),
    READ_MINUTES_100(1 shl 4),
    STORY_TYPE_FAIRY_TALES(1 shl 5),
    STORY_TYPE_FOLK_TALES(1 shl 6),
    STORY_TYPE_MYTHS_AND_LEGENDS(1 shl 7),
    STORY_TYPE_FABLES(1 shl 8),
    STORY_TYPE_POEMS_AND_SONGS(1 shl 9),
    STORY_TYPE_RIDDLES(1 shl 10),
    STORY_TYPE_DID_YOU_KNOW(1 shl 11),
    STORY_FORMAT_TEXT(1 shl 12),
    STORY_FORMAT_AUDIO(1 shl 13),
    STORY_FORMAT_VIDEO(1 shl 14),
    AGE_GROUP_3(1 shl 15),
    AGE_GROUP_5(1 shl 16),
    AGE_GROUP_9(1 shl 17),
    AGE_GROUP_17(1 shl 18);

    val displayName: String
        get() = when (this) {
            READ_MINUTES_2 -> "Творби до 2 минути"
            READ_MINUTES_10 -> "Творби от 2 до 10 минути"
            READ_MINUTES_30 -> "Творби от 10 до 30 минути"
            READ_MINUTES_99 -> "Творби от 30 до 99 минути"
            READ_MINUTES_100 -> "Творби над 99 минути"
            STORY_TYPE_FAIRY_TALES -> "Вълшебни приказки"
            STORY_TYPE_FOLK_TALES -> "Битови приказки"
            STORY_TYPE_MYTHS_AND_LEGENDS -> "Митове и Легенди"
            STORY_TYPE_FABLES -> "Басни и Истории за животни"
            STORY_TYPE_POEMS_AND_SONGS -> "Стихове и Песни"
            STORY_TYPE_RIDDLES -> "Гатанки и залъгванки"
            STORY_TYPE_DID_YOU_KNOW -> "Искам да знам"
            STORY_FORMAT_TEXT -> "Текст"
            STORY_FORMAT_AUDIO -> "Аудио"
            STORY_FORMAT_VIDEO -> "Видео"
            AGE_GROUP_3 -> "За възраст над 3 г."
            AGE_GROUP_5 -> "За възраст над 5 г."
            AGE_GROUP_9 -> "За възраст над 9 г."
            AGE_GROUP_17 -> "За възраст над 17 г."
        }
}

