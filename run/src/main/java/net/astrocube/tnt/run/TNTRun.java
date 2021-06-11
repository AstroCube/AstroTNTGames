package net.astrocube.tnt.run;

import com.google.inject.Inject;
import me.fixeddev.inject.ProtectedBinder;
import net.astrocube.tnt.run.loader.InjectionLoaderModule;
import net.astrocube.tnt.run.loader.MainLoader;
import org.bukkit.plugin.java.JavaPlugin;

public class TNTRun extends JavaPlugin {

	@Inject private MainLoader loader;

	@Override
	public void onEnable() {
		loader.load();
		copyDefaults();
	}

	@Override
	public void configure(ProtectedBinder binder) {
		binder.install(new InjectionLoaderModule());
	}

	public void copyDefaults() {
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
	}

}
