package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class UP2DamageTypes {

    public static final ResourceKey<DamageType> EXECUTE = register("execute");
    public static final ResourceKey<DamageType> GRUG = register("grug");

    public static DamageSource execute(Level level, Entity source, @Nullable Entity causingEntity) {
        return level.damageSources().source(EXECUTE, source, causingEntity);
    }

    public static DamageSource grug(Level level, Entity source, @Nullable Entity causingEntity) {
        return level.damageSources().source(GRUG, source, causingEntity);
    }

    public static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, UnusualPrehistory2.modPrefix(name));
    }
}
