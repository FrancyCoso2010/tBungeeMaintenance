package me.francycoso.tBungeeMaintenance.commands;

import me.francycoso.tBungeeMaintenance.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MaintenanceCommand extends Command implements TabExecutor {

    public MaintenanceCommand() {
        super("maintenance", "maintenance.admin");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Main plugin = Main.getInstance();

        if (args.length == 0) {
            sender.sendMessage("§cUtilizzo: /maintenance <on|off|addplayer|removeplayer>");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "on":
                plugin.setMaintenanceEnabled(true);
                sender.sendMessage("§aManutenzione attivata.");
                plugin.kickNonWhitelistedPlayers();
                break;
            case "off":
                plugin.setMaintenanceEnabled(false);
                sender.sendMessage("§aManutenzione disattivata.");
                break;
            case "addplayer":
                if (args.length < 2) {
                    sender.sendMessage("§cUtilizzo: /maintenance addplayer <nome>");
                    return;
                }
                plugin.getWhitelistedPlayers().add(args[1]);
                plugin.saveWhitelist();
                sender.sendMessage("§a" + args[1] + " aggiunto alla whitelist.");
                break;
            case "removeplayer":
                if (args.length < 2) {
                    sender.sendMessage("§cUtilizzo: /maintenance removeplayer <nome>");
                    return;
                }
                plugin.getWhitelistedPlayers().remove(args[1]);
                plugin.saveWhitelist();
                sender.sendMessage("§a" + args[1] + " rimosso dalla whitelist.");
                break;
            default:
                sender.sendMessage("§cComando sconosciuto.");
                break;
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        Main plugin = Main.getInstance();

        if (args.length == 1) {
            List<String> options = new ArrayList<>();
            options.add("on");
            options.add("off");
            options.add("addplayer");
            options.add("removeplayer");

            return options.stream()
                    .filter(opt -> opt.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("removeplayer")) {
            return plugin.getWhitelistedPlayers().stream()
                    .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        } else if (args.length == 2 && args[0].equalsIgnoreCase("addplayer")) {
            return plugin.getProxy().getPlayers().stream()
                    .map(p -> p.getName())
                    .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }

        return Collections.emptyList();
    }
}