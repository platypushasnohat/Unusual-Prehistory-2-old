package com.barl_inc.unusual_prehistory.blocks;

import com.barl_inc.unusual_prehistory.blocks.entity.TransmogrifierBlockEntity;
import com.barl_inc.unusual_prehistory.registry.UP2BlockEntities;
import com.barl_inc.unusual_prehistory.registry.UP2Particles;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class TransmogrifierBlock extends BaseEntityBlock {

    public static final MapCodec<TransmogrifierBlock> CODEC = simpleCodec(TransmogrifierBlock::new);

    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public TransmogrifierBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LIT, false));
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new TransmogrifierBlockEntity(pos, state);
    }

    @Override
    public @NotNull MapCodec<TransmogrifierBlock> codec() {
        return CODEC;
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            this.openContainer(level, pos, player);
            return InteractionResult.CONSUME;
        }
    }

    protected void openContainer(Level level, BlockPos pos, Player player) {
        BlockEntity blockentity = level.getBlockEntity(pos);
        if (blockentity instanceof TransmogrifierBlockEntity) {
            player.openMenu((MenuProvider) blockentity);
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(pos);
            if (blockentity instanceof TransmogrifierBlockEntity transmogrifier) {
                if (level instanceof ServerLevel serverLevel) {
                    Containers.dropContents(level, pos, transmogrifier);
                    transmogrifier.getRecipesToAwardAndPopExperience(serverLevel, Vec3.atCenterOf(pos));
                }
                super.onRemove(state, level, pos, newState, isMoving);
                level.updateNeighbourForOutputSignal(pos, this);
            } else {
                super.onRemove(state, level, pos, newState, isMoving);
            }
        }
    }

    @Override
    protected boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(@NotNull BlockState blockState, Level level, @NotNull BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(pos));
    }

    @Override
    protected @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, LIT);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, UP2BlockEntities.TRANSMOGRIFIER_BLOCK_ENTITY.get(), TransmogrifierBlockEntity::tick);
    }

    @Override
    public void animateTick(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (state.getValue(LIT)) {
            Direction direction = state.getValue(TransmogrifierBlock.FACING).getCounterClockWise();
            Direction.Axis axis = direction.getAxis();
            double x = pos.getX() + 0.5D;
            double y = pos.getY() + 0.5D;
            double z = pos.getZ() + 0.5D;
            double offset = 0.0D;
            double xDirection = axis == Direction.Axis.X ? direction.getStepX() * 0.52D : offset;
            double zDirection = axis == Direction.Axis.Z ? direction.getStepZ() * 0.52D : offset;
            double xoffset = 0.0D;
            double zoffset = 0.0D;
            if (direction == Direction.NORTH) {
                xoffset = -0.25D;
            } else if (direction == Direction.SOUTH) {
                xoffset = 0.25D;
            } else if (direction == Direction.EAST) {
                zoffset = -0.25D;
            } else if (direction == Direction.WEST) {
                zoffset = 0.25D;
            }
            double xSpeed = direction.getStepX() * 0.2F;
            double zSpeed = direction.getStepZ() * 0.2F;
            BlockPos sidePos = pos.relative(direction, 1);
            BlockState sideState = level.getBlockState(sidePos);
            if ((sideState.isAir() || sideState.getCollisionShape(level, sidePos).isEmpty()) && random.nextFloat() < 0.7F) {
                level.addParticle(UP2Particles.OOZE_BUBBLE.get(), (x + xDirection) + xoffset, y - 0.2D, (z + zDirection) + zoffset, xSpeed, 0.0D, zSpeed);
            }
        }
    }
}
