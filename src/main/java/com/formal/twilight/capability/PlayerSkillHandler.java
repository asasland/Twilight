package com.formal.twilight.capability;

import com.formal.twilight.Utils;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID)
public class PlayerSkillHandler {
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END || event.player.level.isClientSide) return;

        PlayerEntity player = event.player;
        player.getCapability(SkillCapability.CAP).ifPresent(skill -> skill.applyAllAttributes(player));
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        if (!player.level.isClientSide) {
            player.getCapability(SkillCapability.CAP).ifPresent(skill -> skill.applyAllAttributes(player));
        }
    }
}
