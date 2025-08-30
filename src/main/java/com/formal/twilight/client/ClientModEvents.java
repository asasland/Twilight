package com.formal.twilight.client;

import com.formal.twilight.Utils;
import com.formal.twilight.container.ContainerRegistry;
import com.formal.twilight.capability.flowerBag.ContainerScreenFlowerBag;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // 把注册的容器类型和对应的屏幕类绑定
        ScreenManager.register(ContainerRegistry.CONTAINER_FLOWER_BAG.get(), ContainerScreenFlowerBag::new);
        System.out.println("正在注册 FlowerBag GUI Screen");
    }
}