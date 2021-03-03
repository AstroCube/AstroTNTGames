package net.astrocube.tnt.run.loader;

import me.fixeddev.inject.ProtectedModule;

public class InjectionLoaderModule extends ProtectedModule {

    @Override
    public void configure() {
        install(new LoaderModule());
    }

}
