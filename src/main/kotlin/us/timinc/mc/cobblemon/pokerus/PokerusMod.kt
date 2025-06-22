package us.timinc.mc.cobblemon.pokerus

import com.cobblemon.mod.common.api.Priority
import com.cobblemon.mod.common.api.events.CobblemonEvents
import com.cobblemon.mod.common.api.scheduling.afterOnServer
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents
import net.minecraft.resources.ResourceLocation
import us.timinc.mc.cobblemon.pokerus.config.ConfigBuilder
import us.timinc.mc.cobblemon.pokerus.config.PokerusConfig
import us.timinc.mc.cobblemon.pokerus.event.handler.PokerusCureHandler
import us.timinc.mc.cobblemon.pokerus.event.handler.PokerusSpreadHandler
import us.timinc.mc.cobblemon.pokerus.registry.PokerusCustomProperties

object PokerusMod : ModInitializer {
    @Suppress("MemberVisibilityCanBePrivate")
    const val MOD_ID = "pokerus"

    @Suppress("MemberVisibilityCanBePrivate")
    lateinit var config: PokerusConfig

    override fun onInitialize() {
        config = ConfigBuilder.load(PokerusConfig::class.java, MOD_ID)

        ServerLifecycleEvents.END_DATA_PACK_RELOAD.register { _, _, _ ->
            config = ConfigBuilder.load(PokerusConfig::class.java, MOD_ID)
        }

        CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.LOWEST, PokerusSpreadHandler::handle)
        CobblemonEvents.BATTLE_VICTORY.subscribe(Priority.LOWEST, PokerusCureHandler::handle)

        var initialized = false
        ServerLifecycleEvents.SERVER_STARTED.register { evt ->
            if (initialized) return@register
            initialized = true
            afterOnServer(1, evt.overworld()) {
                PokerusCustomProperties.register()
            }
        }
    }

    fun modResource(name: String) = ResourceLocation.fromNamespaceAndPath(MOD_ID, name)
}