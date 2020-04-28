package Color_yr.HeartAgeUtils.Utils;

import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class itemNBTSet {//NBT标签获取

    public static NBTTagCompound getNBT(ItemStack item) {
        net.minecraft.server.v1_15_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        return NMSItem.getOrCreateTag();
    }

    public static ItemStack saveNBT(ItemStack item, NBTTagCompound nbt) {
        net.minecraft.server.v1_15_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        NMSItem.setTag(nbt);
        return CraftItemStack.asBukkitCopy(NMSItem.cloneItemStack());
    }
}
