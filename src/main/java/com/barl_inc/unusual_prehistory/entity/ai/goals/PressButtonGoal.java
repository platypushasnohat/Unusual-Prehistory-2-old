package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.utils.ButtonPressingMob;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.NotNull;

public class PressButtonGoal extends MoveToBlockGoal {

    protected final ButtonPressingMob buttonPushingMob;
    protected int ticksWaited = 0;

    public PressButtonGoal(PathfinderMob mob, double speedModifier, int searchRange, int verticalSearchRange) {
        super(mob, speedModifier, searchRange, verticalSearchRange);
        this.buttonPushingMob = (ButtonPressingMob) mob;
    }

    @Override
    public void start() {
        super.start();
        this.ticksWaited = 0;
    }

    @Override
    public void stop() {
        super.stop();
        buttonPushingMob.setPushButtonCooldown(200 + mob.getRandom().nextInt(200));
    }

    @Override
    public boolean canUse() {
        if (mob instanceof ButtonPressingMob) {
            return super.canUse();
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && buttonPushingMob.canPushButton();
    }

    @Override
    public void tick() {
        this.ticksWaited++;
        if (this.isReachedTarget() && ticksWaited > 30) {
            this.onReachedTarget();
        }
        super.tick();
    }

    protected void onReachedTarget() {
        if (EventHooks.canEntityGrief(mob.level(), mob)) {
            BlockState blockstate = mob.level().getBlockState(blockPos);
            if (blockstate.getBlock() instanceof ButtonBlock && !blockstate.getValue(ButtonBlock.POWERED)) {
                this.pushButton();
                this.stop();
            }
        }
        this.ticksWaited = 0;
    }

    @Override
    protected boolean isReachedTarget() {
        double x = mob.getX() - (blockPos.getX() + 0.5D);
        double y = mob.getEyeY() - (blockPos.getY() + 0.5D);
        double z = mob.getZ() - (blockPos.getZ() + 0.5D);
        return (x * x + y * y + z * z) < 2.25D;
    }

    @Override
    protected @NotNull BlockPos getMoveToTarget() {
        return blockPos;
    }

    @Override
    protected boolean isValidTarget(LevelReader level, @NotNull BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.getBlock() instanceof ButtonBlock && !state.getValue(ButtonBlock.POWERED);
    }

    private void pushButton() {
        ((ButtonPressingMob) mob).pressButton();
        this.nextStartTick = this.nextStartTick(mob);
        BlockState state = mob.level().getBlockState(blockPos);
        ((ButtonBlock) state.getBlock()).press(state, mob.level(), blockPos, null);
    }
}