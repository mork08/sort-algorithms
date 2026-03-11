package sort_algorithms.model.debug.impl;

import KAGO_framework.view.DrawTool;
import sort_algorithms.Wrapper;
import sort_algorithms.model.debug.VisualModel;
import sort_algorithms.utils.misc.ColorObject;
import sort_algorithms.utils.misc.FontType;

import java.awt.*;

public class InfoComponent extends VisualModel {

    private final Font debugFont;
    private final double margin;
    private final double startY;

    public InfoComponent() {
        super("fps-component");
        double size = 24;
        this.debugFont = Wrapper.getCacheManager().loadFont(FontType.DEBUG_FONT, size);
        this.margin = size + 5;
        this.startY = 60;
    }

   @Override
    public void draw(DrawTool drawTool) {
        drawTool.push();
        drawTool.setCurrentColor(ColorObject.of(47, 29, 3));
        drawTool.getGraphics2D().setFont(this.debugFont);

        drawTool.drawTextOutline(
                String.format("FPS: %s", Wrapper.getTimer().getFPS()),
                20,
                this.startY + this.margin,
                ColorObject.of("#b29f99"),
                5.0,
                ColorObject.of("#554544")
        );
        drawTool.resetColor();
        drawTool.pop();
    }

    @Override
    public void update(double dt) {}
}
