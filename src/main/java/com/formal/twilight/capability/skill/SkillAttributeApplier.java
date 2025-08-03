package com.formal.twilight.capability.skill;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import java.util.UUID;

public class SkillAttributeApplier {

    private static final UUID ATTACK_MODIFIER_ID = UUID.nameUUIDFromBytes("twilight:skill.attack".getBytes());
    private static final UUID SPEED_MODIFIER_ID = UUID.nameUUIDFromBytes("twilight:skill.speed".getBytes());
    private static final UUID MINING_MODIFIER_ID = UUID.nameUUIDFromBytes("twilight:skill.mining".getBytes());

    public static void applyAll(PlayerEntity player) {
        SkillModel skill = SkillModel.get(player);

        double atkBonus = skill.getLevel(SkillType.ATTACK);
        updateModifier(player, Attributes.ATTACK_DAMAGE, ATTACK_MODIFIER_ID, atkBonus, "Attack Bonus");

        double speBonus = skill.getLevel(SkillType.SPEED) * 0.005;
        updateModifier(player, Attributes.MOVEMENT_SPEED,SPEED_MODIFIER_ID , speBonus, "Speed Bonus");
    }

    private static void updateModifier(PlayerEntity player, net.minecraft.entity.ai.attributes.Attribute attr, UUID id, double value, String name) {
        if (player.getAttribute(attr).getModifier(id) != null) {
            player.getAttribute(attr).removeModifier(id);
        }
        if (value != 0) {
            AttributeModifier modifier = new AttributeModifier(id, name, value, AttributeModifier.Operation.ADDITION);
            player.getAttribute(attr).addPermanentModifier(modifier);
        }
    }
}
