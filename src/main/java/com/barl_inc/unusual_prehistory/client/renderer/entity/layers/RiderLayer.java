package com.barl_inc.unusual_prehistory.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Entity;

public abstract class RiderLayer<T extends Entity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    public RiderLayer(RenderLayerParent<T, M> parent) {
        super(parent);
    }

    public static <E extends Entity> void renderPassenger(E entity, double x, double y, double z, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        EntityRenderer<? super E> render = null;
        EntityRenderDispatcher manager = Minecraft.getInstance().getEntityRenderDispatcher();
        try {
            render = manager.getRenderer(entity);
            if (render != null) {
                try {
                    render.render(entity, yaw, partialTicks, poseStack, bufferSource, packedLight);
                } catch (Throwable throwable1) {
                    throw new ReportedException(CrashReport.forThrowable(throwable1, "Rendering entity in world"));
                }
            }
        } catch (Throwable throwable3) {
            CrashReport crashreport = CrashReport.forThrowable(throwable3, "Rendering entity in world");
            CrashReportCategory crashreportcategory = crashreport.addCategory("Entity being rendered");
            entity.fillCrashReportCategory(crashreportcategory);
            CrashReportCategory details = crashreport.addCategory("Renderer details");
            details.setDetail("Assigned renderer", render);
            details.setDetail("Rotation", yaw);
            details.setDetail("Delta", partialTicks);
            throw new ReportedException(crashreport);
        }
    }
}
