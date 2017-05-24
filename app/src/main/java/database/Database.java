package database;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import vendetta.androidbenchmark.MainActivity;
import vendetta.androidbenchmark.ScoreActivity;

/**
 * Created by Vendetta on 06-May-17.
 */

public class Database {
    private static final String TAG = "Database";
    private static String uid = null;
    protected static FirebaseAuth mAuth = null;
    private static FirebaseDatabase database = null;
    private static DatabaseReference databaseUserScoreRef;
    private static FirebaseAuth.AuthStateListener mAuthListener;
    private static UserScores dbUserScores = new UserScores(true);
    private static Context mainActivityContext;
    private static HashMap<String, String> results;


    public static void establishConnection(Context context) {
        if (mAuth == null) {
            initialize(context);
        } else {
            mainActivityContext = context;
            updateUserScores();
        }
    }

    private static void initialize(Context context) {
        mainActivityContext = context;
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    uid = user.getUid();
                    Log.d(TAG, uid + "connected");
                }
                // User is signed in
                if (uid != null) {
                    Log.d(TAG, "User " + uid + " is logged in!");
                    if (database == null) {
                        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                        database = FirebaseDatabase.getInstance();
                    }
                    updateUserScores();
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + uid);
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    mAuth.signInAnonymously();
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }

    private static void updateUserScores() {
        databaseUserScoreRef = database.getReference().child("users").child(uid);
        databaseUserScoreRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dbUserScores.updateAll((HashMap<String, String>) dataSnapshot.getValue());
                MainActivity.updateScores(dbUserScores, mainActivityContext);
                Log.d(TAG, "UserScores read from DB");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public static void postBenchScore(Score score) {
        score.setUid(uid);
        database.getReference().child("benchmarks").child(score.getBenchName()).child(uid).setValue(score);
        databaseUserScoreRef.updateChildren(score.toMap());
        Log.d("DB ", "posted " + score.toString());
    }

    public static void getBenchScore(String benchmarkName) {
        database.getReference().child("benchmarks").child(benchmarkName).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ScoreActivity.updateResult(dataSnapshot.getValue(Score.class));
                Log.d(TAG, "BenchmarkScore read from DB");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d(TAG, "Failed to read value at benchmarkScore.", error.toException());
            }
        });
    }

    public static void getRankings(String benchmarkName, final Context rankContext) {
        results = new HashMap<>();
        database.getReference().child("benchmarks").child(benchmarkName).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Score tempScore = data.getValue(Score.class);
                    if (results.get(tempScore.getDevice()) == null) {
                        results.put(tempScore.getDevice(), tempScore.getResult());
                    }
                    if (results.get(tempScore.getDevice()).compareTo(tempScore.getResult()) < 0)
                        results.put(tempScore.getDevice(), tempScore.getResult());
                }
                ScoreActivity.updateRanking(results, rankContext);
                Log.d(TAG, "Rankings read from DB");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d(TAG, "Failed to read value at rankings.", error.toException());
            }
        });

    }


}
