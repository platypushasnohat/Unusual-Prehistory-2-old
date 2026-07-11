package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricSchoolingAquaticMob;
import com.mojang.datafixers.DataFixUtils;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;
import java.util.function.Predicate;

public class FollowVariantLeaderGoal extends Goal {

    protected final PrehistoricSchoolingAquaticMob mob;
    private int timeToRecalcPath;
    private int nextStartTick;

    public FollowVariantLeaderGoal(PrehistoricSchoolingAquaticMob mob) {
        this.mob = mob;
        this.nextStartTick = this.nextStartTick(mob);
    }

    protected int nextStartTick(PrehistoricSchoolingAquaticMob mob) {
        return reducedTickDelay(200 + mob.getRandom().nextInt(200) % 20);
    }

    @Override
    public boolean canUse() {
        if (mob.isSitting()) {
            return false;
        } else if (mob.hasFollowers()) {
            return false;
        } else if (mob.isFollower()) {
            return true;
        } else if (nextStartTick > 0) {
            this.nextStartTick--;
            return false;
        } else {
            this.nextStartTick = this.nextStartTick(mob);
            Predicate<PrehistoricSchoolingAquaticMob> predicate = (fishy) -> fishy.canBeFollowed() || !fishy.isFollower();
            List<? extends PrehistoricSchoolingAquaticMob> list = mob.level().getEntitiesOfClass(mob.getClass(), mob.getBoundingBox().inflate(10.0D, 10.0D, 10.0D), predicate);
            PrehistoricSchoolingAquaticMob schoolingFish = DataFixUtils.orElse(list.stream().filter(PrehistoricSchoolingAquaticMob::canBeFollowed).findAny(), mob);
            schoolingFish.addFollowers(list.stream().filter((fishy2) -> !fishy2.isFollower()));
            return mob.isFollower();
        }
    }

    @Override
    public boolean canContinueToUse() {
        return mob.isFollower() && mob.inRangeOfLeader();
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
    }

    @Override
    public void stop() {
        this.mob.stopFollowing();
    }

    @Override
    public void tick() {
        if (this.timeToRecalcPath-- <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.mob.pathToLeader();
        }
    }
}
