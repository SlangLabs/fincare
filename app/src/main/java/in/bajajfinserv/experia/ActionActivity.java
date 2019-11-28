/*
 * // (c) 2017 Copyright, Slang Labs Private Limited. All rights reserved.
 */

package in.bajajfinserv.experia;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import in.slanglabs.platform.SlangBuddy;

/**
 * TODO: Add a class header comment!
 */
public class ActionActivity extends AppCompatActivity {
    public static final String ACTION = "action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        performAction();
    }

    void performAction() {
        ImageView view = findViewById(R.id.main_image);
        Intent i = getIntent();
        String imageToShow = i.getStringExtra(ACTION);

        view.setImageResource(actions.get(CommandType.valueOf(imageToShow)));
    }

    public enum CommandType {
        MAIN,
        SOA,
        NOTIF,
        OFFERS,
        EMI_FAQ,
        EMI_PIN,
        EMI_DETAILS,
        INTEREST_CERT,
        REPAYMENT_SCHEDULE
    }

    public Map<CommandType, Integer> actions = new HashMap<CommandType, Integer>() {{
        put(CommandType.MAIN, R.drawable.bajaj_main);
        put(CommandType.SOA, R.drawable.bajaj_soa_full);
        put(CommandType.NOTIF, R.drawable.bajaj_notif);
        put(CommandType.OFFERS, R.drawable.bajaj_offers);
        put(CommandType.EMI_DETAILS, R.drawable.bajaj_emi1);
        put(CommandType.EMI_FAQ, R.drawable.bajaj_emi_faq);
        put(CommandType.EMI_PIN, R.drawable.bajaj_emi_change);
        put(CommandType.INTEREST_CERT, R.drawable.bajaj_interest_full);
        put(CommandType.REPAYMENT_SCHEDULE, R.drawable.bajaj_repayment_full);
    }};
}
