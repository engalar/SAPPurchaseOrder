// This file was generated by Mendix Studio Pro.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package sapodataconnector.actions;

import java.net.URI;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import com.mendix.core.Core;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.MendixException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.thirdparty.org.json.JSONObject;
import com.mendix.webui.CustomJavaAction;
import sapodataconnector.proxies.constants.Constants;
import sapodataconnector.utils.ExpectedHttpResultOptional;
import sapodataconnector.utils.ODataClientBuilder;
import sapodataconnector.utils.ODataRequestBuilder;
import sapodataconnector.utils.ODataResponseHandler;
import sapodataconnector.utils.ToMendixSerializer;

public class Refresh extends CustomJavaAction<java.lang.Boolean>
{
	private IMendixObject __odataObject;
	private sapodataconnector.proxies.OdataObject odataObject;
	private IMendixObject __destination;
	private sapodataconnector.proxies.Destination destination;
	private IMendixObject __requestParameters;
	private sapodataconnector.proxies.RequestParams requestParameters;

	public Refresh(IContext context, IMendixObject odataObject, IMendixObject destination, IMendixObject requestParameters)
	{
		super(context);
		this.__odataObject = odataObject;
		this.__destination = destination;
		this.__requestParameters = requestParameters;
	}

	@java.lang.Override
	public java.lang.Boolean executeAction() throws Exception
	{
		this.odataObject = this.__odataObject == null ? null : sapodataconnector.proxies.OdataObject.initialize(getContext(), __odataObject);

		this.destination = this.__destination == null ? null : sapodataconnector.proxies.Destination.initialize(getContext(), __destination);

		this.requestParameters = this.__requestParameters == null ? null : sapodataconnector.proxies.RequestParams.initialize(getContext(), __requestParameters);

		// BEGIN USER CODE
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(new StringBuilder(getContext().getSession().getId().toString())
					.append("|REFRESH|single, query: ")
					.append(odataObject.getmeta_objectURI())
					.toString());
		}

		String response = null;
		if(destination != null) {
			LOGGER.info("Refresh :: Destination found, refreshing CSRF Token");
			URI meta_objectURI = new URI(odataObject.getmeta_objectURI());
			URI destinationURI = new URI(destination.getUrl());
			String uriStr = odataObject.getmeta_objectURI();
			if(destinationURI.getHost().equals(meta_objectURI.getHost())){
				uriStr = meta_objectURI.getPath();
				if(meta_objectURI.getPath().startsWith(destinationURI.getPath())){
					uriStr = uriStr.substring(destinationURI.getPath().length());
				}
			}
			LOGGER.debug(new StringBuilder(getContext().getSession().getId().toString())
					.append("Updated query: ")
					.append(uriStr)
					.toString());
			
			final HttpGet httpGet = ODataRequestBuilder.builder()
					.get(destination, uriStr)
					.setContext(getContext())
					.setRequestHeader(requestParameters)
					.fetchCSRFToken()
					.build();

			try (final CloseableHttpClient httpClient = ODataClientBuilder.builder()
					.setContext(getContext())
					.setRequestParameters(requestParameters)
					.setDestination(destination)
					.build()){

				response = httpClient.execute(httpGet, new ODataResponseHandler("REFRESH", LOGGER, getContext(),
						ExpectedHttpResultOptional.ofNullable(requestParameters).getExpectedHttpResult()));
			}
		}
		else {
			LOGGER.info("Refresh :: Destination not found, refreshing CSRF Token");
			final HttpGet httpGet = ODataRequestBuilder.builder()
					.get(odataObject.getmeta_objectURI())
					.setContext(getContext())
					.setRequestHeader(requestParameters)
					.fetchCSRFToken()
					.build();

			try (final CloseableHttpClient httpClient = ODataClientBuilder.builder()
					.setContext(getContext())
					.setRequestParameters(requestParameters)
					.build()){

				response = httpClient.execute(httpGet, new ODataResponseHandler("REFRESH", LOGGER, getContext(),
						ExpectedHttpResultOptional.ofNullable(requestParameters).getExpectedHttpResult()));
			}
		}

		if (response == null) {
			throw new MendixException("This call has not been performed correctly, please make sure that this action is supported by the service provider.");
		}

		final JSONObject jsonObjArray = new JSONObject(response).getJSONObject("d");
		if (jsonObjArray == null) {
			throw new MendixException("Expectinng exactly one result, got nothing");
		}

		ToMendixSerializer.updateMendixObject(getContext(), jsonObjArray, __odataObject);

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
		return "Refresh";
	}

	// BEGIN EXTRA CODE
	private static final ILogNode LOGGER = Core.getLogger(Constants.getLogNode());
	// END EXTRA CODE
}