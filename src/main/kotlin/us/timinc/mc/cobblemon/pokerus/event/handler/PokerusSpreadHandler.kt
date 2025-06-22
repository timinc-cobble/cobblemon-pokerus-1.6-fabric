package us.timinc.mc.cobblemon.pokerus.event.handler

import com.cobblemon.mod.common.api.battles.model.actor.ActorType
import com.cobblemon.mod.common.api.events.battles.BattleVictoryEvent
import us.timinc.mc.cobblemon.pokerus.pokerus.PokerusManager

object PokerusSpreadHandler {
    fun handle(evt: BattleVictoryEvent) {
        if (!evt.battle.isPvW) return

        (evt.winners + evt.losers)
            .filter { it.type == ActorType.PLAYER }
            .map { actor -> actor.pokemonList.map { it.effectedPokemon } }
            .forEach(PokerusManager::affectTeamPostBattle)
    }
}