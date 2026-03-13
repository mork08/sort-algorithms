package sort_algorithms.graphics.level;

import sort_algorithms.model.transitions.Transition;

public record LevelSwitch(String id, LevelSwitchDirection dir, Level last, Level next, Runnable runnable, Transition<Level> transition) {
    public enum LevelSwitchDirection {
        NEXT,
        PREVIOUS
    }
}
