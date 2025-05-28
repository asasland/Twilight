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

            int level = skills.get(entry.getKey());

            if (player.getAttribute(entry.getKey().attr) == null)
                continue;

            // 清除旧 modifier
            player.getAttribute(entry.getKey().attr).removeModifier(entry.getKey().uuid);

            // 即使为 0 也添加，确保属性存在
            AttributeModifier modifier = new AttributeModifier(
                    entry.getKey().uuid,
                    "twilight.skill." + entry.getKey().name().toLowerCase(),
                    entry.getKey().bonus * level,
                    AttributeModifier.Operation.ADDITION
            );
            player.getAttribute(entry.getKey().attr).addPermanentModifier(modifier);
        }
    }
}
