package com.barl_inc.unusual_prehistory.client.inventory;

import com.barl_inc.unusual_prehistory.UnusualPrehistory2;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class TransmogrifierScreen extends AbstractContainerScreen<TransmogrifierMenu> {

    private static final ResourceLocation TEXTURE = UnusualPrehistory2.modPrefix("textures/gui/transmogrifier.png");

    private static final int SCREEN_WIDTH = 176;
    private static final int SCREEN_HEIGHT = 166;

    private static final int PROGRESS_X = 62;
    private static final int PROGRESS_Y = 29;
    public static final int PROGRESS_WIDTH = 54;
    public static final int PROGRESS_HEIGHT = 20;

    private static final int FUEL_X = 102;
    private static final int FUEL_Y = 60;
    public static final int FUEL_WIDTH = 44;
    public static final int FUEL_HEIGHT = 14;

    public TransmogrifierScreen(TransmogrifierMenu handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        this.imageWidth = SCREEN_WIDTH;
        this.imageHeight = SCREEN_HEIGHT;
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        if (menu.isCrafting()) {
            int width = menu.getScaledProgress(PROGRESS_WIDTH);
            graphics.blit(TEXTURE, leftPos + PROGRESS_X, topPos + PROGRESS_Y, 176, 14, width, PROGRESS_HEIGHT);
        }

        int width = menu.getScaledFuel(FUEL_WIDTH);
        graphics.blit(TEXTURE, leftPos + FUEL_X, topPos + FUEL_Y, 176, 0, width, FUEL_HEIGHT);
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        this.renderBg(graphics, delta, mouseX, mouseY);
        super.render(graphics, mouseX, mouseY, delta);
        this.renderTooltip(graphics, mouseX, mouseY);
    }
}
