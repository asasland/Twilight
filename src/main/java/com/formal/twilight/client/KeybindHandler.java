package com.formal.twilight.client;

import com.formal.twilight.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE,value = Dist.CLIENT)
public class KeybindHandler {
    public static KeyBinding OPEN_SKILL;

    public static void register() {
        OPEN_SKILL = new KeyBinding("key.twilight.openskill", GLFW.GLFW_KEY_K, "key.categories.misc");
        ClientRegistry.registerKeyBinding(OPEN_SKILL);
        MinecraftForge.EVENT_BUS.register(new KeybindHandler());
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (OPEN_SKILL.isDown()) {
            Minecraft.getInstance().setScreen(new SkillScreen());
        }
    }
}
