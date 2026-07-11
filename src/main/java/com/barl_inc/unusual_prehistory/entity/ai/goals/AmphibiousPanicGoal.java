package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class AmphibiousPanicGoal extends PrehistoricPanicGoal {

    public AmphibiousPanicGoal(PrehistoricMob mob, double speedModifier) {
        this(mob, speedModifier, 10, 4, true);
    }

    public AmphibiousPanicGoal(PrehistoricMob mob, double speedModifier, int radius, int height) {
        this(mob, speedModifier, radius, height, true);
    }

    public AmphibiousPanicGoal(PrehistoricMob mob, double speedModifier, int radius, int height, boolean shouldEscapeToWater) {
        super(mob, speedModifier, radius, height, shouldEscapeToWater);
    }

    @Override
    protected boolean findRandomPosition() {
        Vec3 vec3 = DefaultRandomPos.getPos(prehistoricMob, radius, height);
        if (prehistoricMob.getLastHurtByMob() != null) {
            vec3 = DefaultRandomPos.getPosAway(prehistoricMob, radius, height, prehistoricMob.getLastHurtByMob().position());
        }
        if (vec3 == null) {
            return false;
        } else {
            this.posX = vec3.x;
            this.posY = vec3.y;
            this.posZ = vec3.z;
            return true;
        }
    }
}
