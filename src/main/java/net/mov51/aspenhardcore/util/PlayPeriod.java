package net.mov51.aspenhardcore.util;

import org.bukkit.entity.Player;

public class PlayPeriod {
    private final long joinedTime;
    private final long leftTime;
    public PlayPeriod(long joinedTime, long leftTime){
        this.joinedTime = joinedTime;
        this.leftTime = leftTime;
    }
    public long getJoinedTime(){
        return joinedTime;
    }
    public long getLeftTime(){
        return leftTime;
    }
    public long getTimePlayed(){
        return leftTime - joinedTime;
    }
}
