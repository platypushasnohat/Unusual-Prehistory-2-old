package com.barlinc.unusual_prehistory.client.renderer.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.UP2Model;
import com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic.TherizinosaurusBabyModel;
import com.barlinc.unusual_prehistory.client.models.entity.mob.mesozoic.TherizinosaurusModel;
import com.barlinc.unusual_prehistory.entity.mob.mesozoic.Therizinosaurus;
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
public class TherizinosaurusRenderer extends MobRenderer<Therizinosaurus, UP2Model<Therizinosaurus>> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/entity/mob/therizinosaurus/therizinosaurus.png");
    private static final ResourceLocation TEXTURE_BABY = UnusualPrehistory2.modPrefix("textures/entity/mob/therizinosaurus/therizinosaurus_baby.png");

    private final TherizinosaurusModel adultModel;
    private final TherizinosaurusBabyModel babyModel;

    public TherizinosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new TherizinosaurusModel(context.bakeLayer(UP2ModelLayers.THERIZINOSAURUS)), 1.5F);
        this.adultModel = new TherizinosaurusModel(context.bakeLayer(UP2ModelLayers.THERIZINOSAURUS));
        this.babyModel = new TherizinosaurusBabyModel(context.bakeLayer(UP2ModelLayers.THERIZINOSAURUS_BABY));
    }

    @Override
    public void render(Therizinosaurus entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        this.model = entity.isBaby() ? babyModel : adultModel;
        super.render(entity, entityYaw, partialTicks, poseStack, bufferSource, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull Therizinosaurus entity) {
        return entity.isBaby() ? TEXTURE_BABY : TEXTURE;
    }
}
