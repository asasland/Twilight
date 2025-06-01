package com.formal.twilight.client;
import com.formal.twilight.capability.SkillCapability;
import com.formal.twilight.capability.SkillType;
import com.formal.twilight.net.NetworkHandler;
import com.formal.twilight.net.SkillDowngradePacket;
import com.formal.twilight.net.SkillUpgradePacket;
import com.formal.twilight.net.TestPingPacket;
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
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // 升级攻击
        this.addButton(new Button(centerX - 120, centerY - 30, 100, 20,
                new StringTextComponent("升级攻击"), (btn) -> {
            NetworkHandler.INSTANCE.sendToServer(new SkillUpgradePacket(SkillType.ATTACK));
        }));

        // 降级攻击
        this.addButton(new Button(centerX + 20, centerY - 30, 100, 20,
                new StringTextComponent("降级攻击"), (btn) -> {
            NetworkHandler.INSTANCE.sendToServer(new SkillDowngradePacket(SkillType.ATTACK));
        }));

        // 升级移速
        this.addButton(new Button(centerX - 120, centerY, 100, 20,
                new StringTextComponent("升级移速"), (btn) -> {
            NetworkHandler.INSTANCE.sendToServer(new SkillUpgradePacket(SkillType.SPEED));
        }));

        // 降级移速
        this.addButton(new Button(centerX + 20, centerY, 100, 20,
                new StringTextComponent("降级移速"), (btn) -> {
            NetworkHandler.INSTANCE.sendToServer(new SkillDowngradePacket(SkillType.SPEED));
        }));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
