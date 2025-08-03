package com.formal.twilight.common;

import com.formal.twilight.Utils;
import com.formal.twilight.capability.flowerBag.ContainerFlowerBag;
import com.formal.twilight.capability.skill.SkillAttributeApplier;
import com.formal.twilight.capability.skill.SkillProvider;
import com.formal.twilight.item.ItemFlowerBag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Utils.MOD_ID)
public class CommonEventHandlers {
    public static ItemFlowerBag itemFlowerBag;  // this holds the unique instance of your block
    public static ContainerType<ContainerFlowerBag> containerTypeFlowerBag;
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(Utils.MOD_ID, "skill"), new SkillProvider());
        }
    }
    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        SkillAttributeApplier.applyAll(event.getPlayer());
    }

    @SubscribeEvent
    public static void onDimensionChange(PlayerEvent.PlayerChangedDimensionEvent event) {
        SkillAttributeApplier.applyAll(event.getPlayer());
    }
}

