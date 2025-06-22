package us.timinc.mc.cobblemon.pokerus.pokerus

import com.cobblemon.mod.common.api.pokemon.stats.EvCalculator
import com.cobblemon.mod.common.api.pokemon.stats.Stat
import com.cobblemon.mod.common.api.pokemon.stats.Stats
import com.cobblemon.mod.common.api.tags.CobblemonItemTags
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon
import us.timinc.mc.cobblemon.pokerus.pokemon.hasPokerus

object PokerusEvCalculator : EvCalculator {
    private val powerItems = mapOf(
        Stats.SPEED to CobblemonItemTags.POWER_ANKLET,
        Stats.SPECIAL_DEFENCE to CobblemonItemTags.POWER_BAND,
        Stats.DEFENCE to CobblemonItemTags.POWER_BELT,
        Stats.ATTACK to CobblemonItemTags.POWER_BRACER,
        Stats.SPECIAL_ATTACK to CobblemonItemTags.POWER_LENS,
        Stats.HP to CobblemonItemTags.POWER_WEIGHT
    )

    override fun calculate(battlePokemon: BattlePokemon, opponentPokemon: BattlePokemon): Map<Stat, Int> {
        val heldItem = battlePokemon.effectedPokemon.heldItem()
        val pokerusBoost = if (battlePokemon.effectedPokemon.hasPokerus()) 2 else 1
        val evYield = mutableMapOf<Stat, Int>()

        for ((stat, value) in opponentPokemon.originalPokemon.form.evYield) {
            val heldItemBoost = if (!heldItem.isEmpty && powerItems[stat]?.let { heldItem.`is`(it) } == true) 8 else 0
            evYield[stat] = evYield.getOrDefault(stat, 0) + value * pokerusBoost + heldItemBoost
        }
        return evYield
    }

}