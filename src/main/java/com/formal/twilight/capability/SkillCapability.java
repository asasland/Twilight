package com.formal.twilight.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class SkillCapability {
    public static Capability<IPlayerSkill> CAP = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IPlayerSkill.class, new Storage(), PlayerSkill::new);
    }

    private static class Storage implements IStorage<IPlayerSkill> {

        @Override
        public INBT writeNBT(Capability<IPlayerSkill> capability, IPlayerSkill instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            // 假设IPlayerSkill有方法getAllSkills返回Map<SkillType, Integer>
            instance.getSkillLevels().forEach((skillType, level) -> {
                tag.putInt(skillType.name(), level);
            });
            return tag;
        }

        @Override
        public void readNBT(Capability<IPlayerSkill> capability, IPlayerSkill instance, Direction side, INBT nbt) {
            if (nbt instanceof CompoundNBT) {
                CompoundNBT tag = (CompoundNBT) nbt;
                for (SkillType skill : SkillType.values()) {
                    if (tag.contains(skill.name())) {
                        instance.setSkillLevel(skill, tag.getInt(skill.name()));
                    }
                }
            }
        }
    }
}
