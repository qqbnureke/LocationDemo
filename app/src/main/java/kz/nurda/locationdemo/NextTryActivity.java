package kz.nurda.locationdemo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class NextTryActivity extends AppCompatActivity {
    private static final String TAG = "NextTryActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_try);
        startService(new Intent(NextTryActivity.this, LocationTracker.class));

    }

}
