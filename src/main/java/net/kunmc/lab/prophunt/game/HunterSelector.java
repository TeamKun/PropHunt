package net.kunmc.lab.prophunt.game;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public enum HunterSelector {

    RANDOM,SELECT,RANDOM_SELECT;

    String argsGenerator(HunterSelector selector, String bukkit_selector){
        if(selector == SELECT || selector == RANDOM_SELECT){
            List<Entity> entityList = Bukkit.selectEntities(Bukkit.getConsoleSender(), bukkit_selector);
            entityList.removeIf(entity -> !(entity instanceof Player));
            StringBuffer buffer = new StringBuffer();
            for(Entity p : entityList){
                if(p instanceof Player){
                    buffer.append(p.getUniqueId()).append(":");
                }
            }
            return buffer.toString();
        } else {
            return null;
        }
    }

}
