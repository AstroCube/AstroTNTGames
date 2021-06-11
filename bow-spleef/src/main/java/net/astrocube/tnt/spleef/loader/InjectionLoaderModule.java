package net.astrocube.tnt.spleef.loader;

import me.fixeddev.inject.ProtectedModule;
import net.astrocube.api.bukkit.translation.TranslationModule;
import net.astrocube.tnt.podium.CoreMatchProgressHandler;
import net.astrocube.tnt.podium.MatchProgressHandler;
import net.astrocube.tnt.shared.SharedModule;
import net.astrocube.tnt.spleef.game.GameModule;
import net.astrocube.tnt.spleef.projectile.ProjectileModule;

public class InjectionLoaderModule extends ProtectedModule {

	@Override
	public void configure() {
		install(new LoaderModule());
		install(new TranslationModule());
		install(new GameModule());
		install(new ProjectileModule());
		install(new SharedModule());

		bind(MatchProgressHandler.class).to(CoreMatchProgressHandler.class);

	}

}
