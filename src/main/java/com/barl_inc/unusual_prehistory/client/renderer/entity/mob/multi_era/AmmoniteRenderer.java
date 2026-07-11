package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.multi_era;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.multi_era.*;
import com.barl_inc.unusual_prehistory.entity.mob.multi_era.Ammonite;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public class AmmoniteRenderer extends MobRenderer<Ammonite, UP2Model<Ammonite>> {

    private final AmmoniteCrioceratitesModel crioceratitesModel;
    private final AmmoniteHoplitesModel ammoniteHoplitesModel;
    private final AmmoniteNostocerasModel ammoniteNostocerasModel;
    private final AmmonitePinacocerasModel ammonitePinacocerasModel;
    private final AmmoniteTropitesModel ammoniteTropitesModel;

    public AmmoniteRenderer(EntityRendererProvider.Context context) {
        super(context, new AmmoniteCrioceratitesModel(context.bakeLayer(UP2ModelLayers.AMMONITE_CRIOCERATITES)), 0.25F);
        this.crioceratitesModel = new AmmoniteCrioceratitesModel(context.bakeLayer(UP2ModelLayers.AMMONITE_CRIOCERATITES));
        this.ammoniteHoplitesModel = new AmmoniteHoplitesModel(context.bakeLayer(UP2ModelLayers.AMMONITE_HOPLITES));
        this.ammoniteNostocerasModel = new AmmoniteNostocerasModel(context.bakeLayer(UP2ModelLayers.AMMONITE_NOSTOCERAS));
        this.ammonitePinacocerasModel = new AmmonitePinacocerasModel(context.bakeLayer(UP2ModelLayers.AMMONITE_PINACOCERAS));
        this.ammoniteTropitesModel = new AmmoniteTropitesModel(context.bakeLayer(UP2ModelLayers.AMMONITE_TROPITES));
    }

    @Override
    public void render(Ammonite entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        switch (entity.getVariant().getId()) {
            case 1:
                this.model = ammoniteHoplitesModel;
                break;
            case 2:
                this.model = ammoniteNostocerasModel;
                break;
            case 3:
                this.model = ammonitePinacocerasModel;
                break;
            case 4:
                this.model = ammoniteTropitesModel;
                break;
            default:
                this.model = crioceratitesModel;
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Ammonite entity) {
        Ammonite.AmmoniteVariant variant = Ammonite.AmmoniteVariant.byId(entity.getVariant().getId());
        return UnusualPrehistory2.modPrefix("textures/entity/mob/ammonite/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }

    @Override
    @Nullable
    protected RenderType getRenderType(@NotNull Ammonite entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }

    @Override
    protected void setupRotations(@NotNull Ammonite entity, @NotNull PoseStack poseStack, float bob, float yBodyRot, float partialTicks, float scale) {
        super.setupRotations(entity, poseStack, bob, yBodyRot + 180.0F, partialTicks, scale);
    }
}
