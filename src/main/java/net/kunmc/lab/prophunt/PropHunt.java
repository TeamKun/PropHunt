package net.kunmc.lab.prophunt;

import net.kunmc.lab.prophunt.listeners.BlockInteractEventListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class PropHunt extends JavaPlugin {

    public ItemStack transform_item;

    public final boolean DEBUG = true;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Kei.z(this);
        Kei.out(Kei.a(this));

        // Setup items
        transform_item = new ItemStack(Material.STICK);
        ItemMeta meta = transform_item.getItemMeta();
        meta.setDisplayName("Transform Stick");
        meta.setLore(Arrays.asList(ChatColor.RESET + "右クリックをすると変身ができる"));
        transform_item.setItemMeta(meta);

        // Register event listeners
        Kei.a(new BlockInteractEventListener(this), this);

        // Debug mode
        if(DEBUG) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.getInventory().addItem(transform_item);
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
