package com.formal.twilight.capability.flowerBag;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityProviderFlowerBag implements ICapabilitySerializable<CompoundNBT> {

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == cap)
            return (LazyOptional<T>) lazyInitialisionSupplier;
        return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return (CompoundNBT) CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(getCachedInventory(), null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
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
