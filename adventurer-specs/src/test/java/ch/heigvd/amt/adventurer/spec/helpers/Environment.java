package ch.heigvd.amt.adventurer.spec.helpers;

import ch.heigvd.amt.adventurer.ApiException;
import ch.heigvd.amt.adventurer.ApiResponse;
import ch.heigvd.amt.adventurer.api.DefaultApi;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Olivier Liechti on 24/06/17.
 */
public class Environment {

    private static DefaultApi api = null;

    public ApiResponse apiResponse;
    public ApiException apiException;
    public boolean apiCallThrewException;
    public int statusCode;

    public static DefaultApi getApi() throws IOException {
        if (api == null) {
            api = new DefaultApi();

            Properties properties = new Properties();
            properties.load(Environment.class.getClassLoader().getResourceAsStream("environment.properties"));
            String url = properties.getProperty("ch.heigvd.amt.adventurer.server.url");
            api.getApiClient().setBasePath(url);
        }

        return api;
    }
}
