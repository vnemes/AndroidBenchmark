package database;

import java.util.HashMap;

import benchmark.Benchmarks;

/**
 * Created by Vendetta on 20-May-17.
 */

public class UserScores {
    private HashMap<String,String> scoreMap;

    public UserScores(){
        scoreMap = new HashMap<>();
        for (Benchmarks bench: Benchmarks.values()){
            scoreMap.put(bench.toString(),"Tap to run!");
        }
    }

    public void updateAll(UserScores dbUserScores){
        scoreMap.putAll(dbUserScores.scoreMap);
    }

    public  HashMap<String, String> getScoreMap() {
        return this.scoreMap;
    }

    public void setScoreMap(HashMap<String, String> scoreMap) {
        this.scoreMap = scoreMap;
    }
}
