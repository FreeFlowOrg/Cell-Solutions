package org.freeflow.cellsolutions.models

data class Company(
        var name: String = "",
        var models: Map<String, Model>? = null
)

data class Model(
        var name: String = "",
        var description: String = ""
)