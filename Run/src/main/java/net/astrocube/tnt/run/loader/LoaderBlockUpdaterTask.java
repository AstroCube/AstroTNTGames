package net.astrocube.tnt.run.loader;

import com.google.inject.Inject;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.tnt.run.block.BlockUpdaterTask;
import net.astrocube.tnt.run.block.TnTBlockHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class LoaderBlockUpdaterTask implements Loader {

    @Inject
    private Plugin plugin;

    @Inject
    private TnTBlockHandler tnTBlockHandler;

    @Override
    public void load() {
        Bukkit.getScheduler().runTaskTimer(plugin, new BlockUpdaterTask(tnTBlockHandler), 0L, 16L);
    }

}
