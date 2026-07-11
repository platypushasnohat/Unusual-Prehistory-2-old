package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.utils.PackAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.List;

public class JoinPackGoal extends Goal {

    public final LivingEntity entity;
    public final PackAnimal packAnimal;
    private int distCheckCounter;

    private final int rate;
    private final int packSize;

    public JoinPackGoal(LivingEntity animal, int rate, int packSize) {
        this.entity = animal;
        this.packAnimal = (PackAnimal) animal;
        this.rate = rate;
        this.packSize = packSize;
    }

    @Override
    public boolean canUse() {
        long worldTime = entity.level().getGameTime() % 10;
        if (worldTime != 0 && entity.getRandom().nextInt(reducedTickDelay(rate)) != 0) {
            return false;
        }
        if (!this.packAnimal.isPackFollower() && !this.packAnimal.hasPackFollower()) {
            double dist = 30D;
            List<? extends LivingEntity> list = entity.level().getEntitiesOfClass(entity.getClass(), entity.getBoundingBox().inflate(dist, dist, dist));
            LivingEntity closestTail = null;
            double value = Double.MAX_VALUE;
            for (LivingEntity animal : list) {
                if (!((PackAnimal) animal).hasPackFollower() && ((PackAnimal) animal).isValidLeader(((PackAnimal) animal).getPackLeader()) && !animal.getUUID().equals(entity.getUUID()) && !((PackAnimal) animal).isInPack(packAnimal) && ((PackAnimal) animal).getPackSize() < packSize) {
                    double distance = this.entity.distanceToSqr(animal);
                    if (!(distance > value)) {
                        value = distance;
                        closestTail = animal;
                    }
                }
            }
            if (closestTail == null) {
                return false;
            } else if (value < 1.0D) {
                return false;
            } else if (!packAnimal.isValidLeader(((PackAnimal) closestTail).getPackLeader())) {
                return false;
            } else {
                this.packAnimal.joinPackOf((PackAnimal) closestTail);
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.packAnimal.isPackFollower() && packAnimal.isValidLeader(packAnimal.getPackLeader())) {
            double distance = this.entity.distanceToSqr((LivingEntity) this.packAnimal.getPriorPackMember());
            if (distance > 676.0D) {
                if (this.distCheckCounter == 0) {
                    return false;
                }
            }
            if (this.distCheckCounter > 0) {
                this.distCheckCounter--;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void stop() {
        this.packAnimal.leavePack();
    }
}