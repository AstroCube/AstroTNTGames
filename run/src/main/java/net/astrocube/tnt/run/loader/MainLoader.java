package net.astrocube.tnt.run.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;

public class MainLoader implements Loader {

	@Inject private ListenerLoader listenerLoader;

	@Override
	public void load() {
		this.listenerLoader.load();
	}

}
