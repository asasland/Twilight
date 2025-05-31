package com.formal.twilight.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SkillProvider implements ICapabilitySerializable<CompoundNBT> {
    private final SkillModel skillModel = new SkillModel();
    private final LazyOptional<SkillModel> optional = LazyOptional.of(() -> skillModel);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == SkillCapability.INSTANCE ? optional.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return skillModel.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        skillModel.deserializeNBT(nbt);
    }
}