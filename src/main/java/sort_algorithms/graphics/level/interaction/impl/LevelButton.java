package sort_algorithms.graphics.level.interaction.impl;

import KAGO_framework.view.DrawTool;
import sort_algorithms.Wrapper;
import sort_algorithms.graphics.level.Level;
import sort_algorithms.graphics.level.interaction.LevelInteractionElement;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

public class LevelButton extends LevelInteractionElement {

    private final String text;

    private final Color textColor;
    private final Font textFont;

    private final Color backgroundColor;
    private final Color shadowColor;

    private Consumer<LevelButton> click;

    public LevelButton(Level level, String text, double x, double y, double width, double height, int fontSize, Color color) {
        super(level, x, y, width, height);
        this.text = text;

        this.textColor = Color.decode("#fcf965");
        this.textFont = Wrapper.getCacheManager().loadFont(fontSize);

        this.backgroundColor = color == null ? new Color(174, 46, 6) : color;
        this.shadowColor = Color.decode("#2c0601");
    }

    public LevelButton onClick(Consumer<LevelButton> click) {
        this.click = click;
        return this;
    }

    @Override
    public void update(double dt) {}

    @Override
    public void draw(DrawTool drawTool) {
        drawTool.push();

        drawTool.setCurrentColor(this.shadowColor);
        drawTool.drawFilledRectangle(this.getX(), this.getY(), this.width, this.height + 5);

        drawTool.setCurrentColor(this.isHover(Wrapper.getViewController().getMouseX(), Wrapper.getViewController().getMouseY()) ? this.backgroundColor.darker() : this.backgroundColor);
        drawTool.drawFilledRectangle(this.getX(), this.getY(), this.width, this.height);

        drawTool.getGraphics2D().setFont(this.textFont);
        drawTool.setCurrentColor(this.isHover(Wrapper.getViewController().getMouseX(), Wrapper.getViewController().getMouseY()) ? this.textColor.brighter() : this.textColor);
        String t = this.text.toUpperCase();
        drawTool.drawText(t, this.getX() + (this.width - drawTool.getFontWidth(t)) / 2, this.getY() + (this.height) / 2 + drawTool.getFontHeight() + 2);

        drawTool.pop();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.isHover(e.getX(), e.getY())) {
            if (this.click != null) this.click.accept(this);
        }
    }

    public String getText() {
        return this.text;
    }
}
