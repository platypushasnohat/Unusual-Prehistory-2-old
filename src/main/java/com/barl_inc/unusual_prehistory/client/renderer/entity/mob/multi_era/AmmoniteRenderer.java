package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.multi_era;

import com.barl_inc.unusual_prehistory.client.models.entity.UP2Model;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.multi_era.*;
import com.barl_inc.unusual_prehistory.entity.mob.multi_era.Ammonite;
import com.barl_inc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class AmmoniteRenderer extends MobRenderer<Ammonite, UP2Model<Ammonite>> {

    private final AmmoniteCrioceratitesModel ammoniteCrioceratitesModel;
    private final AmmoniteHoplitesModel ammoniteHoplitesModel;
    private final AmmoniteNostocerasModel ammoniteNostocerasModel;
    private final AmmonitePinacocerasModel ammonitePinacocerasModel;
    private final AmmoniteTropitesModel ammoniteTropitesModel;

    public AmmoniteRenderer(EntityRendererProvider.Context context) {
        super(context, new AmmoniteCrioceratitesModel(context.bakeLayer(UP2ModelLayers.AMMONITE_CRIOCERATITES)), 0.25F);
        this.ammoniteCrioceratitesModel = new AmmoniteCrioceratitesModel(context.bakeLayer(UP2ModelLayers.AMMONITE_CRIOCERATITES));
        this.ammoniteHoplitesModel = new AmmoniteHoplitesModel(context.bakeLayer(UP2ModelLayers.AMMONITE_HOPLITES));
        this.ammoniteNostocerasModel = new AmmoniteNostocerasModel(context.bakeLayer(UP2ModelLayers.AMMONITE_NOSTOCERAS));
        this.ammonitePinacocerasModel = new AmmonitePinacocerasModel(context.bakeLayer(UP2ModelLayers.AMMONITE_PINACOCERAS));
        this.ammoniteTropitesModel = new AmmoniteTropitesModel(context.bakeLayer(UP2ModelLayers.AMMONITE_TROPITES));
    }

    @Override
    public void render(Ammonite entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        switch (entity.getVariantModelKey().orElse("hoplites")) {
            case "crioceratites":
                this.model = this.ammoniteCrioceratitesModel;
                break;
            case "tropites":
                this.model = this.ammoniteTropitesModel;
                break;
            case "pinacoceras":
                this.model = this.ammonitePinacocerasModel;
                break;
            case "nostoceras":
                this.model = this.ammoniteNostocerasModel;
                break;
            default:
                this.model = this.ammoniteHoplitesModel;
                break;
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Ammonite entity) {
        return entity.getVariantTexture();
    }

    @Override
    protected void setupRotations(Ammonite entity, PoseStack poseStack, float bob, float yBodyRot, float partialTicks, float scale) {
        super.setupRotations(entity, poseStack, bob, yBodyRot + 180.0F, partialTicks, scale);
    }
}
