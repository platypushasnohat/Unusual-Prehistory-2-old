package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.utils.ChestLootingMob;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LootChestGoal extends MoveToBlockGoal {

    protected final ChestLootingMob chestLooter;
    protected boolean hasOpenedChest = false;

    public LootChestGoal(PathfinderMob mob, double speedModifier, int range, int verticalRange) {
        super(mob, speedModifier, range, verticalRange);
        this.chestLooter = (ChestLootingMob) mob;
    }

    @Override
    public boolean canUse() {
        if (mob instanceof TamableAnimal && ((TamableAnimal) mob).isTame()) {
            return false;
        }
        if (!mob.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
            return false;
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && mob.getItemInHand(InteractionHand.MAIN_HAND).isEmpty();
    }

    @Override
    public void stop() {
        super.stop();
        BlockEntity blockEntity = mob.level().getBlockEntity(blockPos);
        if (blockEntity instanceof Container) {
            this.toggleChest((Container) blockEntity, false);
        }
        this.blockPos = BlockPos.ZERO;
        this.hasOpenedChest = false;
    }

    @Override
    protected int nextStartTick(@NotNull PathfinderMob mob) {
        return reducedTickDelay(100 + mob.getRandom().nextInt(100));
    }

    public boolean isChestRaidable(LevelReader level, BlockPos pos) {
        if (level.getBlockState(pos).getBlock() instanceof BaseEntityBlock) {
            BlockEntity entity = level.getBlockEntity(pos);
            if (entity instanceof Container inventory) {
                try {
                    if (!inventory.isEmpty() && chestLooter.isLootable(inventory)) return true;
                } catch (Exception exception) {
                    UnusualPrehistory2.LOGGER.warn("Unusual Prehistory 2 stopped a {} from causing a crash during access", entity.getClass().getSimpleName());
                    exception.printStackTrace();
                }
            }
        }
        return false;
    }

    @Override
    protected @NotNull BlockPos getMoveToTarget() {
        return this.blockPos;
    }

    @Override
    protected void moveMobToBlock() {
        BlockPos pos = getMoveToTarget();
        this.mob.getNavigation().moveTo((double) ((float) pos.getX()) + 0.5D, pos.getY() + 1, (double) ((float) pos.getZ()) + 0.5D, speedModifier);
    }

    public boolean hasLineOfSightChest() {
        HitResult hitResult = mob.level().clip(new ClipContext(mob.getEyePosition(1.0F), new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, mob));
        if (hitResult instanceof BlockHitResult blockHitResult) {
            BlockPos pos = blockHitResult.getBlockPos();
            return pos.equals(blockPos) || mob.level().isEmptyBlock(pos) || mob.level().getBlockEntity(pos) == mob.level().getBlockEntity(blockPos);
        }
        return true;
    }

    public ItemStack getItemsFromInventory(Container inventory, RandomSource random) {
        List<ItemStack> items = new ArrayList<ItemStack>();
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty() && chestLooter.shouldLootItem(stack)) {
                items.add(stack);
            }
        }
        if (items.isEmpty()) {
            return ItemStack.EMPTY;
        } else if (items.size() == 1) {
            return items.get(0);
        } else {
            return items.get(random.nextInt(items.size() - 1));
        }
    }

    @Override
    public double acceptedDistance() {
        return Math.pow(mob.getBbWidth(), 2) + 3.0F;
    }

    @Override
    public boolean shouldRecalculatePath() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        BlockEntity blockEntity = mob.level().getBlockEntity(blockPos);
        if (blockEntity instanceof Container container) {
            double distance = mob.distanceToSqr(blockPos.getX() + 0.5F, blockPos.getY() + 0.5F, blockPos.getZ() + 0.5F);
            this.mob.getNavigation().moveTo(blockPos.getX() + 0.5F, blockPos.getY() - 1, blockPos.getZ() + 0.5F, 1.0F);
            if (this.hasLineOfSightChest()) {
                this.mob.lookAt(EntityAnchorArgument.Anchor.EYES, Vec3.atCenterOf(blockPos));
                if (distance <= acceptedDistance()) {
                    this.mob.getNavigation().stop();
                    this.chestLooter.startOpeningChest();
                    if (!hasOpenedChest) {
                        this.hasOpenedChest = true;
                        this.toggleChest(container, true);
                    }
                    if (hasOpenedChest && chestLooter.openChest()) {
                        ItemStack stack = this.getItemsFromInventory(container, mob.level().random);
                        if (stack == ItemStack.EMPTY) {
                            this.stop();
                        } else {
                            ItemStack duplicate = stack.copy();
                            duplicate.setCount(1);
                            if (!mob.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && !mob.level().isClientSide) {
                                this.mob.spawnAtLocation(mob.getItemInHand(InteractionHand.MAIN_HAND), 0.0F);
                            }
                            this.mob.setItemInHand(InteractionHand.MAIN_HAND, duplicate);
                            stack.shrink(1);
                            this.chestLooter.afterLooting(blockPos);
                            this.stop();
                        }
                    }
                }
            }
        }
    }

    @Override
    protected boolean isValidTarget(@NotNull LevelReader level, @NotNull BlockPos pos) {
        return this.isChestRaidable(level, pos);
    }

    public void toggleChest(Container container, boolean open) {
        if (container instanceof ChestBlockEntity chest) {
            if (open) {
                this.mob.level().blockEvent(blockPos, chest.getBlockState().getBlock(), 1, 1);
                this.playChestSound(mob.level(), blockPos, chest.getBlockState(), SoundEvents.CHEST_OPEN);
            } else {
                this.mob.level().blockEvent(blockPos, chest.getBlockState().getBlock(), 1, 0);
                this.playChestSound(mob.level(), blockPos, chest.getBlockState(), SoundEvents.CHEST_CLOSE);
            }
            this.mob.level().updateNeighborsAt(blockPos, chest.getBlockState().getBlock());
            this.mob.level().updateNeighborsAt(blockPos.below(), chest.getBlockState().getBlock());
        }
    }

    protected void playChestSound(Level level, BlockPos pos, BlockState state, SoundEvent soundEvent) {
        ChestType type = state.getValue(ChestBlock.TYPE);
        if (type != ChestType.LEFT) {
            double x = (double) pos.getX() + 0.5D;
            double y = (double) pos.getY() + 0.5D;
            double z = (double) pos.getZ() + 0.5D;
            if (type == ChestType.RIGHT) {
                Direction direction = ChestBlock.getConnectedDirection(state);
                x += (double) direction.getStepX() * 0.5D;
                z += (double) direction.getStepZ() * 0.5D;
            }
            level.playSound(null, x, y, z, soundEvent, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
        }
    }
}