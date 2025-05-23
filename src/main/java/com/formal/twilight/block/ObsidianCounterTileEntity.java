package com.formal.twilight.block;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;



public class ObsidianCounterTileEntity extends TileEntity {
    private int counter = 0;

    public ObsidianCounterTileEntity() {
        super(TileEntityTypeRegistry.OBSIDIAN_COUNTER_TILE_ENTITY.get());
    }

    public int increase() {
        counter++;
        return counter;
    }


    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
        counter = nbt.getInt("counter");
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        compound.putInt("counter", counter);
        return super.save(compound);
    }
}
