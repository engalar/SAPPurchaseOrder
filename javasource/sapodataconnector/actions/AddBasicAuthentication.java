// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package sapodataconnector.actions;

import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.webui.CustomJavaAction;
import sapodataconnector.request.RequestParameterUtil;

public class AddBasicAuthentication extends CustomJavaAction<java.lang.Boolean>
{
	private IMendixObject __RequestParameters;
	private sapodataconnector.proxies.RequestParams RequestParameters;
	private java.lang.String username;
	private java.lang.String password;

	public AddBasicAuthentication(IContext context, IMendixObject RequestParameters, java.lang.String username, java.lang.String password)
	{
		super(context);
		this.__RequestParameters = RequestParameters;
		this.username = username;
		this.password = password;
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		this.RequestParameters = this.__RequestParameters == null ? null : sapodataconnector.proxies.RequestParams.initialize(getContext(), __RequestParameters);

		// BEGIN USER CODE
		if (RequestParameters == null) {
			throw new IllegalArgumentException("RequestParams is not provided");
		}

		RequestParameterUtil.change(getContext(), RequestParameters)
			.addBasicAuthentication(username, password)
			.build();

		return true;
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 * @return a string representation of this action
	 */
	@java.lang.Override
	public java.lang.String toString()
	{
		return "AddBasicAuthentication";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
