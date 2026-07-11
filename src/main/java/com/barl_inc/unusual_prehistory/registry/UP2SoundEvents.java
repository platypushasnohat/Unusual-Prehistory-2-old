package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class UP2SoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, UnusualPrehistory2.MOD_ID);

    // Update 1
    public static final DeferredHolder<SoundEvent, SoundEvent> CARNOTAURUS_HURT = registerSoundEvent("carnotaurus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> CARNOTAURUS_DEATH = registerSoundEvent("carnotaurus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> CARNOTAURUS_IDLE = registerSoundEvent("carnotaurus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> CARNOTAURUS_STEP = registerSoundEvent("carnotaurus_step");
    public static final DeferredHolder<SoundEvent, SoundEvent> CARNOTAURUS_ROAR = registerSoundEvent("carnotaurus_roar");
    public static final DeferredHolder<SoundEvent, SoundEvent> CARNOTAURUS_SNIFF = registerSoundEvent("carnotaurus_sniff");
    public static final DeferredHolder<SoundEvent, SoundEvent> CARNOTAURUS_BITE = registerSoundEvent("carnotaurus_bite");
    public static final DeferredHolder<SoundEvent, SoundEvent> CARNOTAURUS_CHARGE = registerSoundEvent("carnotaurus_charge");
    public static final DeferredHolder<SoundEvent, SoundEvent> CARNOTAURUS_HEADBUTT = registerSoundEvent("carnotaurus_headbutt");
    public static final DeferredHolder<SoundEvent, SoundEvent> CARNOTAURUS_HEADBUTT_VOCAL = registerSoundEvent("carnotaurus_headbutt_vocal");

    public static final DeferredHolder<SoundEvent, SoundEvent> DIPLOCAULUS_HURT = registerSoundEvent("diplocaulus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> DIPLOCAULUS_DEATH = registerSoundEvent("diplocaulus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> DIPLOCAULUS_IDLE = registerSoundEvent("diplocaulus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> DIPLOCAULUS_STEP = registerSoundEvent("diplocaulus_step");

    public static final DeferredHolder<SoundEvent, SoundEvent> DROMAEOSAURUS_HURT = registerSoundEvent("dromaeosaurus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> DROMAEOSAURUS_DEATH = registerSoundEvent("dromaeosaurus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> DROMAEOSAURUS_IDLE = registerSoundEvent("dromaeosaurus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> DROMAEOSAURUS_EEPY = registerSoundEvent("dromaeosaurus_eepy");

    public static final DeferredHolder<SoundEvent, SoundEvent> DUNKLEOSTEUS_HURT = registerSoundEvent("dunkleosteus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> DUNKLEOSTEUS_DEATH = registerSoundEvent("dunkleosteus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> DUNKLEOSTEUS_FLOP = registerSoundEvent("dunkleosteus_flop");
    public static final DeferredHolder<SoundEvent, SoundEvent> SMALL_DUNKLEOSTEUS_BITE = registerSoundEvent("small_dunkleosteus_bite");
    public static final DeferredHolder<SoundEvent, SoundEvent> MEDIUM_DUNKLEOSTEUS_BITE = registerSoundEvent("medium_dunkleosteus_bite");
    public static final DeferredHolder<SoundEvent, SoundEvent> LARGE_DUNKLEOSTEUS_BITE = registerSoundEvent("large_dunkleosteus_bite");

    public static final DeferredHolder<SoundEvent, SoundEvent> KENTROSAURUS_HURT = registerSoundEvent("kentrosaurus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> KENTROSAURUS_DEATH = registerSoundEvent("kentrosaurus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> KENTROSAURUS_IDLE = registerSoundEvent("kentrosaurus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> KENTROSAURUS_STEP = registerSoundEvent("kentrosaurus_step");

    public static final DeferredHolder<SoundEvent, SoundEvent> KIMMERIDGEBRACHYPTERAESCHNIDIUM_HURT = registerSoundEvent("kimmeridgebrachypteraeschnidium_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> KIMMERIDGEBRACHYPTERAESCHNIDIUM_DEATH = registerSoundEvent("kimmeridgebrachypteraeschnidium_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> KIMMERIDGEBRACHYPTERAESCHNIDIUM_LOOP = registerSoundEvent("kimmeridgebrachypteraeschnidium_loop");

    public static final DeferredHolder<SoundEvent, SoundEvent> JAWLESS_FISH_HURT = registerSoundEvent("jawless_fish_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> JAWLESS_FISH_DEATH = registerSoundEvent("jawless_fish_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> JAWLESS_FISH_FLOP = registerSoundEvent("jawless_fish_flop");

    public static final DeferredHolder<SoundEvent, SoundEvent> MAJUNGASAURUS_HURT = registerSoundEvent("majungasaurus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> MAJUNGASAURUS_DEATH = registerSoundEvent("majungasaurus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> MAJUNGASAURUS_IDLE = registerSoundEvent("majungasaurus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> MAJUNGASAURUS_ATTACK = registerSoundEvent("majungasaurus_attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> MAJUNGASAURUS_SNIFF = registerSoundEvent("majungasaurus_sniff");
    public static final DeferredHolder<SoundEvent, SoundEvent> MAJUNGASAURUS_STEP = registerSoundEvent("majungasaurus_step");

    public static final DeferredHolder<SoundEvent, SoundEvent> MEGALANIA_HURT = registerSoundEvent("megalania_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> MEGALANIA_DEATH = registerSoundEvent("megalania_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> MEGALANIA_IDLE = registerSoundEvent("megalania_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> MEGALANIA_ROAR = registerSoundEvent("megalania_roar");
    public static final DeferredHolder<SoundEvent, SoundEvent> MEGALANIA_STEP = registerSoundEvent("megalania_step");
    public static final DeferredHolder<SoundEvent, SoundEvent> MEGALANIA_TAIL_SWING = registerSoundEvent("megalania_tail_swing");
    public static final DeferredHolder<SoundEvent, SoundEvent> MEGALANIA_BITE = registerSoundEvent("megalania_bite");

    public static final DeferredHolder<SoundEvent, SoundEvent> STETHACANTHUS_HURT = registerSoundEvent("stethacanthus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> STETHACANTHUS_DEATH = registerSoundEvent("stethacanthus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> STETHACANTHUS_FLOP = registerSoundEvent("stethacanthus_flop");
    public static final DeferredHolder<SoundEvent, SoundEvent> STETHACANTHUS_BITE = registerSoundEvent("stethacanthus_bite");

    public static final DeferredHolder<SoundEvent, SoundEvent> TALPANAS_HURT = registerSoundEvent("talpanas_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> TALPANAS_DEATH = registerSoundEvent("talpanas_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> TALPANAS_IDLE = registerSoundEvent("talpanas_idle");

    public static final DeferredHolder<SoundEvent, SoundEvent> TELECREX_HURT = registerSoundEvent("telecrex_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> TELECREX_DEATH = registerSoundEvent("telecrex_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> TELECREX_IDLE = registerSoundEvent("telecrex_idle");

    public static final DeferredHolder<SoundEvent, SoundEvent> UNICORN_HURT = registerSoundEvent("unicorn_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> UNICORN_DEATH = registerSoundEvent("unicorn_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> UNICORN_IDLE = registerSoundEvent("unicorn_idle");

    public static final DeferredHolder<SoundEvent, SoundEvent> TRANSMOGRIFIER_LOOP = registerSoundEvent("transmogrifier_loop");

    public static final DeferredHolder<SoundEvent, SoundEvent> TAR_POP = registerSoundEvent("tar_pop");

    public static final DeferredHolder<SoundEvent, SoundEvent> TARIFYING_DISC = registerSoundEvent("tarifying_disc");

    // Update 2
    public static final DeferredHolder<SoundEvent, SoundEvent> ONCHOPRISTIS_HURT = registerSoundEvent("onchopristis_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ONCHOPRISTIS_DEATH = registerSoundEvent("onchopristis_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ONCHOPRISTIS_FLOP = registerSoundEvent("onchopristis_flop");

    public static final DeferredHolder<SoundEvent, SoundEvent> DOOMSURF_DISC = registerSoundEvent("doomsurf_disc");
    public static final DeferredHolder<SoundEvent, SoundEvent> MEGALANIA_DISC = registerSoundEvent("megalania_disc");

    // Update 3
    public static final DeferredHolder<SoundEvent, SoundEvent> METRIORHYNCHUS_HURT = registerSoundEvent("metriorhynchus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> METRIORHYNCHUS_DEATH = registerSoundEvent("metriorhynchus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> METRIORHYNCHUS_IDLE = registerSoundEvent("metriorhynchus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> METRIORHYNCHUS_BITE = registerSoundEvent("metriorhynchus_bite");
    public static final DeferredHolder<SoundEvent, SoundEvent> METRIORHYNCHUS_BELLOW = registerSoundEvent("metriorhynchus_bellow");

    public static final DeferredHolder<SoundEvent, SoundEvent> LIVING_OOZE_HURT = registerSoundEvent("living_ooze_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> LIVING_OOZE_DEATH = registerSoundEvent("living_ooze_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> LIVING_OOZE_SQUISH = registerSoundEvent("living_ooze_squish");
    public static final DeferredHolder<SoundEvent, SoundEvent> LIVING_OOZE_JUMP = registerSoundEvent("living_ooze_jump");
    public static final DeferredHolder<SoundEvent, SoundEvent> LIVING_OOZE_SPIT = registerSoundEvent("living_ooze_spit");

    // Update 4
    public static final DeferredHolder<SoundEvent, SoundEvent> BRACHIOSAURUS_HURT = registerSoundEvent("brachiosaurus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> BRACHIOSAURUS_DEATH = registerSoundEvent("brachiosaurus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> BRACHIOSAURUS_IDLE = registerSoundEvent("brachiosaurus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> BRACHIOSAURUS_ATTACK = registerSoundEvent("brachiosaurus_attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> BRACHIOSAURUS_STEP = registerSoundEvent("brachiosaurus_step");
    public static final DeferredHolder<SoundEvent, SoundEvent> BRACHIOSAURUS_CALL = registerSoundEvent("brachiosaurus_call");

    public static final DeferredHolder<SoundEvent, SoundEvent> COELACANTHUS_HURT = registerSoundEvent("coelacanthus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> COELACANTHUS_DEATH = registerSoundEvent("coelacanthus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> COELACANTHUS_FLOP = registerSoundEvent("coelacanthus_flop");

    public static final DeferredHolder<SoundEvent, SoundEvent> HIBBERTOPTERUS_HURT = registerSoundEvent("hibbertopterus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> HIBBERTOPTERUS_DEATH = registerSoundEvent("hibbertopterus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> HIBBERTOPTERUS_IDLE = registerSoundEvent("hibbertopterus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> HIBBERTOPTERUS_STEP = registerSoundEvent("hibbertopterus_step");

    public static final DeferredHolder<SoundEvent, SoundEvent> KAPROSUCHUS_HURT = registerSoundEvent("kaprosuchus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> KAPROSUCHUS_DEATH = registerSoundEvent("kaprosuchus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> KAPROSUCHUS_IDLE = registerSoundEvent("kaprosuchus_idle");

    public static final DeferredHolder<SoundEvent, SoundEvent> LEPTICTIDIUM_HURT = registerSoundEvent("leptictidium_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> LEPTICTIDIUM_DEATH = registerSoundEvent("leptictidium_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> LEPTICTIDIUM_IDLE = registerSoundEvent("leptictidium_idle");

    public static final DeferredHolder<SoundEvent, SoundEvent> LYSTROSAURUS_HURT = registerSoundEvent("lystrosaurus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> LYSTROSAURUS_DEATH = registerSoundEvent("lystrosaurus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> LYSTROSAURUS_IDLE = registerSoundEvent("lystrosaurus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> LYSTROSAURUS_STEP = registerSoundEvent("lystrosaurus_step");
    public static final DeferredHolder<SoundEvent, SoundEvent> LYSTROSAURUS_CHAINSMOKER = registerSoundEvent("lystrosaurus_chainsmoker");

    public static final DeferredHolder<SoundEvent, SoundEvent> PACHYCEPHALOSAURUS_HURT = registerSoundEvent("pachycephalosaurus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> PACHYCEPHALOSAURUS_DEATH = registerSoundEvent("pachycephalosaurus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> PACHYCEPHALOSAURUS_IDLE = registerSoundEvent("pachycephalosaurus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> PACHYCEPHALOSAURUS_WARN = registerSoundEvent("pachycephalosaurus_warn");
    public static final DeferredHolder<SoundEvent, SoundEvent> PACHYCEPHALOSAURUS_STEP = registerSoundEvent("pachycephalosaurus_step");
    public static final DeferredHolder<SoundEvent, SoundEvent> PACHYCEPHALOSAURUS_BONK = registerSoundEvent("pachycephalosaurus_bonk");

    public static final DeferredHolder<SoundEvent, SoundEvent> PRAEPUSA_HURT = registerSoundEvent("praepusa_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> PRAEPUSA_DEATH = registerSoundEvent("praepusa_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> PRAEPUSA_IDLE = registerSoundEvent("praepusa_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> PRAEPUSA_MITOSIS = registerSoundEvent("praepusa_mitosis");
    public static final DeferredHolder<SoundEvent, SoundEvent> PRAEPUSA_BOUNCE = registerSoundEvent("praepusa_bounce");

    public static final DeferredHolder<SoundEvent, SoundEvent> PTERODACTYLUS_HURT = registerSoundEvent("pterodactylus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> PTERODACTYLUS_DEATH = registerSoundEvent("pterodactylus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> PTERODACTYLUS_IDLE = registerSoundEvent("pterodactylus_idle");

    public static final DeferredHolder<SoundEvent, SoundEvent> ULUGHBEGSAURUS_HURT = registerSoundEvent("ulughbegsaurus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ULUGHBEGSAURUS_DEATH = registerSoundEvent("ulughbegsaurus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ULUGHBEGSAURUS_IDLE = registerSoundEvent("ulughbegsaurus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> ULUGHBEGSAURUS_ATTACK = registerSoundEvent("ulughbegsaurus_attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> ULUGHBEGSAURUS_STEP = registerSoundEvent("ulughbegsaurus_step");

    // Update 5
    public static final DeferredHolder<SoundEvent, SoundEvent> AEGIROCASSIS_HURT = registerSoundEvent("aegirocassis_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> AEGIROCASSIS_DEATH = registerSoundEvent("aegirocassis_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> AEGIROCASSIS_IDLE = registerSoundEvent("aegirocassis_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> AEGIROCASSIS_HOVER = registerSoundEvent("aegirocassis_hover");

    public static final DeferredHolder<SoundEvent, SoundEvent> DESMATOSUCHUS_HURT = registerSoundEvent("desmatosuchus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> DESMATOSUCHUS_DEATH = registerSoundEvent("desmatosuchus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> DESMATOSUCHUS_IDLE = registerSoundEvent("desmatosuchus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> DESMATOSUCHUS_STEP = registerSoundEvent("desmatosuchus_step");

    public static final DeferredHolder<SoundEvent, SoundEvent> PSILOPTERUS_HURT = registerSoundEvent("psilopterus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> PSILOPTERUS_DEATH = registerSoundEvent("psilopterus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> PSILOPTERUS_IDLE = registerSoundEvent("psilopterus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> PSILOPTERUS_ATTACK = registerSoundEvent("psilopterus_attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> PSILOPTERUS_BITE = registerSoundEvent("psilopterus_bite");
    public static final DeferredHolder<SoundEvent, SoundEvent> PSILOPTERUS_CALL = registerSoundEvent("psilopterus_call");

    public static final DeferredHolder<SoundEvent, SoundEvent> GRUG_HURT = registerSoundEvent("grug_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> GRUG_DEATH = registerSoundEvent("grug_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> GRUG_IDLE = registerSoundEvent("grug_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> GRUG_CHASE = registerSoundEvent("grug_chase");

    public static final DeferredHolder<SoundEvent, SoundEvent> BUG_HURT = registerSoundEvent("bug_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> BUG_DEATH = registerSoundEvent("bug_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> BUG_BUZZ = registerSoundEvent("bug_buzz");

    public static final DeferredHolder<SoundEvent, SoundEvent> PUMMEL_AND_SNATCH_DISC = registerSoundEvent("pummel_and_snatch_disc");

    // Update 6
    public static final DeferredHolder<SoundEvent, SoundEvent> AMMONITE_HURT = registerSoundEvent("ammonite_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> AMMONITE_DEATH = registerSoundEvent("ammonite_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> AMMONITE_IDLE = registerSoundEvent("ammonite_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> AMMONITE_FLOP = registerSoundEvent("ammonite_flop");
    public static final DeferredHolder<SoundEvent, SoundEvent> AMMONITE_BLOCK = registerSoundEvent("ammonite_block");
    public static final DeferredHolder<SoundEvent, SoundEvent> AMMONITE_SWIM = registerSoundEvent("ammonite_swim");

    public static final DeferredHolder<SoundEvent, SoundEvent> ANTARCTOPELTA_HURT = registerSoundEvent("antarctopelta_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ANTARCTOPELTA_DEATH = registerSoundEvent("antarctopelta_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ANTARCTOPELTA_IDLE = registerSoundEvent("antarctopelta_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> ANTARCTOPELTA_STEP = registerSoundEvent("antarctopelta_step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ANUROGNATHUS_HURT = registerSoundEvent("anurognathus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ANUROGNATHUS_DEATH = registerSoundEvent("anurognathus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ANUROGNATHUS_IDLE = registerSoundEvent("anurognathus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> ANUROGNATHUS_HISS = registerSoundEvent("anurognathus_hiss");

    public static final DeferredHolder<SoundEvent, SoundEvent> ARTHROPLEURA_HURT = registerSoundEvent("arthropleura_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ARTHROPLEURA_DEATH = registerSoundEvent("arthropleura_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ARTHROPLEURA_IDLE = registerSoundEvent("arthropleura_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> ARTHROPLEURA_STEP = registerSoundEvent("arthropleura_step");

    public static final DeferredHolder<SoundEvent, SoundEvent> AUSTRORAPTOR_HURT = registerSoundEvent("austroraptor_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> AUSTRORAPTOR_DEATH = registerSoundEvent("austroraptor_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> AUSTRORAPTOR_IDLE = registerSoundEvent("austroraptor_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> AUSTRORAPTOR_HISS = registerSoundEvent("austroraptor_hiss");
    public static final DeferredHolder<SoundEvent, SoundEvent> AUSTRORAPTOR_ATTACK = registerSoundEvent("austroraptor_attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> AUSTRORAPTOR_SCREAM = registerSoundEvent("austroraptor_scream");

    public static final DeferredHolder<SoundEvent, SoundEvent> BRONTOSCORPIO_HURT = registerSoundEvent("brontoscorpio_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> BRONTOSCORPIO_DEATH = registerSoundEvent("brontoscorpio_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> BRONTOSCORPIO_IDLE = registerSoundEvent("brontoscorpio_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> BRONTOSCORPIO_STEP = registerSoundEvent("brontoscorpio_step");

    public static final DeferredHolder<SoundEvent, SoundEvent> CONCAVENATOR_HURT = registerSoundEvent("concavenator_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> CONCAVENATOR_DEATH = registerSoundEvent("concavenator_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> CONCAVENATOR_IDLE = registerSoundEvent("concavenator_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> CONCAVENATOR_STEP = registerSoundEvent("concavenator_step");

    public static final DeferredHolder<SoundEvent, SoundEvent> COTYLORHYNCHUS_HURT = registerSoundEvent("cotylorhynchus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> COTYLORHYNCHUS_DEATH = registerSoundEvent("cotylorhynchus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> COTYLORHYNCHUS_IDLE = registerSoundEvent("cotylorhynchus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> COTYLORHYNCHUS_STEP = registerSoundEvent("cotylorhynchus_step");
    public static final DeferredHolder<SoundEvent, SoundEvent> COTYLORHYNCHUS_BURP = registerSoundEvent("cotylorhynchus_burp");

    public static final DeferredHolder<SoundEvent, SoundEvent> CRYPTOCLIDUS_HURT = registerSoundEvent("cryptoclidus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYPTOCLIDUS_DEATH = registerSoundEvent("cryptoclidus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYPTOCLIDUS_IDLE = registerSoundEvent("cryptoclidus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYPTOCLIDUS_ATTACK = registerSoundEvent("cryptoclidus_attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> CRYPTOCLIDUS_STEP = registerSoundEvent("cryptoclidus_step");

    public static final DeferredHolder<SoundEvent, SoundEvent> GASTRIC_BROODING_FROG_HURT = registerSoundEvent("gastric_brooding_frog_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> GASTRIC_BROODING_FROG_DEATH = registerSoundEvent("gastric_brooding_frog_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> GASTRIC_BROODING_FROG_IDLE = registerSoundEvent("gastric_brooding_frog_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> GASTRIC_BROODING_FROG_STEP = registerSoundEvent("gastric_brooding_frog_step");
    public static final DeferredHolder<SoundEvent, SoundEvent> GASTRIC_BROODING_FROG_LEAP = registerSoundEvent("gastric_brooding_frog_leap");
    public static final DeferredHolder<SoundEvent, SoundEvent> GASTRIC_BROODING_FROG_LAUNCH = registerSoundEvent("gastric_brooding_frog_launch");
    public static final DeferredHolder<SoundEvent, SoundEvent> GASTRIC_BROODING_FROG_EAT = registerSoundEvent("gastric_brooding_frog_eat");
    public static final DeferredHolder<SoundEvent, SoundEvent> GASTRIC_BROODING_FROG_TONGUE = registerSoundEvent("gastric_brooding_frog_tongue");

    public static final DeferredHolder<SoundEvent, SoundEvent> GIANT_CAMPANILE_HURT = registerSoundEvent("giant_campanile_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> GIANT_CAMPANILE_DEATH = registerSoundEvent("giant_campanile_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> GIANT_CAMPANILE_IDLE = registerSoundEvent("giant_campanile_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> GIANT_CAMPANILE_STEP = registerSoundEvent("giant_campanile_step");
    public static final DeferredHolder<SoundEvent, SoundEvent> GIANT_CAMPANILE_BLOCK = registerSoundEvent("giant_campanile_block");

    public static final DeferredHolder<SoundEvent, SoundEvent> HYNERPETON_HURT = registerSoundEvent("hynerpeton_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> HYNERPETON_DEATH = registerSoundEvent("hynerpeton_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> HYNERPETON_IDLE = registerSoundEvent("hynerpeton_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> HYNERPETON_STEP = registerSoundEvent("hynerpeton_step");

    public static final DeferredHolder<SoundEvent, SoundEvent> ICHTHYOSAURUS_HURT = registerSoundEvent("ichthyosaurus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> ICHTHYOSAURUS_DEATH = registerSoundEvent("ichthyosaurus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> ICHTHYOSAURUS_IDLE = registerSoundEvent("ichthyosaurus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> ICHTHYOSAURUS_FLOP = registerSoundEvent("ichthyosaurus_flop");
    public static final DeferredHolder<SoundEvent, SoundEvent> ICHTHYOSAURUS_DASH = registerSoundEvent("ichthyosaurus_dash");
    public static final DeferredHolder<SoundEvent, SoundEvent> ICHTHYOSAURUS_DASH_READY = registerSoundEvent("ichthyosaurus_dash_ready");

    public static final DeferredHolder<SoundEvent, SoundEvent> LEEDSICHTHYS_HURT = registerSoundEvent("leedsichthys_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> LEEDSICHTHYS_DEATH = registerSoundEvent("leedsichthys_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> LEEDSICHTHYS_IDLE = registerSoundEvent("leedsichthys_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> LEEDSICHTHYS_SWIM = registerSoundEvent("leedsichthys_swim");

    public static final DeferredHolder<SoundEvent, SoundEvent> LORRAINOSAURUS_HURT = registerSoundEvent("lorrainosaurus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> LORRAINOSAURUS_DEATH = registerSoundEvent("lorrainosaurus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> LORRAINOSAURUS_IDLE = registerSoundEvent("lorrainosaurus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> LORRAINOSAURUS_ATTACK = registerSoundEvent("lorrainosaurus_attack");

    public static final DeferredHolder<SoundEvent, SoundEvent> PROGNATHODON_HURT = registerSoundEvent("prognathodon_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> PROGNATHODON_DEATH = registerSoundEvent("prognathodon_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> PROGNATHODON_IDLE = registerSoundEvent("prognathodon_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> PROGNATHODON_ATTACK = registerSoundEvent("prognathodon_attack");

    public static final DeferredHolder<SoundEvent, SoundEvent> RHIZODUS_HURT = registerSoundEvent("rhizodus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> RHIZODUS_DEATH = registerSoundEvent("rhizodus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> RHIZODUS_STEP = registerSoundEvent("rhizodus_step");

    public static final DeferredHolder<SoundEvent, SoundEvent> SPIKE_TOOTHED_SALMON_HURT = registerSoundEvent("spike_toothed_salmon_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPIKE_TOOTHED_SALMON_DEATH = registerSoundEvent("spike_toothed_salmon_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPIKE_TOOTHED_SALMON_FLOP = registerSoundEvent("spike_toothed_salmon_flop");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPIKE_TOOTHED_SALMON_ATTACK = registerSoundEvent("spike_toothed_salmon_attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPIKE_TOOTHED_SALMON_CONVERT = registerSoundEvent("spike_toothed_salmon_convert");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPIKE_TOOTHED_SALMON_INFECT = registerSoundEvent("spike_toothed_salmon_infect");
    public static final DeferredHolder<SoundEvent, SoundEvent> SPIKE_TOOTHED_SALMON_CURE = registerSoundEvent("spike_toothed_salmon_cure");

    public static final DeferredHolder<SoundEvent, SoundEvent> THERIZINOSAURUS_HURT = registerSoundEvent("therizinosaurus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> THERIZINOSAURUS_DEATH = registerSoundEvent("therizinosaurus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> THERIZINOSAURUS_IDLE = registerSoundEvent("therizinosaurus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> THERIZINOSAURUS_STEP = registerSoundEvent("therizinosaurus_step");
    public static final DeferredHolder<SoundEvent, SoundEvent> THERIZINOSAURUS_ATTACK = registerSoundEvent("therizinosaurus_attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> THERIZINOSAURUS_ROAR = registerSoundEvent("therizinosaurus_roar");

    public static final DeferredHolder<SoundEvent, SoundEvent> TUSOTEUTHIS_HURT = registerSoundEvent("tusoteuthis_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> TUSOTEUTHIS_DEATH = registerSoundEvent("tusoteuthis_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> TUSOTEUTHIS_IDLE = registerSoundEvent("tusoteuthis_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> TUSOTEUTHIS_FLASH = registerSoundEvent("tusoteuthis_flash");
    public static final DeferredHolder<SoundEvent, SoundEvent> TUSOTEUTHIS_ABDUCT = registerSoundEvent("tusoteuthis_abduct");

    public static final DeferredHolder<SoundEvent, SoundEvent> WOOLLY_MAMMOTH_HURT = registerSoundEvent("woolly_mammoth_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> WOOLLY_MAMMOTH_DEATH = registerSoundEvent("woolly_mammoth_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> WOOLLY_MAMMOTH_IDLE = registerSoundEvent("woolly_mammoth_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> WOOLLY_MAMMOTH_STEP = registerSoundEvent("woolly_mammoth_step");

    public static final DeferredHolder<SoundEvent, SoundEvent> PACHYRHINOSAURUS_HURT = registerSoundEvent("pachyrhinosaurus_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> PACHYRHINOSAURUS_DEATH = registerSoundEvent("pachyrhinosaurus_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> PACHYRHINOSAURUS_IDLE = registerSoundEvent("pachyrhinosaurus_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> PACHYRHINOSAURUS_STEP = registerSoundEvent("pachyrhinosaurus_step");
    public static final DeferredHolder<SoundEvent, SoundEvent> PACHYRHINOSAURUS_ATTACK = registerSoundEvent("pachyrhinosaurus_attack");
    public static final DeferredHolder<SoundEvent, SoundEvent> PACHYRHINOSAURUS_HEADBUTT = registerSoundEvent("pachyrhinosaurus_headbutt");

    public static final DeferredHolder<SoundEvent, SoundEvent> HESPERORNIS_HURT = registerSoundEvent("hesperornis_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> HESPERORNIS_DEATH = registerSoundEvent("hesperornis_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> HESPERORNIS_IDLE = registerSoundEvent("hesperornis_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> HESPERORNIS_STEP = registerSoundEvent("hesperornis_step");

    public static final DeferredHolder<SoundEvent, SoundEvent> DIRE_WOLF_HURT = registerSoundEvent("dire_wolf_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> DIRE_WOLF_DEATH = registerSoundEvent("dire_wolf_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> DIRE_WOLF_IDLE = registerSoundEvent("dire_wolf_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> DIRE_WOLF_GROWL = registerSoundEvent("dire_wolf_growl");
    public static final DeferredHolder<SoundEvent, SoundEvent> DIRE_WOLF_STEP = registerSoundEvent("dire_wolf_step");

    private static DeferredHolder<SoundEvent, SoundEvent> registerSoundEvent(final String soundName) {
        return SOUND_EVENTS.register(soundName, () -> SoundEvent.createVariableRangeEvent(UnusualPrehistory2.modPrefix(soundName)));
    }
}
