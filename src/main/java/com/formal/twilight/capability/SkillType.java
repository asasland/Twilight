package com.formal.twilight.capability;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.Attributes;

import java.util.UUID;

public enum SkillType {
    SPEED("移速", Attributes.MOVEMENT_SPEED, 0.05, UUID.fromString("1b0faaa4-5d3d-35c1-8aab-35c61c5e2c0e")),
    ATTACK("攻击", Attributes.ATTACK_DAMAGE, 0.5,UUID.fromString("ee7743c2-e86d-37e9-9d1b-cc8cdb8e84d3"));

    public final String name;
    public final Attribute attr;
    public final double bonus;

    public final UUID uuid;

    SkillType(String name, Attribute attr, double bonus,UUID uuid) {
        this.name = name;
        this.attr = attr;
        this.bonus = bonus;
        this.uuid = uuid;
    }
}

