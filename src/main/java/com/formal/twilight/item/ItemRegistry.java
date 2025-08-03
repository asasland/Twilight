package com.formal.twilight.item;

import com.formal.twilight.Utils;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Utils.MOD_ID);
    public static final RegistryObject<Item> obsidianIngot = ITEMS.register("obsidian_ingot", ObsidianIngot::new);
    public static final RegistryObject<Item> obsidian = ITEMS.register("obsidian", ObsidianIngot::new);

    public static final RegistryObject<Item> magicIngot = ITEMS.register("magic_ingot", MagicIngot::new);
    public static final RegistryObject<Item> newmagicIngot = ITEMS.register("magic_ingot2", NewMagicIngot::new);

    public static final RegistryObject<Item> flowerBag = ITEMS.register("flowerbag", ItemFlowerBag::new);
}
