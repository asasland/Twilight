package com.formal.twilight.client;

import com.formal.twilight.Utils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID, value = Dist.CLIENT)
public class KeybindRegisty {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeybindHandler.OPEN_SKILL.isDown()) {
            System.out.println("客户端：打开！");
//            NetworkHandler.INSTANCE.sendToServer(new SkillUpgradePacket(SkillType.SPEED));
            Minecraft.getInstance().setScreen(new SkillScreen());
        }
    }
}
