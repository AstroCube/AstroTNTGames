package net.astrocube.tnt.lobby.menu;

import org.bukkit.entity.Player;

public interface UpgradeConfirmationMenu {

    void open(
            Player player,
            Runnable confirm,
            Runnable reject
    );

}
