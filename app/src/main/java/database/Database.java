package database;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Vendetta on 06-May-17.
 */

public class Database {
    public ProgressDialog mProgressDialog;
    private static final String TAG = "Auth";
    private static String uid;
    protected FirebaseAuth mAuth;
    private static FirebaseDatabase database;
    private static DatabaseReference databaseRef;
    private static ValueEventListener databaseListener;

    private static FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                uid = user.getUid();
            }
            // User is signed in
            //TODO: Remove mail access, replace with anon auth
            if (uid != null) {
                Log.d("debug","User is logged in!");
                database = FirebaseDatabase.getInstance();
                databaseRef = database.getReference().child("users").child(uid);
                databaseListener = databaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.

                        //DBWeek = dataSnapshot.getValue(Week.class);
                        //JsonWeek = JsonHandler.getFileContent();
                        Log.d("DB", "Value read from DB");
//                            if (JsonWeek != null){
//                                Log.d("debug", "Json not null");
//                                if (JsonWeek.getData().isEmpty()) {
//                                    Log.d("debug", "Json is empty");
//                                    if (!isEmpty()) {
//                                        Log.d("debug", "Email week not null");
//                                        JsonHandler.postFileContent(DBWeek);
//                                    }
//                                } else{
//                                    Log.d("debug", "Json not empty, wait for user save");
//                                    //updateWeek(JsonWeek);
//                                    DBWeek = JsonWeek;
//                                }
//                            }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w("DB:", "Failed to read value.", error.toException());


                    }
                });
                Log.d(TAG, "onAuthStateChanged:signed_in:" + uid);
            } else {
                Log.d(TAG, "onAuthStateChanged:signed_out");
                //TODO: Anonymously authenticate the user here!
            }
        }
    };


}
