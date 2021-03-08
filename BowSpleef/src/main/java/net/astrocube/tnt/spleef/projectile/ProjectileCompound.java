package net.astrocube.tnt.spleef.projectile;

import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.function.Consumer;

public interface ProjectileCompound {

    /**
     * @return registered at the database.
     */
    String getName();

    /**
     * @return runnable to be executed while
     */
    Consumer<ProjectileLaunchEvent> getRunnable();

}
