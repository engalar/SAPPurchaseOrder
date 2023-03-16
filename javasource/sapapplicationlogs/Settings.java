package sapapplicationlogs;

import com.mendix.core.Core;
import com.mendix.logging.ILogNode;
import com.mendix.thirdparty.org.json.JSONException;
import com.mendix.thirdparty.org.json.JSONObject;

import sapapplicationlogs.proxies.constants.Constants;

public class Settings {
	private static final String VCAP = "VCAP_SERVICES";
	static ILogNode logger = Core.getLogger(Constants.getLogNode());

	
	public static boolean isServiceAvailable() {
		final String vcapString = System.getenv(VCAP);
		// no VCAP present
		if (vcapString == null) {
			logger.debug("No VCAP is present.");
			return false;
		}
		
		try {		
			// try to parse VCAP
			final JSONObject vcap = new JSONObject(vcapString);
			// try to get logging service from the VCAP
			final JSONObject loggingService = vcap.getJSONArray(Constants.getApplicationLoggingService()).getJSONObject(0);
			if (loggingService  != null) {
				return true;
			}
			logger.debug(String.format("No application logging service with name %s is present.\n", Constants.getApplicationLoggingService()));
		} catch (JSONException e) {
			logger.debug(String.format("Failed to parse VCAP JSON: %s\n", e.getMessage()));
			return false;
		}
		
		return false;
	}

}
