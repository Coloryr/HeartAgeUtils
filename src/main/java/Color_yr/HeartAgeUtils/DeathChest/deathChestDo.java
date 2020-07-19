package Color_yr.HeartAgeUtils.DeathChest;

import Color_yr.HeartAgeUtils.Config.configMain;
import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Hook.Hook;
import Color_yr.HeartAgeUtils.Obj.configObj;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.MetadataValueAdapter;

import java.util.*;

public class deathChestDo {
    public static final List<String> enable = new ArrayList<>();
    public static final Map<String, playSetObj> deathChestSet = new HashMap<>();
    public static final Map<String, InventoryView> guiSave = new HashMap<>();
    private static final MetadataValue drop = new MetadataValueAdapter(HeartAgeUtils.plugin) {
        @Override
        public Object value() {
            return true;
        }

        @Override
        public void invalidate() {

        }
    };
    public static boolean enableLocal = false;
    public static String help = "";

    public static void GenHelp() {
        help = "";
        enable.clear();
        configObj config = HeartAgeUtils.configMain.Config;
        languageObj lan = HeartAgeUtils.configMain.lan;

        for (int a = 0; a < 8; a++) {
            if (!config.getDeathChest().getDisable().contains(a)) {
                enable.add("" + a);
                help += lan.getHelpModeDeathChest().get(a) + "\n";
            }
        }

        if (config.getDeathChest().getCost().isEnable()) {
            if (!config.getDeathChest().getDisable().contains(1)
                    || !config.getDeathChest().getDisable().contains(4)
                    || !config.getDeathChest().getDisable().contains(5)
                    || !config.getDeathChest().getDisable().contains(7)) {
                help += lan.getHelpCostDeathChest().get(0).replace("%Cost%",
                        "" + config.getDeathChest().getCost().getSaveInLocal()) + "\n";
            }
            if (!config.getDeathChest().getDisable().contains(2)
                    || !config.getDeathChest().getDisable().contains(4)
                    || !config.getDeathChest().getDisable().contains(5)
                    || !config.getDeathChest().getDisable().contains(7)) {
                help += lan.getHelpCostDeathChest().get(1).replace("%Cost%",
                        "" + config.getDeathChest().getCost().getSaveInChest()) + "\n";
                enableLocal = true;
            } else {
                enableLocal = false;
            }
            if (!config.getDeathChest().getDisable().contains(3)
                    || !config.getDeathChest().getDisable().contains(4)
                    || !config.getDeathChest().getDisable().contains(5)
                    || !config.getDeathChest().getDisable().contains(6)) {
                help += lan.getHelpCostDeathChest().get(2).replace("%Cost%",
                        "" + config.getDeathChest().getCost().getNoDrop()) + "\n";
            }
        }
    }

    public static playSetObj getPlayerSet(UUID player, boolean saveData) {
        String uuid = player.toString().replace("-", "0");
        playSetObj save = deathChestSet.get(uuid);
        if (save == null) {
            save = new playSetObj();
            deathChestSet.put(uuid, save);
        }
        if (saveData) {
            configMain.deathChest.save(save, uuid);
        }
        return save;
    }

    public static void setPlayerSet(UUID player, playSetObj set) {
        String uuid = player.toString().replace("-", "0");
        deathChestSet.put(uuid, set);
        configMain.deathChest.save(set, uuid);
    }

    public static void GenChest(Player player, Location pos) {
        deathChestConfigObj config = HeartAgeUtils.configMain.Config.getDeathChest();
        languageObj lan = HeartAgeUtils.configMain.lan;
        String temp = lan.getDeathChestCost();
        temp = temp.replace("%Money%", "" + config.getCost().getSaveInLocal());
        Hook.vaultCost(player, config.getCost().getSaveInLocal(), temp);
        temp = lan.getDeathChestGen();
        temp = temp.replace("%x%", "" + pos.getBlockX())
                .replace("%y%", "" + pos.getBlockY())
                .replace("%z%", "" + pos.getBlockZ());
        player.sendMessage(temp);
    }

    public static void GenLocalChest(Player player) {
        deathChestConfigObj config = HeartAgeUtils.configMain.Config.getDeathChest();
        languageObj lan = HeartAgeUtils.configMain.lan;
        String temp = lan.getDeathChestCost1();
        temp = temp.replace("%Money%", "" + config.getCost().getSaveInChest());
        Hook.vaultCost(player, config.getCost().getSaveInChest(), temp);
    }

    public static void NoDrop(Player player) {
        deathChestConfigObj config = HeartAgeUtils.configMain.Config.getDeathChest();
        languageObj lan = HeartAgeUtils.configMain.lan;
        String temp = lan.getDeathChestCost2();
        temp = temp.replace("%Money%", "" + config.getCost().getNoDrop());
        Hook.vaultCost(player, config.getCost().getNoDrop(), temp);
    }

