package com.formal.twilight.container;

import com.formal.twilight.Utils;
import com.formal.twilight.capability.flowerBag.ContainerFlowerBag;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ContainerRegistry {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS =
            DeferredRegister.create(ForgeRegistries.CONTAINERS, Utils.MOD_ID);

    public static final RegistryObject<ContainerType<ContainerFlowerBag>> FLOWER_BAG_CONTAINER =
            CONTAINERS.register("flower_bag", () ->
                    IForgeContainerType.create(ContainerFlowerBag::new)
            );
}
