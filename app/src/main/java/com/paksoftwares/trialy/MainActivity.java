package com.paksoftwares.trialy;

import static io.trialy.library.Constants.STATUS_TRIAL_JUST_ENDED;
import static io.trialy.library.Constants.STATUS_TRIAL_JUST_STARTED;
import static io.trialy.library.Constants.STATUS_TRIAL_NOT_YET_STARTED;
import static io.trialy.library.Constants.STATUS_TRIAL_OVER;
import static io.trialy.library.Constants.STATUS_TRIAL_RUNNING;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import io.trialy.library.Trialy;
import io.trialy.library.TrialyCallback;

public class MainActivity extends AppCompatActivity {
    Trialy mTrialy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         mTrialy = new Trialy(this, "IMTAWVQYSR0TK21NFPL");
        mTrialy.clearLocalCache("sample2024_test");
        mTrialy.checkTrial("sample2024_test", mTrialyCallback);

    }

    private TrialyCallback mTrialyCallback = new TrialyCallback() {
        @Override
        public void onResult(int status, long timeRemaining, String sku) {
            switch (status){
                case STATUS_TRIAL_JUST_STARTED:
                    Toast.makeText(MainActivity.this,"STATUS_TRIAL_JUST_STARTED",Toast.LENGTH_LONG).show();
                    //The trial has just started - enable the premium features for the user
                    break;
                case STATUS_TRIAL_RUNNING:
                    Toast.makeText(MainActivity.this,"STATUS_TRIAL_RUNNING",Toast.LENGTH_LONG).show();
                    //The trial is currently running - enable the premium features for the user
                    break;
                case STATUS_TRIAL_JUST_ENDED:
                    Toast.makeText(MainActivity.this,"STATUS_TRIAL_JUST_ENDED",Toast.LENGTH_LONG).show();
                    //The trial has just ended - block access to the premium features
                    break;
                case STATUS_TRIAL_NOT_YET_STARTED:
                    mTrialy.startTrial("sample2024_test", mTrialyCallback);
                    Toast.makeText(MainActivity.this,"STATUS_TRIAL_NOT_YET_STARTED",Toast.LENGTH_LONG).show();
                    //The user hasn't requested a trial yet - no need to do anything
                    break;
                case STATUS_TRIAL_OVER:
                   // mTrialy.startTrial("rashid", mTrialyCallback);

                    Toast.makeText(MainActivity.this,"STATUS_TRIAL_OVER",Toast.LENGTH_LONG).show();
                    //The trial is over
                    MainActivity.this.finish();
                    break;
                default:
                    Toast.makeText(MainActivity.this,Trialy.getStatusMessage(status),Toast.LENGTH_LONG).show();

                    break;
            }
            Log.i("TRIALY", "Returned status: " + Trialy.getStatusMessage(status));
        }

    };
}