package sort_algorithms.graphics.level;


import KAGO_framework.view.DrawTool;
import sort_algorithms.Wrapper;
import sort_algorithms.graphics.ThemeColor;
import sort_algorithms.graphics.level.impl.*;
import sort_algorithms.graphics.level.interaction.LevelInteractionElement;
import sort_algorithms.graphics.level.interaction.impl.LevelButton;
import sort_algorithms.graphics.tooltip.Tooltip;
import sort_algorithms.model.KeyManagerModel;
import sort_algorithms.model.sorting.SorterHistory;
import sort_algorithms.model.sorting.SortingDirection;
import sort_algorithms.model.sorting.impl.SortingAlgorithmVisualizer;
import sort_algorithms.model.transitions.DefaultTransition;
import sort_algorithms.utils.math.MathUtils;

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
    protected Level(String name, ThemeColor theme) {
        this.name = name;
        this.theme = theme;
        this.array = new int[] { 20, 3, 31, 17, 2, 6 }; // generateArray(0,20);
        this.sorterHistory = sort(array);
        double width = 900;
        double height = 400;
        this.visualizer = new SortingAlgorithmVisualizer(this.sorterHistory, (Wrapper.getScreenWidth() - width) / 2, (Wrapper.getScreenHeight() - height) / 2, width, height, this.theme);
        this.visualizer.processStep(SortingDirection.FORWARD);

        Wrapper.getTooltipManager().register(
                new Tooltip(
                        KeyManagerModel.KEY_NEXT_ALG_STEP,
                        (keyManager) -> {
                            LevelManager levelManager = Wrapper.getLevelManager();
                            if (levelManager != null && levelManager.getCurrent() == this && this.visualizer.allowSkip() && this.sorterHistory.getCurrentIndex() < this.sorterHistory.getSteps() - 1) {
                                return "Nächster Schritt";
                            }
                            return null;
                        },
                        theme
                )
        );

        Wrapper.getTooltipManager().register(
            new Tooltip(
                KeyManagerModel.KEY_PREVIOUS_ALG_STEP,
                (keyManager) -> {
                    LevelManager levelManager = Wrapper.getLevelManager();
                    if (levelManager != null && levelManager.getCurrent() == this && this.visualizer.allowSkip() && this.sorterHistory.getCurrentIndex() > 0) {
                        return "Vorheriger Schritt";
                    }
                    return null;
                },
                theme
            )
        );

        if (!(this instanceof LevelBinarySearch || this instanceof LevelLinearSearch)) {
            Wrapper.getTooltipManager().register(
                new Tooltip(
                    KeyManagerModel.KEY_AUTOPLAY,
                    (keyManager) -> {
                        LevelManager levelManager = Wrapper.getLevelManager();
                        if (levelManager != null && levelManager.getCurrent() == this) {
                            if (this.autoplayActive) {
                                return "Autoplay aus";

                            } else {
                                return "Autoplay an";
                            }
                        }
                        return null;
                    },
                    theme
                )
            );
        }

        double buttonWidth = 320;
        double buttonHeight = 50;
        double buttonGap = 20;
        double buttonY = 40;
        double totalButtonWidth = buttonWidth * 4 + buttonGap * 3;

        double startX = (Wrapper.getScreenWidth() - totalButtonWidth) / 2;

        new LevelButton(this, "Quick Sort", startX, buttonY, buttonWidth, buttonHeight, 24, theme.accent())
                .onClick((btn) -> {
                    if (!this.name.equals(btn.getText())) {
                        Wrapper.getLevelManager().nextLevel(new LevelQuickSort(), "bb1", null, new DefaultTransition());
                    }
                });

        new LevelButton(this, "Selection Sort", startX + buttonWidth + buttonGap, buttonY, buttonWidth, buttonHeight, 24, theme.accent())
                .onClick((btn) -> {
                    if (!this.name.equals(btn.getText())) {
                        Wrapper.getLevelManager().nextLevel(new LevelSelectionSort(), "bb2", null, new DefaultTransition());
                    }
                });

        new LevelButton(this, "Insertion Sort", startX + (buttonWidth + buttonGap) * 2, buttonY, buttonWidth, buttonHeight, 24, theme.accent())
                .onClick((btn) -> {
                    if (!this.name.equals(btn.getText())) {
                        Wrapper.getLevelManager().nextLevel(new LevelInsertionSort(), "bb3", null, new DefaultTransition());
                    }
                });

        new LevelButton(this, "Bubble Sort", startX + (buttonWidth + buttonGap) * 3, buttonY, buttonWidth, buttonHeight, 24, theme.accent())
                .onClick((btn) -> {
                    if (!this.name.equals(btn.getText())) {
                        Wrapper.getLevelManager().nextLevel(new LevelBubbleSort(), "bb4", null, new DefaultTransition());
                    }
                });

        totalButtonWidth = buttonWidth * 2 + buttonGap;
        startX = (Wrapper.getScreenWidth() - totalButtonWidth) / 2;

        new LevelButton(this, "Linear Search", startX, buttonY + buttonHeight + 10, buttonWidth, buttonHeight, 24, theme.accent())
                .onClick((btn) -> {
                    if (!this.name.equals(btn.getText())) {
                        Wrapper.getLevelManager().nextLevel(new LevelLinearSearch(), "bb5", null, new DefaultTransition());
                    }
                });

        new LevelButton(this, "Binary Search", startX + buttonWidth + buttonGap, buttonY + buttonHeight + 10, buttonWidth, buttonHeight, 24, theme.accent())
                .onClick((btn) -> {
                    if (!this.name.equals(btn.getText())) {
                        Wrapper.getLevelManager().nextLevel(new LevelBinarySearch(), "bb6", null, new DefaultTransition());
                    }
                });
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
    public void onReset() {
        this.array = generateArray(0, 100, MathUtils.random(6, 10));
        this.sorterHistory = sort(array);
        double width = 900;
        double height = 400;
        this.visualizer = new SortingAlgorithmVisualizer(sorterHistory, (Wrapper.getScreenWidth() - width) / 2, (Wrapper.getScreenHeight() - height) / 2, width, height, this.theme);
        this.visualizer.processStep(SortingDirection.FORWARD);
    }

    public void update(double dt) {
        this.autoplay();
    }
    public void draw(DrawTool drawTool) {
        this.visualizer.draw(drawTool);
    }
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

    protected int[] generateArray(int minValue, int maxValue, int length) {
        int[] array = new int[length];
        for (int i = 0; i < array.length; i++) {
            array[i] = minValue + (int) (Math.random() * (maxValue - minValue + 1));
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

        } else if (e.getKeyCode() == KeyEvent.VK_SPACE && !(this instanceof LevelBinarySearch || this instanceof LevelLinearSearch)) {
            this.toggleAutoplay(!this.autoplayActive);
        }
    }
    public void keyReleased(KeyEvent e) {}

    private void toggleAutoplay(boolean flag) {
        this.autoplayActive = flag;
        if (this.autoplayActive) {
            this.visualizer.setAnimationDuration(0.4);

        } else {
            this.visualizer.setAnimationDuration(1.0);
        }
    }

    protected void autoplay(){
        if(!this.autoplayActive) return;
        if (this.sorterHistory.stepForward(this.visualizer) == 2) this.toggleAutoplay(false);
    }
}
