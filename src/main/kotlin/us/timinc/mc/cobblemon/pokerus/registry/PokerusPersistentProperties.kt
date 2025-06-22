package us.timinc.mc.cobblemon.pokerus.registry

import us.timinc.mc.cobblemon.pokerus.PokerusMod.modResource
import us.timinc.mc.cobblemon.pokerus.api.cobblemon.pokemon.PersistentProperty

object PokerusPersistentProperties {
    val LAST_CHECKED_DAY = PersistentProperty.Integer(modResource("last_checked_day"))
}