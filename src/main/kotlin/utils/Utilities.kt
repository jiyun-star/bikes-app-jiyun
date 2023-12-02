package utils

import models.Bike
import models.Member

object Utilities {

    @JvmStatic
    fun formatListString(membersToFormat: List<Member>): String =
        membersToFormat
            .joinToString(separator = "\n") { member -> "$member" }

    @JvmStatic
    fun formatSetString(bikesToFormat: Set<Bike>): String =
        bikesToFormat
            .joinToString(separator = "\n") { bike -> "\t$bike" }
}
