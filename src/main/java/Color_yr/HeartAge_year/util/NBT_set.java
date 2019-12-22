package Color_yr.HeartAge_year.util;

import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBT_set {//NBT标签获取

    public NBTTagCompound NBT_get(ItemStack item) {
        net.minecraft.server.v1_14_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        return NMSItem.getOrCreateTag();
    }

    public ItemStack NBT_save(ItemStack item, NBTTagCompound nbt) {
        net.minecraft.server.v1_14_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        NMSItem.setTag(nbt);
        return CraftItemStack.asBukkitCopy(NMSItem.cloneItemStack());
    }
}
