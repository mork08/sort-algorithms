package sort_algorithms.model.scene.impl;

import KAGO_framework.view.DrawTool;
import sort_algorithms.Config;
import sort_algorithms.Wrapper;
import sort_algorithms.graphics.ThemeColor;
import sort_algorithms.model.scene.Scene;
import sort_algorithms.model.transitions.DefaultTransition;
import sort_algorithms.utils.misc.FontType;

import java.awt.*;

public class LoadingScene extends Scene {

    private double loadingProgress = 0;
    private double elapsed = 0;
    private double duration = 2;
    private boolean loadingComplete = false;
    private Font titleFont;

    public LoadingScene() {
        super("loading");
        this.titleFont = Wrapper.getCacheManager().loadFont(FontType.PIXEL_FONT, 100);
    }

    @Override
    public void update(double dt) {
        if (this.elapsed < this.duration) {
            this.elapsed += dt;
            this.loadingProgress = this.elapsed / this.duration;
            if (this.elapsed >= this.duration) {
                this.loadingComplete = true;
            }
        }
    }

    @Override
    public void draw(DrawTool drawTool) {
        // Hintergrund
        drawTool.setCurrentColor(new Color(117, 206, 94));
        drawTool.drawFilledRectangle(0, 0, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);

        // Titel (zentriert)
        drawTool.setCurrentColor(new Color(56, 89, 8));
        drawTool.getGraphics2D().setFont(this.titleFont);
        String title = "Sorting Algorithms";

        drawTool.drawCenteredText("Sorting".toUpperCase(), 0, -130, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        drawTool.drawCenteredText("Algorithms".toUpperCase(), 0, -50, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);

        // Ladebalken
        int barWidth = 400;
        int barHeight = 20;
        int barX = Config.WINDOW_WIDTH / 2 - barWidth / 2;
        int barY = Config.WINDOW_HEIGHT - 150;
        int progressWidth = (int) (loadingProgress * barWidth);

        // Balkenhintergrund
        drawTool.setCurrentColor(new Color(81, 107, 84));
        drawTool.drawFilledRectangle(barX, barY, barWidth, barHeight);

        // Ladefortschritt
        drawTool.setCurrentColor(new Color(87, 245, 77));
        drawTool.drawFilledRectangle(barX, barY, progressWidth, barHeight);

        // Prozentanzeige
        drawTool.setCurrentColor(Color.BLACK);
        drawTool.getGraphics2D().setFont(Wrapper.getCacheManager().loadFont(FontType.PIXEL_FONT, 16));
        String percentText = (int) (loadingProgress * 100) + "%";
        drawTool.drawText(percentText, barX + barWidth / 2 - 15, barY - 10);

        // Andere Elemente
        super.draw(drawTool);

        if (loadingComplete) {
            Scene.open(GameScene.getInstance(), new DefaultTransition());
        }
    }

    @Override
    public void onOpen(Scene last) {}

    public boolean isLoadingComplete() {
        return loadingComplete;
    }
}