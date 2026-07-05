package com.barlinc.unusual_prehistory.entity.mob.mesozoic;

import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingLookControl;
import com.barlinc.unusual_prehistory.entity.ai.control.PrehistoricSwimmingMoveControl;
import com.barlinc.unusual_prehistory.entity.ai.goals.IdleAnimationGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricBabyPanicGoal;
import com.barlinc.unusual_prehistory.entity.ai.goals.PrehistoricSwimGoal;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricAquaticMob;
import com.barlinc.unusual_prehistory.entity.mob.base.PrehistoricPartEntity;
import com.barlinc.unusual_prehistory.entity.utils.SmoothAnimationState;
import com.barlinc.unusual_prehistory.registry.UP2Entities;
import com.barlinc.unusual_prehistory.registry.UP2Items;
import com.barlinc.unusual_prehistory.registry.UP2SoundEvents;
import com.barlinc.unusual_prehistory.tags.UP2ItemTags;
import com.barlinc.unusual_prehistory.utils.UP2MobUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.entity.PartEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Leedsichthys extends PrehistoricAquaticMob {

    private final LeedsichthysPart headPart;
    private final LeedsichthysPart tailPart1;
    private final LeedsichthysPart tailPart2;
    private final LeedsichthysPart[] allParts;

    private boolean wasPreviouslyBaby;

    public final SmoothAnimationState gulpAnimationState = new SmoothAnimationState();
    public final SmoothAnimationState yawnAnimationState = new SmoothAnimationState();

    @SuppressWarnings("all")
    private float[] yawBuffer = new float[128];
    private int yawPointer = -1;
    private float fakeYRot;

    public Leedsichthys(EntityType<? extends PrehistoricAquaticMob> entityType, Level level) {
        super(entityType, level);
        this.switchShallowNavigation(false);
        this.moveControl = new PrehistoricSwimmingMoveControl(this, 45, 4, 0.02F);
        this.lookControl = new PrehistoricSwimmingLookControl(this, 6);
        this.headPart = new LeedsichthysPart(this);
        this.tailPart1 = new LeedsichthysPart(this);
        this.tailPart2 = new LeedsichthysPart(this);
        this.allParts = new LeedsichthysPart[]{headPart, tailPart1, tailPart2};
        this.fakeYRot = this.getYRot();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 300.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.65F)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PrehistoricBabyPanicGoal(this, 2.0D, 16, 8));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.2D, Ingredient.of(UP2ItemTags.DIET_PISCIVORE), false));
        this.goalSelector.addGoal(3, new PrehistoricSwimGoal(this, 1.0D, 60, 35, 15, 3, true));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new IdleAnimationGoal(this, 160, 1, false, 0.001F, this::canPlayIdles));
        this.goalSelector.addGoal(5, new IdleAnimationGoal(this, 160, 2, false, 0.001F, this::canPlayIdles));
    }

    @Override
    public float getAgeScale() {
        return this.isBaby() ? 0.1F : 1.0F;
    }

    @Override
    public int getHeadRotSpeed() {
        return 4;
    }

    private boolean canPlayIdles(Entity entity) {
        return entity.isInWaterOrBubble();
    }

    @Override
    public boolean isPushable() {
        return this.isBaby();
    }

    @Override
    public boolean shouldFlop() {
        return false;
    }

    @Override
    protected boolean shouldUseShallowNavigation() {
        return true;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(UP2ItemTags.DIET_PISCIVORE);
    }

    @Override
    public void travel(@NotNull Vec3 travelVector) {
        if (this.isEffectiveAi() && this.isInWater()) {
            UP2MobUtils.travelInWater(this, travelVector);
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        InteractionResult type = super.mobInteract(player, hand);
        if (itemStack.is(Tags.Items.TOOLS_SHEAR) && !this.isBaby() && !player.getCooldowns().isOnCooldown(itemStack.getItem())) {
            if (!this.level().isClientSide) {
                this.level().playSound(null, this, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 1.0F);
                this.gameEvent(GameEvent.SHEAR, player);
                if (!player.addItem(new ItemStack(UP2Items.LEEDSICHTHYS_SLICE.get()))) {
                    player.spawnAtLocation(UP2Items.LEEDSICHTHYS_SLICE.get());
                }
                itemStack.hurtAndBreak(2, player, getSlotForHand(hand));
                player.getCooldowns().addCooldown(itemStack.getItem(), player.getAbilities().instabuild ? 0 : 10);
            }
            return InteractionResult.SUCCESS;
        }
        return type;
    }

    @Override
    public void tick() {
        if (!this.isBaby()) {
            this.tickMultipart();
        }
        super.tick();

        this.fakeYRot = Mth.approachDegrees(fakeYRot, this.yBodyRot, 10);

        if (wasPreviouslyBaby != this.isBaby()) {
            this.wasPreviouslyBaby = this.isBaby();
            this.refreshDimensions();
            for (LeedsichthysPart part : this.allParts) {
                part.refreshDimensions();
            }
        }
    }

    @Override
    public void setupAnimationStates() {
        this.swimIdleAnimationState.animateWhen(this.isInWaterOrBubble(), this.tickCount);
        this.flopAnimationState.animateWhen(!this.isInWaterOrBubble(), this.tickCount);
        this.gulpAnimationState.animateWhen(this.getIdleState() == 1, this.tickCount);
        this.yawnAnimationState.animateWhen(this.getIdleState() == 2, this.tickCount);
    }

    private void tickMultipart() {
        if (yawPointer == -1) {
            this.fakeYRot = this.yBodyRot;
            for (int i = 0; i < yawBuffer.length; i++) {
                this.yawBuffer[i] = this.fakeYRot;
            }
        }
        if (++this.yawPointer == this.yawBuffer.length) {
            this.yawPointer = 0;
        }
        this.yawBuffer[this.yawPointer] = this.fakeYRot;

        Vec3[] vec3s = new Vec3[this.allParts.length];
        for (int j = 0; j < this.allParts.length; j++) {
            vec3s[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
        }

        Vec3 center = this.position().add(0, this.getBbHeight() * 0.5F, 0);
        float offset = 4.25F;
        this.headPart.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, offset), this.getXRot() * 0.33F, this.getYHeadRot()).add(center));
        this.tailPart1.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -offset), this.getXRot() * 0.33F, this.getYHeadRot()).add(center));
        this.tailPart2.setPosCenteredY(this.rotateOffsetVec(new Vec3(0, 0, -offset), this.getXRot() * 0.33F, this.getYHeadRot()).add(this.tailPart1.centeredPosition()));

        for (int l = 0; l < this.allParts.length; ++l) {
            this.allParts[l].xo = vec3s[l].x;
            this.allParts[l].yo = vec3s[l].y;
            this.allParts[l].zo = vec3s[l].z;
            this.allParts[l].xOld = vec3s[l].x;
            this.allParts[l].yOld = vec3s[l].y;
            this.allParts[l].zOld = vec3s[l].z;
        }
    }

    @Override
    public void remove(@NotNull RemovalReason removalReason) {
        super.remove(removalReason);
        if (allParts != null) {
            for (LeedsichthysPart part : allParts) {
                part.remove(RemovalReason.KILLED);
            }
        }
    }

    @Override
    public boolean isMultipartEntity() {
        return !this.isBaby();
    }

    @Override
    public PartEntity<?> @NotNull [] getParts() {
        return this.isBaby() ? super.getParts() : allParts;
    }

    private Vec3 rotateOffsetVec(Vec3 offset, float xRot, float yRot) {
        return offset.xRot(-xRot * ((float) Math.PI / 180F)).yRot(-yRot * ((float) Math.PI / 180F));
    }

    @Override
    public int getIdleAnimationCooldown(int idleState) {
        if (idleState == 1) {
            return 1400 + this.getRandom().nextInt(1400);
        }
        else if (idleState == 2) {
            return 1200 + this.getRandom().nextInt(1200);
        }
        else {
            throw new IllegalStateException("Unexpected value: " + idleState);
        }
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(6, 4, 6);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel serverLevel, @NotNull AgeableMob ageableMob) {
        return UP2Entities.LEEDSICHTHYS.get().create(serverLevel);
    }

    @Override
    @Nullable
    protected SoundEvent getAmbientSound() {
        return UP2SoundEvents.LEEDSICHTHYS_IDLE.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return UP2SoundEvents.LEEDSICHTHYS_DEATH.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return UP2SoundEvents.LEEDSICHTHYS_HURT.get();
    }

    @Override
    protected @NotNull SoundEvent getSwimSound() {
        if (this.isBaby()) {
            return super.getSwimSound();
        }
        return UP2SoundEvents.LEEDSICHTHYS_SWIM.get();
    }

    @Override
    protected void playSwimSound(float volume) {
        if (this.isBaby()) {
            super.playSwimSound(volume);
        } else {
            if (this.getRandom().nextFloat() < 0.33F) {
                super.playSwimSound(1.0F);
            }
        }
    }

    @Override
    public int getAmbientSoundInterval() {
        return 200;
    }

    @Override
    public float getSoundVolume() {
        return this.isBaby() ? 0.5F : 2.0F;
    }

    private static class LeedsichthysPart extends PrehistoricPartEntity<Leedsichthys> {

        public LeedsichthysPart(Leedsichthys parent) {
            super(parent, 4.5F, 4.5F);
        }
    }
}