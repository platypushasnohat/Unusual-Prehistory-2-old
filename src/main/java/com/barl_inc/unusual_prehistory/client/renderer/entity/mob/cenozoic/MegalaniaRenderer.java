package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.cenozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.cenozoic.MegalaniaModel;
import com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic.layers.MegalaniaTemperatureLayer;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.Megalania;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class MegalaniaRenderer extends MobRenderer<Megalania, MegalaniaModel> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/megalania/megalania_temperate.png");
    private static final ResourceLocation TEXTURE_COLD = UnusualPrehistory2.modPrefix("textures/entity/mob/megalania/megalania_cold.png");
    private static final ResourceLocation TEXTURE_WARM = UnusualPrehistory2.modPrefix("textures/entity/mob/megalania/megalania_warm.png");
    private static final ResourceLocation TEXTURE_NETHER = UnusualPrehistory2.modPrefix("textures/entity/mob/megalania/megalania_nether.png");
    private static final ResourceLocation TEXTURE_EEPY = UnusualPrehistory2.modPrefix("textures/entity/mob/megalania/megalania_temperate_eepy.png");
    private static final ResourceLocation TEXTURE_COLD_EEPY = UnusualPrehistory2.modPrefix("textures/entity/mob/megalania/megalania_cold_eepy.png");
    private static final ResourceLocation TEXTURE_WARM_EEPY = UnusualPrehistory2.modPrefix("textures/entity/mob/megalania/megalania_warm_eepy.png");
    private static final ResourceLocation TEXTURE_NETHER_EEPY = UnusualPrehistory2.modPrefix("textures/entity/mob/megalania/megalania_nether_eepy.png");

    public MegalaniaRenderer(EntityRendererProvider.Context context) {
        super(context, new MegalaniaModel(context.bakeLayer(UP2ModelLayers.MEGALANIA)), 0.9F);
        this.addLayer(new MegalaniaTemperatureLayer(this));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Megalania entity) {
        return switch (entity.getTemperatureState()) {
            case COLD -> entity.isEepy() ? TEXTURE_COLD_EEPY : TEXTURE_COLD;
            case WARM -> entity.isEepy() ? TEXTURE_WARM_EEPY : TEXTURE_WARM;
            case NETHER -> entity.isEepy() ? TEXTURE_NETHER_EEPY : TEXTURE_NETHER;
            default -> entity.isEepy() ? TEXTURE_EEPY : TEXTURE;
        };
    }

    @Override
    protected @Nullable RenderType getRenderType(@NotNull Megalania entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }
}