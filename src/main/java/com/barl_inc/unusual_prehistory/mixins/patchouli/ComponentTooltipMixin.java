package com.barl_inc.unusual_prehistory.mixins.patchouli;

import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.client.book.template.component.ComponentTooltip;

@Mixin(ComponentTooltip.class)
public class ComponentTooltipMixin {

    @Redirect(method = "onDisplayed", at = @At(value = "INVOKE", target = "Lvazkii/patchouli/api/IVariable;as(Ljava/lang/Class;)Ljava/lang/Object;"))
    private Object unusualPrehistory$translateTooltips(IVariable variable, Class<?> clazz) {
        String text = variable.asString();
        if (text.contains(".")) {
            return Component.translatable(text);
        }
        return variable.as(Component.class);
    }
}