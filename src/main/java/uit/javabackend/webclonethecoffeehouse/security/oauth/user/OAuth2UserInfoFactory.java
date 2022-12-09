package uit.javabackend.webclonethecoffeehouse.security.oauth.user;

import uit.javabackend.webclonethecoffeehouse.security.exception.OAuth2AuthenticationProcessingException;
import uit.javabackend.webclonethecoffeehouse.user.model.User;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(User.Provider.google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
