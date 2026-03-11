package sort_algorithms.event.services;

public abstract class EventProcess implements Runnable {

    private final String name;

    public EventProcess(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
