package sort_algorithms;

import KAGO_framework.control.ViewController;
import sort_algorithms.event.EventManager;
import sort_algorithms.event.services.EventProcessingQueue;
import sort_algorithms.graphics.level.LevelManager;
import sort_algorithms.graphics.tooltip.TooltipManager;
import sort_algorithms.model.entity.EntityManager;
import sort_algorithms.model.sound.SoundConstants;
import sort_algorithms.utils.math.TimerUtils;
import sort_algorithms.utils.misc.CacheManager;

public class Wrapper {

    private final static EventManager eventManager = new EventManager();
    private final static EntityManager entityManager = new EntityManager();
    private final static EventProcessingQueue processManager = new EventProcessingQueue();
    private final static TooltipManager tooltipManager = new TooltipManager();
    private final static SoundConstants soundConstants = new SoundConstants();
    private final static TimerUtils timer = new TimerUtils();
    private final static CacheManager cacheManager = new CacheManager();
    private final static LevelManager levelManager = new LevelManager(1);

    public static EventManager getEventManager() {
        return eventManager;
    }

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    public static EventProcessingQueue getProcessManager() { return processManager; }

    public static TooltipManager getTooltipManager() { return tooltipManager; }

    public static SoundConstants getSoundConstants() { return soundConstants; }

    public static TimerUtils getTimer() { return timer; }

    public static CacheManager getCacheManager() { return cacheManager; }

    public static ViewController getViewController() { return ViewController.getInstance(); }

    public static ProgramController getProgramController() { return ViewController.getInstance().getProgramController(); }

    public static int getScreenWidth() { return ViewController.getInstance().getScreenWidth(); }

    public static int getScreenHeight() { return ViewController.getInstance().getScreenHeight(); }

    public static LevelManager getLevelManager() { return levelManager; }
}
