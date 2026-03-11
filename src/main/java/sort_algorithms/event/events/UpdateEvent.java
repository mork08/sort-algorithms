package sort_algorithms.event.events;

import sort_algorithms.event.Event;

public class UpdateEvent extends Event {

    private double deltaTime;

    public UpdateEvent(double dt) {
        super("update");
        this.deltaTime = dt;
    }

    public double getDeltaTime() {
        return deltaTime;
    }
}
