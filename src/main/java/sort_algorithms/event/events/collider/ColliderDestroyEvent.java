package sort_algorithms.event.events.collider;

import sort_algorithms.dyn4j.ColliderBody;
import sort_algorithms.event.Event;

public class ColliderDestroyEvent extends Event {

    private ColliderBody collider;

    public ColliderDestroyEvent(ColliderBody collider) {
        super("collider_destroy");
        this.collider = collider;
    }

    public ColliderBody getCollider() {
        return collider;
    }
}
