package com.barl_inc.unusual_prehistory.blocks.plant;

import com.barl_inc.unusual_prehistory.registry.UP2Blocks;
import com.barl_inc.unusual_prehistory.tags.UP2BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.common.ItemAbilities;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class GuangdedendronStalkBlock extends Block implements BonemealableBlock {

    protected static final VoxelShape SMALL_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    protected static final VoxelShape COLLISION_SHAPE = Block.box(6.5D, 0.0D, 6.5D, 9.5D, 16.0D, 9.5D);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_1;
    public static final EnumProperty<BambooLeaves> LEAVES = BlockStateProperties.BAMBOO_LEAVES;
    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;

    public GuangdedendronStalkBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(LEAVES, BambooLeaves.NONE).setValue(STAGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, LEAVES, STAGE);
    }

    @Override
    public boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos) {
        return true;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Vec3 vec3 = state.getOffset(getter, pos);
        return SMALL_SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    protected boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType pathComputationType) {
        return false;
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Vec3 vec3 = state.getOffset(getter, pos);
        return COLLISION_SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    @Override
    public boolean isCollisionShapeFullBlock(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos) {
        return false;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        if (!fluidstate.isEmpty()) {
            return null;
        } else {
            BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos().below());
            if (blockstate.is(UP2BlockTags.GUANGDEDENDRON_PLANTABLE_ON)) {
                if (blockstate.is(UP2Blocks.GUANGDEDENDRON_SPORE.get())) {
                    return this.defaultBlockState().setValue(AGE, 0);
                } else if (blockstate.is(UP2Blocks.GUANGDEDENDRON.get())) {
                    int i = blockstate.getValue(AGE) > 0 ? 1 : 0;
                    return this.defaultBlockState().setValue(AGE, i);
                } else {
                    BlockState blockstate1 = context.getLevel().getBlockState(context.getClickedPos().above());
                    return blockstate1.is(UP2Blocks.GUANGDEDENDRON.get()) ? this.defaultBlockState().setValue(AGE, blockstate1.getValue(AGE)) : UP2Blocks.GUANGDEDENDRON_SPORE.get().defaultBlockState();
                }
            } else {
                return null;
            }
        }
    }

    @Override
    public void tick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(STAGE) == 0;
    }

    @Override
    public void randomTick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(STAGE) == 0) {
            if (level.isEmptyBlock(pos.above()) && level.getRawBrightness(pos.above(), 0) >= 9) {
                int belowMax = this.getHeightBelowUpToMax(level, pos) + 1;
                if (belowMax < 16 && CommonHooks.canCropGrow(level, pos, state, random.nextInt(3) == 0)) {
                    this.growGuangdedendron(state, level, pos, random, belowMax);
                    CommonHooks.fireCropGrowPost(level, pos, state);
                }
            }
        }
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).is(UP2BlockTags.GUANGDEDENDRON_PLANTABLE_ON);
    }

    @Override
    public @NotNull BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState state1, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos pos1) {
        if (!state.canSurvive(level, pos)) {
            level.scheduleTick(pos, this, 1);
        }
        if (direction == Direction.UP && state1.is(UP2Blocks.GUANGDEDENDRON.get()) && state1.getValue(AGE) > state.getValue(AGE)) {
            level.setBlock(pos, state.cycle(AGE), 2);
        }
        return super.updateShape(state, direction, state1, level, pos, pos1);
    }

    @Override
    public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        int aboveMax = this.getHeightAboveUpToMax(level, pos);
        int belowMax = this.getHeightBelowUpToMax(level, pos);
        return aboveMax + belowMax + 1 < 16 && level.getBlockState(pos.above(aboveMax)).getValue(STAGE) != 1;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(@NotNull ServerLevel level, RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        int aboveMax = this.getHeightAboveUpToMax(level, pos);
        int belowMax = this.getHeightBelowUpToMax(level, pos);
        int k = aboveMax + belowMax + 1;
        int l = 1 + random.nextInt(2);

        for (int i = 0; i < l; i++) {
            BlockPos blockpos = pos.above(aboveMax);
            BlockState blockstate = level.getBlockState(blockpos);
            if (k >= 16 || blockstate.getValue(STAGE) == 1 || !level.isEmptyBlock(blockpos.above())) {
                return;
            }
            this.growGuangdedendron(blockstate, level, blockpos, random, k);
            aboveMax++;
            k++;
        }
    }

    @Override
    public float getDestroyProgress(@NotNull BlockState state, Player player, @NotNull BlockGetter getter, @NotNull BlockPos pos) {
        return player.getMainHandItem().canPerformAction(ItemAbilities.SWORD_DIG) ? 1.0F : super.getDestroyProgress(state, player, getter, pos);
    }

    protected void growGuangdedendron(BlockState state, Level level, BlockPos pos, RandomSource random, int age) {
        BlockState belowState = level.getBlockState(pos.below());
        BlockState twoBelowState = level.getBlockState(pos.below(2));
        BambooLeaves leaves = BambooLeaves.NONE;
        if (age >= 1) {
            if (state.is(UP2Blocks.GUANGDEDENDRON.get()) && state.getValue(LEAVES) != BambooLeaves.NONE) {
                leaves = BambooLeaves.LARGE;
                if (belowState.is(UP2Blocks.GUANGDEDENDRON.get())) {
                    level.setBlock(pos, state.setValue(LEAVES, BambooLeaves.SMALL), 3);
                    if (twoBelowState.is(UP2Blocks.GUANGDEDENDRON.get())) {
                        level.setBlock(pos.below(), belowState.setValue(LEAVES, BambooLeaves.SMALL), 3);
                        level.setBlock(pos.below(2), twoBelowState.setValue(LEAVES, BambooLeaves.NONE), 3);
                    }
                }
            } else {
                leaves = BambooLeaves.SMALL;
            }
        }

        int i = state.getValue(AGE) != 1 && !twoBelowState.is(UP2Blocks.GUANGDEDENDRON.get()) ? 0 : 1;
        int j = (age < 11 || !(random.nextFloat() < 0.25F)) && age != 15 ? 0 : 1;
        level.setBlock(pos.above(), this.defaultBlockState().setValue(AGE, i).setValue(LEAVES, leaves).setValue(STAGE, j), 3);
    }

    protected int getHeightAboveUpToMax(BlockGetter getter, BlockPos pos) {
        int i;
        for (i = 0; i < 16 && getter.getBlockState(pos.above(i + 1)).is(UP2Blocks.GUANGDEDENDRON.get()); i++) {
        }
        return i;
    }

    protected int getHeightBelowUpToMax(BlockGetter getter, BlockPos pos) {
        int i;
        for (i = 0; i < 16 && getter.getBlockState(pos.below(i + 1)).is(UP2Blocks.GUANGDEDENDRON.get()); i++) {
        }
        return i;
    }
}