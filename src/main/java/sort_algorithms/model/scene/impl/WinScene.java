package sort_algorithms.model.scene.impl;

import KAGO_framework.view.DrawTool;
import sort_algorithms.Config;
import sort_algorithms.Wrapper;
import sort_algorithms.graphics.ThemeColor;
import sort_algorithms.model.scene.Scene;

import java.awt.*;
import java.awt.event.KeyEvent;

public class WinScene extends Scene {

    private Font titleFont;

    public WinScene() {
        super("win");
        this.titleFont = Wrapper.getCacheManager().loadFont(100);
    }

    @Override
    public void update(double dt) {}

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.push();
        drawTool.setCurrentColor(new Color(238, 195, 154));
        drawTool.drawFilledRectangle(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);

        drawTool.setCurrentColor(ThemeColor.getTextColor());
        drawTool.getGraphics2D().setFont(this.titleFont);
        drawTool.drawCenteredText("GEWONNEN!!!", 0, -100, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);

        drawTool.pop();
    }

    private String formatSecondsToMMSS(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            Wrapper.getViewController().shutdown();
        }
    }
}