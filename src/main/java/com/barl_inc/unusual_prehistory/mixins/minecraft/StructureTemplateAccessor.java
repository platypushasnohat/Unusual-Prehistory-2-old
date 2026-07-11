package com.barl_inc.unusual_prehistory.mixins.minecraft;

import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(StructureTemplate.class)
public interface StructureTemplateAccessor {

    @Accessor("palettes")
    List<StructureTemplate.Palette> unusualPrehistory$getBlocks();

    @Accessor("entityInfoList")
    List<StructureTemplate.StructureEntityInfo> unusualPrehistory$getEntityInfoList();
}