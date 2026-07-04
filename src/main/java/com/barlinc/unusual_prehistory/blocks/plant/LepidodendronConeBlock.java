package com.barlinc.unusual_prehistory.blocks.plant;

import com.barlinc.unusual_prehistory.registry.UP2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class LepidodendronConeBlock extends SaplingBlock {

    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 13.0D, 12.0D);
    protected static final VoxelShape HANGING_SHAPE = Block.box(4.0D, 2.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public LepidodendronConeBlock(TreeGrower tree, Properties properties) {
        super(tree, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(HANGING, false).setValue(STAGE, 0));
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos blockPos, @NotNull RandomSource random) {
        if (!isHanging(state)) {
            if (level.getMaxLocalRawBrightness(blockPos.above()) >= 9 && random.nextInt(7) == 0) {
                this.advanceTree(level, blockPos, state, random);
            }
        }
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos blockPos) {
        return isHanging(state) ? level.getBlockState(blockPos.above()).is(UP2Blocks.LEPIDODENDRON_LEAVES.get()) : super.canSurvive(state, level, blockPos);
    }

    private static boolean isHanging(BlockState state) {
        return state.getValue(HANGING);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos blockPos, @NotNull BlockState state) {
        return !isHanging(state);
    }

    public static BlockState createHangingCone() {
        return UP2Blocks.LEPIDODENDRON_CONE.get().defaultBlockState().setValue(HANGING, true);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HANGING).add(STAGE);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Vec3 offset = state.getOffset(level, pos);
        return state.getValue(HANGING) ? HANGING_SHAPE.move(offset.x, offset.y, offset.z) : SHAPE.move(offset.x, offset.y, offset.z);
    }
}
