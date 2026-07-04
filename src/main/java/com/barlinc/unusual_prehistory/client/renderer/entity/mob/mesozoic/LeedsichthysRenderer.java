package com.barlinc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic.LeedsichthysBabyModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic.LeedsichthysModel;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Leedsichthys;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class LeedsichthysRenderer extends MobRenderer<Leedsichthys, UP2Model<Leedsichthys>> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/leedsichthys/leedsichthys.png");
    private static final ResourceLocation TEXTURE_BABY = UnusualPrehistory2.modPrefix("textures/entity/mob/leedsichthys/leedsichthys_baby.png");

    private final LeedsichthysModel adultModel;
    private final LeedsichthysBabyModel babyModel;

    public LeedsichthysRenderer(EntityRendererProvider.Context context) {
        super(context, new LeedsichthysModel(context.bakeLayer(UP2ModelLayers.LEEDSICHTHYS)), 1.0F);
        this.adultModel = new LeedsichthysModel(context.bakeLayer(UP2ModelLayers.LEEDSICHTHYS));
        this.babyModel = new LeedsichthysBabyModel(context.bakeLayer(UP2ModelLayers.LEEDSICHTHYS_BABY));
    }

    @Override
    public void render(Leedsichthys entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        this.model = entity.isBaby() ? babyModel : adultModel;
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Leedsichthys entity) {
        return entity.isBaby() ? TEXTURE_BABY : TEXTURE;
    }
}
