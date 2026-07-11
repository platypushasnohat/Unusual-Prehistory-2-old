package com.barl_inc.unusual_prehistory.registry;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.blocks.fluid.TarFluidType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class UP2Fluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, UnusualPrehistory2.MOD_ID);
    public static final DeferredRegister<FluidType> TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, UnusualPrehistory2.MOD_ID);

    private static BaseFlowingFluid.Properties tarProperties() {
        return new BaseFlowingFluid.Properties(TAR_TYPE, TAR_FLUID_SOURCE, TAR_FLUID_FLOWING).bucket(UP2Items.TAR_BUCKET).block(() -> (LiquidBlock) UP2Blocks.TAR.get()).levelDecreasePerBlock(3).slopeFindDistance(3).tickRate(40);
    }

    public static final DeferredHolder<FluidType, FluidType> TAR_TYPE = TYPES.register("tar", () -> new TarFluidType(FluidType.Properties.create().descriptionId("block.unusual_prehistory.tar").density(4000).viscosity(12000).pathType(PathType.WALKABLE).adjacentPathType(PathType.WALKABLE).canSwim(false).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_POWDER_SNOW).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_POWDER_SNOW)));
    public static final DeferredHolder<Fluid, FlowingFluid> TAR_FLUID_SOURCE = FLUIDS.register("tar", () -> new BaseFlowingFluid.Source(tarProperties()));
    public static final DeferredHolder<Fluid, FlowingFluid> TAR_FLUID_FLOWING = FLUIDS.register("tar_flowing", () -> new BaseFlowingFluid.Flowing(tarProperties()));

    public static void postInit() {
        FluidInteractionRegistry.addInteraction(TAR_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                NeoForgeMod.WATER_TYPE.value(),
                fluidState -> UP2Blocks.ASPHALT.get().defaultBlockState()
        ));
        FluidInteractionRegistry.addInteraction(TAR_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                NeoForgeMod.LAVA_TYPE.value(),
                fluidState -> UP2Blocks.ASPHALT.get().defaultBlockState()
        ));
    }
}
