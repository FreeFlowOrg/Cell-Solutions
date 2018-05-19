package org.freeflow.cellsolutions.models

data class Report(
        var company: String= "",
        var model: String= "",
        var description: String = "",
        var submissionTimestamp: Long = 0L,
        var isResolved: Boolean = false,
        var resolvedTimestamp: Long = 0L
)