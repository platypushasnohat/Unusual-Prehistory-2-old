package com.barlinc.unusual_prehistory.registry;

import com.barlinc.unusual_prehistory.entity.projectile.ThrowableEgg;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.FireBlock;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class UP2Compat {

    public static void registerCompat() {
        registerFlammables();
        registerDispenserBehaviours();
    }

    public static void registerFlammables() {
        // Update 1
        registerFlammable(UP2Blocks.CALAMOPHYTON.get(), 60, 100);
        registerFlammable(UP2Blocks.CLADOPHLEBIS.get(), 60, 100);
        registerFlammable(UP2Blocks.COOKSONIA.get(), 60, 100);
        registerFlammable(UP2Blocks.HORSETAIL.get(), 60, 100);
        registerFlammable(UP2Blocks.LARGE_HORSETAIL.get(), 60, 100);
        registerFlammable(UP2Blocks.QUILLWORT.get(), 60, 100);
        registerFlammable(UP2Blocks.LEEFRUCTUS.get(), 60, 100);
        registerFlammable(UP2Blocks.RAIGUENRAYUN.get(), 60, 100);
        registerFlammable(UP2Blocks.RHYNIA.get(), 60, 100);

        UP2Blocks.GINKGO.setFlammables();
        registerFlammable(UP2Blocks.GINKGO_LEAVES.get(), 30, 60);
        registerFlammable(UP2Blocks.GOLDEN_GINKGO_LEAVES.get(), 30, 60);

        UP2Blocks.LEPIDODENDRON.setFlammables();
        registerFlammable(UP2Blocks.MOSSY_LEPIDODENDRON_LOG.get(), 5, 5);
        registerFlammable(UP2Blocks.MOSSY_LEPIDODENDRON_WOOD.get(), 5, 5);
        registerFlammable(UP2Blocks.LEPIDODENDRON_LEAVES.get(), 30, 60);

        // Update 4
        registerFlammable(UP2Blocks.TEMPSKYA.get(), 60, 100);
        registerFlammable(UP2Blocks.BRACHYPHYLLUM.get(), 60, 100);
        registerFlammable(UP2Blocks.NEOMARIOPTERIS.get(), 60, 100);
        registerFlammable(UP2Blocks.BENNETTITALES.get(), 60, 100);
        registerFlammable(UP2Blocks.AETHOPHYLLUM.get(), 60, 100);
        registerFlammable(UP2Blocks.ARCHAEOSIGILLARIA.get(), 60, 100);

        registerFlammable(UP2Blocks.GUANGDEDENDRON_SPORE.get(), 20, 100);
        registerFlammable(UP2Blocks.GUANGDEDENDRON.get(), 20, 100);
        registerFlammable(UP2Blocks.CYCAD_STEM.get(), 20, 100);
        registerFlammable(UP2Blocks.CYCAD_CROWN.get(), 20, 100);
        registerFlammable(UP2Blocks.CYCAD_SEEDLING.get(), 20, 100);

        UP2Blocks.DRYOPHYLLUM.setFlammables();
        registerFlammable(UP2Blocks.DRYOPHYLLUM_LEAVES.get(), 30, 60);

        UP2Blocks.METASEQUOIA.setFlammables();
        registerFlammable(UP2Blocks.METASEQUOIA_LEAVES.get(), 30, 60);
        registerFlammable(UP2Blocks.DAWN_METASEQUOIA_LEAVES.get(), 30, 60);
        registerFlammable(UP2Blocks.DUSK_METASEQUOIA_LEAVES.get(), 30, 60);

        // Update 5
        registerFlammable(UP2Blocks.DELITZSCHALA_STALK.get(), 60, 100);
        registerFlammable(UP2Blocks.ZHANGSOLVA_BLOOM.get(), 60, 100);

        // Update 6
        registerFlammable(UP2Blocks.BRACHIOSAURUS_PLUSHIE.get(), 30, 60);
        registerFlammable(UP2Blocks.CARNOTAURUS_PLUSHIE.get(), 30, 60);
        registerFlammable(UP2Blocks.COTYLORHYNCHUS_PLUSHIE.get(), 30, 60);
        registerFlammable(UP2Blocks.GIANT_CAMPANILE_PLUSHIE.get(), 30, 60);
        registerFlammable(UP2Blocks.HIBBERTOPTERUS_PLUSHIE.get(), 30, 60);
        registerFlammable(UP2Blocks.KENTROSAURUS_PLUSHIE.get(), 30, 60);
        registerFlammable(UP2Blocks.MAJUNGASAURUS_PLUSHIE.get(), 30, 60);
        registerFlammable(UP2Blocks.TARTUOSTEUS_PLUSHIE.get(), 30, 60);
    }

    public static void registerDispenserBehaviours() {
        // Update 1
        registerEggDispenserBehaviour(UP2Items.DROMAEOSAURUS_EGG, UP2Entities.DROMAEOSAURUS_EGG::get);
        registerEggDispenserBehaviour(UP2Items.TALPANAS_EGG, UP2Entities.TALPANAS_EGG::get);
        registerEggDispenserBehaviour(UP2Items.TELECREX_EGG, UP2Entities.TELECREX_EGG::get);

        // Update 4
        registerEggDispenserBehaviour(UP2Items.PTERODACTYLUS_EGG, UP2Entities.PTERODACTYLUS_EGG::get);

        // Update 5
        registerEggDispenserBehaviour(UP2Items.PSILOPTERUS_EGG, UP2Entities.PSILOPTERUS_EGG::get);

        // Update 6
        registerEggDispenserBehaviour(UP2Items.AUSTRORAPTOR_EGG, UP2Entities.AUSTRORAPTOR_EGG::get);
    }

    public static void registerEggDispenserBehaviour(Supplier<Item> itemSupplier, Supplier<EntityType<? extends ThrowableEgg>> entityTypeSupplier) {
        DispenserBlock.registerBehavior(itemSupplier.get(), new DispenseItemBehavior() {
            @Override
            public @NotNull ItemStack dispense(@NotNull BlockSource source, @NotNull ItemStack stack) {
                Level level = source.level();
                ThrowableEgg egg = entityTypeSupplier.get().create(level);
                if (egg != null) {
                    BlockPos pos = source.pos();
                    egg.setPos(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                    level.addFreshEntity(egg);
                }
                stack.shrink(1);
                return stack;
            }
        });
    }

    public static void registerFlammable(Block block, int encouragement, int flammability) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(block, encouragement, flammability);
    }
}
