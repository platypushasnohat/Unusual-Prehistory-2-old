package com.barl_inc.unusual_prehistory.events;

import com.barl_inc.unusual_prehistory.entity.accessor.MobAccessor;
import com.barl_inc.unusual_prehistory.entity.ai.goals.PrehistoricAvoidEntityGoal;
import com.barl_inc.unusual_prehistory.entity.ai.goals.WololoSpellGoal;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.Leptictidium;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Kentrosaurus;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Majungasaurus;
import com.barl_inc.unusual_prehistory.entity.mob.mesozoic.Ulughbegsaurus;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.ArthropleuraPart;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Dunkleosteus;
import com.barl_inc.unusual_prehistory.registry.UP2DamageTypes;
import com.barl_inc.unusual_prehistory.registry.UP2Entities;
import com.barl_inc.unusual_prehistory.registry.UP2MobEffects;
import com.barl_inc.unusual_prehistory.tags.UP2BlockTags;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.level.BlockEvent;

@EventBusSubscriber()
public class NeoEvents {

    @SubscribeEvent
    @SuppressWarnings("DataFlowIssue")
    public static void onBabySpawn(BabyEntitySpawnEvent event) {
        if (event.getParentA() instanceof Cow parent && event.getParentB() instanceof Cow) {
            Level level = parent.getCommandSenderWorld();
            if (level.getRandom().nextInt(10000) == 0) {
                event.setChild(UP2Entities.UNICORN.get().create(level));
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityJoinWorld(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Mob mob) {
//            if (mob instanceof Evoker evoker) {
//                evoker.goalSelector.addGoal(6, new WololoSpellGoal<>(evoker, Ulughbegsaurus.class, (livingEntity) -> ((Ulughbegsaurus) livingEntity).getVariant() == Ulughbegsaurus.UlughbegsaurusVariant.BLUE) {
//                    @Override
//                    protected void performSpellCasting() {
//                        LivingEntity wololoTarget = evokerAccess.unusualPrehistory$getLivingWololoTarget();
//                        if (wololoTarget != null && wololoTarget.isAlive() && wololoTarget instanceof Ulughbegsaurus ulughbegsaurus) {
//                            ulughbegsaurus.setVariant(Ulughbegsaurus.UlughbegsaurusVariant.RED);
//                        }
//                    }
//                });
//            }
            if (mob instanceof Guardian guardian) {
                guardian.goalSelector.addGoal(3, new AvoidEntityGoal<>(guardian, Dunkleosteus.class, 12.0F, 1.5D, 1.5D));
            }
            if (mob instanceof PathfinderMob pathfinderMob && pathfinderMob.getType().is(EntityTypeTags.ARTHROPOD)) {
                if (pathfinderMob instanceof PrehistoricMob prehistoricMob) {
                    prehistoricMob.goalSelector.addGoal(1, new PrehistoricAvoidEntityGoal<>(prehistoricMob, Leptictidium.class, 12.0F, 1.5D, false));
                } else {
                    pathfinderMob.goalSelector.addGoal(1, new AvoidEntityGoal<>(pathfinderMob, Leptictidium.class, 12.0F, 1.5D, 1.5D));
                }
            }
        }
    }

    @SubscribeEvent
    public static void breakBlock(BlockEvent.BreakEvent event) {
        BlockState state = event.getState();
        if (state.is(UP2BlockTags.GUARDED_BY_KENTROSAURUS)) {
            Kentrosaurus.angerNearbyKentrosaurus(event.getPlayer(), false);
        }
    }

    @SubscribeEvent
    public static void onMobHurt(LivingDamageEvent.Pre event) {
        LivingEntity entity = event.getEntity();
        DamageSource damageSource = event.getSource();

        if (entity instanceof Guardian && damageSource.getEntity() instanceof Dunkleosteus) {
            event.setNewDamage(event.getOriginalDamage() * 2);
        }
        if (entity.getType().is(EntityTypeTags.ARTHROPOD) && damageSource.getEntity() instanceof Leptictidium) {
            event.setNewDamage(event.getOriginalDamage() * 2);
        }
    }

    @SubscribeEvent
    public static void onLivingVisibility(LivingEvent.LivingVisibilityEvent event) {
        LivingEntity entity = event.getEntity();
        if (event.getLookingEntity() != null) {
            if (entity instanceof Majungasaurus majungasaurus) {
                if (majungasaurus.isCamo()) event.modifyVisibility(0.25F);
            }
        }
    }

    @SubscribeEvent
    public static void setTargetEvent(LivingChangeTargetEvent event){
        if (event.getNewAboutToBeSetTarget() != null){
            if (event.getNewAboutToBeSetTarget() instanceof Majungasaurus majungasaurus && majungasaurus.isCamo()) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (event.getSource().is(UP2DamageTypes.EXECUTE) && !entity.level().isClientSide) {
            if (entity instanceof Player player) {
                ItemStack itemstack = new ItemStack(Items.PLAYER_HEAD);
                GameProfile profile = player.getGameProfile();
                itemstack.set(DataComponents.PROFILE, new ResolvableProfile(profile));
                player.spawnAtLocation(itemstack);
            } else if (entity instanceof Mob mob && mob.level().getRandom().nextFloat() < 0.1F) {
                Creeper fakeCreeperForSkullDrop = EntityType.CREEPER.create(mob.level());
                if (fakeCreeperForSkullDrop != null) {
                    if (event.getEntity().level() instanceof ServerLevel serverLevel) {
                        LightningBolt fakeThunder = EntityType.LIGHTNING_BOLT.create(serverLevel);
                        if (fakeThunder != null) {
                            fakeThunder.setVisualOnly(true);
                            fakeCreeperForSkullDrop.thunderHit(serverLevel, fakeThunder);
                        }
                    }
                    DamageSource fakeCreeperDamage = mob.level().damageSources().mobAttack(fakeCreeperForSkullDrop);
                    MobAccessor accessor = (MobAccessor) mob;
                    if (mob.level() instanceof ServerLevel serverLevel2) {
                        accessor.unusualPrehistory$dropCustomDeathLoot(serverLevel2, fakeCreeperDamage, false);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(UP2MobEffects.PARALYSIS)) {
            entity.setDeltaMovement(entity.getDeltaMovement().x(), 0.0D, entity.getDeltaMovement().z());
        }
    }

    @SubscribeEvent
    public static void entityInvulnerability(EntityInvulnerabilityCheckEvent event) {
        Entity entity = event.getEntity();
        DamageSource source = event.getSource();
        if (entity.getVehicle() instanceof ArthropleuraPart && source.is(DamageTypes.IN_WALL)) {
            event.setInvulnerable(true);
        }
    }
}
