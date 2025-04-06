package wynntools.wynntools;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import java.io.Serializable;
import java.util.function.Consumer;

public class TextHudElement implements HudElement, Serializable {
    private static final long serialVersionUID = 1L;
    private int x, y;
    private String text;
    private int colour;
    private final String name;
    private boolean visible = false;
    private float scale = 1.f;
    private float stringWidth;
    private float stringHeight;
    private double grabDifX;
    private double grabDifY;
    private Runnable onMouseClickedAction;

    public TextHudElement(int x, int y, String text, int colour, String name, float scale) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.colour = colour;
        this.name = name;
        this.scale = scale;
    }
    public TextHudElement(int x, int y, String text, String name, float scale) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.colour = 0xFF0000;
        this.name = name;
        this.scale = scale;
    }

    public String getName() {
        return name;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public String getText(){
        return text;
    }
    public int getColour(){
        return colour;
    }
    public boolean getVisibility(){
        return visible;
    }
    public float getScale(){
        return scale;
    }
    public float getWidth(){
        return stringWidth;
    }
    public float getHeight(){
        return stringHeight;
    }

    public void setString(String text){
        this.text = text;
    }
    public void setColour(int colour){
        this.colour = colour;
    }
    public void setVisibility(boolean visible){
        this.visible = visible;
    }
    public void setScale(float scale){
        this.scale = scale;
    }

    @Override
    public void render(DrawContext drawContext) {
        if(visible) {
            MinecraftClient client = MinecraftClient.getInstance();
            TextRenderer textRenderer = client.textRenderer;
            MatrixStack matrixstack = drawContext.getMatrices();
            matrixstack.push();
            float offsetX = (scale - 1) * x;
            float offsetY = (scale - 1) * y;
            matrixstack.translate(-offsetX, -offsetY, 0);
            matrixstack.scale(scale, scale, 1.f);
            stringWidth = textRenderer.getWidth(text) * scale;
            stringHeight = textRenderer.fontHeight * scale;
            drawContext.drawText(textRenderer, text, x, y, colour, true);
            matrixstack.pop();
        }
    }

    @Override
    public boolean isHovering(double mouseX, double mouseY) {
        boolean boo = mouseX >= x && mouseX < x + stringWidth && mouseY >= y && mouseY < y + stringHeight;
        //System.out.println(this.getText() + "is being hovered: " + boo);
        return boo;
    }

    @Override
    public void onMouseDragged(double mouseX, double mouseY) {
        this.x = (int)(mouseX + grabDifX);
        this.y = (int)(mouseY + grabDifY);
    }

    @Override
    public void onMouseClicked(double mouseX, double mouseY) {
        grabDifX = x - mouseX;
        grabDifY = y - mouseY;
        //System.out.println("button" + this.text + " was clicked");
        if (onMouseClickedAction != null) {
            onMouseClickedAction.run();
        }
    }

    @Override
    public void onMouseReleased() {
        // Handle mouse release if needed
    }

    public void setMouseClicked(Runnable onMouseClickedAction) {
        this.onMouseClickedAction = onMouseClickedAction;
    }
}
