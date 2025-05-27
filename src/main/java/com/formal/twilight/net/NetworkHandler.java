package com.formal.twilight.net;


import com.formal.twilight.Utils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "1.0";

    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
            .named(new ResourceLocation(Utils.MOD_ID, "main"))
            .networkProtocolVersion(() -> "1.0")
            .clientAcceptedVersions(s -> true)
            .serverAcceptedVersions(s -> true)
            .simpleChannel();

    private static int packetId = 0;

    // 用于自增获取唯一 ID
    private static int nextId() {
        return packetId++;
    }

    // 注册所有数据包
    public static void register() {
        INSTANCE.registerMessage(
                nextId(),
                SkillUpgradePacket.class,
                SkillUpgradePacket::encode,
                SkillUpgradePacket::decode,
                SkillUpgradePacket::handle
        );
        System.out.println("✅ [NetworkHandler] SkillUpgradePacket 注册成功！");
    }
}

