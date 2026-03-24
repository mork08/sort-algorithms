package sort_algorithms.graphics.level;


import KAGO_framework.view.DrawTool;
import sort_algorithms.Wrapper;
import sort_algorithms.graphics.ThemeColor;
import sort_algorithms.graphics.level.interaction.LevelInteractionElement;
import sort_algorithms.graphics.tooltip.Tooltip;
import sort_algorithms.model.KeyManagerModel;
import sort_algorithms.model.sorting.SorterHistory;
import sort_algorithms.model.sorting.impl.SortingAlgorithmVisualizer;
import sort_algorithms.utils.misc.ColorObject;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class Level {

    protected boolean autoplayActive = false;
    protected List<LevelInteractionElement<?>> elements = new ArrayList<>();

    protected String name;
    protected ThemeColor theme;
    protected SorterHistory sorterHistory;
    protected SortingAlgorithmVisualizer visualizer;
    protected int[] array;

    /***
     * "Erstellt" quasi das Level, wird in der ganzen Runtime nur einmal ausgeführt
     * @param name
     */
    protected Level(String name) {
        this.name = name;
        this.theme = ThemeColor.createDefault();
        this.array = new int[] { 20, 3, 31, 17, 2, 6 }; // generateArray(0,20);
        sorterHistory = sort(array);
        double width = 600;
        double height = 400;
        this.visualizer = new SortingAlgorithmVisualizer(sorterHistory, (Wrapper.getScreenWidth() - width) / 2, (Wrapper.getScreenHeight() - height) / 2, width, height, (ColorObject) this.theme.accent());
        this.visualizer.processStep();

        Wrapper.getTooltipManager().register(
            new Tooltip(
                KeyManagerModel.KEY_NEXT_ALG_STEP,
                (keyManager) -> {
                    LevelManager levelManager = Wrapper.getLevelManager();
                    if (levelManager != null && levelManager.getCurrent() == this && this.visualizer.allowSkip()) {
                        return "Nächster Schritt";
                    }
                    return null;
                }
            )
        );
        Wrapper.getTooltipManager().register(
            new Tooltip(
                KeyManagerModel.KEY_PREVIOUS_ALG_STEP,
                (keyManager) -> {
                    LevelManager levelManager = Wrapper.getLevelManager();
                    if (levelManager != null && levelManager.getCurrent() == this && this.visualizer.allowSkip()) {
                        return "Vorheriger Schritt";
                    }
                    return null;
                }
            )
        );
    }

    /***
     * Wenn Level nicht angezeigt wurde und angezeigt wird
     * Im Gegensatz zum Konstruktor wird es öfters während der Runtime aufgerufen
     */
    public abstract void onActive();

    /***
     * Wenn Level beendet wird und nicht mehr angezeigt wird
     */
    public abstract void onHide();

    /***
     * Wenn Level zurückgesetzt werden soll
     */
    public abstract void onReset();

    public abstract void update(double dt);
    public abstract void draw(DrawTool drawTool);
    protected abstract SorterHistory sort(int[] array);
    public void drawAfterObjects(DrawTool drawTool) {}

    public void drawHUD(DrawTool drawTool) {
        this.elements.forEach(element -> element.draw(drawTool));
    }

    protected int[] generateArray(int min, int max) {
        int[] array = new int[max - min + 1];
        for (int i = 0; i < array.length; i++) {
            array[i] = min + i;
        }
        return array;
    }

    protected void scramble(int[] array) {
        for (int i = 0; i < 1000; i++) {
            int index1 = (int) (Math.random() * (array.length));
            int index2 = (int) (Math.random() * (array.length));
            int temp = array[index1];
            array[index1] = array[index2];
            array[index2] = temp;
        }
    }

    public void registerElement(LevelInteractionElement<?> element) {
        this.elements.add(element);
    }

    public String getName() {
        return this.name;
    }

    public ThemeColor getTheme() {
        return this.theme;
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e) {}
    public void mouseDragged(MouseEvent e) {}
    public void mouseMoved(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.sorterHistory.stepForward(this.visualizer);

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.sorterHistory.stepBack(this.visualizer);
        }else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            autoplayActive = !autoplayActive;
        }
        if (e.getKeyCode() == KeyEvent.VK_1) {
            Wrapper.getLevelManager().nextLevel("dfsfgdf");
        }
    }
    public void keyReleased(KeyEvent e) {}
    protected void autoplay(){
        if(!autoplayActive) return;
        this.sorterHistory.stepForward(this.visualizer);

    }
}
