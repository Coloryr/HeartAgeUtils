package Color_yr.HeartAgeUtils.enchantment;

import Color_yr.HeartAgeUtils.HeartAgeUtils;
import Color_yr.HeartAgeUtils.Hook.Hook;
import Color_yr.HeartAgeUtils.Obj.languageObj;
import Color_yr.HeartAgeUtils.Utils.Exp;
import Color_yr.HeartAgeUtils.Utils.Tools;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

import java.util.Map;

public class enchantmentDo {
    public static String help = "";

    public static void GetHelp() {
        help = "";
        enchantmentObj obj = HeartAgeUtils.configMain.Config.getEnchantment();
        languageObj lan = HeartAgeUtils.configMain.lan;
        help += lan.getHelpCostEnchantment().get(0)
                .replace("%Cost%", String.valueOf(obj.getBreakCost()))
                .replace("%Exp%", String.valueOf(obj.getBreakExp()));
        help += lan.getHelpCostEnchantment().get(1)
                .replace("%Cost%", String.valueOf(obj.getAddCost()))
                .replace("%Exp%", String.valueOf(obj.getAddExp()));
    }

    public static void EMDo(Player player, boolean f) {
        enchantmentObj obj = HeartAgeUtils.configMain.Config.getEnchantment();
        languageObj lan = HeartAgeUtils.configMain.lan;
        ItemStack itemHand = player.getInventory().getItemInMainHand();
        if (itemHand.getType().equals(Material.AIR)) {
            player.sendMessage(lan.getEnchantmentNoTool());
        }
        ItemStack itemOff = player.getInventory().getItemInOffHand();
        if (f) {//Add
            if (!itemOff.getType().equals(Material.ENCHANTED_BOOK)) {
                player.sendMessage(lan.getEnchantmentNoEMBook());
            } else if (!Hook.vaultCheck(player, obj.getAddCost())) {
                player.sendMessage(lan.getEnchantmentNoMoney());
            } else if (Exp.getExpPoints(player) < obj.getAddExp()) {
                player.sendMessage(lan.getEnchantmentNoExp());
            } else {
                Map<Enchantment, Integer> Enchantment1 = ((EnchantmentStorageMeta) itemOff.getItemMeta()).getStoredEnchants();
                if (Enchantment1.size() == 0) {
                    player.sendMessage(lan.getEnchantmentNo());
                } else {
                    Hook.vaultCost(player, obj.getAddCost(), "已花费" + obj.getAddCost());
                    Exp.subtractExpPoints(player, obj.getAddExp());
                    if (itemHand.getType().equals(Material.ENCHANTED_BOOK)) {
                        Map<Enchantment, Integer> Enchantment2 = ((EnchantmentStorageMeta) itemHand.getItemMeta()).getStoredEnchants();
                        EnchantmentStorageMeta data1 = ((EnchantmentStorageMeta) itemHand.getItemMeta());
                        for (Map.Entry<Enchantment, Integer> item : Enchantment1.entrySet()) {
                            if (Enchantment2.containsKey(item.getKey())) {
                                int level1 = Enchantment2.get(item.getKey());
                                int level2 = item.getValue();
                                if (level2 > level1) {
                                    data1.addStoredEnchant(item.getKey(), level2, true);
                                } else if (level2 == level1) {
                                    data1.addStoredEnchant(item.getKey(), level2 + 1, true);
                                }
                            } else {
                                data1.addStoredEnchant(item.getKey(), item.getValue(), true);
                            }
                        }
                        itemHand.setItemMeta(data1);
                    } else {
                        Map<Enchantment, Integer> Enchantment = itemHand.getItemMeta().getEnchants();
                        for (Map.Entry<Enchantment, Integer> item : Enchantment1.entrySet()) {
                            if (Enchantment.containsKey(item.getKey())) {
                                int level1 = Enchantment.get(item.getKey());
                                int level2 = item.getValue();
                                if (level2 > level1) {
                                    itemHand.addUnsafeEnchantment(item.getKey(), level2);
                                } else if (level2 == level1) {
                                    itemHand.addUnsafeEnchantment(item.getKey(), level2 + 1);
                                }
                            } else {
                                itemHand.addUnsafeEnchantment(item.getKey(), item.getValue());
                            }
                        }
                    }
                    player.getInventory().setItemInMainHand(itemHand);
                    player.getInventory().setItemInOffHand(null);
                    player.sendMessage(lan.getEnchantmentAdd());
                }
            }
        } else {//Break
            if (!itemOff.getType().equals(Material.BOOK)) {
                player.sendMessage(lan.getEnchantmentNoBook());
            } else if (!Hook.vaultCheck(player, obj.getBreakCost())) {
                player.sendMessage(lan.getEnchantmentNoMoney());
            } else if (Exp.getExpPoints(player) < obj.getBreakExp()) {
                player.sendMessage(lan.getEnchantmentNoExp());
            } else {
                Map<Enchantment, Integer> Enchantment;
                if (!itemHand.getType().equals(Material.ENCHANTED_BOOK)) {
                    Enchantment = itemHand.getItemMeta().getEnchants();
                    if (Enchantment.size() == 0) {
                        player.sendMessage(lan.getEnchantmentNoEM());
                        return;
                    }
                } else {
                    Enchantment = ((EnchantmentStorageMeta) itemHand.getItemMeta()).getStoredEnchants();
                    if (Enchantment.size() <= 1) {
                        player.sendMessage(lan.getEnchantmentNoEMs());
                        return;
                    }
                }

                Hook.vaultCost(player, obj.getBreakCost(), "已花费" + obj.getBreakCost());
                Exp.subtractExpPoints(player, obj.getBreakExp());
                if (!itemHand.getType().equals(Material.ENCHANTED_BOOK)) {
                    for (Map.Entry<Enchantment, Integer> item : Enchantment.entrySet()) {
                        itemHand.removeEnchantment(item.getKey());
                        player.getInventory().setItemInMainHand(itemHand);
                        ItemStack item1 = new ItemStack(Material.ENCHANTED_BOOK);
                        EnchantmentStorageMeta data = (EnchantmentStorageMeta) item1.getItemMeta();
                        data.addStoredEnchant(item.getKey(), item.getValue(), true);
                        data.setDisplayName("拆分后的附魔书");
                        item1.setItemMeta(data);
                        if (itemOff.getAmount() > 1) {
                            itemOff.setAmount(itemOff.getAmount() - 1);
                            if (Tools.CheckIsFull(player))
                                player.getLocation().getWorld().dropItem(player.getLocation(), item1);
                            else
                                player.getInventory().addItem(item1);
                            player.getInventory().setItemInOffHand(itemOff);
                        } else {
                            player.getInventory().setItemInOffHand(item1);
                        }
                        player.sendMessage(lan.getEnchantmentBreak());
                        return;
                    }
                } else {
                    for (Map.Entry<Enchantment, Integer> item : Enchantment.entrySet()) {
                        EnchantmentStorageMeta data1 = (EnchantmentStorageMeta) itemHand.getItemMeta();
                        data1.removeStoredEnchant(item.getKey());
                        itemHand.setItemMeta(data1);
                        player.getInventory().setItemInMainHand(itemHand);
                        ItemStack item1 = new ItemStack(Material.ENCHANTED_BOOK);
                        EnchantmentStorageMeta data = (EnchantmentStorageMeta) item1.getItemMeta();
                        data.addStoredEnchant(item.getKey(), item.getValue(), true);
                        data.setDisplayName("拆分后的附魔书");
                        item1.setItemMeta(data);
                        if (itemOff.getAmount() > 1) {
                            itemOff.setAmount(itemOff.getAmount() - 1);
                            if (Tools.CheckIsFull(player))
                                player.getLocation().getWorld().dropItem(player.getLocation(), item1);
                            else
                                player.getInventory().addItem(item1);
                            player.getInventory().setItemInOffHand(itemOff);
                        } else {
                            player.getInventory().setItemInOffHand(item1);
                        }
                        player.sendMessage(lan.getEnchantmentBreak());
                        return;
                    }
                }
            }
        }
    }
}
