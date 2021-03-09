package net.astrocube.tnt.lobby.loader;

import net.astrocube.api.bukkit.game.exception.GameControlException;
import net.astrocube.api.core.loader.Loader;
import net.astrocube.api.core.service.find.FindService;
import net.astrocube.api.core.virtual.gamemode.GameMode;
import net.astrocube.api.core.virtual.gamemode.SubGameMode;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;
import java.util.logging.Level;

public class TNTLobbyLoader implements Loader {

    private @Inject @Named("listener") Loader listenerLoader;

    private @Inject FindService<GameMode> findService;
    private @Inject Plugin plugin;

    @Override
    public void load() {
        try {

            GameMode mode = findService.findSync(plugin.getConfig().getString("registry.mode"));

            if (mode.getSubTypes() == null) {
                throw new GameControlException("Registered mode has no child subModes");
            }

            Set<SubGameMode> subGameModes = mode.getSubTypes();

            if (checkRegistryAbsence("registry.tnt-run", subGameModes)) {
                throw new GameControlException("No TNT Run registered");
            }

            if (checkRegistryAbsence("registry.bow-spleef", subGameModes)) {
                throw new GameControlException("No Bow Spleef registered");
            }

            plugin.getLogger().info("Successfully paired with modes.");

            listenerLoader.load();

        } catch (Exception e) {
            Bukkit.getPluginManager().disablePlugin(plugin);
            plugin.getLogger().log(Level.SEVERE, "Error while validating registered modes.");
        }
    }

    private boolean checkRegistryAbsence(String mode, Set<SubGameMode> subGameModes) {
        return subGameModes.stream().noneMatch(
                m -> m.getId().equalsIgnoreCase(
                        plugin.getConfig().getString(mode)
                )
        );
    }

}
