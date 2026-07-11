package com.barl_inc.unusual_prehistory.entity.mob.base;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public abstract class PrehistoricSchoolingAquaticMob extends PrehistoricAquaticMob {

    @Nullable
    private PrehistoricSchoolingAquaticMob leader;
    protected int schoolSize = 1;

    protected PrehistoricSchoolingAquaticMob(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.hasFollowers() && this.level().random.nextInt(200) == 1) {
            List<? extends PrehistoricSchoolingAquaticMob> list = this.level().getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(8.0D, 8.0D, 8.0D));
            if (list.size() <= 1) {
                this.schoolSize = 1;
            }
        }
    }

    @Override
    protected void playSwimSound(float volume) {
        float multiplier = 1.0F;
        if (this.leader != null) {
            multiplier = Math.max(1.0F - (leader.schoolSize - 1) * 0.1F, 0.4F);
        }
        this.playSound(this.getSwimSound(), volume * multiplier, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return this.getMaxSchoolSize();
    }

    public int getMaxSchoolSize() {
        return super.getMaxSpawnClusterSize();
    }

    public boolean isFollower() {
        return this.leader != null && this.leader.isAlive();
    }

    public void startFollowing(PrehistoricSchoolingAquaticMob entity) {
        this.leader = entity;
        entity.addFollower();
    }

    public void stopFollowing() {
        if (leader != null) {
            this.leader.removeFollower();
            this.leader = null;
        }
    }

    protected void addFollower() {
        this.schoolSize++;
    }

    protected void removeFollower() {
        this.schoolSize--;
    }

    public boolean canBeFollowed() {
        return this.hasFollowers() && this.schoolSize < this.getMaxSchoolSize();
    }

    public boolean hasFollowers() {
        return this.schoolSize > 1;
    }

    public boolean inRangeOfLeader() {
        if (leader != null) {
            return this.distanceToSqr(this.leader) <= 121.0D;
        }
        return false;
    }

    public void addFollowers(Stream<? extends PrehistoricSchoolingAquaticMob> entity) {
        entity.limit(this.getMaxSchoolSize() - this.schoolSize).filter((entity1) -> entity1 != this).forEach((entity2) -> {
            if (!this.isBaby()) {
                entity2.startFollowing(this);
            }
        });
    }

    public void pathToLeader() {
        if (this.isFollower() && leader != null) {
            this.getNavigation().moveTo(this.leader, 1.0D);
        }
    }
}
