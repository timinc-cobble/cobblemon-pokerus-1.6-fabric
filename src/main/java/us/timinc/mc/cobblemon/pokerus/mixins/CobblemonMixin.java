package us.timinc.mc.cobblemon.pokerus.mixins;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.stats.EvCalculator;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import us.timinc.mc.cobblemon.pokerus.pokerus.PokerusEvCalculator;

@Mixin(Cobblemon.class)
public class CobblemonMixin {
    @Inject(
            method = "getEvYieldCalculator",
            at = @At(value = "HEAD"),
            cancellable = true,
            remap = false
    )
    void getPokerusEvYieldCalculator(CallbackInfoReturnable<EvCalculator> cir) {
        cir.setReturnValue(PokerusEvCalculator.INSTANCE);
    }
}
