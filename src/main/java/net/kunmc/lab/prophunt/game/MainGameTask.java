package net.kunmc.lab.prophunt.game;

import net.kunmc.lab.prophunt.Kei;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainGameTask extends BukkitRunnable {

    private HunterSelector selector;
    int time, timemax, waittime, waittimemax, gameStatus, cache;
    Team team_hunter, team_seeker;
    List<UUID> hunter_player_cache, hunter_player;

    Location hunter_spawn;

    public MainGameTask(int time, HunterSelector selector, String hunter_args){
        this.time = time;
        this.timemax = time;
        this.gameStatus = 0;
        this.waittime = 30; // 始まるまでの時間
        this.waittimemax = 30; // 始まるまでの時間のキャッシュ
        this.hunter_player_cache = new ArrayList<>();
        for(String str : hunter_args.split(":")){
            hunter_player_cache.add(UUID.fromString(str));
        }
        this.hunter_player = new ArrayList<>();
        this.selector = selector;

        if(selector == HunterSelector.SELECT){
            hunter_player.addAll(hunter_player_cache);
        }

    }



    @Override
    public void run() {
        if(gameStatus == 0){

            if(hunter_spawn != null) {
                for(OfflinePlayer p : team_hunter.getPlayers()){
                    if(p.isOnline()){
                        ((Player) p).teleport(hunter_spawn);
                        ((Player) p).addPotionEffect(Kei.infPot.blind);
                        ((Player) p).addPotionEffect(Kei.infPot.invisible);
                    }
                }
            }

            if(waittime <= 0){
                gameStatus = 1;
            } else {
                // TODO: add scoreboard
            }
        } else if(gameStatus == 1){
            Kei.bc("鬼がプレイヤーを攻撃できるようになりました。");
            for(OfflinePlayer p : team_hunter.getPlayers()){
                if(p.isOnline()){
                    ((Player) p).removePotionEffect(Kei.infPot.blind.getType());
                    ((Player) p).removePotionEffect(Kei.infPot.invisible.getType());
                }
            }
            gameStatus = 2;
        } else if(gameStatus == 2){
            List<OfflinePlayer> players = new ArrayList<>();
            List<OfflinePlayer> hunter = new ArrayList<>();
            for(OfflinePlayer p : team_seeker.getPlayers()){
                if(p.isOnline()){
                    players.add(p);
                }
            }

            for(OfflinePlayer p : team_hunter.getPlayers()){
                if(p.isOnline()){
                    hunter.add(p);
                }
            }

            if(players.isEmpty()){
                Kei.bc("ハンターが全員を捕まえました。");
                gameStatus = 3;
            } else {
                int alive = players.size();
                int hunters = hunter.size();
            }
        }
    }

    void Refresh(){
        for (Player p : Bukkit.getOnlinePlayers()) {

        }
    }
}
