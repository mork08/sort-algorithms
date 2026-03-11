package sort_algorithms.graphics.level;

import sort_algorithms.model.transitions.Transition;

public record LevelSwitch(String id, LevelSwitchDirection dir, LevelMap last, LevelMap next, Runnable runnable, Transition<LevelMap> transition) {
    public enum LevelSwitchDirection {
        NEXT,
        PREVIOUS
    }
}
