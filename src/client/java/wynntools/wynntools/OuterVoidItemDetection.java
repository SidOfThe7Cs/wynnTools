package wynntools.wynntools;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.types.Type;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import wynntools.wynntools.mixin.client.GameRendererMixin;

import java.awt.*;
import java.util.*;

public class OuterVoidItemDetection implements ClientModInitializer {

    // Track seen item entity UUIDs to avoid duplicate logging
    private static final Map<UUID, ItemEntity> seenItems = new HashMap<>();
    private static final Set<ItemEntity> unknownItems = new HashSet<>();

    @Override
    public void onInitializeClient() {
    }

    // runs every tick
    public static void processSeenItems(MinecraftClient client){
        if (!Config.isOuter_Void_Item_Helper_Main_Toggle()) return;
        if (client.world == null) return;
        if (MinecraftClient.getInstance().player == null) return;

        Map<UUID, ItemEntity> nearbyItems = new HashMap<>();

        PlayerEntity player = MinecraftClient.getInstance().player;

        //check if player coords are in outer void
        if (!isInOuterVoid(player.getX(), player.getZ())) return;


        double range = 64;
        Box box = player.getBoundingBox().expand(range);
        for (ItemEntity itemEntity : client.world.getEntitiesByClass(ItemEntity.class, box, item -> true)) {
            nearbyItems.put(itemEntity.getUuid(), itemEntity);
            if (seenItems.containsKey(itemEntity.getUuid())){
                continue;
            }

            // New item detected!
            seenItems.put(itemEntity.getUuid(), itemEntity);

            Utils.Rarities rarity = OuterVoidItemDatabase.getRarity(itemEntity);
            if (rarity == Utils.Rarities.UNKNOWN) {
                if (!unknownItems.contains(itemEntity)) {
                    unknownItems.add(itemEntity);
                    System.out.println("Name: " + itemEntity.getStack().getName());
                    System.out.println("Model: " + itemEntity.getStack().get(DataComponentTypes.CUSTOM_MODEL_DATA).floats().getFirst());
                    System.out.println("Rarity: " + OuterVoidItemDatabase.getRarity(itemEntity).toString());
                    System.out.println("durability: " + itemEntity.getStack().getDamage());
                }
            }
        }

        Set<UUID> toRemove = new HashSet<>();

        Box box2 = player.getBoundingBox().expand(45);
        // loop through all cached items
        for (Map.Entry<UUID, ItemEntity> entry : seenItems.entrySet()) {
            UUID id = entry.getKey();
            ItemEntity item = entry.getValue();
            // if the item is close enough to the player to be gotten from the server
            if (box2.contains(item.getPos())){
                // if the item is not gotten from the server
                if (!nearbyItems.containsKey(id)){
                    toRemove.add(id);
                }
            }
        }

        for (UUID id : toRemove){
            seenItems.remove(id);
        }
    }

    public static boolean isInOuterVoid(double x, double z) {
        // Define the bounding box coordinates
        int minX = 13577;
        int maxX = 14046;
        int minZ = -3588;
        int maxZ = -3187;

        // Check if player is within the bounds
        return x >= minX && x <= maxX &&
                z >= minZ && z <= maxZ;
    }

