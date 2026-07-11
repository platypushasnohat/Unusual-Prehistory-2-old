package com.barl_inc.unusual_prehistory.entity.ai.goals.update_6;

import com.barl_inc.unusual_prehistory.entity.ai.goals.AttackGoal;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Lorrainosaurus;
import com.barl_inc.unusual_prehistory.entity.utils.UP2Poses;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class LorrainosaurusAttackGoal extends AttackGoal {

    private final Lorrainosaurus lorrainosaurus;
    private Vec3 wantedPos = null;

    public LorrainosaurusAttackGoal(Lorrainosaurus lorrainosaurus) {
        super(lorrainosaurus);
        this.lorrainosaurus = lorrainosaurus;
    }

    @Override
    public void start() {
        super.start();
        this.lorrainosaurus.setGrabTime(150 + lorrainosaurus.getRandom().nextInt(50));
    }

    @Override
    public void tick() {
        LivingEntity target = lorrainosaurus.getTarget();
        if (target != null) {
            double distance = lorrainosaurus.distanceToSqr(target);
            int attackState = lorrainosaurus.getAttackState();

            if (wantedPos != null) {
                this.lorrainosaurus.getNavigation().moveTo(wantedPos.x, wantedPos.y, wantedPos.z, 1.5D);
            } else {
                this.lorrainosaurus.lookAt(target, 30F, 30F);
                this.lorrainosaurus.getLookControl().setLookAt(target, 30F, 30F);
            }

            if (attackState == 1) {
                this.tickBite();
            } else if (attackState == 2) {
                this.tickGrab();
            } else {
                if (distance <= this.getAttackReachSqr(target, 1.6D) && lorrainosaurus.biteCooldown == 0 && !this.canGrab(target)) {
                    this.lorrainosaurus.setAttackState(1);
                } else if (distance <= this.getAttackReachSqr(target, 1.3D) && this.canGrab(target)) {
                    this.lorrainosaurus.setAttackState(2);
                } else {
                    this.lorrainosaurus.getNavigation().moveTo(target, 1.5D);
                }
                if (!target.isInWaterOrBubble() && lorrainosaurus.horizontalCollision && lorrainosaurus.isInWaterOrBubble() && lorrainosaurus.tickCount % 2 == 0) {
                    float rot = lorrainosaurus.getYRot() * ((float) Math.PI / 180F);
                    this.lorrainosaurus.getNavigation().stop();
                    this.lorrainosaurus.setDeltaMovement(lorrainosaurus.getDeltaMovement().add(-Mth.sin(rot) * 0.4F, 0.2D, Mth.cos(rot) * 0.4F));
                }
            }
        }
    }

    protected boolean canGrab(LivingEntity target) {
        return !target.isInWaterOrBubble() && lorrainosaurus.canPickUpTarget(target) && lorrainosaurus.grabCooldown == 0;
    }

    protected void tickBite() {
        this.timer++;
        LivingEntity target = lorrainosaurus.getTarget();
        this.lorrainosaurus.getNavigation().stop();
        if (timer == 1) {
            this.lorrainosaurus.setPose(UP2Poses.ATTACKING.get());
            this.lorrainosaurus.playSound(UP2SoundEvents.LORRAINOSAURUS_ATTACK.get(), 1.0F, 1.0F * lorrainosaurus.getRandom().nextFloat() * 0.2F);
        }
        if (timer == 10 && this.isInAttackRange(target, 2.0D)) {
            this.lorrainosaurus.doHurtTarget(target);
            this.strongKnockback(target, 0.4D, 0.1D);
        }
        if (timer > 20) {
            this.timer = 0;
            this.lorrainosaurus.setPose(Pose.STANDING);
            this.lorrainosaurus.setAttackState(0);
            this.lorrainosaurus.biteCooldown = 10;
        }
    }

    protected void tickGrab() {
        this.timer++;
        LivingEntity target = lorrainosaurus.getTarget();
        if (timer == 1) {
            this.lorrainosaurus.getNavigation().stop();
            this.lorrainosaurus.setPose(UP2Poses.GRAB_START.get());
            this.lorrainosaurus.playSound(UP2SoundEvents.LORRAINOSAURUS_ATTACK.get(), 1.0F, 0.9F * lorrainosaurus.getRandom().nextFloat() * 0.2F);
        }
        if (timer == 10) {
            if (this.isInAttackRange(target, 2.0D)) {
                this.lorrainosaurus.setHeldMobId(target.getId());
            }
        }
        if (timer > 10 && timer <= lorrainosaurus.getGrabTime() && lorrainosaurus.getHeldMobId() != -1 && lorrainosaurus.getNavigation().isDone()) {
            Vec3 vec3 = this.getPosition();
            if (vec3 != null) {
                this.wantedPos = vec3;
            }
        }
        if (timer > lorrainosaurus.getGrabTime() || (timer > 11 && lorrainosaurus.getHeldMobId() == -1)) {
            this.timer = 0;
            this.wantedPos = null;
            this.lorrainosaurus.setAttackState(0);
            this.lorrainosaurus.setPose(Pose.STANDING);
            this.lorrainosaurus.getNavigation().stop();
            this.lorrainosaurus.grabCooldown = 150 + lorrainosaurus.getRandom().nextInt(100);
            this.lorrainosaurus.biteCooldown = 10;
            if (lorrainosaurus.getHeldMobId() != -1) {
                this.lorrainosaurus.setHeldMobId(-1);
            }
        }
    }

    @Nullable
    private Vec3 getPosition() {
        Vec3 vec3 = null;
        if (lorrainosaurus.isInWaterOrBubble()) {
            vec3 = BehaviorUtils.getRandomSwimmablePos(lorrainosaurus, 30, 15);
        } else {
            BlockPos blockPos = lorrainosaurus.blockPosition();
            BlockPos.MutableBlockPos mutablePos = blockPos.mutable();
            for (int i = 0; i < 10; i++) {
                mutablePos.set(blockPos.getX() + lorrainosaurus.getRandom().nextInt(20) - 10, blockPos.getY() + lorrainosaurus.getRandom().nextInt(20) - 10, blockPos.getZ() + lorrainosaurus.getRandom().nextInt(20) - 10);
                if (lorrainosaurus.level().getFluidState(mutablePos).is(FluidTags.WATER)) {
                    vec3 = Vec3.atCenterOf(mutablePos);
                    break;
                }
            }
            return vec3;
        }
        if (vec3 == null) {
            Vec3 randomPos = DefaultRandomPos.getPos(lorrainosaurus, 10, 7);
            if (randomPos != null) {
                vec3 = randomPos;
            }
        }
        return vec3;
    }
}