
package ru.javafx.dolphin.springbootfx.config;

import com.canoo.dolphin.client.HttpURLConnectionFactory;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.javafx.dolphin.springbootfx.config.properties.AuthorizationProperties;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class AppConnectionFactory implements HttpURLConnectionFactory {
    
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private AuthorizationProperties authorizationProperties;

    @Override
    public HttpURLConnection create(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        String authorStr = authorizationProperties.getUsername() + ":" + authorizationProperties.getPassword();
        String encoding = Base64.getEncoder().encodeToString(authorStr.getBytes("utf-8"));
        connection.setRequestProperty("Authorization", "Basic " + encoding);
        return connection;
    }

}
