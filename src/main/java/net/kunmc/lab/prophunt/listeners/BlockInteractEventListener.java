package net.kunmc.lab.prophunt.listeners;

import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.utilities.DisguiseUtilities;
import me.libraryaddict.disguise.utilities.parser.DisguiseParser;
import me.libraryaddict.disguise.utilities.parser.DisguisePermissions;
import net.kunmc.lab.prophunt.PropHunt;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockInteractEventListener implements Listener {

    PropHunt plugin;

    public BlockInteractEventListener(PropHunt ph) {
        plugin = ph;
    }

    @EventHandler
    public void on(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (plugin.transform_item.isSimilar(e.getPlayer().getInventory().getItemInMainHand())) {
                // If ItemStack is a match
                try {
                    Disguise disguise = DisguiseParser.parseDisguise(
                            (CommandSender) e.getPlayer(),
                            (Entity) e.getPlayer(),
                            "disguise",
                            DisguiseUtilities.split(
                                    "Falling_Block " +
                                            e.getClickedBlock().getType().toString() +
                                            " setNotifyBar NONE"
                            ),
                            new DisguisePermissions(Bukkit.getConsoleSender(), "disguise")
                    );
                    disguise.setEntity(e.getPlayer());
                    disguise.startDisguise();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    }
}
