package com.barl_inc.unusual_prehistory.blocks;

import com.barl_inc.unusual_prehistory.registry.UP2Particles;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TarBlock extends LiquidBlock {

    public TarBlock(FlowingFluid fluid, Properties properties) {
        super(fluid, properties);
    }

    @Override
    public void entityInside(@NotNull BlockState state, @NotNull Level level, BlockPos pos, Entity entity) {
        if (entity.getY() < pos.getY() + STABLE_SHAPE.max(Direction.Axis.Y)) {
            if (!(entity instanceof LivingEntity) || entity.getBlockStateOn().is(this)) {
                entity.makeStuckInBlock(state, new Vec3(0.9F, 1.0D, 0.9F));
            }
        }
    }

    @Override
    public void fallOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull Entity entity, float fallDistance) {
        if (fallDistance >= 4.0D && entity instanceof LivingEntity livingEntity) {
            LivingEntity.Fallsounds fallSound = livingEntity.getFallSounds();
            SoundEvent sound = fallDistance < 7.0D ? fallSound.small() : fallSound.big();
            entity.playSound(sound, 1.0F, 1.0F);
        }
    }

    @Override
    public @NotNull Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL_POWDER_SNOW);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(@NotNull BlockState state, Level level, BlockPos pos, @NotNull RandomSource random) {
        boolean top = level.getFluidState(pos.above()).isEmpty();
        if (random.nextInt(400) == 0) {
            level.playLocalSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, UP2SoundEvents.TAR_POP.get(), SoundSource.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
        }
        if (random.nextInt(top ? 10 : 40) == 0) {
            float height = top ? state.getFluidState().getHeight(level, pos) : random.nextFloat();
            level.addParticle(UP2Particles.TAR_BUBBLE.get(), pos.getX() + random.nextFloat(), pos.getY() + height, pos.getZ() + random.nextFloat(), (random.nextFloat() - 0.5F) * 0.1F, 0.05F + random.nextFloat() * 0.1F, (random.nextFloat() - 0.5F) * 0.1F);
        }
    }
}