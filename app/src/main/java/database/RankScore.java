package database;

/**
 * Created by Vendetta on 23-May-17.
 */

public class RankScore {
    private String device;
    private String score;

    public RankScore(String d, String s){
        device = d;
        score = s;
    }

    public String getDevice() {
        return device;
    }

    public String getScore() {
        return score;
    }
}
