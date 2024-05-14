package dashboard.messages;

import java.util.LinkedHashMap;
import java.util.Map;

import es.prodevelop.pui9.messages.AbstractPuiListResourceBundle;

public abstract class DashboardResourceBundle extends AbstractPuiListResourceBundle {

	public static final String sampleMessage = "sampleMessage";

	@Override
	protected Map<Object, String> getMessages() {
		Map<Object, String> messages = new LinkedHashMap<>();

		// messages
		messages.put(sampleMessage, getSampleMessage());

		// exceptions
//		messages.put(MyApplicationException.CODE, getMyApplication_1000());

		return messages;
	}

	protected abstract String getSampleMessage();

//	protected abstract String getMyApplication_1000();

}
