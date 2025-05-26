package com.formal.twilight.capability;


import java.util.EnumMap;
import java.util.Map;

public class PlayerSkill implements IPlayerSkill {
    private final Map<SkillType, Integer> skills = new EnumMap<>(SkillType.class);

    @Override
    public Map<SkillType, Integer> getSkillLevels() {
        return skills;
    }

    @Override
    public void setSkillLevel(SkillType type, int level) {
        skills.put(type, level);
    }

    @Override
    public int getSkillLevel(SkillType type) {
        return skills.getOrDefault(type, 0);
    }
}
