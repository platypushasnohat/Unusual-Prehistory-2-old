package com.barl_inc.unusual_prehistory.items;

import com.barl_inc.unusual_prehistory.entity.projectile.ThrowableEgg;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.function.Supplier;

public class ThrowableEggItem extends Item {

    private final Random random = new Random();
    private final Supplier<? extends EntityType<?>> toSpawn;

    public ThrowableEggItem(Properties properties, Supplier<? extends EntityType<?>> toSpawn) {
        super(properties);
        this.toSpawn = toSpawn;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.gameEvent(GameEvent.ITEM_INTERACT_START);

        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
        
        if (!level.isClientSide) {
            if (toSpawn.get() == null) return InteractionResultHolder.fail(itemstack);
            Entity entity = toSpawn.get().create(level);
            if (entity instanceof ThrowableEgg egg) {
                egg.setOwner(player);
                egg.setPos(player.getX(), player.getEyeY() - (double)0.1F, player.getZ());
                egg.setItem(itemstack);
                egg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(egg);
            }
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
