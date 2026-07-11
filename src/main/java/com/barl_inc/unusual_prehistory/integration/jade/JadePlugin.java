package com.barl_inc.unusual_prehistory.integration.jade;

import com.barl_inc.unusual_prehistory.blocks.TransmogrifierBlock;
import com.barl_inc.unusual_prehistory.blocks.egg.EggBlock;
import com.barl_inc.unusual_prehistory.blocks.entity.TransmogrifierBlockEntity;
import com.barl_inc.unusual_prehistory.entity.mob.base.PrehistoricMob;
import com.barl_inc.unusual_prehistory.entity.mob.other.LivingOoze;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.Arthropleura;
import com.barl_inc.unusual_prehistory.entity.mob.paleozoic.ArthropleuraPart;
import net.minecraft.world.entity.Entity;
import snownee.jade.api.*;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(new TransmogrifierProvider(), TransmogrifierBlockEntity.class);
        registration.registerBlockDataProvider(new EggBlockProvider(), EggBlock.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerEntityComponent(new PrehistoricMobProvider(), PrehistoricMob.class);
        registration.registerEntityComponent(new LivingOozeProvider(), LivingOoze.class);
        registration.registerBlockComponent(new TransmogrifierProvider(), TransmogrifierBlock.class);
        registration.registerBlockComponent(new EggBlockProvider(), EggBlock.class);

        registration.addRayTraceCallback((hitResult, accessor, originalAccessor) -> {
            if (accessor instanceof EntityAccessor entityAccessor) {
                Entity entity = entityAccessor.getEntity();
                if (entity instanceof ArthropleuraPart arthropleuraPart) {
                    Entity headEntity = arthropleuraPart.getHeadEntity();
                    if (headEntity instanceof Arthropleura arthropleura) {
                        return registration.entityAccessor().from(entityAccessor).entity(arthropleura).build();
                    }
                }
            }
            return accessor;
        });
    }
}
