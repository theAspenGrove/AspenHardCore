package net.mov51.aspenhardcore.util;


import java.util.UUID;

public class PlayPeriod {
    private final long joinedTime;
    private final long leftTime;
    private UUID UUID;
    public PlayPeriod(long joinedTime, long leftTime){
        this.joinedTime = joinedTime;
        this.leftTime = leftTime;
    }
    public PlayPeriod(long joinedTime, long leftTime, UUID UUID){
        this(joinedTime,leftTime);
        this.UUID = UUID;
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
    public UUID getUUID(){
        return UUID;
    }
}
