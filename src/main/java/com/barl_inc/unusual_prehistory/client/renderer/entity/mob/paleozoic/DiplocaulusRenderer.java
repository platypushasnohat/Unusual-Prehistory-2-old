package com.barl_inc.unusual_prehistory.client.renderer.entity.mob.paleozoic;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.DiplocaulusDwarfModel;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.DiplocaulusMuddyModel;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.DiplocaulusSwampyModel;
import com.barl_inc.unusual_prehistory.client.models.entity.mob.paleozoic.DiplocaulusTigerModel;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Diplocaulus;
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
public class DiplocaulusRenderer extends MobRenderer<Diplocaulus, HierarchicalModel<Diplocaulus>> {

    private final DiplocaulusTigerModel tigerModel;
    private final DiplocaulusSwampyModel swampyModel;
    private final DiplocaulusMuddyModel muddyModel;
    private final DiplocaulusDwarfModel dwarfModel;

    public DiplocaulusRenderer(EntityRendererProvider.Context context) {
        super(context, new DiplocaulusTigerModel(context.bakeLayer(UP2ModelLayers.DIPLOCAULUS_TIGER)), 0.3F);
        this.tigerModel = new DiplocaulusTigerModel(context.bakeLayer(UP2ModelLayers.DIPLOCAULUS_TIGER));
        this.swampyModel = new DiplocaulusSwampyModel(context.bakeLayer(UP2ModelLayers.DIPLOCAULUS_SWAMPY));
        this.muddyModel = new DiplocaulusMuddyModel(context.bakeLayer(UP2ModelLayers.DIPLOCAULUS_MUDDY));
        this.dwarfModel = new DiplocaulusDwarfModel(context.bakeLayer(UP2ModelLayers.DIPLOCAULUS_DWARF));
    }

    @Override
    public void render(Diplocaulus entity, float entityYaw, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
        switch (entity.getVariant().getId()) {
            case 1:
                this.model = swampyModel;
                break;
            case 2:
                this.model = muddyModel;
                break;
            case 3:
                this.model = dwarfModel;
                break;
            default:
                this.model = tigerModel;
        }
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(Diplocaulus entity) {
        Diplocaulus.DiplocaulusVariant variant = Diplocaulus.DiplocaulusVariant.byId(entity.getVariant().getId());
        return UnusualPrehistory2.modPrefix("textures/entity/mob/diplocaulus/" + variant.name().toLowerCase(Locale.ROOT) + ".png");
    }
}
