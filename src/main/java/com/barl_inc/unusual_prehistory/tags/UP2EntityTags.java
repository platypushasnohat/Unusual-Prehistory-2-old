package com.barl_inc.unusual_prehistory.tags;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class UP2EntityTags {

    // Update 1
    public static final TagKey<EntityType<?>> CARNOTAURUS_TARGETS = modEntityTag("carnotaurus_targets");
    public static final TagKey<EntityType<?>> DROMAEOSAURUS_TARGETS = modEntityTag("dromaeosaurus_targets");
    public static final TagKey<EntityType<?>> SMALL_DUNKLEOSTEUS_TARGETS = modEntityTag("small_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> MEDIUM_DUNKLEOSTEUS_TARGETS = modEntityTag("medium_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> LARGE_DUNKLEOSTEUS_TARGETS = modEntityTag("large_dunkleosteus_targets");
    public static final TagKey<EntityType<?>> MAJUNGASAURUS_TARGETS = modEntityTag("majungasaurus_targets");
    public static final TagKey<EntityType<?>> MEGALANIA_TARGETS = modEntityTag("megalania_targets");
    public static final TagKey<EntityType<?>> STETHACANTHUS_TARGETS = modEntityTag("stethacanthus_targets");

    public static final TagKey<EntityType<?>> DIPLOCAULUS_AVOIDS = modEntityTag("diplocaulus_avoids");
    public static final TagKey<EntityType<?>> JAWLESS_FISH_AVOIDS = modEntityTag("jawless_fish_avoids");
    public static final TagKey<EntityType<?>> KIMMERIDGEBRACHYPTERAESCHNIDIUM_AVOIDS = modEntityTag("kimmeridgebrachypteraeschnidium_avoids");
    public static final TagKey<EntityType<?>> KIMMERIDGEBRACHYPTERAESCHNIDIUM_NYMPH_AVOIDS = modEntityTag("kimmeridgebrachypteraeschnidium_nymph_avoids");
    public static final TagKey<EntityType<?>> MAJUNGASAURUS_AVOIDS = modEntityTag("majungasaurus_avoids");
    public static final TagKey<EntityType<?>> STETHACANTHUS_AVOIDS = modEntityTag("stethacanthus_avoids");
    public static final TagKey<EntityType<?>> TALPANAS_AVOIDS = modEntityTag("talpanas_avoids");
    public static final TagKey<EntityType<?>> TELECREX_AVOIDS = modEntityTag("telecrex_avoids");

    public static final TagKey<EntityType<?>> CARNOTAURUS_IGNORES = modEntityTag("carnotaurus_ignores");

    public static final TagKey<EntityType<?>> SWEET_BERRY_BUSH_IMMUNE = modEntityTag("sweet_berry_bush_immune");

    // Update 3
    public static final TagKey<EntityType<?>> METRIORHYNCHUS_TARGETS = modEntityTag("metriorhynchus_targets");

    public static final TagKey<EntityType<?>> TARTUOSTEUS_AVOIDS = modEntityTag("tartuosteus_avoids");

    public static final TagKey<EntityType<?>> METRIORHYNCHUS_CANT_GRAB = modEntityTag("metriorhynchus_cant_grab");
    public static final TagKey<EntityType<?>> METRIORHYNCHUS_CAN_GRAB = modEntityTag("metriorhynchus_can_grab");

    // Update 4
    public static final TagKey<EntityType<?>> KAPROSUCHUS_TARGETS = modEntityTag("kaprosuchus_targets");
    public static final TagKey<EntityType<?>> PACHYCEPHALOSAURUS_TARGETS = modEntityTag("pachycephalosaurus_targets");
    public static final TagKey<EntityType<?>> PACHYCEPHALOSAURUS_TARGETS_TO_KILL = modEntityTag("pachycephalosaurus_targets_to_kill");
    public static final TagKey<EntityType<?>> PRAEPUSA_TARGETS = modEntityTag("praepusa_targets");
    public static final TagKey<EntityType<?>> ULUGHBEGSAURUS_TARGETS = modEntityTag("ulughbegsaurus_targets");

    public static final TagKey<EntityType<?>> COELACANTHUS_AVOIDS = modEntityTag("coelacanthus_avoids");
    public static final TagKey<EntityType<?>> LEPTICTIDIUM_AVOIDS = modEntityTag("leptictidium_avoids");
    public static final TagKey<EntityType<?>> PRAEPUSA_AVOIDS = modEntityTag("praepusa_avoids");

    public static final TagKey<EntityType<?>> LEPTICTIDIUM_DOESNT_TARGET = modEntityTag("leptictidium_doesnt_target");

    public static final TagKey<EntityType<?>> COELACANTHUS_NEVER_EATS = modEntityTag("coelacanthus_never_eats");
    public static final TagKey<EntityType<?>> COELACANTHUS_ALWAYS_EATS = modEntityTag("coelacanthus_always_eats");

    // Update 5
    public static final TagKey<EntityType<?>> PSILOPTERUS_KICK_TARGETS = modEntityTag("psilopterus_kick_targets");
    public static final TagKey<EntityType<?>> MEDIUM_PSILOPTERUS_PACK_TARGETS = modEntityTag("medium_psilopterus_pack_targets");
    public static final TagKey<EntityType<?>> LARGE_PSILOPTERUS_PACK_TARGETS = modEntityTag("large_psilopterus_pack_targets");

    public static final TagKey<EntityType<?>> PALEOZOIC_MOBS = modEntityTag("paleozoic_mobs");
    public static final TagKey<EntityType<?>> MESOZOIC_MOBS = modEntityTag("mesozoic_mobs");
    public static final TagKey<EntityType<?>> CENOZOIC_MOBS = modEntityTag("cenozoic_mobs");

    // Update 6
    public static final TagKey<EntityType<?>> CONCAVENATOR_TARGETS = modEntityTag("concavenator_targets");
    public static final TagKey<EntityType<?>> MEDIUM_CONCAVENATOR_PACK_TARGETS = modEntityTag("medium_concavenator_pack_targets");
    public static final TagKey<EntityType<?>> LARGE_CONCAVENATOR_PACK_TARGETS = modEntityTag("large_concavenator_pack_targets");
    public static final TagKey<EntityType<?>> CRYPTOCLIDUS_TARGETS = modEntityTag("cryptoclidus_targets");
    public static final TagKey<EntityType<?>> LORRAINOSAURUS_TARGETS = modEntityTag("lorrainosaurus_targets");
    public static final TagKey<EntityType<?>> PROGNATHODON_TARGETS = modEntityTag("prognathodon_targets");
    public static final TagKey<EntityType<?>> PROGNATHODON_FIGHT_TARGETS = modEntityTag("prognathodon_fight_targets");
    public static final TagKey<EntityType<?>> THYLACINE_TARGETS = modEntityTag("thylacine_targets");

    public static final TagKey<EntityType<?>> GASTRIC_BROODING_FROG_TARGETS = modEntityTag("gastric_brooding_frog_targets");

    public static final TagKey<EntityType<?>> ARTHROPLEURA_CANT_CARRY = modEntityTag("arthropleura_cant_carry");
    public static final TagKey<EntityType<?>> ARTHROPLEURA_CAN_CARRY = modEntityTag("arthropleura_can_carry");

    public static final TagKey<EntityType<?>> CONCAVENATOR_AVOIDS = modEntityTag("concavenator_avoids");
    public static final TagKey<EntityType<?>> THYLACINE_AVOIDS = modEntityTag("thylacine_avoids");

    public static final TagKey<EntityType<?>> LORRAINOSAURUS_CANT_GRAB = modEntityTag("lorrainosaurus_cant_grab");
    public static final TagKey<EntityType<?>> LORRAINOSAURUS_CAN_GRAB = modEntityTag("lorrainosaurus_can_grab");

    public static final TagKey<EntityType<?>> UNAFFECTED_BY_PARALYSIS = modEntityTag("unaffected_by_paralysis");

    public static final TagKey<EntityType<?>> RHIZODUS_CANT_ATTACK = modEntityTag("metriorhynchus_cant_attack");
    public static final TagKey<EntityType<?>> RHIZODUS_CAN_ATTACK = modEntityTag("metriorhynchus_can_attack");

    private static TagKey<EntityType<?>> modEntityTag(String name) {
        return entityTag(UnusualPrehistory2.MOD_ID, name);
    }

    private static TagKey<EntityType<?>> commonEntityTag(String name) {
        return entityTag("c", name);
    }

    public static TagKey<EntityType<?>> entityTag(String modid, String name) {
        return TagKey.create(Registries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(modid, name));
    }
}
