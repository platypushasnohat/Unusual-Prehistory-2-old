package com.barl_inc.unusual_prehistory.entity.ai.goals.update_4;

import com.barl_inc.unusual_prehistory.entity.mob.multi_era.Lystrosaurus;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.EnumSet;

public class LystrosaurusRunLikeCrazyGoal extends Goal {

    protected final Lystrosaurus lystrosaurus;
    protected double wantedX;
    protected double wantedY;
    protected double wantedZ;

    public LystrosaurusRunLikeCrazyGoal(Lystrosaurus lystrosaurus) {
        this.lystrosaurus = lystrosaurus;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.lystrosaurus.isVehicle()) {
            return false;
        } else {
            Vec3 vec3 = this.getPosition();
            if (vec3 == null) {
                return false;
            } else if (this.lystrosaurus.getHealth() <= this.lystrosaurus.getMaxHealth() * 0.6F) {
                this.wantedX = vec3.x;
                this.wantedY = vec3.y;
                this.wantedZ = vec3.z;
                return true;
            }
        }
        return false;
    }

    @Nullable
    protected Vec3 getPosition() {
        return DefaultRandomPos.getPos(this.lystrosaurus, 15, 7);
    }

    @Override
    public boolean canContinueToUse() {
        return !this.lystrosaurus.isVehicle() && !this.lystrosaurus.getNavigation().isDone();
    }

    @Override
    public void start() {
        this.lystrosaurus.setRunning(true);
        this.lystrosaurus.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, 2.0D);
    }

    @Override
    public void tick() {
        this.lystrosaurus.getLookControl().setLookAt(this.wantedX, this.wantedY, this.wantedZ, 30F, 30F);
    }

    @Override
    public void stop() {
        this.lystrosaurus.setRunning(false);
        this.lystrosaurus.getNavigation().stop();
    }
}