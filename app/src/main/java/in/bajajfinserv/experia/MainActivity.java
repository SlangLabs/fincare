package in.bajajfinserv.experia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.slanglabs.platform.SlangBuddy;

/**
 * An example activity that shows the Slang trigger
 */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Show Slang in this activity 
        // Note: Will be shown in all following activities unless explicilty hidden
        SlangBuddy.getBuiltinUI().show(this);
    }
}
