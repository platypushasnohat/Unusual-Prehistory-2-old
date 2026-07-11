package com.barl_inc.unusual_prehistory.blocks.plant;

import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.barl_inc.unusual_prehistory.tags.UP2BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class GuangdedendronSporeBlock extends Block implements BonemealableBlock {

    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D);

    public GuangdedendronSporeBlock(BlockBehaviour.Properties pProperties) {
        super(pProperties);
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Vec3 vec3 = state.getOffset(getter, pos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, RandomSource random) {
        if (random.nextInt(3) == 0 && level.isEmptyBlock(pos.above()) && level.getRawBrightness(pos.above(), 0) >= 9) {
            this.growGuangdedendron(level, pos);
        }
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.GUANGDEDENDRON_PLANTABLE_ON);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState state1, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos pos1) {
        if (!state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        } else {
            if (direction == Direction.UP && state1.is(UP2Blocks.GUANGDEDENDRON.get())) {
                level.setBlock(pos, UP2Blocks.GUANGDEDENDRON.get().defaultBlockState(), 2);
            }
            return super.updateShape(state, direction, state1, level, pos, pos1);
        }
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return new ItemStack(UP2Blocks.GUANGDEDENDRON.get());
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, @NotNull BlockState state) {
        return level.getBlockState(pos.above()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        this.growGuangdedendron(level, pos);
    }

    @Override
    public float getDestroyProgress(@NotNull BlockState state, Player player, @NotNull BlockGetter getter, @NotNull BlockPos pos) {
        return player.getMainHandItem().canPerformAction(ItemAbilities.SWORD_DIG) ? 1.0F : super.getDestroyProgress(state, player, getter, pos);
    }

    protected void growGuangdedendron(Level level, BlockPos state) {
        level.setBlock(state.above(), UP2Blocks.GUANGDEDENDRON.get().defaultBlockState().setValue(GuangdedendronStalkBlock.LEAVES, BambooLeaves.SMALL), 3);
    }
}