    public static List<Inventory> setBlock(World world, Location location, boolean needDoble) {
        boolean up = true;
        if (location.getY() >= world.getMaxHeight()) {
            up = false;
            location.setY(world.getMaxHeight() - 1);
        }
        List<Inventory> list = new ArrayList<>();
        Block temp;
        int max = world.getMaxHeight();
        while (needDoble) {
            temp = world.getBlockAt(location);
            Block temp1;
            if (temp.getType().equals(Material.AIR)) {
                Location location1 = location.clone();
                location1.setX(location1.getX() + 1);
                temp1 = world.getBlockAt(location1);
                if (temp1.getType().equals(Material.AIR)) {
                    temp1.setType(Material.CHEST);
                    temp.setType(Material.CHEST);

                    BlockState state = temp.getState();
                    if (state instanceof Chest)
                        list.add(((Chest) state).getBlockInventory());
                    state = temp1.getState();
                    if (state instanceof Chest)
                        list.add(((Chest) state).getBlockInventory());

                    return list;
                }
                location1.setX(location1.getX() - 2);
                temp1 = world.getBlockAt(location1);
                if (temp1.getType().equals(Material.AIR)) {
                    temp1.setType(Material.CHEST);
                    temp.setType(Material.CHEST);

                    BlockState state = temp.getState();
                    if (state instanceof Chest)
                        list.add(((Chest) state).getBlockInventory());
                    state = temp1.getState();
                    if (state instanceof Chest)
                        list.add(((Chest) state).getBlockInventory());

                    return list;
                }
                location1.setX(location1.getX() + 1);
                location1.setZ(location1.getZ() + 1);
                temp1 = world.getBlockAt(location1);
                if (temp1.getType().equals(Material.AIR)) {
                    temp1.setType(Material.CHEST);
                    temp.setType(Material.CHEST);

                    BlockState state = temp.getState();
                    if (state instanceof Chest)
                        list.add(((Chest) state).getBlockInventory());
                    state = temp1.getState();
                    if (state instanceof Chest)
                        list.add(((Chest) state).getBlockInventory());

                    return list;
                }
                location1.setZ(location1.getZ() - 2);
                temp1 = world.getBlockAt(location1);
                if (temp1.getType().equals(Material.AIR)) {
                    temp1.setType(Material.CHEST);
                    temp.setType(Material.CHEST);

                    BlockState state = temp.getState();
                    if (state instanceof Chest)
                        list.add(((Chest) state).getBlockInventory());
                    state = temp1.getState();
                    if (state instanceof Chest)
                        list.add(((Chest) state).getBlockInventory());

                    temp.setMetadata("NoDrop", drop);
                    temp1.setMetadata("NoDrop", drop);

                    return list;
                }
            }
            if (up)
                location.setY(location.getBlockY() + 1);
            else
                location.setY(location.getBlockY() - 1);
            if (location.getBlockY() >= max) {
                up = false;
            }
            if (location.getBlockY() <= 0) {
                return null;
            }
        }
        temp = world.getBlockAt(location);
        while (!temp.getType().equals(Material.AIR)) {
            temp = world.getBlockAt(location);
            if (up)
                location.setY(location.getBlockY() + 1);
            else
                location.setY(location.getBlockY() - 1);
            if (location.getBlockY() >= max) {
                up = false;
            }
            if (location.getBlockY() <= 0) {
                return null;
            }
        }
        temp.setType(Material.CHEST);
        BlockState state = temp.getState();
        if (state instanceof Chest)
            list.add(((Chest) state).getBlockInventory());
        temp.setMetadata("NoDrop", drop);

        return list;
    }

    public static Inventory getNe(Block block) {
        Block block1 = block.getRelative(BlockFace.NORTH);
        if (block1.getType().equals(Material.CHEST)) {
            BlockState state = block1.getState();
            if (state instanceof Chest)
                return (((Chest) state).getBlockInventory());
        }
        block1 = block.getRelative(BlockFace.EAST);
        if (block1.getType().equals(Material.CHEST)) {
            BlockState state = block1.getState();
            if (state instanceof Chest)
                return (((Chest) state).getBlockInventory());
        }
        block1 = block.getRelative(BlockFace.SOUTH);
        if (block1.getType().equals(Material.CHEST)) {
            BlockState state = block1.getState();
            if (state instanceof Chest)
                return (((Chest) state).getBlockInventory());
        }
        block1 = block.getRelative(BlockFace.WEST);
        if (block1.getType().equals(Material.CHEST)) {
            BlockState state = block1.getState();
            if (state instanceof Chest)
                return (((Chest) state).getBlockInventory());
        }
        return null;
    }

    public static List<Inventory> getChest(World world, Location location) {
        Block block;
        List<Inventory> list = new ArrayList<>();
        block = world.getBlockAt(location);
        if (!block.getType().equals(Material.CHEST) && !block.getType().equals(Material.TRAPPED_CHEST)) {
            return null;
        }
        Inventory temp = getNe(block);
        if (temp != null)
            list.add(temp);
        BlockState state = block.getState();
        if (state instanceof Chest)
            list.add(((Chest) state).getBlockInventory());
        return list;
    }

    public static RE genDeathChest(PlayerDeathEvent e) {
        Player player = e.getEntity();
        List<Inventory> inventory = setBlock(player.getWorld(), player.getLocation(), e.getDrops().size() > 27);
        RE re = new RE();
        re.ok = Check(inventory, e, player);
        if (re.ok)
            re.location = inventory.get(0).getLocation();
        return re;
    }

    public static boolean setChest(PlayerDeathEvent e) {
        Player player = e.getEntity();
        playSetObj set = deathChestDo.getPlayerSet(player.getUniqueId(), true);
        World world = Bukkit.getWorld(set.getWorld());
        Location location = new Location(world, set.getX(), set.getY(), set.getZ());
        List<Inventory> inventory = getChest(world, location);
        return Check(inventory, e, player);
    }

    public static boolean Check(List<Inventory> inventory, PlayerDeathEvent e, Player player) {
        if (inventory == null) {
            return false;
        }
        List<ItemStack> list = new ArrayList<>(e.getDrops());
        for (Inventory item : inventory) {
            for (ItemStack item1 : new ArrayList<>(list)) {
                item.addItem(item1);
                e.getDrops().remove(item1);
                list.remove(item1);
                if (item.firstEmpty() == -1)
                    break;
            }
        }
        if (e.getDrops().size() > 0) {
            languageObj lan = HeartAgeUtils.configMain.lan;
            player.sendMessage(lan.getDeathChestCantPlace());
        }
        return true;
    }

    public static class RE {
        public boolean ok;
        public Location location;
    }
}
