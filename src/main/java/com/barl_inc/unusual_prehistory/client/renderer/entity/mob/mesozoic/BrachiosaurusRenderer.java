package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.BrachiosaurusBabyModel;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.mesozoic.BrachiosaurusModel;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Brachiosaurus;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class BrachiosaurusRenderer extends MobRenderer<Brachiosaurus, UP2Model<Brachiosaurus>> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/brachiosaurus/brachiosaurus.png");
    private static final ResourceLocation TEXTURE_EEPY = UnusualPrehistory2.modPrefix("textures/entity/mob/brachiosaurus/brachiosaurus_eepy.png");
    private static final ResourceLocation TEXTURE_BABY = UnusualPrehistory2.modPrefix("textures/entity/mob/brachiosaurus/brachiosaurus_baby.png");

    private final BrachiosaurusModel adultModel;
    private final BrachiosaurusBabyModel babyModel;

    public BrachiosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new BrachiosaurusModel(context.bakeLayer(UP2ModelLayers.BRACHIOSAURUS)), 1.8F);
        this.adultModel = new BrachiosaurusModel(context.bakeLayer(UP2ModelLayers.BRACHIOSAURUS));
        this.babyModel = new BrachiosaurusBabyModel(context.bakeLayer(UP2ModelLayers.BRACHIOSAURUS_BABY));
    }

    @Override
    public void render(Brachiosaurus entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        this.model = entity.isBaby() ? babyModel : adultModel;
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Brachiosaurus entity) {
        if (entity.isEepy() && !entity.isBaby()) return TEXTURE_EEPY;
        return entity.isBaby() ? TEXTURE_BABY : TEXTURE;
    }
}
