package dashboard.exceptions;

import dashboard.messages.DashboardMessages;
import es.prodevelop.pui9.exceptions.PuiServiceException;

public class DashboardException extends PuiServiceException {

	private static final long serialVersionUID = 1L;

	public DashboardException(Integer code) {
		this(code, new Object[0]);
	}

	public DashboardException(Integer code, Object... parameters) {
		super(code, DashboardMessages.getSingleton().getString(code), parameters);
	}

}
