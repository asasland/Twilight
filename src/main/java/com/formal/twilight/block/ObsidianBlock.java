package com.formal.twilight.block;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ObsidianBlock extends Block {
    public ObsidianBlock() {
        super(AbstractBlock.Properties.of(Material.STONE)
                .strength(50.0f, 1200.0f)
                .sound(SoundType.METAL)
                .harvestLevel(2)
                .harvestTool(ToolType.PICKAXE)
                .lightLevel(value -> 15));
    }
}
