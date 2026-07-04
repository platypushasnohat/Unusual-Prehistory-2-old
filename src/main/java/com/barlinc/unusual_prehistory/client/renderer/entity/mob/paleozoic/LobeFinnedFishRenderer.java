package com.barlinc.unusual_prehistory.client.renderer.entity.mob.paleozoic;

import com.barlinc.unusual_prehistory.UnusualPrehistory2;
import com.barlinc.unusual_prehistory.client.models.entity.mob.paleozoic.*;
import com.barlinc.unusual_prehistory.entity.mob.paleozoic.LobeFinnedFish;
import com.barlinc.unusual_prehistory.registry.UP2ModelLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HierarchicalModel;
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
public class LobeFinnedFishRenderer extends MobRenderer<LobeFinnedFish, HierarchicalModel<LobeFinnedFish>> {

    private final LobeFinnedFishAllenypterusModel lobeFinnedFishAllenypterusModel;
    private final LobeFinnedFishEusthenopteronModel eusthenopteronModel;
    private final LobeFinnedFishGooloogongiaModel gooloogongiaModel;
    private final LobeFinnedFishLaccognathusModel laccognathusModel;
    private final LobeFinnedFishScaumenaciaModel scaumenaciaModel;

    public LobeFinnedFishRenderer(EntityRendererProvider.Context context) {
        super(context, new LobeFinnedFishAllenypterusModel(context.bakeLayer(UP2ModelLayers.LOBE_FINNED_FISH_ALLENYPTERUS)), 0.35F);
        this.lobeFinnedFishAllenypterusModel = new LobeFinnedFishAllenypterusModel(context.bakeLayer(UP2ModelLayers.LOBE_FINNED_FISH_ALLENYPTERUS));
        this.eusthenopteronModel = new LobeFinnedFishEusthenopteronModel(context.bakeLayer(UP2ModelLayers.LOBE_FINNED_FISH_EUSTHENOPTERON));
        this.gooloogongiaModel = new LobeFinnedFishGooloogongiaModel(context.bakeLayer(UP2ModelLayers.LOBE_FINNED_FISH_GOOLOOGONGIA));
        this.laccognathusModel = new LobeFinnedFishLaccognathusModel(context.bakeLayer(UP2ModelLayers.LOBE_FINNED_FISH_LACCOGNATHUS));
        this.scaumenaciaModel = new LobeFinnedFishScaumenaciaModel(context.bakeLayer(UP2ModelLayers.LOBE_FINNED_FISH_SCAUMENACIA));
    }

    @Override
    public void render(LobeFinnedFish entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        switch (entity.getVariant().getId()) {
            case 1:
                this.model = eusthenopteronModel;
                break;
            case 2:
                this.model = gooloogongiaModel;
                break;
            case 3:
                this.model = laccognathusModel;
                break;
            case 4:
                this.model = scaumenaciaModel;
                break;
            default:
                this.model = lobeFinnedFishAllenypterusModel;
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(LobeFinnedFish entity) {
        LobeFinnedFish.LobeFinnedFishVariant variant = LobeFinnedFish.LobeFinnedFishVariant.byId(entity.getVariant().getId());
        return UnusualPrehistory2.modPrefix("textures/entity/mob/lobe_finned_fish/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }

    @Override
    @Nullable
    protected RenderType getRenderType(@NotNull LobeFinnedFish entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(this.getTextureLocation(entity));
    }
}
