package us.timinc.mc.cobblemon.pokerus.api.cobblemon.pokemon

import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.nbt.CompoundTag
import net.minecraft.resources.ResourceLocation

abstract class PersistentProperty<T>(val id: ResourceLocation, val defaultValueGetter: () -> T) {
    fun getKeyName() = id.toString()

    abstract fun getFromPokemon(pokemon: Pokemon): T?
    abstract fun setOnPokemon(pokemon: Pokemon, value: T)
    fun clearFromPokemon(pokemon: Pokemon) {
        pokemon.persistentData.remove(getKeyName())
    }

    fun getFromPokemonOrCreate(pokemon: Pokemon): T = getFromPokemon(pokemon) ?: run {
        val newEntry = defaultValueGetter()
        setOnPokemon(pokemon, newEntry)
        newEntry
    }

    open class Compound(id: ResourceLocation) : PersistentProperty<CompoundTag>(id, ::CompoundTag) {
        override fun getFromPokemon(pokemon: Pokemon): CompoundTag? {
            if (!pokemon.persistentData.contains(getKeyName())) return null
            return pokemon.persistentData.getCompound(getKeyName())
        }

        override fun setOnPokemon(pokemon: Pokemon, value: CompoundTag) {
            pokemon.persistentData.put(getKeyName(), value)
        }
    }

    open class Integer(id: ResourceLocation) : PersistentProperty<Int>(id, { 0 }) {
        override fun getFromPokemon(pokemon: Pokemon): Int? {
            if (!pokemon.persistentData.contains(getKeyName())) return null
            return pokemon.persistentData.getInt(getKeyName())
        }

        override fun setOnPokemon(pokemon: Pokemon, value: Int) {
            pokemon.persistentData.putInt(getKeyName(), value)
        }
    }
}