    public static void renderItemsToHud(DrawContext context, RenderTickCounter renderTickCounter) {
        if (!Config.isOuter_Void_Item_Helper_Main_Toggle()) return;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;
        if (client.player == null) return;
        if (!isInOuterVoid(client.player.getX(), client.player.getZ())) return;

        Camera camera = client.gameRenderer.getCamera();
        GameRenderer renderer = client.gameRenderer;
        double FOV = ((GameRendererMixin) (Object) renderer).invokeGetFov(
                camera,
                client.getRenderTickCounter().getTickDelta(true),
                true
        );

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        Vec3d cameraPos = camera.getPos();


        // Get camera angles in radians
        float yaw = (float)Math.toRadians(camera.getYaw());
        float pitch = (float)Math.toRadians(-camera.getPitch());
        for (Map.Entry<UUID, ItemEntity> entry : seenItems.entrySet()) {
            ItemEntity item = entry.getValue();
            Vec3d itemPos = item.getPos();

            //item detection
            String name = item.getStack().getName().getString();
            CustomModelDataComponent modelData = item.getStack().get(DataComponentTypes.CUSTOM_MODEL_DATA);
            float ID;
            if (modelData != null) {
                ID = modelData.floats().getFirst();
            } else ID = -1;
            if (Objects.equals(name, "Diamond Axe")){
                ID = item.getStack().getDamage();
            }
            Utils.Rarities rarity = OuterVoidItemDatabase.getRarity(name, ID);
            Color color = OuterVoidItemDatabase.getColor(rarity);

            if (rarity.ordinal() < Config.getLowest_rarity_To_Show().ordinal()) continue;

            // Calculate relative position to camera
            double dx = itemPos.x - cameraPos.x;
            double dy = itemPos.y - cameraPos.y;
            double dz = itemPos.z - cameraPos.z;
            dx *= -1; // we dont ask questions we just provide solutions

            // Rotate based on yaw (horizontal rotation)
            double x = dx * Math.cos(yaw) - dz * Math.sin(yaw);
            double z = dx * Math.sin(yaw) + dz * Math.cos(yaw);

            // Rotate based on pitch (vertical rotation)
            double y = dy * Math.cos(pitch) - z * Math.sin(pitch);
            z = dy * Math.sin(pitch) + z * Math.cos(pitch);

            // if behind camera dont do weird stuff
            if (z <= 0.1) {
                z = 0.1;
            }


            // Perspective projection
            double scale = Math.min(screenWidth, screenHeight) / (2.0 * Math.tan(Math.toRadians(FOV) / 2));
            double screenX = (x / z) * scale + screenWidth / 2;
            double screenY = (-y / z) * scale + screenHeight / 2;

            int minSize = 4;
            int edgeSize = minSize * 3;
            int size = (int) (100 / z);
            size = Math.max(size, minSize);

            if (screenX < 0) {
                screenX = 0;
                size = edgeSize;
            }
            if (screenY < 0) {
                screenY = 0;
                size = edgeSize;
            }
            if (screenX > screenWidth) {
                screenX = screenWidth;
                size = edgeSize;
            }
            if (screenY > screenHeight) {
                screenY = screenHeight;
                size = edgeSize;
            }

            context.fill((int) screenX - size / 2, (int) screenY - size / 2,
                    (int) screenX + size / 2, (int) screenY + size / 2, color.getRGB());

            //draw line to items
            if (rarity.ordinal() > Config.getShow_Lines_Above_Rarity().ordinal()){
                // Get the transformation matrix from the matrix stack, alongside the tessellator instance and a new buffer builder.
                Matrix4f transformationMatrix = context.getMatrices().peek().getPositionMatrix();
                Tessellator tessellator = Tessellator.getInstance();

                RenderSystem.disableCull();

                // Begin a triangle strip buffer using the POSITION_COLOR vertex format.
                BufferBuilder buffer = tessellator.begin(VertexFormat.DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);

                // Write our vertices, Z doesn't really matter since it's on the HUD.
                buffer.vertex(transformationMatrix, (float) screenX - (float) size / 2, (float) screenY - (float) size / 2, 5).color(color.getRGB());
                buffer.vertex(transformationMatrix, (float) screenWidth / 2, (float) screenHeight / 2, 5).color(color.getRGB());
                buffer.vertex(transformationMatrix, (float) screenX + (float) size / 2, (float) screenY + (float) size / 2, 5).color(color.getRGB());

                // Make sure the correct shader for your chosen vertex format is set!
                // You can find all the shaders in the ShaderProgramKeys class.
                RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

                // Draw the buffer onto the screen.
                BufferRenderer.drawWithGlobalProgram(buffer.end());
            }
        }
    }
}