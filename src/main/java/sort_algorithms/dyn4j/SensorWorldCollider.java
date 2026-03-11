package sort_algorithms.dyn4j;

import sort_algorithms.graphics.level.ObjectIdResolver;
import sort_algorithms.graphics.map.GsonMap;

public class SensorWorldCollider extends WorldCollider {

    protected WorldCollider collider;

    public SensorWorldCollider(GsonMap.ObjectCollider data) {
        super(data);
    }

    public SensorWorldCollider(GsonMap.ObjectCollider data, ObjectIdResolver resolver) {
        super(data, resolver);
    }

    public WorldCollider getCollider() {
        return this.collider;
    }

    public void setCollider(WorldCollider collider) {
        this.collider = collider;
    }
}
