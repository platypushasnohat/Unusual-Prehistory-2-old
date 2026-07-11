package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.TargetsItems;
import com.google.common.base.Predicate;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class TargetItemsGoal extends TargetGoal {

    protected final PrehistoricMob prehistoricMob;
    protected final TargetItemsGoal.Sorter sorter;
    protected final Predicate<? super ItemEntity> predicate;
    private final boolean offhand;
    private final int tickThreshold;

    protected ItemEntity item;
    protected TargetsItems hunter;
    protected int executionChance;
    protected boolean mustUpdate;
    private int walkCooldown = 0;

    public TargetItemsGoal(PrehistoricMob mob, boolean checkSight) {
        this(mob, checkSight, false);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public TargetItemsGoal(PrehistoricMob mob, boolean checkSight, int tickThreshold) {
        this(mob, checkSight, false, tickThreshold);
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    public TargetItemsGoal(PrehistoricMob mob, boolean checkSight, boolean onlyNearby) {
        this(mob, 10, checkSight, onlyNearby, 0, false);
    }

    public TargetItemsGoal(PrehistoricMob mob, boolean checkSight, boolean onlyNearby, int tickThreshold) {
        this(mob, 10, checkSight, onlyNearby, tickThreshold, false);
    }

    public TargetItemsGoal(PrehistoricMob mob, int chance, boolean checkSight, boolean onlyNearby, int ticksExisted, boolean offhand) {
        super(mob, checkSight, onlyNearby);
        this.prehistoricMob = mob;
        this.executionChance = chance;
        this.tickThreshold = ticksExisted;
        this.hunter = (TargetsItems) mob;
        this.sorter = new TargetItemsGoal.Sorter(mob);
        this.predicate = (Predicate<ItemEntity>) item -> {
            ItemStack stack = item.getItem();
            return !stack.isEmpty() && hunter.canTargetItems(stack) && item.tickCount > tickThreshold;
        };
        this.offhand = offhand;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (prehistoricMob.isPassenger() || prehistoricMob.isVehicle() && prehistoricMob.getControllingPassenger() != null || prehistoricMob.isOrderedToSit() || prehistoricMob.isEepy() || prehistoricMob.isSitting() || prehistoricMob.getEatTicks() > 0 || prehistoricMob.getEatCooldown() > 0) {
            return false;
        }
        if (!prehistoricMob.getItemInHand(offhand ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND).isEmpty()) {
            return false;
        }
        if (!this.mustUpdate) {
            long worldTime = prehistoricMob.level().getGameTime() % 10;
            if (prehistoricMob.getNoActionTime() >= 100 && worldTime != 0) {
                return false;
            }
            if (prehistoricMob.getRandom().nextInt(executionChance) != 0 && worldTime != 0) {
                return false;
            }
        }
        List<ItemEntity> list = prehistoricMob.level().getEntitiesOfClass(ItemEntity.class, this.getTargetableArea(this.getFollowDistance()), predicate);
        if (list.isEmpty()) {
            return false;
        } else {
            list.sort(sorter);
            this.item = list.getFirst();
            this.mustUpdate = false;
            this.hunter.onFindItem(item);
            return true;
        }
    }

    protected AABB getTargetableArea(double distance) {
        Vec3 center = new Vec3(prehistoricMob.getX() + 0.5D, prehistoricMob.getY() + 0.5D, prehistoricMob.getZ() + 0.5D);
        AABB aabb = new AABB(-distance, -distance, -distance, distance, distance, distance);
        return aabb.move(center);
    }

    @Override
    public void start() {
        this.moveToItem();
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
        this.prehistoricMob.getNavigation().stop();
        this.item = null;
    }

    protected void moveToItem() {
        if (walkCooldown > 0) {
            this.walkCooldown--;
        } else {
            this.prehistoricMob.getNavigation().moveTo(item.getX(), item.getY(), item.getZ(), 1.0D);
            this.prehistoricMob.lookAt(EntityAnchorArgument.Anchor.EYES, item.position());
            this.walkCooldown = 30 + this.prehistoricMob.getRandom().nextInt(40);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (item == null || !item.isAlive()) {
            this.stop();
            this.prehistoricMob.getNavigation().stop();
        } else {
            this.moveToItem();
        }
        if (item != null && prehistoricMob.hasLineOfSight(item) && prehistoricMob.getBbWidth() > 2.0D && prehistoricMob.onGround()) {
            this.prehistoricMob.getMoveControl().setWantedPosition(item.getX(), item.getY(), item.getZ(), 1.0D);
        }
        if (item != null && item.isAlive() && prehistoricMob.distanceToSqr(item) < hunter.getMaxDistToItem() && prehistoricMob.getItemInHand(offhand ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND).isEmpty()) {
            this.hunter.onGetItem(item);
            this.stop();
        }
    }

    @Override
    public boolean canContinueToUse() {
        boolean path = prehistoricMob.getBbWidth() > 2.0D || !prehistoricMob.getNavigation().isDone();
        return path && item != null && item.isAlive() && prehistoricMob.getEatTicks() <= 0;
    }

    public static class Sorter implements Comparator<Entity> {

        private final Entity entity;

        public Sorter(Entity entity) {
            this.entity = entity;
        }

        public int compare(Entity entity1, Entity entity2) {
            double distance1 = this.entity.distanceToSqr(entity1);
            double distance2 = this.entity.distanceToSqr(entity2);
            return Double.compare(distance1, distance2);
        }
    }
}