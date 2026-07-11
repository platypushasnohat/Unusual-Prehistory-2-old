package com.barl_inc.unusual_prehistory.blocks.plant;

import com.barl_inc.unusual_prehistory.entity.mob.base.AmbientMob;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class TallAmbientPlantBlock extends PrehistoricTallPlantBlock {

    private final Supplier<EntityType<?>> toSpawn;
    private final int spawnCount;

    public TallAmbientPlantBlock(Properties properties, Supplier<EntityType<?>> toSpawn, int spawnCount) {
        super(properties);
        this.toSpawn = toSpawn;
        this.spawnCount = spawnCount;
    }

    @Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        this.spawnEntity(level, pos, state, random);
    }

    public void spawnEntity(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
        if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
            level.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.1F, 0.8F + random.nextFloat() * 0.2F);
            for (int j = 0; j < this.spawnCount; j++) {
                Entity entity = this.toSpawn.get().create(level);
                Vec3 vec3 = pos.getCenter();
                if (entity instanceof AmbientMob mob) {
                    mob.setShouldBeRestricted(true);
                    entity.moveTo(vec3.x(), vec3.y(), vec3.z(), Mth.wrapDegrees(level.random.nextFloat() * 360.0F), 0.0F);
                    level.addFreshEntity(entity);
                    EventHooks.finalizeMobSpawn(mob, level, level.getCurrentDifficultyAt(pos), MobSpawnType.NATURAL, null);
                }
            }
            level.scheduleTick(pos, this, 2600 + level.getRandom().nextInt(1200));
        }
    }

    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState blockState, boolean b) {
        if (state.getValue(HALF) != DoubleBlockHalf.UPPER) {
            level.gameEvent(GameEvent.BLOCK_PLACE, pos, GameEvent.Context.of(state));
            level.scheduleTick(pos, this, 2600 + level.getRandom().nextInt(1200));
        }
    }
}
