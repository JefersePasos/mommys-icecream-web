package MommysIceCreamWeb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class TranslationService {

    private static final Logger log = LoggerFactory.getLogger(TranslationService.class);

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${translation.libretranslate.url:https://es.libretranslate.com/translate}")
    private String libreTranslateUrl;

    public Optional<String> translate(String text, String source, String target) {
        if (text == null || text.isBlank()) {
            return Optional.empty();
        }
        Map<String, Object> body = new HashMap<>();
        body.put("q", text);
        body.put("source", source);
        body.put("target", target);
        body.put("format", "text");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            var response = restTemplate.postForObject(
                    libreTranslateUrl,
                    new HttpEntity<>(body, headers),
                    LibreTranslateResponse.class
            );
            if (response != null && response.translatedText != null && !response.translatedText.isBlank()) {
                return Optional.of(response.translatedText);
            }
        } catch (Exception e) {
            log.warn("No se pudo traducir con LibreTranslate: {}", e.getMessage());
        }
        return Optional.empty();
    }

    private record LibreTranslateResponse(String translatedText) {}
}
