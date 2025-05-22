package me.francycoso.tBungeeMaintenance.listeners;

import me.francycoso.tBungeeMaintenance.Main;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class MaintenanceListener implements Listener {

    @EventHandler
    public void onLogin(LoginEvent event) {
        Main plugin = Main.getInstance();

        if (!plugin.isMaintenanceEnabled()) return;

        PendingConnection connection = event.getConnection();
        String name = connection.getName();

        if (!plugin.getWhitelistedPlayers().contains(name)) {
            event.setCancelled(true);
            event.setCancelReason(new TextComponent(
                    "§cIl server è in manutenzione.\n" +
                            "§7Riprova più tardi o controlla:\n" +
                            "§bdiscord.tharisnetwork.cc\n" +
                            "§bforum.tharisnetwork.cc"
            ));
        }
    }
}
