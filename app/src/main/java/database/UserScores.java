package database;

import java.util.HashMap;

import benchmark.Benchmarks;

/**
 * Created by Vendetta on 20-May-17.
 */

public class UserScores {
    private HashMap<String,String> scoreMap;

    public UserScores(){ }

    public UserScores(boolean x){
        scoreMap = new HashMap<>();
        for (Benchmarks bench: Benchmarks.values()){
            scoreMap.put(bench.toString(),"Tap to run!");
        }
    }

    public UserScores(HashMap<String,String> paramMap){
        for (Benchmarks bench: Benchmarks.values()){
            scoreMap.put(bench.toString(),"Tap to run!");
        }
        scoreMap.putAll(paramMap);

    }

    public void updateAll(UserScores dbUserScores){
        scoreMap.putAll(dbUserScores.scoreMap);
    }

    public void updateAll(HashMap<String,String> dbUserScores){
        scoreMap.putAll(dbUserScores);
    }

    public HashMap<String, String> getScoreMap() {
        return this.scoreMap;
    }

    public void setScoreMap(HashMap<String, String> scoreMap) {
        this.scoreMap = scoreMap;
    }
}
