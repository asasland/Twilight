package com.formal.twilight.capability;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class SkillCapability {
    @CapabilityInject(SkillModel.class)
    public static Capability<SkillModel> INSTANCE;
    public static void register() {
        CapabilityManager.INSTANCE.register(
                SkillModel.class,
                new SkillStorage(),
                SkillModel::new
        );
    }
}