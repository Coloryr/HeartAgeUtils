package Color_yr.HeartAgeUtils.Command;

import Color_yr.HeartAgeUtils.API.ICommand;
import Color_yr.HeartAgeUtils.DeathChest.deathChestDo;
import Color_yr.HeartAgeUtils.DeathChest.playSetObj;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import Color_yr.HeartAgeUtils.Utils.Tools;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

class deathChest implements ICommand {

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        languageObj lan = HeartAgeUtils.configMain.lan;
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            for (String help : lan.getHelpCommandDeathChest()) {
                sender.sendMessage(help);
            }
            sender.sendMessage(deathChestDo.help);
            return true;
        } else if (args[0].equalsIgnoreCase("set") && deathChestDo.enableLocal) {
            Player player = (Player) sender;
            Block block = player.getTargetBlockExact(5);
            if (block == null) {
                sender.sendMessage(lan.getDeathLockAt());
                return true;
            }
            if (!block.getType().equals(Material.CHEST) && !block.getType().equals(Material.TRAPPED_CHEST)) {
                sender.sendMessage(lan.getDeathLockChest());
                return true;
            }
            playSetObj set = deathChestDo.getPlayerSet(((Player) sender).getUniqueId(), false);
            set.setX(block.getX());
            set.setY(block.getY());
            set.setZ(block.getZ());
            set.setWorld(block.getWorld().getName());
            deathChestDo.setPlayerSet(((Player) sender).getUniqueId(), set);
            sender.sendMessage(lan.getDeathChestSet().replace("%x%", "" + set.getX())
                    .replace("%y%", "" + set.getY()).replace("%z%", "" + set.getZ()));
            return true;
        } else if (args[0].equalsIgnoreCase("mode") && args.length == 2) {
            if (!Tools.isNumber(args[1])) {
                sender.sendMessage(lan.getDeathChestNum());
                return true;
            }
            int a = Integer.parseInt(args[1]);
            if (a < 0 || a > 7) {
                sender.sendMessage(lan.getDeathChestModeError());
                return true;
            } else if (HeartAgeUtils.configMain.Config.getDeathChest().getDisable().contains(a)) {
                sender.sendMessage(lan.getDeathChestModeNo());
                return true;
            }
            playSetObj set = deathChestDo.getPlayerSet(((Player) sender).getUniqueId(), false);
            set.setMode(a);
            deathChestDo.setPlayerSet(((Player) sender).getUniqueId(), set);
            sender.sendMessage(lan.getDeathChestModeSet().replace("%Mode%", "" + set.getMode()));
            return true;
        } else {
            sender.sendMessage(lan.getUnknownCommandDeathChest());
            return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            ArrayList<String> arguments = new ArrayList<>();
            arguments.add("help");
            arguments.add("set");
            arguments.add("mode");
            return arguments;
        } else if (args.length == 2 && args[0].equalsIgnoreCase("mode")) {
            return deathChestDo.enable;
        }
        return null;
    }
}
