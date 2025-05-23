package com.formal.twilight.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.property.Properties;
import org.lwjgl.system.CallbackI;

public class MagicIngot extends Item {
    public MagicIngot() {
        super(new Properties().tab(ItemGroup.TAB_MATERIALS));
    }
}

