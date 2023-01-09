package net.mov51.aspenhardcore.util;

import java.util.ArrayList;

public class PlayPeriodForDay {
    private ArrayList<PlayPeriod> playPeriods = new ArrayList<>();
    public PlayPeriodForDay(PlayPeriod playPeriod){
        this.playPeriods.add(playPeriod);
    }
    public void addPlayPeriod(PlayPeriod playPeriod){
        this.playPeriods.add(playPeriod);
    }
    public void clearPlayPeriods(){
        this.playPeriods.clear();
    }
    public int sumOfTimePlayed(){
        long timePlayed = 0;
        for(PlayPeriod playPeriod : this.playPeriods){
            timePlayed += playPeriod.getTimePlayed();
        }
        return (int) timePlayed;
    }
}
