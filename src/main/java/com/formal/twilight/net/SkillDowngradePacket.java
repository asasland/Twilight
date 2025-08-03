package com.formal.twilight.net;

import com.formal.twilight.capability.skill.SkillAttributeApplier;
import com.formal.twilight.capability.skill.SkillCapability;
import com.formal.twilight.capability.skill.SkillType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SkillDowngradePacket {
    public final SkillType skillType;

    public SkillDowngradePacket(SkillType skillType) {
        this.skillType = skillType;
    }

    public static void encode(SkillDowngradePacket pkt, PacketBuffer buf) {
        buf.writeEnum(pkt.skillType);
    }

    public static SkillDowngradePacket decode(PacketBuffer buf) {
        return new SkillDowngradePacket(buf.readEnum(SkillType.class));
    }

    public static void handle(SkillDowngradePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();
            if (player != null) {
                player.getCapability(SkillCapability.INSTANCE).ifPresent(skill -> {
                    int current = skill.getLevel(msg.skillType);
                    if (current > 0) {
                        skill.setLevel(msg.skillType, current - 1);
                        player.sendMessage(new StringTextComponent("技能 [" + msg.skillType.name() + "] 降级到等级 " + (current - 1)), player.getUUID());
                        SkillAttributeApplier.applyAll(player);
                        System.out.println("⚠️ 服务端处理 SkillDowngradePacket：" + msg.skillType.name());
                    } else {
                        player.sendMessage(new StringTextComponent("技能 [" + msg.skillType.name() + "] 已经是最低等级"), player.getUUID());
                    }
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
