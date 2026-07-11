package com.barl_inc.unusual_prehistory.entity.ai.goals;

import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

import java.util.EnumSet;

public class PrehistoricOwnerHurtByTargetGoal extends TargetGoal {

   protected final PrehistoricMob tamedMob;
   protected LivingEntity ownerLastHurtBy;
   private int timestamp;

   public PrehistoricOwnerHurtByTargetGoal(PrehistoricMob tamedMob) {
      super(tamedMob, false);
      this.tamedMob = tamedMob;
      this.setFlags(EnumSet.of(Flag.TARGET));
   }

   @Override
   public boolean canUse() {
      if (this.tamedMob.isTame() && !this.tamedMob.isOrderedToSit()) {
         LivingEntity livingentity = this.tamedMob.getOwner();
         if (livingentity == null) {
            return false;
         } else {
            this.ownerLastHurtBy = livingentity.getLastHurtByMob();
            int i = livingentity.getLastHurtByMobTimestamp();
            return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT) && this.tamedMob.wantsToAttack(this.ownerLastHurtBy, livingentity);
         }
      } else {
         return false;
      }
   }

   @Override
   public void start() {
      this.mob.setTarget(this.ownerLastHurtBy);
      LivingEntity livingentity = this.tamedMob.getOwner();
      if (livingentity != null) {
         this.timestamp = livingentity.getLastHurtByMobTimestamp();
      }
      super.start();
   }
}