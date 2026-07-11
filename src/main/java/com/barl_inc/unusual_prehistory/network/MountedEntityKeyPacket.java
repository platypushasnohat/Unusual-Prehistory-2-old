package com.barl_inc.unusual_prehistory.network;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.barl_inc.unusual_prehistory.entity.utils.KeybindUsingMount;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class MountedEntityKeyPacket implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<MountedEntityKeyPacket> TYPE = new CustomPacketPayload.Type<>(UnusualPrehistory2.modPrefix("mounted_entity_key"));

    public static final StreamCodec<FriendlyByteBuf, MountedEntityKeyPacket> CODEC = StreamCodec.ofMember(MountedEntityKeyPacket::write, MountedEntityKeyPacket::read);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public int mountId;
    public int playerId;
    public int type;

    public MountedEntityKeyPacket(int mountId, int playerId, int type) {
        this.mountId = mountId;
        this.playerId = playerId;
        this.type = type;
    }

    public MountedEntityKeyPacket() {
    }

    public static MountedEntityKeyPacket read(FriendlyByteBuf buf) {
        return new MountedEntityKeyPacket(buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void write(MountedEntityKeyPacket message, FriendlyByteBuf buf) {
        buf.writeInt(message.mountId);
        buf.writeInt(message.playerId);
        buf.writeInt(message.type);
    }

    public static void handle(MountedEntityKeyPacket message, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player playerSided = context.player();
            if (playerSided != null) {
                Entity parent = playerSided.level().getEntity(message.mountId);
                Entity keyPresser = playerSided.level().getEntity(message.playerId);
                if (keyPresser != null && parent instanceof KeybindUsingMount mount && keyPresser instanceof Player && keyPresser.isPassengerOfSameVehicle(parent)) {
                    mount.onKeyPacket(keyPresser, message.type);
                }
            }
        });
    }
}