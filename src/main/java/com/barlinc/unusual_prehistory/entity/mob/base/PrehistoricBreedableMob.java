package com.barlinc.unusual_prehistory.entity.mob.base;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class PrehistoricBreedableMob extends PrehistoricMob {

    protected PrehistoricBreedableMob(EntityType<? extends PrehistoricMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public int getBaseExperienceReward() {
        return 1 + this.level().getRandom().nextInt(3);
    }

    @Override
    public boolean shouldDropExperience() {
        return !this.isBaby();
    }

    @Override
    public boolean canFallInLove() {
        return this.getInLoveTime() <= 0;
    }

    @Override
    public boolean canMate(@NotNull Animal animal) {
        return true;
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.getEatTicks() <= 0 && this.isFood(itemstack) ) {
            int i = this.getAge();
            if (!this.level().isClientSide && i == 0 && this.canFallInLove()) {
                this.usePlayerItem(player, hand, itemstack);
                this.playSound(this.getEatingSound());
                this.setInLove(player);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void finalizeSpawnChildFromBreeding(ServerLevel level, Animal animal, @Nullable AgeableMob baby) {
        Optional.ofNullable(this.getLoveCause()).or(() -> Optional.ofNullable(animal.getLoveCause())).ifPresent((serverPlayer) -> {
            serverPlayer.awardStat(Stats.ANIMALS_BRED);
            CriteriaTriggers.BRED_ANIMALS.trigger(serverPlayer, this, animal, baby);
        });
        this.setAge(6000);
        animal.setAge(6000);
        this.resetLove();
        animal.resetLove();
        level.broadcastEntityEvent(this, (byte) 18);
        if (level.getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            level.addFreshEntity(new ExperienceOrb(level, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
        }
    }
}
