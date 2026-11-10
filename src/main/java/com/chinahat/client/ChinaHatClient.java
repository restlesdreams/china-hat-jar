package com.chinahat.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChinaHatClient implements ClientModInitializer {
    public static final String MOD_NAME = "China Hat";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static KeyBinding openGuiKey;
    private static Path configPath;

    public static boolean watermark = true;
    public static boolean coordinates = true;
    public static boolean fps = true;
    public static boolean arrayList = true;
    public static int accentColor = 0xFF55CCFF;

    @Override
    public void onInitializeClient() {
        configPath = MinecraftClient.getInstance().runDirectory.toPath()
                .resolve("config").resolve("china-hat.json");
        loadConfig();

        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.china_hat.open_gui",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                "category.china_hat"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGuiKey.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new ChinaHatScreen());
                }
            }
        });

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null || client.options.hudHidden) return;

            int x = 8;
            int y = 8;
            int color = accentColor;

            if (watermark) {
                drawContext.drawTextWithShadow(client.textRenderer,
                        Text.literal("CHINA HAT"), x, y, color);
                y += 12;
            }
            if (fps) {
                drawContext.drawTextWithShadow(client.textRenderer,
                        Text.literal("FPS: " + client.getCurrentFps()), x, y, 0xFFFFFFFF);
                y += 12;
            }
            if (coordinates && client.player != null) {
                String coords = String.format("XYZ: %.1f / %.1f / %.1f",
                        client.player.getX(), client.player.getY(), client.player.getZ());
                drawContext.drawTextWithShadow(client.textRenderer,
                        Text.literal(coords), x, y, 0xFFFFFFFF);
            }

            if (arrayList) {
                int right = client.getWindow().getScaledWidth() - 8;
                int listY = 8;
                String[] enabled = enabledModules();
                for (String module : enabled) {
                    int width = client.textRenderer.getWidth(module);
                    drawContext.drawTextWithShadow(client.textRenderer,
                            Text.literal(module), right - width, listY, color);
                    listY += 11;
                }
            }
        });
    }

    private static String[] enabledModules() {
        java.util.ArrayList<String> modules = new java.util.ArrayList<>();
        if (watermark) modules.add("Watermark");
        if (coordinates) modules.add("Coordinates");
        if (fps) modules.add("FPS");
        return modules.toArray(new String[0]);
    }

    public static void saveConfig() {
        try {
            Files.createDirectories(configPath.getParent());
            JsonObject json = new JsonObject();
            json.addProperty("watermark", watermark);
            json.addProperty("coordinates", coordinates);
            json.addProperty("fps", fps);
            json.addProperty("arrayList", arrayList);
            json.addProperty("accentColor", accentColor);
            try (Writer writer = Files.newBufferedWriter(configPath)) {
                GSON.toJson(json, writer);
            }
        } catch (Exception e) {
            System.err.println("[China Hat] Could not save config: " + e.getMessage());
        }
    }

    private static void loadConfig() {
        if (!Files.exists(configPath)) return;
        try (Reader reader = Files.newBufferedReader(configPath)) {
            JsonObject json = GSON.fromJson(reader, JsonObject.class);
            if (json == null) return;
            if (json.has("watermark")) watermark = json.get("watermark").getAsBoolean();
            if (json.has("coordinates")) coordinates = json.get("coordinates").getAsBoolean();
            if (json.has("fps")) fps = json.get("fps").getAsBoolean();
            if (json.has("arrayList")) arrayList = json.get("arrayList").getAsBoolean();
            if (json.has("accentColor")) accentColor = json.get("accentColor").getAsInt();
        } catch (Exception e) {
            System.err.println("[China Hat] Could not load config: " + e.getMessage());
        }
    }
}
