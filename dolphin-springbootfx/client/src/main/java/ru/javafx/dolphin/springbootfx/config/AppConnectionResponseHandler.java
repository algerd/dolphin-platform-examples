
package ru.javafx.dolphin.springbootfx.config;

import com.canoo.dolphin.client.HttpURLConnectionResponseHandler;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AppConnectionResponseHandler implements HttpURLConnectionResponseHandler {
    
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @Override
    public void handle(HttpURLConnection response) {
        Map<String, List<String>> headerFields = response.getHeaderFields();
        LOGGER.info("HeaderFields: {}", headerFields);
    }

}
