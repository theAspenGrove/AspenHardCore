package net.mov51.aspenhardcore.util;

import org.bukkit.entity.Player;

public class PlayPeriod {
    private final long joinedTime;
    private final long leftTime;
    private final Player p;
    public PlayPeriod(long joinedTime, long leftTime, Player p){
        this.joinedTime = joinedTime;
        this.leftTime = leftTime;
        this.p = p;
    }
    public long getJoinedTime(){
        return joinedTime;
    }
    public Player getPlayer(){
        return p;
    }
    public long getLeftTime(){
        return leftTime;
    }
    public long getTimePlayed(){
        return leftTime - joinedTime;
    }
}
