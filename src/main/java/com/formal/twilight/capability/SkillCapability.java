package com.formal.twilight.capability;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class SkillCapability {
    public static Capability<IPlayerSkill> CAP;

    public static void register() {
        CapabilityManager.INSTANCE.register(IPlayerSkill.class, new Storage(), PlayerSkill::new);
    }

    private static class Storage implements IStorage<IPlayerSkill> {

        @Override
        public INBT writeNBT(Capability<IPlayerSkill> capability, IPlayerSkill instance, Direction side) {
            CompoundNBT tag = new CompoundNBT();
            CompoundNBT skillsTag = new CompoundNBT();

            instance.getSkillLevels().forEach((skillType, level) -> {
                skillsTag.putInt(skillType.name(), level);
            });
            tag.put("Skills", skillsTag);
            return tag;
        }

        @Override
        public void readNBT(Capability<IPlayerSkill> capability, IPlayerSkill instance, Direction side, INBT nbt) {
            if (nbt instanceof CompoundNBT) {
                CompoundNBT tag = (CompoundNBT) nbt;
                CompoundNBT skillsTag = tag.getCompound("Skills");

                for (SkillType skill : SkillType.values()) {
                    if (skillsTag.contains(skill.name())) {
                        instance.setSkillLevel(skill, skillsTag.getInt(skill.name()));
                    }
                }
            }
        }
    }
}
