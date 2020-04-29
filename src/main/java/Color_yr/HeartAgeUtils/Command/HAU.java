package Color_yr.HeartAgeUtils.Command;

import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.TileEntity;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.MetadataValueAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

class HAU implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] arg) {
        if (command.getName().equalsIgnoreCase("heartageutils")
                || command.getName().equalsIgnoreCase("hau")) {
            languageObj lan = HeartAgeUtils.configMain.lan;
            if (arg.length == 0) {
                sender.sendMessage(lan.getTitle() + lan.getUnknownCommandHeartAge());
                return true;
            } else if (arg[0].equalsIgnoreCase("help")) {
                for (String a : lan.getHelpCommandHeartAge()) {
                    sender.sendMessage(lan.getTitle() + a);
                }
                return true;
            } else if (arg[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("heartage.admin")) {
                    HeartAgeUtils.LoadConfig();
                    sender.sendMessage(lan.getTitle() + lan.getReloadCommand());
                    return true;
                }
            } else if (arg[0].equalsIgnoreCase("tpstone")) {
                String[] ss = new String[arg.length - 1];
                System.arraycopy(arg, 1, ss, 0, ss.length);
                return commandList.tpStone.onCommand(sender, ss);
            } else if (arg[0].equalsIgnoreCase("deathchest")) {
                String[] ss = new String[arg.length - 1];
                System.arraycopy(arg, 1, ss, 0, ss.length);
                return commandList.deathChest.onCommand(sender, ss);
            } else if (arg[0].equalsIgnoreCase("test")) {
                Player player = (Player) sender;
                Block block = player.getTargetBlockExact(5);
                if (block == null) {
                    sender.sendMessage(lan.getDeathLockAt());
                    return true;
                }
                BlockState temp = block.getState();
                temp.setMetadata("NoDrop", new MetadataValueAdapter(HeartAgeUtils.plugin) {
                    @Override
                    public Object value() {
                        return false;
                    }

                    @Override
                    public void invalidate() {

                    }
                });
                List<MetadataValue> list = temp.getMetadata("NoDrop");
                boolean test = list.get(0).asBoolean();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] arg) {
        if (arg.length == 1) {
            List<String> temp = new ArrayList<>();
            if (sender.hasPermission("heartage.admin")) {
                temp.add("reload");
            }
            temp.add("tpstone");
            temp.add("deathchest");
            temp.add("help");
            return temp;
        } else if (arg.length >= 2) {
            if (arg[0].equalsIgnoreCase("tpstone")) {
                String[] ss = new String[arg.length - 1];
                System.arraycopy(arg, 1, ss, 0, ss.length);
                return commandList.tpStone.onTabComplete(sender, ss);
            } else if (arg[0].equalsIgnoreCase("deathchest")) {
                String[] ss = new String[arg.length - 1];
                System.arraycopy(arg, 1, ss, 0, ss.length);
                return commandList.deathChest.onTabComplete(sender, ss);
            }
        }
        return null;
    }
}
