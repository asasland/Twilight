package com.formal.twilight.common;

import com.formal.twilight.Utils;
import com.formal.twilight.capability.SkillProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID)
public class CommonEventHandlers {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<PlayerEntity> event) {
        event.addCapability(new ResourceLocation(Utils.MOD_ID, "skill"), new SkillProvider());
    }
}
