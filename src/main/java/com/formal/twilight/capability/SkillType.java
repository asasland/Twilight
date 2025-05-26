package com.formal.twilight.capability;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;

public enum SkillType {
    SPEED("移速", Attributes.MOVEMENT_SPEED, 0.05),
    ATTACK("攻击", Attributes.ATTACK_DAMAGE, 0.5);

    public final String name;
    public final Attribute attr;
    public final double bonus;

    SkillType(String name, Attribute attr, double bonus) {
        this.name = name;
        this.attr = attr;
        this.bonus = bonus;
    }
}

