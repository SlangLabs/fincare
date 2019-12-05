package in.bajajfinserv.experia;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import in.slanglabs.platform.SlangBuddy;
import in.slanglabs.platform.SlangBuddyOptions;
import in.slanglabs.platform.SlangEntity;
import in.slanglabs.platform.SlangIntent;
import in.slanglabs.platform.SlangLocale;
import in.slanglabs.platform.SlangSession;
import in.slanglabs.platform.action.SlangAction.Status;
import in.slanglabs.platform.action.SlangMultiStepIntentAction;
import in.slanglabs.platform.action.SlangUtteranceAction;

/**
 * A utility class for initializing and managing integration with Slang,
 * where all Slang specific code resides.
 *
 * For more examples and details about how to integrate Slang with your app,
 * please refer to the documentation at:
 *     https://docs.slanglabs.in/slang/developer-guide/sdk-integration/android
 *
 * To find more details about the APIs referenced in the following code,
 * please see the API reference at:
 *     https://slanglabs.in/docs/slang/api/android/
 *
 * To initialize Slang in your application, call:
 *     SlangInterface.init(context)
 * from your onCreate method of your Application class.
 *
 * In the activity from where you want Slang to show up (the mic icon), call
 *     SlangBuddy.getBuiltinUI().show(this)
 * from the onResume method of that activity
 */
public class SlangInterface {
    private static String sAppId = "fill_app_id";   // "8f5d99457b7c4267a2518079d6d8aa5d";
    private static String sApiKey = "fill_api_key"; // "0889ab1a2c014638b6ef9ca6188c2224";
    private static Application sApplication;

    /****************************************************************
     * App specific logic goes here
     ***************************************************************/

    private static void performAction(final ActionActivity.CommandType type) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(sApplication, ActionActivity.class);

