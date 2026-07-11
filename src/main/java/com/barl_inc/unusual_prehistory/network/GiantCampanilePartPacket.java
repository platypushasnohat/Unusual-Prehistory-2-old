package com.barl_inc.unusual_prehistory.network;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.mob.cenozoic.GiantCampanile;
import com.barl_inc.unusual_prehistory.registry.UP2SoundEvents;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class GiantCampanilePartPacket implements CustomPacketPayload {

    public static final Type<GiantCampanilePartPacket> TYPE = new Type<>(UnusualPrehistory2.modPrefix("giant_campanile_part"));

    public static final StreamCodec<FriendlyByteBuf, GiantCampanilePartPacket> CODEC = StreamCodec.ofMember(GiantCampanilePartPacket::write, GiantCampanilePartPacket::read);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public int parentId;
    public int playerId;

    public GiantCampanilePartPacket(int parentId, int playerId) {
        this.parentId = parentId;
        this.playerId = playerId;
    }


    public GiantCampanilePartPacket() {
    }

    public static GiantCampanilePartPacket read(FriendlyByteBuf buf) {
        return new GiantCampanilePartPacket(buf.readInt(), buf.readInt());
    }

    public static void write(GiantCampanilePartPacket message, FriendlyByteBuf buf) {
        buf.writeInt(message.parentId);
        buf.writeInt(message.playerId);
    }

    public static void handle(GiantCampanilePartPacket message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (!player.level().isClientSide) {
                Entity parent = player.level().getEntity(message.parentId);
                if (parent != null && parent.isMultipartEntity() && player.distanceTo(parent) < 16 && parent instanceof GiantCampanile giantCampanile) {
                    player.level().playSound(null, parent.blockPosition(), UP2SoundEvents.GIANT_CAMPANILE_BLOCK.get(), SoundSource.NEUTRAL, 1.0F, giantCampanile.getVoicePitch());
                }
            }
        });
    }
}