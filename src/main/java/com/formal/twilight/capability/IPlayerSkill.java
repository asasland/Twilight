package com.formal.twilight.capability;

import net.minecraft.entity.player.PlayerEntity;

import java.util.Map;

public interface IPlayerSkill {
    Map<SkillType, Integer> getSkillLevels();
    void setSkillLevel(SkillType type, int level);
    int getSkillLevel(SkillType type);

    void applyAllAttributes(PlayerEntity player);
}
