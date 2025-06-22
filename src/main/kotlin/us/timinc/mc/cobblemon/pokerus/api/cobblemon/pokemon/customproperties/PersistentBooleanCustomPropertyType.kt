package us.timinc.mc.cobblemon.pokerus.api.cobblemon.pokemon.customproperties

import com.cobblemon.mod.common.api.properties.CustomPokemonPropertyType
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.pokemon.properties.BooleanProperty

open class PersistentBooleanCustomPropertyType(override val keys: Iterable<String>) :
    CustomPokemonPropertyType<BooleanProperty> {
    constructor(key: String) : this(listOf(key))

    override val needsKey: Boolean = true
    override fun examples(): Collection<String> = setOf("yes", "no")

    override fun fromString(value: String?): BooleanProperty = BooleanProperty(
        keys.first(), value == "yes", ::pokemonApplicator, ::entityApplicator, ::pokemonMatcher, ::entityMatcher
    )

    open fun pokemonApplicator(pokemon: Pokemon, value: Boolean) {
        pokemon.persistentData.putBoolean(keys.first(), value)
    }

    open fun pokemonMatcher(pokemon: Pokemon, value: Boolean): Boolean =
        (pokemon.persistentData.contains(keys.first()) && pokemon.persistentData.getBoolean(keys.first())) == value

    fun entityApplicator(entity: PokemonEntity, value: Boolean) = pokemonApplicator(entity.pokemon, value)
    fun entityMatcher(entity: PokemonEntity, value: Boolean) = pokemonMatcher(entity.pokemon, value)
}