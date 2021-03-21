package net.astrocube.tnt.shared.money;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import net.astrocube.tnt.shared.perk.TNTPerkProvider;
import net.astrocube.tnt.shared.perk.TNTPerkManifest;
import org.bukkit.plugin.Plugin;

import java.text.NumberFormat;
import java.util.logging.Level;

@Singleton
public class CoreMoneyTransactionHandler implements MoneyTransactionHandler {

    private @Inject TNTPerkProvider TNTPerkProvider;
    private @Inject Plugin plugin;
    private @Inject NumberFormat numberFormat;

    @Override
    public int getActualMoney(String player) {

        try {
            return TNTPerkProvider.getManifest(player)
                    .map(TNTPerkManifest::getMoney).orElse(-1);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "There was an error obtaining player money", e);
        }

        return -1;

    }

    @Override
    public String getFormattedMoney(String player) {
        return numberFormat.format(getActualMoney(player));
    }

}
