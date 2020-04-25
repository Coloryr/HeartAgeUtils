package Color_yr.HeartAgeUtils.API;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface ICommand {
    boolean onCommand(CommandSender sender, String[] args);

    List<String> onTabComplete(CommandSender sender, String[] args);
}
