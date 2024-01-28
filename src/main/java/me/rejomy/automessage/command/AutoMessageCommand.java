package me.rejomy.automessage.command;

import me.rejomy.automessage.Main;
import me.rejomy.automessage.util.ColorUtil;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class AutoMessageCommand implements CommandExecutor {

    private final String allCommands = ColorUtil.toColor("&8       &m-----\n" +
            " &f/automessage reload &8- &7Use for reload the plugin config."
            + "\n&8       &m-----");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(allCommands);
            return false;
        }

        switch (args[0]) {
            case "reload":
                Main.getInstance().reloadConfig();
                Main.getInstance().stopTask();
                Main.getInstance().runTask();
                sender.sendMessage(ColorUtil.toColor("&e[AutoMessage] &aConfig has been reloaded!"));
                break;
            default:
                sender.sendMessage(allCommands);
        }

        return false;
    }

}
