package net.astrocube.tnt.lobby.menu;

import com.google.inject.Inject;
import me.yushust.message.MessageHandler;
import org.bukkit.entity.Player;
import team.unnamed.gui.core.gui.GUIBuilder;

public class CoreUpgradeConfirmationMenu implements UpgradeConfirmationMenu {

    private @Inject MessageHandler messageHandler;

    @Override
    public void open(Player player, Runnable confirm, Runnable reject) {

        GUIBuilder builder = GUIBuilder.builder(messageHandler.get(player, "upgrade.confirm.title"),3);

        builder.addItem(
                g
        )

    }
}
