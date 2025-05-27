package com.formal.twilight.item;

import com.formal.twilight.Utils;
import com.formal.twilight.capability.SkillProvider;
import com.formal.twilight.client.KeybindHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraft.item.ItemStack;
@Mod.EventBusSubscriber(modid ="twilight",bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class PropertyRegistry {
    @SubscribeEvent//物品属性覆盖
    public static void propertyOverrideRegistry(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemModelsProperties.register(
                    ItemRegistry.newmagicIngot.get(),
                    new ResourceLocation(Utils.MOD_ID, "sprinting"),
                    (stack, world, entity) -> entity != null && entity.isSprinting() ? 1.0F : 0.0F
            );
            ItemModelsProperties.register(
                    ItemRegistry.magicIngot.get(),
                    new ResourceLocation(Utils.MOD_ID, "size"),
                    (stack, world, entity) -> stack.getCount()
            );
        });

    }
}