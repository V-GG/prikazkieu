package com.prikazkieu.app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class JsonAuthorDto(
    val name: String,
    val lived: String? = null,
    val origin: String? = null,
    val image: String? = null,
    val moreInfo: String? = null
)

@Serializable
data class JsonAuthorListDto(
    val authors: List<JsonAuthorDto>
)