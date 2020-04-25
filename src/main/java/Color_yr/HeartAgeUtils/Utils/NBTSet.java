package Color_yr.HeartAgeUtils.Utils;

import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBTSet {//NBT标签获取

    public static NBTTagCompound NBT_get(ItemStack item) {
        net.minecraft.server.v1_15_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        return NMSItem.getOrCreateTag();
    }

    public static ItemStack NBT_save(ItemStack item, NBTTagCompound nbt) {
        net.minecraft.server.v1_15_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        NMSItem.setTag(nbt);
        return CraftItemStack.asBukkitCopy(NMSItem.cloneItemStack());
    }
}
