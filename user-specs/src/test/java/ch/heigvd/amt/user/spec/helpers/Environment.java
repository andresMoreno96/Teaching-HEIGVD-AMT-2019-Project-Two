package ch.heigvd.amt.user.spec.helpers;

import ch.heigvd.amt.user.ApiException;
import ch.heigvd.amt.user.ApiResponse;
import ch.heigvd.amt.user.api.DefaultApi;
import ch.heigvd.amt.user.api.dto.User;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Olivier Liechti on 24/06/17.
 */
public class Environment {

    private static DefaultApi api = null;

    public User user;

    public ApiResponse apiResponse;
    public ApiException apiException;
    public boolean apiCallThrewException;
    public int statusCode;

    public static DefaultApi getApi() throws IOException {
        if (api == null) {
            api = new DefaultApi();

            Properties properties = new Properties();
            properties.load(Environment.class.getClassLoader().getResourceAsStream("environment.properties"));
            String url = properties.getProperty("ch.heigvd.amt.user.server.url");
            api.getApiClient().setBasePath(url);
        }

        return api;
    }
}
