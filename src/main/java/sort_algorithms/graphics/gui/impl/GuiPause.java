package sort_algorithms.graphics.gui.impl;

import KAGO_framework.model.abitur.datenstrukturen.AbiList;
import KAGO_framework.view.DrawTool;
import org.dyn4j.geometry.Vector2;
import sort_algorithms.Wrapper;
import sort_algorithms.graphics.ThemeColor;
import sort_algorithms.graphics.gui.Gui;
import sort_algorithms.graphics.gui.GuiElementPosition;
import sort_algorithms.graphics.gui.GuiSizing;
import sort_algorithms.graphics.gui.elements.GuiButton;
import sort_algorithms.graphics.gui.elements.GuiToggle;
import sort_algorithms.model.debug.VisualModel;
import sort_algorithms.model.scene.Scene;
import sort_algorithms.model.scene.impl.GameScene;

import java.awt.*;
import java.awt.event.KeyEvent;

/***
 * @author Leon
 */
public class GuiPause extends Gui {

    private AbiList<String> creators;
    private String subtitle = "CREATOR OF DÜBEL LEVEL";
    private Font titleFont;
    private Font textFont;
    private Color textColor;

    public GuiPause() {
        super(600, 420);
        this.creators = new AbiList<>();
        this.creators.append("LEON");
        this.creators.append("MARK");
        this.titleFont = Wrapper.getCacheManager().loadFont(20);
        this.textFont = Wrapper.getCacheManager().loadFont(16);
        this.textColor = ThemeColor.getTextColor().brighter().brighter().brighter();

        GuiButton resumeBtn = new GuiButton("RESUME", GuiSizing.of(GuiElementPosition.CENTER, GuiElementPosition.START, 400, 60), 30);
        resumeBtn.setOffset(new Vector2(0, 60));
        resumeBtn.onClick(btn -> Scene.getCurrentScene().closeGUI());
        this.addElement(resumeBtn);

        GuiToggle fpsBtn = new GuiToggle("SHOW  FPS", false, GuiSizing.of(GuiElementPosition.CENTER, GuiElementPosition.START, 400, 60), 30);
        fpsBtn.setOffset(new Vector2(0, 60 * 2 + 20));
        fpsBtn.onValueChange(v -> {
            VisualModel vis = GameScene.getInstance().getVisual("fps-component");
            if (vis != null) vis.toggleVisible(v);
        });
        this.addElement(fpsBtn);

        GuiButton exitBtn = new GuiButton("EXIT", GuiSizing.of(GuiElementPosition.CENTER, GuiElementPosition.START, 400, 60), 30);
        exitBtn.setOffset(new Vector2(0, 60 * 3 + 20 * 2));
        exitBtn.onClick(btn -> Wrapper.getViewController().shutdown());
        this.addElement(exitBtn);
    }

    @Override
    public void draw(DrawTool drawTool) {
        super.draw(drawTool);
        drawTool.push();

        drawTool.getGraphics2D().setFont(this.titleFont);
        drawTool.setCurrentColor(ThemeColor.getTextColor());

        double startX = getX() + (600 - drawTool.getFontWidth(this.subtitle)) / 2;
        double startY = getY() + 60 * 4 + 20 * 3 + 30;
        drawTool.drawText(this.subtitle, startX, startY);

        this.creators.toFirst();
        int count = 1;
        drawTool.getGraphics2D().setFont(this.textFont);
        drawTool.setCurrentColor(this.textColor);
        while (this.creators.hasAccess()) {
            startX = getX() + (600 - drawTool.getFontWidth(this.creators.getContent())) / 2;
            drawTool.drawText(this.creators.getContent(), startX, startY + (drawTool.getFontHeight() + 15) * count);
            count++;
            this.creators.next();
        }

        drawTool.pop();
    }

    @Override
    public boolean shouldOpen() {
        return Scene.getCurrentScene() == GameScene.getInstance() && !Scene.performingTransition() && !Wrapper.getLevelManager().performingTransition();
    }

    @Override
    public int keyToOpen() {
        return KeyEvent.VK_ESCAPE;
    }
}
