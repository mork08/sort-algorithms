package sort_algorithms.animation.entity;

import sort_algorithms.animation.IAnimationState;
import sort_algorithms.model.entity.EntityDirection;

public interface IEntityAnimationState extends IAnimationState {
    EntityDirection getDirection();
    EntityState getState();
}
