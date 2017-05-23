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
import java.util.Map;

import vendetta.androidbenchmark.MainActivity;
import vendetta.androidbenchmark.ScoreActivity;

/**
 * Created by Vendetta on 06-May-17.
 */

public class Database {
    public ProgressDialog mProgressDialog;
    private static final String TAG = "DB ";
    private static String uid = null;
    protected static FirebaseAuth mAuth = null;
    private static FirebaseDatabase database = null;
    private static DatabaseReference databaseUserScoreRef;
    private static ValueEventListener databaseListener;
    private static FirebaseAuth.AuthStateListener mAuthListener;
    private static UserScores dbUserScores = new UserScores(true);
    private static Context mainActivityContext;


    public static void establishConnection(Context context) {
        if (mAuth == null) {
            initialize(context);
        }
    }

    private static void initialize(Context context) {
        //initialize thingies here.
        mainActivityContext = context;
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    uid = user.getUid();
                    Log.d(TAG, uid +"connected");
                }
                // User is signed in
                if (uid != null) {
                    Log.d(TAG, "User " + uid + " is logged in!");
                    if (database == null) {
                        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                        database = FirebaseDatabase.getInstance();
                    }
                    databaseUserScoreRef = database.getReference().child("users").child(uid);
                    databaseListener = databaseUserScoreRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.

                            dbUserScores.updateAll((HashMap<String,String>) dataSnapshot.getValue());
//                            UserScores dbUserScoresData = dataSnapshot.getValue(UserScores.class);
//                            if (dbUserScoresData != null)
//                                dbUserScores.updateAll(dbUserScoresData);
                            MainActivity.updateScores(dbUserScores,mainActivityContext);
                            Log.d(TAG, "UserScores read from DB");
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + uid);
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    mAuth.signInAnonymously();
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }

    public static void postBenchScore(Score score){
        score.setUid(uid);
        database.getReference().child("benchmarks").child(score.getBenchName()).child(uid).setValue(score);
        databaseUserScoreRef.updateChildren(score.toMap());
        Log.d("DB ","posted "+score.toString());
    }

    public static void getBenchScore(String benchmarkName){
        database.getReference().child("benchmarks").child(benchmarkName).child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ScoreActivity.updateResult(dataSnapshot.getValue(Score.class));
                Log.d(TAG, "BenchmarkScore read from DB");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.d(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    public static void getRankings(String benchmarName){
        //TODO implement database query to get a sorted list of RankScore
        //then call ScoreActivity.updateRanking(scoreList)
    }



}
