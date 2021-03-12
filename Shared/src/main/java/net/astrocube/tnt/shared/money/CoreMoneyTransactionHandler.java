package net.astrocube.tnt.shared.money;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.tnt.shared.perk.PerkManifestProvider;
import net.astrocube.tnt.shared.perk.TNTPerkManifest;
import org.bukkit.plugin.Plugin;

import java.text.NumberFormat;
import java.util.Optional;
import java.util.logging.Level;

@Singleton
public class CoreMoneyTransactionHandler implements MoneyTransactionHandler {

    private @Inject PerkManifestProvider perkManifestProvider;
    private @Inject Plugin plugin;

    @Override
    public int getActualMoney(String player) {

        try {
            return perkManifestProvider.getManifest(player)
                    .map(TNTPerkManifest::getMoney).orElse(-1);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "There was an error obtaining player money", e);
        }

        return -1;

    }

    @Override
    public String getFormattedMoney(String player) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(getActualMoney(player));
    }

    @Override
    public void addMoney(String player, int quantity) {
        modifyBalance(player, quantity);
    }

    @Override
    public void withdrawMoney(String player, int quantity) {
        modifyBalance(player, - (quantity));
    }

    private void modifyBalance(String player, int quantity) {
        try {
            Optional<TNTPerkManifest> manifest = perkManifestProvider.getManifest(player);

            manifest.ifPresent(gameManifest -> {

                gameManifest.setMoney(gameManifest.getMoney() + quantity);

                try {
                    perkManifestProvider.update(player, gameManifest);
                } catch (Exception e) {
                    plugin.getLogger().log(Level.SEVERE, "There was an error updating player money", e);
                }

            });

        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "There was an error updating player money", e);
        }
    }

}
