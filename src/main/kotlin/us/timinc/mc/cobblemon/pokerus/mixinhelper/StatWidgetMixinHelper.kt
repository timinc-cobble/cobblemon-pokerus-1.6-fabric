package us.timinc.mc.cobblemon.pokerus.mixinhelper

import com.cobblemon.mod.common.api.gui.blitk
import com.cobblemon.mod.common.pokemon.Pokemon
import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.resources.ResourceLocation
import us.timinc.mc.cobblemon.pokerus.PokerusMod.modResource
import us.timinc.mc.cobblemon.pokerus.registry.PokerusCustomProperties.HAD_POKERUS
import us.timinc.mc.cobblemon.pokerus.registry.PokerusCustomProperties.POKERUS

object StatWidgetMixinHelper {
    var INFECTED_RESOURCE: ResourceLocation = modResource("textures/infected.png")
    var CURED_RESOURCE: ResourceLocation = modResource("textures/cured.png")

    fun drawIcon(matrices: PoseStack, pokemon: Pokemon) {
        val iconToDraw = when {
            POKERUS.pokemonMatcher(pokemon, true) -> INFECTED_RESOURCE
            HAD_POKERUS.pokemonMatcher(pokemon, true) -> CURED_RESOURCE
            else -> return
        }
        blitk(
            matrices,
            iconToDraw,
            213,
            129,
            10,
            10
        )
    }
}