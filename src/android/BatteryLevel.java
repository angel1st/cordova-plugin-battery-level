package hk.polaris.cordova.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;

import android.os.BatteryManager;
import android.content.Intent;
import android.content.IntentFilter;


public class BatteryLevel extends CordovaPlugin {

	public static final String GET_BATTERY_LEVEL = "getBatteryLevel";
	public static final String IS_PLUGGED_IN = "isPluggedIn";
	
	private CallbackContext mCallbackContext;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) {
        
		if (action.equals(GET_BATTERY_LEVEL)) {
			mCallbackContext = callbackContext;
            getBatteryPercentage();

			return true;
        }
		
		else {
            callbackContext.error("Invalid Action: " + action);
            return false;
        }
    }

	private void getBatteryPercentage() {
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
}