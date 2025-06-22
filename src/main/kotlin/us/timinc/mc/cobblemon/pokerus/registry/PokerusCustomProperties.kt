package us.timinc.mc.cobblemon.pokerus.registry

import com.cobblemon.mod.common.api.properties.CustomPokemonProperty
import us.timinc.mc.cobblemon.pokerus.api.cobblemon.pokemon.customproperties.PersistentBooleanCustomPropertyType
import us.timinc.mc.cobblemon.pokerus.api.cobblemon.pokemon.customproperties.PersistentIntegerCustomPropertyType

object PokerusCustomProperties {
    val POKERUS = PersistentBooleanCustomPropertyType("pokerus")
    val HAD_POKERUS = PersistentBooleanCustomPropertyType("had_pokerus")
    val POKERUS_X = PersistentIntegerCustomPropertyType("pokerus_x", 0..255)
    val POKERUS_Y = PersistentIntegerCustomPropertyType("pokerus_y", 0..255)

    fun register() {
        CustomPokemonProperty.register(POKERUS)
        CustomPokemonProperty.register(HAD_POKERUS)
        CustomPokemonProperty.register(POKERUS_X)
        CustomPokemonProperty.register(POKERUS_Y)
    }
}