                i.putExtra(ActionActivity.ACTION, type.toString());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                sApplication.startActivity(i);
            }
        },
            0
        );
    }

    /*
     * Action callback functions for intents
     */

    /**
     * Action for emi_faq intent 
     */
    private static Status doEmiFaq(SlangSession slangSession) {
        performAction(ActionActivity.CommandType.EMI_FAQ);
//        Toast.makeText(sApplication, "Recognized emi_faq intent", Toast.LENGTH_LONG).show();  // TODO: FIXME: implement it
        return Status.SUCCESS;
    }

    /**
     * Action for notifications intent 
     */
    private static Status doNotifications(SlangSession slangSession) {
        performAction(ActionActivity.CommandType.NOTIF);
//        Toast.makeText(sApplication, "Recognized notifications intent", Toast.LENGTH_LONG).show();  // TODO: FIXME: implement it
        return Status.SUCCESS;
    }

    /**
     * Action for emi_pin_change intent 
     */
    private static Status doEmiPinChange(SlangSession slangSession, Integer emiId) {
        performAction(ActionActivity.CommandType.EMI_PIN);
//        Toast.makeText(sApplication, "Recognized emi_pin_change intent", Toast.LENGTH_LONG).show();  // TODO: FIXME: implement it
        return Status.SUCCESS;
    }

    /**
     * Action for offers intent 
     */
    private static Status doOffers(SlangSession slangSession) {
        performAction(ActionActivity.CommandType.OFFERS);
//        Toast.makeText(sApplication, "Recognized offers intent", Toast.LENGTH_LONG).show();  // TODO: FIXME: implement it
        return Status.SUCCESS;
    }

    /**
     * Action for emi_details intent 
     */
    private static Status doEmiDetails(SlangSession slangSession, Integer emiId) {
        performAction(ActionActivity.CommandType.EMI_DETAILS);
//        Toast.makeText(sApplication, "Recognized emi_details intent", Toast.LENGTH_LONG).show();  // TODO: FIXME: implement it
        return Status.SUCCESS;
    }

    /**
     * Action for show_statement intent 
     */
    private static Status doShowStatement(SlangSession slangSession, Integer loanId, String statementType) {
        switch (statementType) {
            case "SOA":
                performAction(ActionActivity.CommandType.SOA);
                break;

            case "InterestCert":
                performAction(ActionActivity.CommandType.INTEREST_CERT);
                break;

            case "PaymentSchedule":
                performAction(ActionActivity.CommandType.REPAYMENT_SCHEDULE);
                break;

            default:
                Toast.makeText(sApplication, "Recognized show_statement intent", Toast.LENGTH_LONG).show();  // TODO: FIXME: implement it
                break;
        }

        return Status.SUCCESS;
    }

    /****************************************************************
     * Standard boiler plate goes here.
     ***************************************************************/

    /*
     * Intents and Entities defined in Slang Console
     */

    private static String mLastSeenUtterance = null;

    private static final String EMI_FAQ = "emi_faq";

    private static final String NOTIFICATIONS = "notifications";

    private static final String EMI_PIN_CHANGE = "emi_pin_change";
    private static final String EMI_PIN_CHANGE_EMIID = "emiId";

    private static final String OFFERS = "offers";

    private static final String EMI_DETAILS = "emi_details";
    private static final String EMI_DETAILS_EMIID = "cardId";

    private static final String SHOW_STATEMENT = "show_statement";
    private static final String SHOW_STATEMENT_LOANID = "loanId";
    private static final String SHOW_STATEMENT_STATEMENTTYPE = "statementType";

    public static void init(Application application) {
        sApplication = application;

        try {
            SlangBuddyOptions options = new SlangBuddyOptions.Builder()
                .setApplication(sApplication)
                .setBuddyId(sAppId)
                .setAPIKey(sApiKey)
                .setListener(new BuddyListener())
                .setUtteranceAction(new SlangUtteranceHandler())
                .setIntentAction(new IntentActionHandler())
                .setRequestedLocales(getAppLocales())
                .setRequestedLocales(SlangLocale.getSupportedLocales())
                .setDefaultLocale(SlangLocale.LOCALE_ENGLISH_IN)
                // change env to production when the buddy is published to production
                .setEnvironment(SlangBuddy.Environment.STAGING)
                .build();
            SlangBuddy.initialize(options);
        } catch (SlangBuddyOptions.InvalidOptionException e) {
            e.printStackTrace();
        } catch (SlangBuddy.InsufficientPrivilegeException e) {
            e.printStackTrace();
        }
    }

    // Return the required locales
    private static Set<Locale> getAppLocales() {
        HashSet<Locale> locales = new HashSet<>(2);

        locales.add(SlangLocale.LOCALE_ENGLISH_IN);
        locales.add(SlangLocale.LOCALE_HINDI_IN);
        return locales;
    }

    private static class SlangUtteranceHandler implements SlangUtteranceAction {
        @Override
        public void onUtteranceDetected(String s, SlangSession slangSession) {
            // Not handled
        }

        @Override
        public Status onUtteranceUnresolved(String s, SlangSession slangSession) {
            return Status.FAILURE;
        }
    }

    private static class BuddyListener implements SlangBuddy.Listener {
        @Override
        public void onInitialized() {
            Log.d("BuddyListener", "Slang Initialised Successfully");

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(sApplication, "Slang Initialised", Toast.LENGTH_LONG).show();
                }
            }, 10);
        }

        @Override
        public void onInitializationFailed(final SlangBuddy.InitializationError e) {
            Log.d("BuddyListener", "Slang failed:" + e.getMessage());

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(sApplication, "Failed to initialise Slang:" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }, 10);
        }

        @Override
        public void onLocaleChanged(final Locale newLocale) {
            Log.d("BuddyListener", "Locale Changed:" + newLocale.getDisplayName());

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(sApplication, "Locale Changed:" + newLocale.getDisplayName(), Toast.LENGTH_LONG).show();
                }
            }, 10);
        }

        @Override
        public void onLocaleChangeFailed(final Locale newLocale, final SlangBuddy.LocaleChangeError e) {
            Log.d("BuddyListener",
                "Locale(" + newLocale.getDisplayName() + ") Change Failed:" + e.getMessage());

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(sApplication,
                        "Locale(" + newLocale.getDisplayName() + ") Change Failed:" + e.getMessage(),
                        Toast.LENGTH_LONG).show();
                }
            }, 10);
        }
    }

    private static class IntentActionHandler implements SlangMultiStepIntentAction {
        @Override
        public Status action(SlangIntent slangIntent, SlangSession slangSession) {
            // Insert the handler for the intents here
            switch (slangIntent.getName()) {
                case EMI_FAQ:
                    return doEmiFaq(slangSession);

                case NOTIFICATIONS:
                    return doNotifications(slangSession);

                case EMI_PIN_CHANGE:
                    Float emiPinChangeEmiid =
                        slangIntent.getEntity(EMI_PIN_CHANGE_EMIID) != null &&
                        slangIntent.getEntity(EMI_PIN_CHANGE_EMIID).isResolved() ?
                            Float.parseFloat(slangIntent.getEntity(EMI_PIN_CHANGE_EMIID).getValue())
                            : null;

                    return doEmiPinChange(
                        slangSession,
                        emiPinChangeEmiid != null ? emiPinChangeEmiid.intValue() : null
                    );

                case OFFERS:
                    return doOffers(slangSession);

                case EMI_DETAILS:
                    Float emiDetailsEmiid =
                        slangIntent.getEntity(EMI_DETAILS_EMIID) != null &&
                        slangIntent.getEntity(EMI_DETAILS_EMIID).isResolved() ?
                            Float.parseFloat(slangIntent.getEntity(EMI_DETAILS_EMIID).getValue())
                            : null;

                    return doEmiDetails(
                        slangSession,
                        emiDetailsEmiid != null ? emiDetailsEmiid.intValue() : null
                    );

                case SHOW_STATEMENT:
                    Float showStatementLoanid =
                        slangIntent.getEntity(SHOW_STATEMENT_LOANID) != null &&
                        slangIntent.getEntity(SHOW_STATEMENT_LOANID).isResolved() ?
                            Float.parseFloat(slangIntent.getEntity(SHOW_STATEMENT_LOANID).getValue())
                            : null;

                    String showStatementStatementtype =
                        slangIntent.getEntity(SHOW_STATEMENT_STATEMENTTYPE) != null &&
                        slangIntent.getEntity(SHOW_STATEMENT_STATEMENTTYPE).isResolved() ?
                            slangIntent.getEntity(SHOW_STATEMENT_STATEMENTTYPE).getValue()
                            : null;

                    return doShowStatement(
                        slangSession,
                        showStatementLoanid != null ? showStatementLoanid.intValue() : null,
                        showStatementStatementtype
                    );

                default:
                    Log.w("IntentActionHandler", "Unexpected intent: " + slangIntent.getName());
                    return Status.SUCCESS;
            }
        }

        @Override
        public void onIntentResolutionBegin(SlangIntent slangIntent, SlangSession slangSession) {
            return;
        }

        @Override
        public Status onEntityUnresolved(SlangEntity slangEntity, SlangSession slangSession) {
            return Status.SUCCESS;
        }

        @Override
        public Status onEntityResolved(SlangEntity slangEntity, SlangSession slangSession) {
            return Status.SUCCESS;
        }

        @Override
        public void onIntentResolutionEnd(SlangIntent slangIntent, SlangSession slangSession) {
            return;
        }
    }
}
