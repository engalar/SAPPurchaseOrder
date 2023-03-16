package sapauthentication.xsuaa;

import static com.google.inject.internal.Lists.newArrayList;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.auth0.jwt.interfaces.Claim;
import com.google.inject.internal.Lists;
import com.mendix.core.Core;
import com.mendix.logging.ILogNode;

import sapauthentication.proxies.constants.Constants;

public class XsuaaIdentity {
	private static final ILogNode LOGGER = Core.getLogger(Constants.getLogNode());

    private final String xsAppName;

    private final String clientId;

    private final String username;
    private final String fullname;
    private final String email;

    private final List<String> scopes;
    private final List<String> samlGroups;
    private final List<String> roleCollections;

    @SuppressWarnings("unchecked")
	public XsuaaIdentity(final Map<String, Claim> claims, final String xsAppName) {
        this.xsAppName = xsAppName;

        final Claim clientIdClaim = claims.get("client_id");
         if(clientIdClaim == null) {
        	LOGGER.warn("There is no client_id claim in the access token");
	    }
        clientId = clientIdClaim.asString();
        
        final Claim userNameClaim = claims.get("user_name");
        if(userNameClaim == null) {
        	LOGGER.warn("There is no user_name claim in the access token");
        }
        username = userNameClaim.asString();
        
        final Claim givenNameClaim = claims.get("given_name");
        if(givenNameClaim == null) {
        	LOGGER.warn("There is no given_name claim in the access token");
        }
        fullname = givenNameClaim.asString();
        
        final Claim emailClaim = claims.get("email");
        if(emailClaim == null) {
        	LOGGER.warn("There is no email claim in the access token");
        }
        email = emailClaim.asString();
       
        
        final Claim scopeClaim = claims.get("scope");
        if(scopeClaim == null) {
        	LOGGER.warn("There is no scope claim in the access token");
        }
        scopes = scopeClaim.asList(String.class);
        
        final Claim xsSystemAttributesClaim = claims.get("xs.system.attributes");
        if(xsSystemAttributesClaim == null) {
        	LOGGER.warn("There is no xs.system.attributes claim in the access token");
        	samlGroups = Lists.newArrayList();
        	roleCollections = Lists.newArrayList();
        	return;
        }

        final Map<String, Object> xsSystemAttributesClaimMap = xsSystemAttributesClaim.asMap();
        
        samlGroups = (List<String>) xsSystemAttributesClaimMap.get("xs.saml.groups");
        roleCollections = (List<String>) xsSystemAttributesClaimMap.get("xs.rolecollections");
    }

    public String getXsAppName() {
        return xsAppName;
    }

    public String getClientId() {
        return clientId;
    }

    public String getUsername() {
        return username;
    }
    
    public String getFullname() {
        return fullname;
    }
    
    public String getEmail() {
        return email;
    }

    public List<String> getSamlGroups() {
        return samlGroups;
    }

    public List<String> getRoleCollections() {
        return roleCollections;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public List<String> getMendixRoles() {
        final List<String> roles = newArrayList();

        for (String scope : scopes) {
            if (!"openid".equals(scope)) {
                final String[] split = scope.split("\\.");
                if (xsAppName.equals(split[0])) {
                    roles.add(split[1]);
                }
            }
        }

        return roles;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
