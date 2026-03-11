package sort_algorithms.event.events;

import sort_algorithms.event.Event;
import sort_algorithms.graphics.camera.CameraRenderer;

public class CameraMoveEvent extends Event {

    private CameraRenderer camera;

    public CameraMoveEvent(CameraRenderer camera) {
        super("cameraMove");
        this.camera = camera;
    }

    public CameraRenderer getCamera() {
        return this.camera;
    }
}