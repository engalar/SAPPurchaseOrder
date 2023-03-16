package sapapplicationlogs;

import com.mendix.core.Core;
import com.mendix.logging.LogLevel;
import com.mendix.logging.LogSubscriber;

public class SubscriberConfigurer {
	
	private static final String SUBSCRIBER = "SAPApplicationLoggingSubscriber";
	private static final String FILE_SUBSCRIBER = "FileSubscriber";
	private static final String CORE_LOGGER = "Core";
	
	
	public static void silenceFileSubscriber() {
		//TODO: there is a potential race condition here. Need to retry if FileSubscriber does not exist yet.
		Core.getLogger(CORE_LOGGER).getSubscribers().keySet().stream()
		.filter(s -> FILE_SUBSCRIBER.equals(s.getName()))
		.findFirst().ifPresent(s -> {
			s.setAutoSubscribeLevel(com.mendix.logging.LogLevel.NONE);
			s.getSubscriptions().forEach((n, l) -> {
				n.unsubscribe(s);
			});
			s.destroy();
		});
	}
	
	public static void registerJsonSubscriber(LogLevel logLevel) {
		LogSubscriber jsonSubscriber = new Subscriber(SUBSCRIBER, logLevel);
		Core.registerLogSubscriber(jsonSubscriber);
	}
}
