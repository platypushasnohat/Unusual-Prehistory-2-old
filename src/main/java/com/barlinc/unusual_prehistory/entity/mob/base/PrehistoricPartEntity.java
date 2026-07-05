package com.barlinc.unusual_prehistory.entity.mob.base;

import com.barlinc.unusual_prehistory.network.MultipartEntityPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.entity.PartEntity;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public abstract class PrehistoricPartEntity<T extends PrehistoricMob> extends PartEntity<T> {

    private final EntityDimensions dimensions;

    public PrehistoricPartEntity(T parent, float width, float height) {
        super(parent);
        this.dimensions = EntityDimensions.scalable(width, height);
        this.refreshDimensions();
        this.blocksBuilding = true;
    }

    public void setPosCenteredY(Vec3 pos) {
        this.setPos(pos.x, pos.y - this.getBbHeight() * 0.5F, pos.z);
    }

    public Vec3 centeredPosition() {
        return this.position().add(0, this.getBbHeight() * 0.5F, 0);
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox().inflate(1.0D);
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return dimensions.scale(this.getParent().getAgeScale());
    }

    @Override
    public void push(double x, double y, double z) {
        this.getParent().push(x, y, z);
    }

    @Override
    public Entity getRootVehicle() {
        return this.getParent().getRootVehicle();
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        Entity parent = this.getParent();
        if (player.level().isClientSide) {
            PacketDistributor.sendToServer(new MultipartEntityPacket(parent.getId(), player.getId(), 0));
        }
        return parent.interact(player, hand);
    }

    @Override
    public boolean canBeCollidedWith() {
        return this.getParent().canBeCollidedWith();
    }

    @Override
    public boolean isPickable() {
        return this.getParent().isPickable();
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return this.getParent().getPickResult();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity parent = this.getParent();
        if (!this.isInvulnerableTo(source)) {
            Entity attacker = source.getEntity();
            if (attacker != null && attacker.level().isClientSide) {
                PacketDistributor.sendToServer(new MultipartEntityPacket(parent.getId(), attacker.getId(), 1));
                return true;
            } else if (attacker == null || !attacker.level().isClientSide) {
                return parent.hurt(source, amount);
            }
        }
        return false;
    }

    @Override
    public boolean is(Entity entity) {
        return this == entity || this.getParent() == entity;
    }

    @Override
    public boolean save(CompoundTag compoundTag) {
        return false;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
    }
}
