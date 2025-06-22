package us.timinc.mc.cobblemon.pokerus.pokemon

import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.network.chat.Component
import us.timinc.mc.cobblemon.pokerus.registry.PokerusCustomProperties.HAD_POKERUS
import us.timinc.mc.cobblemon.pokerus.registry.PokerusCustomProperties.POKERUS
import us.timinc.mc.cobblemon.pokerus.registry.PokerusCustomProperties.POKERUS_X
import us.timinc.mc.cobblemon.pokerus.registry.PokerusCustomProperties.POKERUS_Y

fun Pokemon.hasPokerus() = POKERUS.pokemonMatcher(this, true)
fun Pokemon.neverHadPokerus() = POKERUS.pokemonMatcher(this, false) && HAD_POKERUS.pokemonMatcher(this, false)
fun Pokemon.hasEverHadPokerus() = POKERUS.pokemonMatcher(this, true) || HAD_POKERUS.pokemonMatcher(this, true)
fun Pokemon.setPokerus(x: Int, y: Int) {
    if (!this.hasPokerus()) {
        POKERUS.pokemonApplicator(this, true)
        this.sendOwnerMessage(Component.translatable("pokerus.notifications.pokerus_gained", this.getDisplayName()))
    }
    POKERUS_X.pokemonApplicator(this, x)
    POKERUS_Y.pokemonApplicator(this, y)
    this.updateAspects()
}

fun Pokemon.getPokerus(): Pair<Int, Int>? {
    val x = POKERUS_X.pokemonGetter(this) ?: return null
    val y = POKERUS_Y.pokemonGetter(this) ?: return null

    return x to y
}

fun Pokemon.curePokerus() {
    if (!this.hasPokerus()) return
    this.sendOwnerMessage(Component.translatable("pokerus.notifications.pokerus_cured", this.getDisplayName()))
    POKERUS.pokemonApplicator(this, false)
    HAD_POKERUS.pokemonApplicator(this, true)
    this.updateAspects()
}

fun Pokemon.sendOwnerMessage(msg: Component) {
    this.getOwnerPlayer()?.sendSystemMessage(msg)
}