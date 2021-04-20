package net.astrocube.tnt.run.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;

import javax.inject.Named;

public class LayoutLoader implements Loader {

    private @Inject @Named("listener") Loader listenerLoader;
    private @Inject @Named("task-block") Loader loaderTask;

    @Override
    public void load() {
        listenerLoader.load();
        loaderTask.load();
    }

}
