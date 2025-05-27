package com.formal.twilight.client;

import com.formal.twilight.capability.IPlayerSkill;
import com.formal.twilight.capability.SkillCapability;
import com.formal.twilight.capability.SkillType;
import com.formal.twilight.net.NetworkHandler;
import com.formal.twilight.net.SkillUpgradePacket;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class SkillScreen extends Screen {

    public SkillScreen() {
        super(new TranslationTextComponent("key.twilight.openskill"));
    }

    @Override
    protected void init() {
        // 示例：创建两个按钮，分别升级攻击和移速
        this.addButton(new Button(this.width / 2 - 60, this.height / 2 - 10, 120, 20,
                new StringTextComponent("升级攻击"), (btn) -> {
                NetworkHandler.INSTANCE.sendToServer(new SkillUpgradePacket(SkillType.ATTACK));
        }));

        this.addButton(new Button(this.width / 2 - 60, this.height / 2 + 20, 120, 20,
                new StringTextComponent("升级移速"), (btn) -> {
                System.out.println("客户端：发送升级技能请求！");
                NetworkHandler.INSTANCE.sendToServer(new SkillUpgradePacket(SkillType.SPEED));
        }));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
