package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.DunkleosteusLargeModel;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.DunkleosteusMediumModel;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.DunkleosteusSmallModel;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Dunkleosteus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class DunkleosteusRenderer extends MobRenderer<Dunkleosteus, HierarchicalModel<Dunkleosteus>> {

    private final DunkleosteusLargeModel dunkleosteusLargeModel;
    private final DunkleosteusMediumModel dunkleosteusMediumModel;
    private final DunkleosteusSmallModel dunkleosteusSmallModel;

    public DunkleosteusRenderer(EntityRendererProvider.Context context) {
        super(context, new DunkleosteusLargeModel(context.bakeLayer(UP2ModelLayers.DUNKLEOSTEUS_LARGE)), 0.5F);
        this.dunkleosteusLargeModel = new DunkleosteusLargeModel(context.bakeLayer(UP2ModelLayers.DUNKLEOSTEUS_LARGE));
        this.dunkleosteusMediumModel = new DunkleosteusMediumModel(context.bakeLayer(UP2ModelLayers.DUNKLEOSTEUS_MEDIUM));
        this.dunkleosteusSmallModel = new DunkleosteusSmallModel(context.bakeLayer(UP2ModelLayers.DUNKLEOSTEUS_SMALL));
    }

    @Override
    public void render(Dunkleosteus entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        switch (entity.getVariant().getId()) {
            case 1:
                this.model = dunkleosteusMediumModel;
                this.shadowRadius = 0.8F;
                break;
            case 2:
                this.model = dunkleosteusLargeModel;
                this.shadowRadius = 1.0F;
                break;
            default:
                this.model = dunkleosteusSmallModel;
                this.shadowRadius = 0.4F;
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Dunkleosteus entity) {
        Dunkleosteus.DunkleosteusVariant variant = Dunkleosteus.DunkleosteusVariant.byId(entity.getVariant().getId());
        return UnusualPrehistory2.modPrefix("textures/entity/mob/dunkleosteus/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }
}
