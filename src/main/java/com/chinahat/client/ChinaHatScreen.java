package com.chinahat.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ChinaHatScreen extends Screen {
    private ButtonWidget watermarkButton;
    private ButtonWidget coordsButton;
    private ButtonWidget fpsButton;
    private ButtonWidget arrayButton;
    private ButtonWidget colorButton;

    public ChinaHatScreen() {
        super(Text.literal("China Hat ClickGUI"));
    }

    @Override
    protected void init() {
        int panelWidth = 270;
        int left = (this.width - panelWidth) / 2;
        int top = Math.max(28, (this.height - 270) / 2);
        int buttonX = left + 20;
        int buttonWidth = panelWidth - 40;
        int buttonY = top + 65;

        watermarkButton = addDrawableChild(ButtonWidget.builder(toggleText("Watermark", ChinaHatClient.watermark), b -> {
            ChinaHatClient.watermark = !ChinaHatClient.watermark;
            b.setMessage(toggleText("Watermark", ChinaHatClient.watermark));
            ChinaHatClient.saveConfig();
        }).dimensions(buttonX, buttonY, buttonWidth, 20).build());

        coordsButton = addDrawableChild(ButtonWidget.builder(toggleText("Coordinates", ChinaHatClient.coordinates), b -> {
            ChinaHatClient.coordinates = !ChinaHatClient.coordinates;
            b.setMessage(toggleText("Coordinates", ChinaHatClient.coordinates));
            ChinaHatClient.saveConfig();
        }).dimensions(buttonX, buttonY + 27, buttonWidth, 20).build());

        fpsButton = addDrawableChild(ButtonWidget.builder(toggleText("FPS Counter", ChinaHatClient.fps), b -> {
            ChinaHatClient.fps = !ChinaHatClient.fps;
            b.setMessage(toggleText("FPS Counter", ChinaHatClient.fps));
            ChinaHatClient.saveConfig();
        }).dimensions(buttonX, buttonY + 54, buttonWidth, 20).build());

        arrayButton = addDrawableChild(ButtonWidget.builder(toggleText("Array List", ChinaHatClient.arrayList), b -> {
            ChinaHatClient.arrayList = !ChinaHatClient.arrayList;
            b.setMessage(toggleText("Array List", ChinaHatClient.arrayList));
            ChinaHatClient.saveConfig();
        }).dimensions(buttonX, buttonY + 81, buttonWidth, 20).build());

        colorButton = addDrawableChild(ButtonWidget.builder(colorText(), b -> {
            ChinaHatClient.accentColor = nextColor(ChinaHatClient.accentColor);
            b.setMessage(colorText());
            ChinaHatClient.saveConfig();
        }).dimensions(buttonX, buttonY + 112, buttonWidth, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Done"), b -> close())
                .dimensions(buttonX, buttonY + 151, buttonWidth, 20).build());
    }

    private static Text toggleText(String name, boolean enabled) {
        return Text.literal(name + ": " + (enabled ? "ON" : "OFF"));
    }

    private static Text colorText() {
        return Text.literal("Accent color: " + colorName(ChinaHatClient.accentColor) + "  (click to change)");
    }

    private static String colorName(int color) {
        return switch (color) {
            case 0xFF55CCFF -> "Sky Blue";
            case 0xFFFF5555 -> "Red";
            case 0xFF55FF88 -> "Mint";
            case 0xFFFFAA33 -> "Orange";
            case 0xFFCC66FF -> "Purple";
            case 0xFFFFFF55 -> "Yellow";
            case 0xFFFFFFFF -> "White";
            default -> "Custom";
        };
    }

    private static int nextColor(int current) {
        int[] colors = {
                0xFF55CCFF, 0xFFFF5555, 0xFF55FF88,
                0xFFFFAA33, 0xFFCC66FF, 0xFFFFFF55, 0xFFFFFFFF
        };
        for (int i = 0; i < colors.length; i++) {
            if (colors[i] == current) return colors[(i + 1) % colors.length];
        }
        return colors[0];
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        int panelWidth = 270;
        int panelHeight = 260;
        int left = (this.width - panelWidth) / 2;
        int top = (this.height - panelHeight) / 2;
        context.fill(left, top, left + panelWidth, top + panelHeight, 0xEE10131D);
        context.fill(left, top, left + panelWidth, top + 3, ChinaHatClient.accentColor);
        context.drawCenteredTextWithShadow(this.textRenderer,
                Text.literal("CHINA HAT"), this.width / 2, top + 18, ChinaHatClient.accentColor);
        context.drawCenteredTextWithShadow(this.textRenderer,
                Text.literal("Visual Client  •  Fabric 1.20.1"), this.width / 2, top + 38, 0xFFB8C0D0);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void close() {
        ChinaHatClient.saveConfig();
        if (this.client != null) this.client.setScreen(null);
    }
}
