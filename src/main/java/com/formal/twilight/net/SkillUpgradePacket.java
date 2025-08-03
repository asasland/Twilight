package com.formal.twilight.net;

import com.formal.twilight.capability.skill.SkillAttributeApplier;
import com.formal.twilight.capability.skill.SkillCapability;
import com.formal.twilight.capability.skill.SkillType;
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
            if (player != null) {
                player.getCapability(SkillCapability.INSTANCE).ifPresent(skill -> {
                    int current = skill.getLevel(msg.skillType);
                    skill.setLevel(msg.skillType, current + 1);
                    player.sendMessage(new StringTextComponent("技能 [" + msg.skillType.name() + "] 升级到等级 " + (current + 1)), player.getUUID());
                    SkillAttributeApplier.applyAll(player);
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
