package wynntools.wynntools;


import net.minecraft.client.gui.DrawContext;

public interface HudElement {
    void render(DrawContext drawContext);
    boolean isHovering(double mouseX, double mouseY);
    void onMouseDragged(double mouseX, double mouseY);
    void onMouseClicked(double mouseX, double mouseY);
    void onMouseReleased();
}