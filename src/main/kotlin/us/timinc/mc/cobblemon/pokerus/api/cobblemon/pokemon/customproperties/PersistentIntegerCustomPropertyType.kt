package us.timinc.mc.cobblemon.pokerus.api.cobblemon.pokemon.customproperties

import com.cobblemon.mod.common.api.properties.CustomPokemonPropertyType
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity
import com.cobblemon.mod.common.pokemon.Pokemon
import com.cobblemon.mod.common.pokemon.properties.IntProperty

open class PersistentIntegerCustomPropertyType(override val keys: Iterable<String>, val range: IntRange) :
    CustomPokemonPropertyType<IntProperty> {
    constructor(key: String, range: IntRange) : this(listOf(key), range)

    override val needsKey: Boolean = true
    override fun examples(): Collection<String> = range.map(Int::toString)

    override fun fromString(value: String?): IntProperty = IntProperty(
        keys.first(),
        value = value?.toInt() ?: 0,
        ::pokemonApplicator,
        ::entityApplicator,
        ::pokemonMatcher,
        ::entityMatcher
    )

    open fun pokemonApplicator(pokemon: Pokemon, value: Int) {
        pokemon.persistentData.putInt(keys.first(), value)
    }

    open fun pokemonMatcher(pokemon: Pokemon, value: Int): Boolean =
        pokemon.persistentData.contains(keys.first()) && (pokemonGetter(pokemon) == value)

    fun pokemonGetter(pokemon: Pokemon): Int? =
        if (pokemon.persistentData.contains(keys.first())) pokemon.persistentData.getInt(keys.first()) else null

    fun entityApplicator(entity: PokemonEntity, value: Int) = pokemonApplicator(entity.pokemon, value)
    fun entityMatcher(entity: PokemonEntity, value: Int) = pokemonMatcher(entity.pokemon, value)
    fun entityGetter(entity: PokemonEntity) = pokemonGetter(entity.pokemon)
}