package net.sensortronic.cordova.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.LOG;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.BatteryManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;


public class BatteryStatus extends CordovaPlugin {

	public static final String GET_BATTERY_LEVEL = "getBatteryLevel";
	public static final String GET_BATTERY_STATUS = "getBatteryStatus";
	public static final String IS_PLUGGED_IN = "isPluggedIn";
	private static final String LOG_TAG = "BatteryManager";
	
	private CallbackContext mCallbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        
		if (action.equals(GET_BATTERY_LEVEL)) {
			mCallbackContext = callbackContext;
            getBatteryLevel();

			return true;
        }
		
		else if(action.equals(IS_PLUGGED_IN)) {
			mCallbackContext = callbackContext;
			isPuggedIn();

			return true;
		}

		else if(action.equals(GET_BATTERY_STATUS)) {
			mCallbackContext = callbackContext;
			getBatteryStatus();

			return true;
		}
		
		else {
            callbackContext.error("Invalid Action: " + action);
            return false;
        }
    }

	private void getBatteryLevel() {
		Intent batteryIntent = cordova.getActivity().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		int currentLevel = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
		int level = -1;

		// Error checking that probably isn't needed but I added just in case.
		if (currentLevel >= 0 && scale > 0) {
			level = (currentLevel * 100) / scale;
		}

		mCallbackContext.success(level + "%");
	}
	
	private void isPuggedIn() {
		Intent batteryIntent = cordova.getActivity().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		int plugged = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		
		boolean isPlugged = (plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB);
		
		if (VERSION.SDK_INT > VERSION_CODES.JELLY_BEAN) {
			isPlugged = (isPlugged || plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS);
		}

		mCallbackContext.success(String.valueOf(isPlugged));
	}

	/**
     * Creates a JSONObject with the current battery information
     *
     * @param batteryIntent the current battery information
     * @return a JSONObject containing the battery status information
     */
    private void getBatteryStatus() {
        JSONObject obj = new JSONObject();
		// get level
		Intent batteryIntentLevel = cordova.getActivity().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		int currentLevel = batteryIntentLevel.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryIntentLevel.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
		int level = -1;

		// Error checking that probably isn't needed but I added just in case.
		if (currentLevel >= 0 && scale > 0) {
			level = (currentLevel * 100) / scale;
		}

		// get isPlugged
		Intent batteryIntentPlugged = cordova.getActivity().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
		int plugged = batteryIntentPlugged.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
		
		boolean isPlugged = (plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB);
		
		if (VERSION.SDK_INT > VERSION_CODES.JELLY_BEAN) {
			isPlugged = (isPlugged || plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS);
		}

        try {
            obj.put("level", level + "%");
            obj.put("isPlugged", String.valueOf(isPlugged));
        } catch (JSONException e) {
            LOG.e(LOG_TAG, e.getMessage(), e);
        }
        mCallbackContext.success(obj);
    }
}