package net.astrocube.tnt.spleef.projectile;

import me.fixeddev.inject.ProtectedModule;

public class ProjectileModule extends ProtectedModule {

	@Override
	public void configure() {
		bind(ProjectileCompoundMatcher.class).to(CoreProjectileCompoundMatcher.class);
		bind(ProjectileTaskTracker.class).to(CoreProjectileTaskTracker.class);
	}

}
