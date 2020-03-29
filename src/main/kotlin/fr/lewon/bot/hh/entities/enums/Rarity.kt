package fr.lewon.bot.hh.entities.enums

enum class Rarity(val label: String, val value: Int) {
    STARTING("starting", 0),
    COMMON("common", 1),
    RARE("rare", 2),
    EPIC("epic", 3),
    LEGENDARY("legendary", 4);

    companion object {
        fun getValueByLabel(label: String?): Rarity? {
            for (r in values()) {
                if (r.label == label) {
                    return r
                }
            }
            return null
        }
    }

}