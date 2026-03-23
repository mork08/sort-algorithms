package sort_algorithms.graphics.level.interaction;

import KAGO_framework.view.DrawTool;
import org.dyn4j.geometry.Vector2;
import sort_algorithms.graphics.level.Level;
import sort_algorithms.utils.math.MathUtils;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class LevelInteractionElement<T> {

    private double x;
    private double y;
    protected double width;
    protected double height;
    protected Vector2 offset;
    protected T value;

    public LevelInteractionElement(Level level, double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.offset = new Vector2();
        level.registerElement(this);
    }

    public abstract void update(double dt);
    public abstract void draw(DrawTool drawTool);

    /***
     * Überprüft, ob der Mauszeiger über dem Element ist
     * @param mouseX Maus x-Koordinate
     * @param mouseY Mause y-Koordinate
     * @return true, wenn der Mauszeiger über dem Element ist
     */
    public boolean isHover(int mouseX, int mouseY) {
        return MathUtils.isHover(this.getX(), this.getY(), this.width, this.height, mouseX, mouseY);
    }

    public void setOffset(Vector2 off) {
        this.offset = off;
    }

    public double getX() {
        return this.x + this.offset.x;
    }

    public double getY() {
        return this.y + this.offset.y;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {}
    public void keyReleased(KeyEvent e) {}
}
