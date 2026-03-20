package sort_algorithms.graphics.array;

import KAGO_framework.view.DrawTool;
import sort_algorithms.animation.tween.Tween;

public class DataRepresentation {
    Tween MOVEMENT_ANIMATION;
    int value;
    double x;
    double y;
    double width;
    double height;
    public DataRepresentation(int value, int maxValue, double width, double maxHeight) {
        MOVEMENT_ANIMATION = Tween.to(0d, 0d, 0.0).loop(false);
        this.width = width;
        this.height = (value/maxValue) * maxHeight;
        this.value = value;
    }

    public void moveToX(double x, double duration) {
        MOVEMENT_ANIMATION.redo(this.x, x, duration);
        MOVEMENT_ANIMATION.onUpdate((t) -> this.x = MOVEMENT_ANIMATION.getValueDouble());
        MOVEMENT_ANIMATION.animate();
    }
    public void moveToX(double x) {
        moveToX(x, 0.2);
    }
    public double getX() {
        return x;
    }
    public void setX(double x) {
        this.x = x;
    }
    public void draw(DrawTool dt) {
        dt.push();
        dt.drawFilledRectangle(x, y, width, height);
        dt.pop();
    }
}
