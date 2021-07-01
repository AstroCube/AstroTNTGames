package net.astrocube.tnt.spleef.projectile;

import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.function.Consumer;

public class ProjectileCompound {

	private final String name;
	private final Consumer<ProjectileLaunchEvent> callback;

	public ProjectileCompound(String name, Consumer<ProjectileLaunchEvent> callback) {
		this.name = name;
		this.callback = callback;
	}

	/**
	 * @return registered at the database.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return runnable to be executed while
	 */
	public Consumer<ProjectileLaunchEvent> getRunnable() {
		return callback;
	}

}
