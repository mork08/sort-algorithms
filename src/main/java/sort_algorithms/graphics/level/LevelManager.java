package sort_algorithms.graphics.level;

import KAGO_framework.model.abitur.datenstrukturen.Queue;
import KAGO_framework.view.DrawTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sort_algorithms.model.scene.Scene;
import sort_algorithms.model.scene.impl.GameScene;
import sort_algorithms.model.transitions.DefaultTransition;
import sort_algorithms.model.transitions.Transition;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/***
 * @author Mark
 */
public class LevelManager {

    private static final Logger log = LoggerFactory.getLogger(LevelManager.class);

    public Queue<LevelSwitch> levelSwitchQueue;
    private LevelSwitch transition;

    private List<Level> levels;

    private Level previous;
    private Level current;
    private Level next;

    private int index;

    /**
     * Erstellt den LevelManager und setzt Startlevel sowie Stats-Level als nächstes.
     */
    public LevelManager() {
        this.levels = new ArrayList<>();
        this.levelSwitchQueue = new Queue<>();
        this.previous = null;

        try {
            String packageName = String.format("%s.impl", Level.class.getPackageName());
            String packagePath = packageName.replace('.', '/');

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> resources = classLoader.getResources(packagePath);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                String protocol = resource.getProtocol();

                if (protocol.equals("file")) {
                    // IDE / classes directory
                    File directory = new File(resource.toURI());
                    if (!directory.exists()) continue;

                    File[] files = directory.listFiles((dir, name) -> name.endsWith(".class"));
                    if (files == null) continue;

                    for (File file : files) {
                        String className = file.getName().substring(0, file.getName().length() - 6);
                        String fullName = packageName + "." + className;
                        this.levels.add((Level) Class.forName(fullName).getConstructor().newInstance());
                    }

                } else if (protocol.equals("jar")) {
                    JarURLConnection conn = (JarURLConnection) resource.openConnection();
                    try (JarFile jar = conn.getJarFile()) {
                        Enumeration<JarEntry> entries = jar.entries();

                        while (entries.hasMoreElements()) {
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();

                            // Nur Klassen direkt in diesem Package (nicht in Subpackages)
                            if (!name.startsWith(packagePath + "/")) continue;
                            if (entry.isDirectory()) continue;
                            if (!name.endsWith(".class")) continue;

                            String simple = name.substring(name.lastIndexOf('/') + 1);

                            // Nur direkte Kinder: packagePath/XYZ.class
                            String remainder = name.substring((packagePath + "/").length());
                            if (remainder.contains("/")) continue;

                            String className = simple.substring(0, simple.length() - 6);
                            String fullName = packageName + "." + className;

                            this.levels.add((Level) Class.forName(fullName).getConstructor().newInstance());
                        }
                    }

                } else {
                    log.error("Error during filtering for level files (unknown protocol): {}", protocol);
                }
            }

            if (this.levels.size() > 0) {
                this.current = this.levels.getFirst();
            }

            if (this.levels.size() > 1) {
                this.next = this.levels.get(1);
            }

        } catch (IOException | ClassNotFoundException | URISyntaxException | NoSuchMethodException |
                 InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /** Aktiviert das Startlevel (Loader-Callback). */
    public void loadStartLevel() {
        if (this.current != null) this.current.onActive();
    }

    /**
     * Zeichnet das aktuelle Level.
     *
     * @param drawTool DrawTool
     */
    public void draw(DrawTool drawTool) {
        if (this.current != null) {
            this.current.draw(drawTool);
        }
    }

    /**
     * Zeichnet Inhalte, die nach den Objekten gerendert werden sollen.
     *
     * @param drawTool DrawTool
     */
    public void drawAfterObjects(DrawTool drawTool) {
        if (this.current != null) {
            this.current.drawAfterObjects(drawTool);
        }
    }

    public void drawHUD(DrawTool drawTool) {
        if (this.current != null) {
            this.current.drawHUD(drawTool);
        }
    }

    /**
     * Aktualisiert das aktuelle Level sowie aktive Transition/Queue-Logik.
     *
     * @param dt delta time
     */
    public void update(double dt) {
        if (this.current != null) {
            this.current.update(dt);
        }

        if (this.transition != null) {
            Level last = this.transition.last();
            Level next = this.transition.next();
            Transition tr = this.transition.transition();

            if (tr.swap()) {
                if (this.current != next) {
                    if (this.transition.dir() == LevelSwitch.LevelSwitchDirection.NEXT) {
                        this.setNextLevel();

                    } else {
                        this.setPreviousLevel();
                    }
                    if (this.transition.runnable() != null) this.transition.runnable().run();
                    tr.out(last, next);
                }
                if (tr.finished()) {
                    this.transition = null;
                }
            }
        } else {
            if (!this.levelSwitchQueue.isEmpty()) {
                this.transition = this.levelSwitchQueue.front();
                this.levelSwitchQueue.dequeue();
            }
        }
    }

    /**
     * Zeichnet die aktuelle Transition (falls aktiv).
     *
     * @param drawTool DrawTool
     */
    public void drawTransition(DrawTool drawTool) {
        if (this.transition != null) {
            this.transition.transition().draw(drawTool);
        }
    }

    /**
     * Startet (oder queued) einen Levelwechsel mit Richtung und Transition.
     *
     * @param id eindeutige Wechsel-ID (z.B. Event-Quelle)
     * @param direction Richtung des Wechsels
     * @param level Ziel-Level
     * @param runWhileTransition optionaler Code, der beim Swap ausgeführt wird
     * @param transition Transition-Implementierung
     */
    private void initiateNewLevel(String id, LevelSwitch.LevelSwitchDirection direction, Level level, Runnable runWhileTransition, Transition transition) {
        if (level == null) {
            log.warn("Cancelled level change because level is null");
            return;
        }
        LevelSwitch levelSwitch = new LevelSwitch(id, direction, this.current, level, runWhileTransition, transition);
        if (this.transition == null) {
            this.transition = levelSwitch;
            transition.in(this.current);

        } else if (!this.transition.id().equals(id)) {
            if (!this.levelSwitchQueue.isEmpty() && !this.levelSwitchQueue.front().id().equals(id) && !this.levelSwitchQueue.tail().id().equals(id)) {
                this.levelSwitchQueue.enqueue(levelSwitch);
            }
        }
    }

    /**
     * Wechselt zum nächsten Level mit DefaultTransition.
     *
     * @param id Wechsel-ID
     */
    public void nextLevel(String id) {
        this.nextLevel(id, null, new DefaultTransition());
    }

    /**
     * Wechselt zum nächsten Level mit DefaultTransition.
     *
     * @param id Wechsel-ID
     * @param runWhileTransition optionaler Code beim Swap
     */
    public void nextLevel(String id, Runnable runWhileTransition) {
        this.nextLevel(id, runWhileTransition, new DefaultTransition());
    }

    /**
     * Wechselt zum nächsten Level (nur in der GameScene).
     * Falls nach den Stats kein nächstes Level existiert, wird die WinScene geöffnet.
     *
     * @param id Wechsel-ID
     * @param runWhileTransition optionaler Code beim Swap
     * @param transition Transition-Implementierung
     */
    public void nextLevel(String id, Runnable runWhileTransition, Transition transition) {
        if (Scene.getCurrentScene() != GameScene.getInstance()) return;
        if (this.next == null) {
            log.info("No level left.");
            return;
        }
        this.initiateNewLevel(id, LevelSwitch.LevelSwitchDirection.NEXT, this.next, runWhileTransition, transition);
    }

    /**
     * Wechselt zum vorherigen Level mit DefaultTransition.
     *
     * @param id Wechsel-ID
     */
    public void previousLevel(String id) {
        this.previousLevel(id, new DefaultTransition());
    }

    /**
     * Wechselt zum vorherigen Level.
     *
     * @param id Wechsel-ID
     * @param transition Transition-Implementierung
     */
    public void previousLevel(String id, Transition transition) {
        this.initiateNewLevel(id, LevelSwitch.LevelSwitchDirection.PREVIOUS, this.previous, null, transition);
    }

    /**
     * Wechselt zum vorherigen Level.
     *
     * @param id Wechsel-ID
     * @param runWhileTransition optionaler Code beim Swap
     * @param transition Transition-Implementierung
     */
    public void previousLevel(String id, Runnable runWhileTransition, Transition transition) {
        this.initiateNewLevel(id, LevelSwitch.LevelSwitchDirection.PREVIOUS, this.previous, runWhileTransition, transition);
    }

    /**
     * Setzt current auf next und aktualisiert previous/next sowie Index.
     * Stats-Level werden als Zwischenstufe verwendet.
     */
    private void setNextLevel() {
        this.previous = this.current;
        if (this.next != null) {
            this.next.onReset();
        }
        this.current.onHide();
        this.current = this.next;
        this.current.onActive();
        this.index++;
        this.next = this.levels.size() == this.index + 1 ? null : this.levels.get(this.index + 1);
    }

    /**
     * Setzt current auf previous und aktualisiert previous/next sowie Index.
     */
    private void setPreviousLevel() {
        if (this.previous == null) return;

        this.next = this.current;

        this.previous.onReset();
        this.current.onHide();

        this.current = this.previous;
        this.current.onActive();

        if (this.index > 0) {
            this.index--;
            this.previous = this.levels.get(this.index);

        } else {
            this.previous = null;
        }
    }

    /**
     * Überprüft, ob gerade eine Transition ausgeführt wird
     *
     * @return true/false, falls eine gerade ausgeführt wird
     */
    public boolean performingTransition() {
        return this.transition != null;
    }

    /**
     * @return vorheriges Level
     */
    public Level getPrevious() {
        return this.previous;
    }

    /**
     * @return aktuelles Level
     */
    public Level getCurrent() {
        return this.current;
    }

    /**
     * @return nächstes Level
     */
    public Level getNext() {
        return this.next;
    }

    /**
     * @return aktueller Levelindex
     */
    public int getIndex() {
        return this.index;
    }

    public void mouseEntered(MouseEvent e) {
        if (this.current != null) {
            this.current.elements.forEach(it -> it.mouseEntered(e));
            this.current.mouseEntered(e);
        }
    }

    public void mouseExited(MouseEvent e) {
        if (this.current != null) {
            this.current.elements.forEach(it -> it.mouseExited(e));
            this.current.mouseExited(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (this.current != null) {
            this.current.elements.forEach(it -> it.mouseReleased(e));
            this.current.mouseReleased(e);
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (this.current != null) {
            this.current.elements.forEach(it -> it.mouseClicked(e));
            this.current.mouseClicked(e);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (this.current != null) {
            this.current.elements.forEach(it -> it.mouseDragged(e));
            this.current.mouseDragged(e);
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (this.current != null) {
            this.current.elements.forEach(it -> it.mouseMoved(e));
            this.current.mouseMoved(e);
        }
    }

    public void mousePressed(MouseEvent e) {
        if (this.current != null) {
            this.current.elements.forEach(it -> it.mousePressed(e));
            this.current.mousePressed(e);
        }
    }

    public void keyTyped(KeyEvent e) {
        if (this.current != null) {
            this.current.elements.forEach(it -> it.keyTyped(e));
            this.current.keyTyped(e);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (this.current != null) {
            this.current.elements.forEach(it -> it.keyPressed(e));
            this.current.keyPressed(e);
        }
    }

    public void keyReleased(KeyEvent e) {
        if (this.current != null) {
            this.current.elements.forEach(it -> it.keyReleased(e));
            this.current.keyReleased(e);
        }
    }
}
