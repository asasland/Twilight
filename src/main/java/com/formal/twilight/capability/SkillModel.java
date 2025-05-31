package com.formal.twilight.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.EnumMap;
import java.util.Map;

public class SkillModel implements INBTSerializable<CompoundNBT> {
    private final Map<SkillType, Integer> levels = new EnumMap<>(SkillType.class);

    public SkillModel() {
        for (SkillType type : SkillType.values()) {
            levels.put(type, 0);
        }
    }

    public int getLevel(SkillType type) {
        return levels.getOrDefault(type, 0);
    }

    public void setLevel(SkillType type, int level) {
        levels.put(type, level);
    }

    public void addLevel(SkillType type, int delta) {
        levels.put(type, getLevel(type) + delta);
    }

    public static SkillModel get(PlayerEntity player) {
        return player.getCapability(SkillCapability.INSTANCE)
                .orElseThrow(() -> new IllegalArgumentException("Player " + player.getName().getString() + " missing skill capability"));
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        for (SkillType type : SkillType.values()) {
            tag.putInt(type.name(), getLevel(type));
        }
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        for (SkillType type : SkillType.values()) {
            if (nbt.contains(type.name())) {
                setLevel(type, nbt.getInt(type.name()));
            }
        }
    }
}