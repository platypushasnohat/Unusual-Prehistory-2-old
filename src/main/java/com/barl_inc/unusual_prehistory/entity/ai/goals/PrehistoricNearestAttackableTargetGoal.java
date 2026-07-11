package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class PrehistoricNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    protected final PrehistoricMob prehistoricMob;

    public PrehistoricNearestAttackableTargetGoal(PrehistoricMob mob, Class<T> targetClass, boolean mustSee) {
        super(mob, targetClass, mustSee);
        this.prehistoricMob = mob;
    }

    public PrehistoricNearestAttackableTargetGoal(PrehistoricMob mob, Class<T> targetClass, boolean mustSee, Predicate<LivingEntity> entityPredicate) {
        super(mob, targetClass, mustSee, entityPredicate);
        this.prehistoricMob = mob;
    }

    public PrehistoricNearestAttackableTargetGoal(PrehistoricMob mob, Class<T> targetClass, boolean mustSee, boolean mustReach) {
        super(mob, targetClass, mustSee, mustReach);
        this.prehistoricMob = mob;
    }

    public PrehistoricNearestAttackableTargetGoal(PrehistoricMob mob, Class<T> targetClass, int interval, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> entityPredicate) {
        super(mob, targetClass, interval, mustSee, mustReach, entityPredicate);
        this.prehistoricMob = mob;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && !prehistoricMob.isTame() && !prehistoricMob.isPacified() && !prehistoricMob.isBaby() && !prehistoricMob.isEepy();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && !prehistoricMob.isTame() && !prehistoricMob.isPacified() && !prehistoricMob.isEepy();
    }
}
