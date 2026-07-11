package com.barl_inc.unusual_prehistory.client.renderer.block;

import com.barl_inc.unusual_prehistory.blocks.PlushieBlock;
import com.barl_inc.unusual_prehistory.blocks.entity.PlushieBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class PlushieBlockRenderer implements BlockEntityRenderer<PlushieBlockEntity> {

    private final BlockRenderDispatcher blockRender;

    public PlushieBlockRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRender = context.getBlockRenderDispatcher();
    }

    @Override
    public void render(PlushieBlockEntity blockEntity, float partialTicks, @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        Level level = blockEntity.getLevel();
        BlockState state = blockEntity.getBlockState();
        BlockPos pos = blockEntity.getBlockPos();
        float rotation = RotationSegment.convertToDegrees(blockEntity.getBlockState().getValue(PlushieBlock.ROTATION));
        if (level != null) {
            ModelBlockRenderer.enableCaching();
            poseStack.pushPose();
            poseStack.translate(0.5D, 0.0D, 0.5D);

            poseStack.mulPose(Axis.YP.rotationDegrees(-rotation));

            float squish = blockEntity.getSquishAmount(partialTicks);
            float scale = ((float) Math.sin(squish * Math.PI)) * 0.125F;
            poseStack.scale(1.0F + scale, 1.0F - scale, 1.0F + scale);

            poseStack.translate(-0.5D, 0.0D, -0.5D);

            for (var renderType : blockRender.getBlockModel(state).getRenderTypes(state, RandomSource.create(state.getSeed(pos)), ModelData.EMPTY)) {
                VertexConsumer consumer = bufferSource.getBuffer(renderType);
                this.blockRender.getModelRenderer().tesselateBlock(level, blockRender.getBlockModel(state), state, pos, poseStack, consumer, false, RandomSource.create(), state.getSeed(pos), packedOverlay, ModelData.EMPTY, renderType);
            }
            poseStack.popPose();
            ModelBlockRenderer.clearCache();
        }
    }
}
