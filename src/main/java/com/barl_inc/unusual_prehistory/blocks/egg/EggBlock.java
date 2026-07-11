package com.barl_inc.unusual_prehistory.blocks.egg;

import com.barl_inc.unusual_prehistory.blocks.entity.EggBlockEntity;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.registry.UP2BlockEntities;
import com.barl_inc.unusual_prehistory.registry.UP2Particles;
import com.barl_inc.unusual_prehistory.tags.UP2BlockTags;
import com.barl_inc.unusual_prehistory.utils.UP2ParticleUtils;
import com.mojang.serialization.MapCodec;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Supplier;

public class EggBlock extends BaseEntityBlock {

    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
    protected final VoxelShape shape;
    protected final Supplier<EntityType<?>> hatchedEntity;
    protected final int hatchAmount;
    protected final SoundEvent hatchSound;
    public final boolean hasStages;

    public EggBlock(Properties properties, Supplier<EntityType<?>> hatchedEntity, double widthPx, double heightPx) {
        this(properties, hatchedEntity, 1, widthPx, heightPx, SoundEvents.SNIFFER_EGG_HATCH, true);
    }

    public EggBlock(Properties properties, Supplier<EntityType<?>> hatchedEntity, int hatchAmount, double widthPx, double heightPx) {
        this(properties, hatchedEntity, hatchAmount, widthPx, heightPx, SoundEvents.SNIFFER_EGG_HATCH, true);
    }

    public EggBlock(Properties properties, Supplier<EntityType<?>> hatchedEntity, int hatchAmount, double widthPx, double heightPx, SoundEvent hatchSound, boolean hasStages) {
        super(properties);
        this.hatchedEntity = hatchedEntity;
        this.hatchAmount = hatchAmount;
        this.hatchSound = hatchSound;
        this.hasStages = hasStages;
        double px = (16 - widthPx) / 2;
        this.shape = Block.box(px, 0, px, 16 - px, heightPx, 16 - px);
        this.registerDefaultState(this.defaultBlockState().setValue(HATCH, 0));
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected @Nullable MapCodec<EggBlock> codec() {
        return null;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return shape;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean canHatch(BlockGetter blockGetter, BlockPos pos) {
        return !blockGetter.getBlockState(pos.below()).is(UP2BlockTags.PREVENTS_EGG_HATCHING);
    }

    public boolean hatchBoost(BlockGetter blockGetter, BlockPos pos) {
        return blockGetter.getBlockState(pos.below()).is(UP2BlockTags.ACCELERATES_EGG_HATCHING);
    }

    public void spawnEntity(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof EggBlockEntity eggBlockEntity)) {
            return;
        }
        UUID placer = eggBlockEntity.getOwner();
        level.playSound(null, pos, hatchSound, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
        level.destroyBlock(pos, false);
        int i = this.getMobsBornFrom(state);
        if (random.nextInt(5) == 0) {
            i += 1;
        }
        for (int j = 0; j < i; j++) {
            Vec3 vec3 = pos.getCenter();
            Entity entity = hatchedEntity.get().create(level);
            if (entity instanceof Mob mob) {
                if (entity instanceof Animal animal) {
                    animal.setBaby(true);
                }
                if (entity instanceof PrehistoricMob prehistoricMob) {
                    prehistoricMob.setFromEgg(true);
                }
                if (placer != null) {
                    Player player = level.getPlayerByUUID(placer);
                    if (player instanceof ServerPlayer serverPlayer) {
                        CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayer, entity);
                    }
                }
                entity.moveTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(level.getRandom().nextFloat() * 360.0F), 0.0F);
                level.addFreshEntity(entity);
                EventHooks.finalizeMobSpawn(mob, level, level.getCurrentDifficultyAt(pos), MobSpawnType.NATURAL, null);
            }
        }
    }

    protected int getMobsBornFrom(BlockState state) {
        return this.hatchAmount;
    }

    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState blockState, boolean movedByPiston) {
        if (!level.isClientSide) {
            this.spawnParticles(level, pos);
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof EggBlockEntity eggBlockEntity && eggBlockEntity.getHatchTime() <= 0) {
                int hatchTime = this.hatchBoost(level, pos) ? 12000 : 24000;
                eggBlockEntity.setHatchTime(hatchTime);
                eggBlockEntity.setTotalHatchTime(hatchTime);
            }
        }
        level.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(state));
    }

    protected void spawnParticles(Level level, BlockPos pos) {
        if (this.hatchBoost(level, pos)) {
            UP2ParticleUtils.queueParticlesOnBlockFaces((ServerLevel) level, pos, ParticleTypes.HAPPY_VILLAGER, UniformInt.of(3, 6));
        }
        if (!this.canHatch(level, pos)) {
            UP2ParticleUtils.queueParticlesOnBlockFaces((ServerLevel) level, pos, UP2Particles.SNOWFLAKE.get(), UniformInt.of(3, 6));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HATCH);
    }

    @Override
    protected boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType pathComputationType) {
        return false;
    }

    @Override
    public void setPlacedBy(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity placer, @NotNull ItemStack stack) {
        if (!level.isClientSide && placer instanceof Player player) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof EggBlockEntity owned) {
                owned.setOwner(player.getUUID());
            }
        }
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new EggBlockEntity(pos, state);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return level.isClientSide ? null : createTickerHelper(type, UP2BlockEntities.EGG_BLOCK_ENTITY.get(), EggBlockEntity::serverTick);
    }
}
