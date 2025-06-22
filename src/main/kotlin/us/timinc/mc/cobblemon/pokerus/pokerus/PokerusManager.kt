package us.timinc.mc.cobblemon.pokerus.pokerus

import com.cobblemon.mod.common.pokemon.Pokemon
import net.minecraft.stats.Stats
import us.timinc.mc.cobblemon.pokerus.PokerusMod.config
import us.timinc.mc.cobblemon.pokerus.pokemon.*
import us.timinc.mc.cobblemon.pokerus.registry.PokerusPersistentProperties.LAST_CHECKED_DAY
import kotlin.math.floor
import kotlin.random.Random

object PokerusManager {
    fun affectTeamPostBattle(party: List<Pokemon>) {
        if (party.isEmpty()) return

        val infectedIndexes = party.withIndex()
            .filter { it.value.hasPokerus() }
            .map { it.index }

        tryInfectRandomPartyMember(party)
        for (index in infectedIndexes) {
            trySpreadFromIndex(index, party)
        }
    }

    private fun tryInfectRandomPartyMember(party: List<Pokemon>) {
        if (party.any { it.hasEverHadPokerus() } && config.onlyInfectNewIfNobodyOnTeamHas) return

        val chance = Random.nextInt(config.wildInfectionChanceDenominator)
        if (chance >= config.wildInfectionChangeNumerator) return

        val eligibleIndexes = party.indices.filter { party[it].neverHadPokerus() }
        if (eligibleIndexes.isEmpty()) return

        val targetIndex = eligibleIndexes.random()
        infect(party[targetIndex])
    }

    private fun trySpreadFromIndex(index: Int, party: List<Pokemon>) {
        val adjacentOffsets = listOf(-1, 1)

        for (offset in adjacentOffsets) {
            val targetIndex = index + offset
            if (targetIndex !in party.indices) continue

            val target = party[targetIndex]
            val spreadRoll = Random.nextInt(config.chanceToSpread)
            if (target.neverHadPokerus() && spreadRoll == 0) {
                val (x, y) = party[index].getPokerus() ?: continue
                target.setPokerus(x, y)
            }
        }
    }

    private fun infect(pokemon: Pokemon) {
        val x = Random.nextInt(1, 16)
        val y = (x and 0x03) + 1
        pokemon.setPokerus(x, y)
    }

    fun tickDay(party: List<Pokemon>) {
        for (pokemon in party) {
            val lastCheckedDay = LAST_CHECKED_DAY.getFromPokemon(pokemon)
            val currentPlayTime =
                pokemon.getOwnerPlayer()?.stats?.getValue(Stats.CUSTOM.get(Stats.PLAY_TIME)) ?: continue
            val currentPlayDay = floor(currentPlayTime / 2400.0).toInt() + 1
            LAST_CHECKED_DAY.setOnPokemon(pokemon, currentPlayDay)
            if (lastCheckedDay === null || currentPlayDay <= lastCheckedDay) {
                continue
            }

            val (x, y) = pokemon.getPokerus() ?: continue
            if (y > 0) {
                pokemon.setPokerus(x, y - 1)
            } else {
                pokemon.curePokerus()
            }
        }
    }
}
