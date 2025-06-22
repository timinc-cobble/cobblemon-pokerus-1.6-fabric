package us.timinc.mc.cobblemon.pokerus.mixins;

@org.spongepowered.asm.mixin.Mixin(com.cobblemon.mod.common.client.gui.summary.widgets.screens.stats.StatWidget.class)
public class StatWidgetMixin {
    @org.spongepowered.asm.mixin.Final
    @org.spongepowered.asm.mixin.Shadow
    private com.cobblemon.mod.common.pokemon.Pokemon pokemon;

    @org.spongepowered.asm.mixin.injection.Inject(method = "renderWidget", remap = false, at = @org.spongepowered.asm.mixin.injection.At("TAIL"))
    void drawStatus(net.minecraft.client.gui.GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks, org.spongepowered.asm.mixin.injection.callback.CallbackInfo ci) {
        us.timinc.mc.cobblemon.pokerus.mixinhelper.StatWidgetMixinHelper.INSTANCE.drawIcon(context.pose(), pokemon);
    }
}
