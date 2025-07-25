package de.stubbe.interlude.model

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object Converter: Route

    @Serializable
    data object History: Route

    @Serializable
    data object Settings: Route

}