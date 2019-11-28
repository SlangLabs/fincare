package in.bajajfinserv.experia;

import android.app.Application;

/**
 * TODO: Add a class header comment!
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize Slang
        SlangInterface.init(this);
    }
}
