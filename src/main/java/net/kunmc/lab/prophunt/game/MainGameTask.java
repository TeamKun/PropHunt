package net.kunmc.lab.prophunt.game;

import net.kunmc.lab.prophunt.Kei;
import net.kunmc.lab.prophunt.ScoreboardUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainGameTask extends BukkitRunnable {

    int time, timemax, waittime, waittimemax, gameStatus, cache;
    Team team_hunter, team_seeker;

    String[] teamname = new String[]{
            "hunter",
            "seeker"
    };

    Location hunter_spawn;

    public MainGameTask(int time){
        this.time = time;
        this.timemax = time;
        this.gameStatus = 0;
        this.waittime = 30; // 始まるまでの時間
        this.waittimemax = 30; // 始まるまでの時間のキャッシュ

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getMainScoreboard();
        for(Team t : board.getTeams()){
            if(t.getName().equals(teamname[0])) {
                team_hunter = t;
            } else if(t.getName().equals(teamname[1])){
                team_seeker = t;
            }
        }

        if(team_hunter != null) {
            Kei.bc("ハンターチームが存在していません。");
            this.cancel();
        } else if(team_seeker != null) {
            Kei.bc("シーカーチームが存在していません。");
            this.cancel();
        }

    }



    @Override
    public void run() {
        if(gameStatus == 0){

            if(hunter_spawn != null) {
                for(OfflinePlayer p : team_hunter.getPlayers()){
                    if(p.isOnline()){
                        if(hunter_spawn != null) ((Player) p).teleport(hunter_spawn);
                        ((Player) p).addPotionEffect(Kei.infPot.blind);
                        ((Player) p).addPotionEffect(Kei.infPot.invisible);
                    }
                }
            }

            if(waittime <= 0){
                gameStatus = 1;
            } else {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    ScoreboardUtil.unrankedSidebarDisplay(p, new String[]{
                            p.getName(),
                            ChatColor.GREEN+"",
                            ""
                    });
                }
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

    String getTeam(Player p){
        for(OfflinePlayer p2 : team_hunter.getPlayers()){
            if(p2.getUniqueId() == p.getUniqueId()) return "ハンター";
        }
        for(OfflinePlayer p2 : team_seeker.getPlayers()){
            if(p2.getUniqueId() == p.getUniqueId()) return "シーカー";
        }
        return null;
    }
}
