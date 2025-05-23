package com.formal.twilight.item;

import com.formal.twilight.Utils;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemModelsProperties.register(
                    ItemRegistry.newmagicIngot.get(),
                    new ResourceLocation(Utils.MOD_ID, "sprinting"),
                    (stack, world, entity) -> {
                        if (entity != null && entity.isSprinting()) {
                            return 1.0F;
                        }
                        return 0.0F;
                    }
            );
        });
    }
}

