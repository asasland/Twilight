package com.formal.twilight.capability.flowerBag;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.ItemStackHandler;
import javax.annotation.Nonnull;

public class ItemStackHandlerFlowerBag extends ItemStackHandler{
    public static final int MIN_FLOWER_SLOTS = 1;
    public static final int MAX_FLOWER_SLOTS = 16;

    public ItemStackHandlerFlowerBag(int numberOfSlots){
        super(MathHelper.clamp(numberOfSlots, MIN_FLOWER_SLOTS, MAX_FLOWER_SLOTS));
        if (numberOfSlots < MIN_FLOWER_SLOTS || numberOfSlots > MAX_FLOWER_SLOTS) {
            throw new IllegalArgumentException("Invalid number of flower slots:"+numberOfSlots);
        }
    }
    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (slot < 0 || slot >= MAX_FLOWER_SLOTS) {
            throw new IllegalArgumentException("Invalid slot number:"+slot);
        }
        if (stack.isEmpty()) return false;
        Item item = stack.getItem();
        if (item.is(ItemTags.SMALL_FLOWERS) || item.is(ItemTags.TALL_FLOWERS)) return true;
        return false;
    }

    public int getNumberOfEmptySlots() {
        final int NUMBER_OF_SLOTS = getSlots();

        int emptySlotCount = 0;
        for (int i = 0; i < NUMBER_OF_SLOTS; ++i) {
            if (getStackInSlot(i) == ItemStack.EMPTY) {
                ++emptySlotCount;
            }
        }
        return emptySlotCount;
    }

    public boolean isDirty() {
        boolean currentState = isDirty;
        isDirty = false;
        return currentState;
    }


    protected void onContentsChanged(int slot) {
        isDirty = true;
    }

    private boolean isDirty = true;
}
