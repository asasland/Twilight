package com.formal.twilight.capability;


import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;

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


    @Override
    public void applyAllAttributes(PlayerEntity player) {
        for (Map.Entry<SkillType, Integer> entry : skills.entrySet()) {
            SkillType type = entry.getKey();
            int level = entry.getValue();

            if (player.getAttribute(type.attr) == null) continue;

            player.getAttribute(type.attr).removeModifier(type.uuid);

            if (level > 0) {
                AttributeModifier modifier = new AttributeModifier(
                        type.uuid,
                        "twilight.skill." + type.name().toLowerCase(),
                        type.bonus * level,
                        AttributeModifier.Operation.ADDITION
                );
                player.getAttribute(type.attr).addPermanentModifier(modifier);
            }
        }
    }
}
