package Color_yr.HeartAge_year.Config.tpStone;

import net.minecraft.server.v1_14_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NBTset {
    public NBTTagCompound NBT_get(ItemStack item)
    {
        net.minecraft.server.v1_14_R1.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        return NMSItem.getTag();
    }
}
