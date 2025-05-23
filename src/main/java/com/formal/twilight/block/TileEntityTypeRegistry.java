package com.formal.twilight.block;

import com.formal.twilight.Utils;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityTypeRegistry {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Utils.MOD_ID);
    public static final RegistryObject<TileEntityType<ObsidianCounterTileEntity>> OBSIDIAN_COUNTER_TILE_ENTITY =
            TILE_ENTITIES.register("obsidian_counter_tileentity",
                    () -> TileEntityType.Builder.of(ObsidianCounterTileEntity::new, BlockRegistery.obsidianCounterBlock.get()).build(null));
}