package sapauthentication.xsuaa;


import com.mendix.core.Core;
import com.mendix.logging.ILogNode;
import com.mendix.thirdparty.org.json.JSONArray;
import com.mendix.thirdparty.org.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class XsuaaBindingSettings {

    public static final String AUTHENTICATION_CALLBACK_PATH = "xsauaacallback/";

    private final String hostname;
    private final String redirectUri;
    private final String urlEncodedRedirectUri;

    private final String serviceName;
    private final String servicePlan;

    private final String clientId;
    private final String clientSecret;

    private final String url;

    private final String verificationKey;

    private final String xsAppName;

    public XsuaaBindingSettings(final String vcapApplication, final String vcapServices) {
        final JSONObject vcapApplicationObject = new JSONObject(vcapApplication);

        final JSONArray appliationUrisArray = vcapApplicationObject.getJSONArray("application_uris");
        hostname = appliationUrisArray.getString(0);
        redirectUri = "https://" + hostname + "/" + AUTHENTICATION_CALLBACK_PATH;
        try {
            urlEncodedRedirectUri = URLEncoder.encode(redirectUri, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to encode redirect URI");
        }

        final JSONObject vcapServicesObject = new JSONObject(vcapServices);

        final JSONArray xsuaaArray = vcapServicesObject.getJSONArray("xsuaa");
        final JSONObject xsuaaConfigurationObject = xsuaaArray.getJSONObject(0);

        serviceName = xsuaaConfigurationObject.getString("name");
        servicePlan = xsuaaConfigurationObject.getString("plan");

        final JSONObject credentialsObject = xsuaaConfigurationObject.getJSONObject("credentials");
        clientId = credentialsObject.getString("clientid");
        clientSecret = credentialsObject.getString("clientsecret");
        url = credentialsObject.getString("url");
        verificationKey = credentialsObject.getString("verificationkey");
        xsAppName = credentialsObject.getString("xsappname");
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getUrlEncodedRedirectUri() {
        return urlEncodedRedirectUri;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getServicePlan() {
        return servicePlan;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getUrl() {
        return url;
    }

    public String getVerificationKey() {
        return verificationKey;
    }

    public String getXsAppName() {
        return xsAppName;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static XsuaaBindingSettings importSettingsFromEnvironment() {
        LOGGER.info("Importing XSA-UAA binding settings from environment ...");

        final Map<String, String> environment = System.getenv();
        final String vcapApplication = environment.get("VCAP_APPLICATION");
        final String vcapServices = environment.get("VCAP_SERVICES");

        final XsuaaBindingSettings settings = new XsuaaBindingSettings(vcapApplication, vcapServices);

        LOGGER.info("Imported settings: " + settings);

        return settings;
    }

    public static boolean isXsaUaaEnvironment(){
        LOGGER.debug("Checking XSA-UAA binding environment ...");

        final Map<String, String> environment = System.getenv();
        final String vcapApplication = environment.get("VCAP_APPLICATION");
        final String vcapServices = environment.get("VCAP_SERVICES");

        LOGGER.debug("VCAP_APPLICATION : "+vcapApplication);
        LOGGER.debug("VCAP_SERVICES : "+vcapServices);
        
        if(StringUtils.isBlank(vcapApplication) ||  StringUtils.isBlank(vcapServices)){
            return false;
        }

        return true;
    }

    private final static ILogNode LOGGER = Core.getLogger("XSA-UAA-SSO");

}
