package net.astrocube.tnt.shared.perk.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

@Singleton
public class CorePerkConfigurationCache implements PerkConfigurationCache {

    private @Inject Plugin plugin;
    private @Inject ObjectMapper mapper;

    private final Set<PerkConfiguration.Purchasable> cache = new HashSet<>();
    private boolean generated = false;

    @Override
    public void generate() {

        if (generated) {
            throw new UnsupportedOperationException("Can not re-create repository");
        }

        File folder = new File(plugin.getDataFolder(), "perk");

        if (folder.exists() && folder.isDirectory()) {

            for (File file : folder.listFiles()) {

                if (!file.isDirectory() && FilenameUtils.getExtension(file.getName()).equalsIgnoreCase("json")) {

                    try {
                        PerkConfiguration configuration = mapper.readValue(file, PerkConfiguration.class);
                        cache.addAll(configuration.getTiers());
                    } catch (IOException ex) {
                        plugin.getLogger().log(Level.WARNING, "Error while parsing {0}", file.getName());
                    }

                }

            }

        } else {
            folder.mkdir();
        }

        generated = true;
        plugin.getLogger().log(Level.INFO, "Loaded {0} objects from perk repository.", cache.size());

    }

    @Override
    public Set<PerkConfiguration.Purchasable> getCachedItems() {
        return cache;
    }
}
