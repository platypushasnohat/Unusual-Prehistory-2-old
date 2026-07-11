package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.utils.PackAnimal;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class PackAnimalNearestAttackableTargetGoal<T extends LivingEntity> extends PrehistoricNearestAttackableTargetGoal<T> {

    protected final PackAnimal packAnimal;
    protected final int packSizeRequired;

    public PackAnimalNearestAttackableTargetGoal(PrehistoricMob mob, Class<T> targetClass, boolean mustSee, int packSizeRequired) {
        super(mob, targetClass, mustSee);
        this.packAnimal = (PackAnimal) mob;
        this.packSizeRequired = packSizeRequired;
    }

    public PackAnimalNearestAttackableTargetGoal(PrehistoricMob mob, Class<T> targetClass, boolean mustSee, Predicate<LivingEntity> entityPredicate, int packSizeRequired) {
        super(mob, targetClass, mustSee, entityPredicate);
        this.packAnimal = (PackAnimal) mob;
        this.packSizeRequired = packSizeRequired;
    }

    public PackAnimalNearestAttackableTargetGoal(PrehistoricMob mob, Class<T> targetClass, boolean mustSee, boolean mustReach, int packSizeRequired) {
        super(mob, targetClass, mustSee, mustReach);
        this.packAnimal = (PackAnimal) mob;
        this.packSizeRequired = packSizeRequired;
    }

    public PackAnimalNearestAttackableTargetGoal(PrehistoricMob mob, Class<T> targetClass, int interval, boolean mustSee, boolean mustReach, @Nullable Predicate<LivingEntity> entityPredicate, int packSizeRequired) {
        super(mob, targetClass, interval, mustSee, mustReach, entityPredicate);
        this.packAnimal = (PackAnimal) mob;
        this.packSizeRequired = packSizeRequired;
    }

    @Override
    public boolean canUse() {
        if (super.canUse()) {
            return packAnimal.getPackSize() >= packSizeRequired;
        }
        return false;
    }
}
