package com.formal.twilight.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemFlowerBag extends Item {
    private static final int MAXIMUM_NUMBER_OF_FLOWER_BAGS = 1;
    public  ItemFlowerBag(){
        super(new Item.Properties().stacksTo(MAXIMUM_NUMBER_OF_FLOWER_BAGS).tab(ItemGroup.TAB_MATERIALS));
    }
}
