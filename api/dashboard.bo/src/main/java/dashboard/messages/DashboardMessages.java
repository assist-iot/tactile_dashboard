package dashboard.messages;

import es.prodevelop.pui9.messages.AbstractPuiMessages;

public class DashboardMessages extends AbstractPuiMessages {

	private static DashboardMessages singleton;

	public static DashboardMessages getSingleton() {
		if (singleton == null) {
			singleton = new DashboardMessages();
		}
		return singleton;
	}

	private DashboardMessages() {
	}

	@Override
	protected Class<?> getResourceBundleClass() {
		return DashboardResourceBundle.class;
	}

}
