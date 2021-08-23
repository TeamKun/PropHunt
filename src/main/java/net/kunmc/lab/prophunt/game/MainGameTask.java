package net.kunmc.lab.prophunt.game;

import net.kunmc.lab.prophunt.Kei;
import org.bukkit.Bukkit;
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
    int time = Integer.MAX_VALUE;
    int waittime = Integer.MAX_VALUE;
    int waittimemax = Integer.MAX_VALUE;
    int cache = 0;
    Team team_hunter;
    Team team_seeker;
    List<UUID> hunter_player_cache;
    List<UUID> hunter_player;

    BossBar mainBossbar;

    int gameStatus;

    public MainGameTask(int time, HunterSelector selector, String hunter_args){
        this.time = time;
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

        this.mainBossbar = Bukkit.createBossBar("Loading", BarColor.WHITE, BarStyle.SOLID);
        mainBossbar.setVisible(true);
    }



    @Override
    public void run() {
        barrefresh();
        if(gameStatus == 0){
            if(waittime <= 0){
                gameStatus = 1;
            } else {
                mainBossbar.setTitle("ゲーム開始まであと " + waittime + "秒");
                mainBossbar.setProgress((double) waittime / waittimemax);
                waittime--;
            }
        } else if(gameStatus == 1){
            Kei.bc("鬼がプレイヤーを攻撃できるようになりました。");
        }
    }

    void barrefresh(){
        for (Player p : Bukkit.getOnlinePlayers()) {
            if(!mainBossbar.getPlayers().contains(p)) {
                mainBossbar.addPlayer(p);
            }
        }
    }
}
