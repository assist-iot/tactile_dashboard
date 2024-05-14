package es.prodevelop.pui9.spring.config;

import org.springframework.context.annotation.ComponentScan;

import es.prodevelop.pui9.interceptors.CommonInterceptor;
import es.prodevelop.pui9.interceptors.PuiInterceptor;
import es.prodevelop.pui9.spring.configuration.AbstractAppSpringConfiguration;
import es.prodevelop.pui9.spring.configuration.annotations.PuiSpringConfiguration;

@PuiSpringConfiguration
@ComponentScan(basePackages = { "dashboard" })
public class DashboardSpringConfiguration extends AbstractAppSpringConfiguration {

	@Override
	protected String getJndiName() {
		return "java:comp/env/jdbc/dashboard";
	}

	@Override
	protected PuiInterceptor getHandlerInterceptor() {
		return new CommonInterceptor();
	}

	@Override
	protected String getAesSecret() {
		return "01234567890123456789012345678901";
	}

}
