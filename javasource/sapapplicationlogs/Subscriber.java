package sapapplicationlogs;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.mendix.core.Core;
import com.mendix.logging.ILogNode;
import com.mendix.logging.LogLevel;
import com.mendix.logging.LogMessage;
import com.mendix.logging.LogSubscriber;
import com.mendix.thirdparty.org.json.JSONException;
import com.mendix.thirdparty.org.json.JSONObject;

import sapapplicationlogs.proxies.constants.Constants;


/**
 * The Subscriber outputs LogMessages to the system console.<br />
 * Messages with LogLevel ERROR or higher will be written to STDERR,
 * messages with LogLevel INFO or lower will be written to STDOUT.<br />
 * The subscriber object can subscribe to message channels
 * using the subscribe(LogSubscriber, LogLevel) method of a LogNode object.
 */
public class Subscriber extends LogSubscriber {
	// log node to debug subscriber
	static ILogNode logger = Core.getLogger(Constants.getLogNode());

	public Subscriber(String node, LogLevel level) {
		super(node, level);
	}

	@Override
	public void processMessage(LogMessage msg) {
		if (msg.message == null && msg.cause == null)
			return;

		JSONObject logline = new JSONObject();
		try {
			logline.put("written_at", Instant.ofEpochMilli(msg.timestamp).toString());
			// SAP recommends to set written_ts to System.nanoTime() so that Kibana can
			// sort the logs: https://github.com/SAP/cf-java-logging-support/wiki/Overview
			logline.put("written_ts", System.nanoTime());
			logline.put("logger", msg.prefix);
			logline.put("level", msg.level.toString());
			if (msg.message != null)
				logline.put("msg", msg.message.toString());

			if (msg.cause != null) {
				List<String> cause = new ArrayList<>();
				ExceptionBuilder.buildException(cause, msg.cause);
				logline.put("stacktrace", cause);
			}
		} catch (JSONException e) {
			logger.error("Unable to generate JSON logline! " + e.getMessage());
		}

		if (msg.level.compareTo(LogLevel.ERROR) < 0)
			System.out.println(logline.toString());
		else
			System.err.println(logline.toString());
	}
	
	@Override
	public void subscribe(ILogNode node, LogLevel level) {
		super.subscribe(node, level);
		logger.debug(String.format("Subscribed New node: %s Log level: %s\n", node.name(), level.name()));
	}
}
