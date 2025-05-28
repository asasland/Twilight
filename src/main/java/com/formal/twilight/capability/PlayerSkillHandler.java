package com.formal.twilight.capability;

import com.formal.twilight.Utils;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID)
public class PlayerSkillHandler {
    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();
        if (!player.level.isClientSide) {
            player.getCapability(SkillCapability.CAP).ifPresent(skill -> skill.applyAllAttributes(player));
            if(player.getCapability(SkillCapability.CAP).isPresent()) {
                player.sendMessage(new StringTextComponent("登入了" + player.getCapability(SkillCapability.CAP)), player.getUUID());
            }
        }
    }
}
