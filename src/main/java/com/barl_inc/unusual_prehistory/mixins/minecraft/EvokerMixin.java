package com.barl_inc.unusual_prehistory.mixins.minecraft;

import com.barl_inc.unusual_prehistory.entity.accessor.EvokerAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Evoker;
import net.minecraft.world.entity.monster.SpellcasterIllager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nullable;

@Mixin(Evoker.class)
public abstract class EvokerMixin extends SpellcasterIllager implements EvokerAccessor {

    @Unique
    @Nullable
    private LivingEntity unusualPrehistory$livingWololoTarget;

    protected EvokerMixin(EntityType<? extends SpellcasterIllager> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void unusualPrehistory$setLivingWololoTarget(@Nullable LivingEntity entity) {
        this.unusualPrehistory$livingWololoTarget = entity;
    }

    @Nullable
    @Override
    public LivingEntity unusualPrehistory$getLivingWololoTarget() {
        return this.unusualPrehistory$livingWololoTarget;
    }

    @Override
    public void unusualPrehistory$setSpellCastingTickCount(int spellCastingTickCount) {
        this.spellCastingTickCount = spellCastingTickCount;
    }
}
