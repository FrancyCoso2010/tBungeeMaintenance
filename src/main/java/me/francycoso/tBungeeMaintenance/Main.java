package me.francycoso.tBungeeMaintenance;

import me.francycoso.tBungeeMaintenance.commands.MaintenanceCommand;
import me.francycoso.tBungeeMaintenance.listeners.MaintenanceListener;
import me.francycoso.tBungeeMaintenance.utils.YamlConfig;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public final class Main extends Plugin {

    public static Main instance;
    private boolean maintenanceEnabled = false;
    private final Set<String> whitelistedPlayers = new HashSet<>();
    private File whitelistFile;
    private YamlConfig whitelistConfig;

    @Override
    public void onEnable() {
        instance = this;
        loadWhitelist();

        getProxy().getPluginManager().registerListener(this, new MaintenanceListener());
        getProxy().getPluginManager().registerCommand(this, new MaintenanceCommand());
    }

    @Override
    public void onDisable() {
    }

    public static Main getInstance() {
        return instance;
    }

    public boolean isMaintenanceEnabled() {
        return maintenanceEnabled;
    }

    public void setMaintenanceEnabled(boolean enabled) {
        this.maintenanceEnabled = enabled;
    }

    public Set<String> getWhitelistedPlayers() {
        return whitelistedPlayers;
    }

    private void loadWhitelist() {
        whitelistFile = new File(getDataFolder(), "whitelist.yml");

        if (!whitelistFile.exists()) {
            getDataFolder().mkdirs();
            whitelistConfig = new YamlConfig(whitelistFile);
            whitelistConfig.setList("whitelisted", "Carrellare", "NotDrizy", "Plasmon__", "ItsDavlooo", "MrShadow02");
            whitelistConfig.save();
        } else {
            whitelistConfig = new YamlConfig(whitelistFile);
        }

        whitelistedPlayers.clear();
        whitelistedPlayers.addAll(whitelistConfig.getStringList("whitelisted"));
    }

    public void saveWhitelist() {
        whitelistConfig.set("whitelisted", whitelistedPlayers);
        whitelistConfig.save();
    }

    public void kickNonWhitelistedPlayers() {
        for (ProxiedPlayer player : getProxy().getPlayers()) {
            if (whitelistedPlayers.stream().noneMatch(name -> name.equalsIgnoreCase(player.getName()))) {
                player.disconnect(new TextComponent(
                        "§cIl server è ora in manutenzione.\n" +
                                "§7Solo i membri autorizzati possono restare connessi.\n" +
                                "§bdiscord.tharisnetwork.cc"
                ));
            }
        }
    }

}
