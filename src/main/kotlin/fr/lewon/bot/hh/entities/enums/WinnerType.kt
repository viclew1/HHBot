package fr.lewon.bot.hh.entities.enums

enum class WinnerType(val strValue: String) {
    HERO("hero"), CHAMPION("champion");

    companion object {
        fun findByStrValue(strValue: String): WinnerType? {
            for (wt in values()) {
                if (wt.strValue == strValue) {
                    return wt
                }
            }
            return null
        }
    }

}