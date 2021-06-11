package net.astrocube.tnt.lobby.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.tnt.lobby.loader.listener.UserListenerLoader;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

public class ListenerLoader implements Loader {

	private @Inject Plugin plugin;

	private @Inject UserListenerLoader userListenerLoader;

	@Override
	public void load() {
		plugin.getLogger().log(Level.INFO, "Initializing event listeners");
		userListenerLoader.registerEvents();
	}

}
