package com.formal.twilight.net;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class TestPingPacket {
    public TestPingPacket() {}

    public static void encode(TestPingPacket pkt, PacketBuffer buf) {
        // No data to encode
    }

    public static TestPingPacket decode(PacketBuffer buf) {
        return new TestPingPacket();
    }

    public static void handle(TestPingPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                System.out.println("✅ 服务端收到 TestPingPacket 来自玩家：" + player.getName().getString());
                player.sendMessage(new StringTextComponent("📡 Ping 收到啦！"), player.getUUID());
            } else {
                System.out.println("❌ TestPingPacket：服务端获取不到玩家！");
            }
        });
        ctx.get().setPacketHandled(true);
    }
}


