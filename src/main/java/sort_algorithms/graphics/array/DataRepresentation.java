package sort_algorithms.graphics.array;

import KAGO_framework.view.DrawTool;
import sort_algorithms.animation.Easings;
import sort_algorithms.animation.tween.Tween;
import sort_algorithms.utils.misc.ColorObject;

public class DataRepresentation {
    Tween MOVEMENT_ANIMATION;
    int value;
    double x;
    double y;
    double width;
    double height;
    double maxHeight;
    ColorObject color;
    public DataRepresentation(int value, int maxValue, double width, double maxHeight, ColorObject color) {
        this.color = ColorObject.of(color.getRed(), color.getGreen(), color.getBlue());
        MOVEMENT_ANIMATION = Tween.to(0d, 0d, 0.0).loop(false).ease((x) -> Easings.easeOutBounce(x));
        this.width = width;
        this.height = ((double) value /maxValue) * maxHeight;
        this.value = value;
        this.maxHeight = maxHeight;
    }

    public void moveToX(double x, double duration) {
        MOVEMENT_ANIMATION.redo(this.x, x, duration);
        MOVEMENT_ANIMATION.onStart((t) -> this.color.brighter());
        MOVEMENT_ANIMATION.onFinish((t) -> this.color.darker());
        MOVEMENT_ANIMATION.onUpdate((t) -> this.x = MOVEMENT_ANIMATION.getValueDouble());
        MOVEMENT_ANIMATION.animate();
    }
    public void moveToX(double x) {
        moveToX(x, 0.3);
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y) {
        this.y = y;
    }
    public void draw(DrawTool dt) {
        dt.push();
        dt.setCurrentColor(this.color);
        dt.drawFilledRectangle(x, y + maxHeight - height, width, height);
        dt.pop();
    }
}
