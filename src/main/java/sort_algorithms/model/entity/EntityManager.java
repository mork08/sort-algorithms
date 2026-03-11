package sort_algorithms.model.entity;

import org.dyn4j.geometry.Vector2;
import org.dyn4j.world.World;
import sort_algorithms.dyn4j.ColliderBody;
import sort_algorithms.model.scene.impl.GameScene;

import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private final World<ColliderBody> world;
    private final List<Entity<?>> entities;
    private double accumulator;

    public EntityManager() {
        this.world = new World<ColliderBody>();
        this.world.setGravity(new Vector2(0, 900));
        this.entities = new ArrayList<>();
    }

    public void destroy(Entity entity) {
        this.world.removeBody(entity.getBody());
        GameScene.getInstance().getRenderer().unregister(entity);
    }

    public void updateWorld(double dt) {
        double fixedDt = 1.0 / 400.0;
        this.accumulator += dt;

        while (accumulator >= fixedDt) {
            try {
                this.world.step(1, fixedDt);
                accumulator -= fixedDt;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Entity<?>> getEntities() {
        return this.entities;
    }

    public World<ColliderBody> getWorld() {
        return this.world;
    }
}
