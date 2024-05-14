package es.prodevelop.pui9.spring.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DashboardOnApplicationStartedListener {

	Logger logger = LogManager.getLogger(this.getClass());

	@EventListener
	public void onApplicationEvent(ContextStartedEvent event) {
		logger.info("App started");
	}

}
