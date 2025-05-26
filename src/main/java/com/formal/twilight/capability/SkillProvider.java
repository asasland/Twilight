package com.formal.twilight.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SkillProvider implements ICapabilitySerializable<CompoundNBT> {
    private final IPlayerSkill instance = new PlayerSkill();
    private final LazyOptional<IPlayerSkill> optional = LazyOptional.of(() -> instance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return SkillCapability.CAP.orEmpty(cap, optional);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        CompoundNBT skillsTag = new CompoundNBT();

        // 将每个技能等级写入一个子NBT
        instance.getSkillLevels().forEach((skillType, level) -> {
            skillsTag.putInt(skillType.name(), level);
        });

        // 写入整体技能标签
        nbt.put("Skills", skillsTag);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        CompoundNBT skillsTag = nbt.getCompound("Skills");

        // 从NBT中读取每个技能的等级
        for (SkillType type : SkillType.values()) {
            if (skillsTag.contains(type.name())) {
                instance.setSkillLevel(type, skillsTag.getInt(type.name()));
            }
        }
    }
}
