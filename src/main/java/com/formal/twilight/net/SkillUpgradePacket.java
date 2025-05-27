package com.formal.twilight.net;

import com.formal.twilight.capability.SkillCapability;
import com.formal.twilight.capability.SkillType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillUpgradePacket {
    public final SkillType skillType;

    public SkillUpgradePacket(SkillType skillType) {
        this.skillType = skillType;
    }

    public static void encode(SkillUpgradePacket pkt, PacketBuffer buf) {
        buf.writeEnum(pkt.skillType);
    }

    public static SkillUpgradePacket decode(PacketBuffer buf) {
        return new SkillUpgradePacket(buf.readEnum(SkillType.class));
    }

    public static void handle(SkillUpgradePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player == null) {
                System.out.println("❌ SkillUpgradePacket：服务端未获取到玩家！");
                return;
            }

            player.getCapability(SkillCapability.CAP).ifPresent(cap -> {
                int current = cap.getSkillLevel(msg.skillType);
                cap.setSkillLevel(msg.skillType, current + 1);
                cap.applyAllAttributes(player);

                player.sendMessage(new StringTextComponent(
                                "技能 [" + msg.skillType.name() + "] 升级到等级 " + (current + 1)),
                        player.getUUID());

                System.out.println("✅ 服务端处理 SkillUpgradePacket：" + msg.skillType.name());
            });
        });
        ctx.get().setPacketHandled(true);
    }
}
