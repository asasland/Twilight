package com.formal.twilight.capability.flowerBag;

import net.minecraftforge.items.IItemHandler;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityProviderFlowerBag implements ICapabilitySerializable<INBT> {


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == cap)
            return (LazyOptional<T>) lazyInitialisionSupplier;
        return LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(getCachedInventory(), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(getCachedInventory(), null, nbt);
    }

    private ItemStackHandlerFlowerBag getCachedInventory() {
        if (itemStackHandlerFlowerBag == null) {
            itemStackHandlerFlowerBag = new ItemStackHandlerFlowerBag(MAX_NUMBER_OF_FLOWERS_IN_BAG);
        }
        return itemStackHandlerFlowerBag;
    }

    private static final int MAX_NUMBER_OF_FLOWERS_IN_BAG = 16;
    private ItemStackHandlerFlowerBag itemStackHandlerFlowerBag;
    private final LazyOptional<IItemHandler> lazyInitialisionSupplier = LazyOptional.of(this::getCachedInventory);
}
