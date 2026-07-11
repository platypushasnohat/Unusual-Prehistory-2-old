package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class UP2Particles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, UnusualPrehistory2.MOD_ID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GINKGO_LEAVES = registerParticle("ginkgo_leaves", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GOLDEN_GINKGO_LEAVES = registerParticle("golden_ginkgo_leaves", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> EEPY = registerParticle("eepy", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> OOZE_BUBBLE = registerParticle("ooze_bubble", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> TAR_BUBBLE = registerParticle("tar_bubble", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GOLDEN_HEART = registerParticle("golden_heart", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SNOWFLAKE = registerParticle("snowflake", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> IMPACT_STUN = registerParticle("impact_stun", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> OUT_OF_WATER_BUBBLE = registerParticle("out_of_water_bubble", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SWEET_GROG = registerParticle("sweet_grog", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> FOUL_GROG = registerParticle("foul_grog", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> SAND_SNORT = registerParticle("sand_snort", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> TUSOTEUTHIS_FLASH = registerParticle("tusoteuthis_flash", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> STUN = registerParticle("stun", ()-> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> DAZZLE = registerParticle("dazzle", ()-> new SimpleParticleType(false));

    private static <P extends ParticleType<?>> DeferredHolder<ParticleType<?>, P> registerParticle(String name, Supplier<P> particle ) {
        return PARTICLE_TYPES.register(name, particle);
    }
}
