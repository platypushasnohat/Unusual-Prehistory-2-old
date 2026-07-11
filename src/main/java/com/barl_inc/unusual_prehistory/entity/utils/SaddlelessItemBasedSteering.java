package com.barl_inc.unusual_prehistory.entity.utils;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

public class SaddlelessItemBasedSteering {

   private final SynchedEntityData entityData;
   private final EntityDataAccessor<Integer> boostTimeAccessor;
   private boolean boosting;
   private int boostTime;

   public SaddlelessItemBasedSteering(SynchedEntityData entityData, EntityDataAccessor<Integer> dataAccessor) {
      this.entityData = entityData;
      this.boostTimeAccessor = dataAccessor;
   }

   public void onSynced() {
      this.boosting = true;
      this.boostTime = 0;
   }

   public boolean boost(RandomSource random) {
      if (this.boosting) {
         return false;
      } else {
         this.boosting = true;
         this.boostTime = 0;
         this.entityData.set(this.boostTimeAccessor, random.nextInt(900) + 200);
         return true;
      }
   }

   public void tickBoost() {
      if (this.boosting && this.boostTime++ > this.boostTimeTotal()) {
         this.boosting = false;
      }
   }

   public float boostFactor() {
      return this.boosting ? 1.0F + 1.15F * Mth.sin((float) this.boostTime / (float) this.boostTimeTotal() * (float) Math.PI) : 1.0F;
   }

    public float getBoostProgress() {
        return this.boosting ? (float) this.boostTime / (float) this.boostTimeTotal() : 0.0F;
    }

   private int boostTimeTotal() {
      return this.entityData.get(this.boostTimeAccessor);
   }

   public boolean isBoosting() {
       return this.boosting;
   }
}