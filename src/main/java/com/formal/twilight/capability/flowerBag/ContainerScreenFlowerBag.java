package com.formal.twilight.capability.flowerBag;

import com.formal.twilight.common.CommonEventHandlers;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.*;

public class ContainerScreenFlowerBag extends ContainerScreen<ContainerFlowerBag> {
    public ContainerScreenFlowerBag(ContainerFlowerBag container, PlayerInventory playerInv, ITextComponent title) {
        super(container, playerInv, title);
    }

    private static final ResourceLocation TEXTURE = new ResourceLocation("twilight", "textures/gui/flowerbag_bg.png");

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        final float PLAYER_LABEL_XPOS = 8;
        final float PLAYER_LABEL_DISTANCE_FROM_BOTTOM = (96 - 2);

        final float BAG_LABEL_YPOS = 6;


        TranslationTextComponent bagLabel = new TranslationTextComponent(CommonEventHandlers.itemFlowerBag.getDescriptionId());
        float BAG_LABEL_XPOS = (float) (this.imageWidth / 2 - this.font.width(bagLabel.getString()) / 2);
        this.font.draw(matrixStack, bagLabel, BAG_LABEL_XPOS, BAG_LABEL_YPOS, Color.DARK_GRAY.getRGB());

        // 玩家物品栏标签
        float PLAYER_LABEL_YPOS = this.imageHeight - PLAYER_LABEL_DISTANCE_FROM_BOTTOM;
        this.font.draw(matrixStack, this.inventory.getDisplayName(), PLAYER_LABEL_XPOS, PLAYER_LABEL_YPOS, Color.DARK_GRAY.getRGB());
    }


    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        // 渲染背景图像
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(TEXTURE);
        int edgeSpacingX = (this.width - this.imageWidth) / 2;
        int edgeSpacingY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, edgeSpacingX, edgeSpacingY, 0, 0, this.imageWidth, this.imageHeight);
    }
}
