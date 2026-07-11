package com.barl_inc.unusual_prehistory.datagen.client;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Function;

import static com.barl_inc.unusual_prehistory.registry.UP2Blocks.*;

public class UP2BlockstateProvider extends BlockStateProvider {

    public UP2BlockstateProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, UnusualPrehistory2.MOD_ID, helper);
    }

    @Override
    protected void registerStatesAndModels() {

        this.block(COMMON_FOSSIL_BED);
        this.block(UNCOMMON_FOSSIL_BED);
        this.block(RARE_FOSSIL_BED);
        this.block(UNUSUAL_FOSSIL_BED);

        this.block(ASPHALT);

        this.crossBlockWithPot(BENNETTITALES, POTTED_BENNETTITALES);
        this.crossBlockWithPot(COOKSONIA, POTTED_COOKSONIA);
        this.crossBlockWithPot(HORSETAIL, POTTED_HORSETAIL);
        this.crossBlockWithPot(QUILLWORT, POTTED_QUILLWORT);
        this.crossBlockWithPot(LEEFRUCTUS, POTTED_LEEFRUCTUS);
        this.crossBlockWithPot(NEOMARIOPTERIS, POTTED_NEOMARIOPTERIS);
        this.crossBlockWithPot(ARCHAEOSIGILLARIA, POTTED_ARCHAEOSIGILLARIA);

        this.tintedCrossBlockWithPot(CLADOPHLEBIS, POTTED_CLADOPHLEBIS);

        this.tallPlant(LARGE_HORSETAIL);
        this.tallPlant(RAIGUENRAYUN);
        this.tallPlant(ZHANGSOLVA_BLOOM);
        this.tallPlant(DELITZSCHALA_STALK);

        // dryophyllum
        this.woodSet(DRYOPHYLLUM);
        this.leavesBlock(DRYOPHYLLUM_LEAVES);
        this.crossBlockWithPot(DRYOPHYLLUM_SAPLING, POTTED_DRYOPHYLLUM_SAPLING);

        this.woodSet(GINKGO);
        this.leavesBlock(GINKGO_LEAVES);
        this.leavesBlock(GOLDEN_GINKGO_LEAVES);
        this.crossBlockWithPot(GINKGO_SAPLING, POTTED_GINKGO_SAPLING);
        this.crossBlockWithPot(GOLDEN_GINKGO_SAPLING, POTTED_GOLDEN_GINKGO_SAPLING);

        this.woodSet(LEPIDODENDRON);
        this.leavesBlock(LEPIDODENDRON_LEAVES);
        this.crossBlock(HANGING_LEPIDODENDRON_LEAVES);

        this.woodSet(METASEQUOIA);
        this.leavesBlock(METASEQUOIA_LEAVES);
        this.leavesBlock(DAWN_METASEQUOIA_LEAVES);
        this.leavesBlock(DUSK_METASEQUOIA_LEAVES);

        this.axisBlock(FOSSILIZED_BONE_BLOCK);
        this.axisBlock(FOSSILIZED_BONE_VERTEBRA, UnusualPrehistory2.modPrefix("block/fossilized_bone_vertebra"), UnusualPrehistory2.modPrefix("block/fossilized_bone_block_top"));
        this.axisBlock(FOSSILIZED_BONE_BARK, UnusualPrehistory2.modPrefix("block/fossilized_bone_block"), UnusualPrehistory2.modPrefix("block/fossilized_bone_block"));

        this.block(COBBLED_FOSSILIZED_BONE);
        this.stairsBlock(COBBLED_FOSSILIZED_BONE.get(), COBBLED_FOSSILIZED_BONE_STAIRS.get());
        this.slabBlock(COBBLED_FOSSILIZED_BONE.get(), COBBLED_FOSSILIZED_BONE_SLAB.get());

        this.crossBlock(PETRIFIED_BUSH);
        this.logBlocks(PETRIFIED_LOG, PETRIFIED_WOOD);
        this.block(POLISHED_PETRIFIED_WOOD);
        this.stairsBlock(POLISHED_PETRIFIED_WOOD.get(), POLISHED_PETRIFIED_WOOD_STAIRS.get());
        this.slabBlock(POLISHED_PETRIFIED_WOOD.get(), POLISHED_PETRIFIED_WOOD_SLAB.get());
        this.buttonBlock(POLISHED_PETRIFIED_WOOD.get(), POLISHED_PETRIFIED_WOOD_BUTTON.get());
        this.pressurePlateBlock(POLISHED_PETRIFIED_WOOD.get(), POLISHED_PETRIFIED_WOOD_PRESSURE_PLATE.get());

        this.crossBlock(PROTOTAXITES_CLUSTER);

        this.axisBlock(BLOOD_GASTRICLIGHT);
        this.axisBlock(BILE_GASTRICLIGHT);
        this.axisBlock(BROOD_GASTRICLIGHT);

        // Update 6
        this.block(LEEDSICHTHYS_SLICE_BLOCK);
        this.stairsBlock(LEEDSICHTHYS_SLICE_BLOCK.get(), LEEDSICHTHYS_SLICE_STAIRS.get());
        this.slabBlock(LEEDSICHTHYS_SLICE_BLOCK.get(), LEEDSICHTHYS_SLICE_SLAB.get());

    }

    public static String name(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    public static ResourceLocation prefix(String prefix, ResourceLocation rl) {
        return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), prefix + rl.getPath());
    }

    public static ResourceLocation suffix(ResourceLocation rl, String suffix) {
        return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), rl.getPath() + suffix);
    }

    public static ResourceLocation remove(ResourceLocation rl, String remove) {
        return ResourceLocation.fromNamespaceAndPath(rl.getNamespace(), rl.getPath().replace(remove, ""));
    }

    public ModelFile particle(Block block, ResourceLocation texture) {
        return this.models().getBuilder(name(block)).texture("particle", texture);
    }

    public ModelFile particle(DeferredHolder<? extends Block, ?> block, ResourceLocation texture) {
        return this.particle(block.get(), texture);
    }

    public void blockItem(Block block) {
        this.simpleBlockItem(block, new ModelFile.ExistingModelFile(blockTexture(block), this.models().existingFileHelper));
    }

    public void blockItem(DeferredHolder<Block, ?> block) {
        this.blockItem(block.get());
    }

    public void generatedItem(ItemLike item, String type) {
        this.generatedItem(item, item, type);
    }

    public void generatedItem(ItemLike item, ItemLike texture, String type) {
        this.generatedItem(item, prefix(type + "/", UP2ItemModelProvider.key(texture)));
    }

    public void generatedItem(ItemLike item, ResourceLocation texture) {
        this.itemModels().withExistingParent(BuiltInRegistries.ITEM.getKey(item.asItem()).getPath(), "item/generated").texture("layer0", texture);
    }

    public void block(Block block) {
        this.simpleBlock(block);
        this.blockItem(block);
    }

    public void block(DeferredHolder<Block, ?> block) {
        this.block(block.get());
    }

    public void stairsBlock(Block block, Block stairs) {
        if (stairs instanceof StairBlock stairBlock) {
            this.stairsBlock(stairBlock, this.blockTexture(block));
            this.blockItem(stairs);
        }
    }

    public void slabBlock(Block block, Block slab) {
        if (slab instanceof SlabBlock slabBlock) {
            this.slabBlock(slabBlock, this.blockTexture(block), this.blockTexture(block));
            this.blockItem(slab);
        }
    }

    public void wallBlock(Block block, Block wall) {
        if (wall instanceof WallBlock wallBlock) {
            this.wallBlock(wallBlock, this.blockTexture(block));
            this.itemModels().getBuilder(name(wall)).parent(this.models().wallInventory(name(wall) + "_inventory", this.blockTexture(block)));
        }
    }

    public void fenceBlock(Block block, Block fence) {
        if (fence instanceof FenceBlock fenceBlock) {
            this.fenceBlock(fenceBlock, this.blockTexture(block));
            this.itemModels().getBuilder(name(fence)).parent(this.models().fenceInventory(name(fence) + "_inventory", this.blockTexture(block)));
        }
    }

    public void fenceGateBlock(Block block, Block fenceGate) {
        if (fenceGate instanceof FenceGateBlock fenceGateBlock) {
            this.fenceGateBlock(fenceGateBlock, this.blockTexture(block));
            this.blockItem(fenceGate);
        }
    }

    public void doorBlock(Block door) {
        if (door instanceof DoorBlock doorBlock) {
            this.doorBlock(doorBlock, suffix(this.blockTexture(door), "_bottom"), suffix(this.blockTexture(door), "_top"));
            this.generatedItem(door, "item");
        }
    }

    public void trapDoorBlock(Block trapDoor) {
        if (trapDoor instanceof TrapDoorBlock trapDoorBlock) {
            this.trapdoorBlock(trapDoorBlock, this.blockTexture(trapDoor), true);
            this.itemModels().getBuilder(name(trapDoor)).parent(this.models().trapdoorOrientableBottom(name(trapDoor) + "_bottom", this.blockTexture(trapDoor)));
        }
    }

    public void doorBlocks(Block door, Block trapdoor) {
        this.doorBlock(door);
        this.trapDoorBlock(trapdoor);
    }

    public void signBlocks(Block block, Block sign, Block wallSign) {
        if (sign != null && wallSign != null) {
            ModelFile model = this.particle(sign, this.blockTexture(block));
            this.simpleBlock(sign, model);
            this.generatedItem(sign, "item");
            this.simpleBlock(wallSign, model);
        }
    }

    public void hangingSignBlocks(DeferredHolder<Block, ?> strippedLog, DeferredHolder<? extends Block, ?> sign, DeferredHolder<? extends Block, ?> wallSign) {
        ModelFile model = this.particle(sign, this.blockTexture(strippedLog.get()));
        this.simpleBlock(sign.get(), model);
        this.generatedItem(sign.get(), "item");
        this.simpleBlock(wallSign.get(), model);
    }

    public void buttonBlock(Block block, Block button) {
        this.buttonBlock(button, this.blockTexture(block));
    }

    public void buttonBlock(Block block, ResourceLocation texture) {
        ModelFile button = this.models().button(name(block), texture);
        ModelFile buttonPressed = this.models().buttonPressed(name(block) + "_pressed", texture);
        ModelFile buttonInventoryModel = this.models().buttonInventory(name(block) + "_inventory", texture);
        if (block instanceof ButtonBlock buttonBlock) {
            this.buttonBlock(buttonBlock, button, buttonPressed);
        }
        this.itemModels().getBuilder(name(block)).parent(buttonInventoryModel);
    }

    public void pressurePlateBlock(Block block, Block pressurePlate) {
        this.pressurePlateBlock(pressurePlate, this.blockTexture(block));
    }

    public void pressurePlateBlock(Block block, ResourceLocation texture) {
        ModelFile pressurePlate = this.models().pressurePlate(name(block), texture);
        ModelFile pressurePlateDown = this.models().pressurePlateDown(name(block) + "_down", texture);
        this.getVariantBuilder(block).partialState().with(PressurePlateBlock.POWERED, true).addModels(new ConfiguredModel(pressurePlateDown)).partialState().with(PressurePlateBlock.POWERED, false).addModels(new ConfiguredModel(pressurePlate));
        this.blockItem(block);
    }

    public void logBlocks(DeferredHolder<Block, ?> log, DeferredHolder<Block, ?> wood) {
        this.axisBlock(log);
        this.woodBlock(wood, log);
    }

    public void woodBlock(DeferredHolder<Block, ?> block, DeferredHolder<Block, ?> log) {
        this.axisBlock(block, this.blockTexture(log.get()), this.blockTexture(log.get()));
    }

    public void axisBlock(DeferredHolder<Block, ?> block) {
        this.axisBlock(block, this.blockTexture(block.get()), suffix(this.blockTexture(block.get()), "_top"));
    }

    public void axisBlock(DeferredHolder<Block, ?> block, ResourceLocation sideTexture, ResourceLocation topTexture) {
        if (block.get() instanceof RotatedPillarBlock log) {
            this.axisBlock(log, sideTexture, topTexture);
            this.blockItem(block);
        }
    }

    public void woodSet(WoodSet woodSet) {
        this.logBlocks(woodSet.log(), woodSet.wood());
        this.logBlocks(woodSet.strippedLog(), woodSet.strippedWood());
        this.block(woodSet.planks());
        this.stairsBlock(woodSet.planks().get(), woodSet.stairs().get());
        this.slabBlock(woodSet.planks().get(), woodSet.slab().get());
        this.fenceBlock(woodSet.planks().get(), woodSet.fence().get());
        this.fenceGateBlock(woodSet.planks().get(), woodSet.fenceGate().get());
        this.buttonBlock(woodSet.planks().get(), woodSet.button().get());
        this.pressurePlateBlock(woodSet.planks().get(), woodSet.pressurePlate().get());
        this.doorBlocks(woodSet.door().get(), woodSet.trapdoor().get());
        this.signBlocks(woodSet.planks().get(), woodSet.sign().get(), woodSet.wallSign().get());
        this.hangingSignBlocks(woodSet.strippedLog(), woodSet.hangingSign(), woodSet.hangingWallSign());
    }

    public void leavesBlock(DeferredHolder<Block, ?> leaves) {
        this.simpleBlock(leaves.get(), models().getBuilder(name(leaves.get())).parent(new ModelFile.UncheckedModelFile(ResourceLocation.withDefaultNamespace("block/leaves"))).texture("all", blockTexture(leaves.get())).renderType("cutout_mipped"));
        this.blockItem(leaves);
    }

    private void tallPlant(DeferredHolder<Block, ?> flower) {
        Function<String, ModelFile> model = s -> this.models().cross(name(flower.get()) + "_" + s, this.modLoc("block/" + name(flower.get()) + "_" + s)).renderType("cutout");
        this.itemModels().withExistingParent(name(flower.get()), "item/generated").texture("layer0", this.modLoc("block/" + name(flower.get()) + "_top"));
        this.getVariantBuilder(flower.get()).partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(model.apply("top"))).partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(model.apply("bottom")));
    }

    public void crossBlock(DeferredHolder<Block, ?> cross) {
        this.simpleBlock(cross.get(), this.models().cross(name(cross.get()), this.blockTexture(cross.get())).renderType("cutout"));
        this.generatedItem(cross.get(), "block");
    }

    public void crossBlockWithPot(DeferredHolder<Block, ?> cross, DeferredHolder<Block, ?> flowerPot, ResourceLocation potTexture) {
        this.crossBlock(cross);
        this.simpleBlock(flowerPot.get(), this.models().singleTexture(name(flowerPot.get()), ResourceLocation.withDefaultNamespace("block/flower_pot_cross"), "plant", potTexture).renderType("cutout"));
    }

    public void crossBlockWithPot(DeferredHolder<Block, ?> cross, DeferredHolder<Block, ?> flowerPot) {
        this.crossBlockWithPot(cross, flowerPot, this.blockTexture(cross.get()));
    }

    public void tintedCrossBlock(DeferredHolder<Block, ?> cross) {
        this.simpleBlock(cross.get(), this.models().singleTexture(name(cross.get()), ResourceLocation.withDefaultNamespace("block/tinted_cross"), "cross", this.blockTexture(cross.get())).renderType("cutout"));
        this.generatedItem(cross.get(), "block");
    }

    public void tintedCrossBlockWithPot(DeferredHolder<Block, ?> cross, DeferredHolder<Block, ?> flowerPot, ResourceLocation potTexture) {
        this.tintedCrossBlock(cross);
        this.simpleBlock(flowerPot.get(), this.models().singleTexture(name(flowerPot.get()), ResourceLocation.withDefaultNamespace("block/tinted_flower_pot_cross"), "plant", potTexture).renderType("cutout"));
    }

    public void tintedCrossBlockWithPot(DeferredHolder<Block, ?> cross, DeferredHolder<Block, ?> flowerPot) {
        this.tintedCrossBlockWithPot(cross, flowerPot, this.blockTexture(cross.get()));
    }
}
