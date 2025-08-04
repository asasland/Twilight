package com.formal.twilight;

import com.formal.twilight.block.BlockRegistery;
import com.formal.twilight.block.TileEntityTypeRegistry;
import com.formal.twilight.capability.flowerBag.ContainerScreenFlowerBag;
import com.formal.twilight.capability.skill.SkillCapability;
import com.formal.twilight.common.CommonEventHandlers;
import com.formal.twilight.container.ContainerRegistry;
import com.formal.twilight.item.ItemRegistry;
import com.formal.twilight.net.NetworkHandler;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(Utils.MOD_ID)
public class Twilight {
    public Twilight() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();


        ItemRegistry.ITEMS.register(bus);
        BlockRegistery.BLOCKS.register(bus);
        TileEntityTypeRegistry.TILE_ENTITIES.register(bus);
        ContainerRegistry.CONTAINERS.register(bus);

        bus.addListener(this::setup);
    }
    private void setup(final FMLCommonSetupEvent event) {
        SkillCapability.register();
        event.enqueueWork(NetworkHandler::register);
    }

}
