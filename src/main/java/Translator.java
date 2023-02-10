import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Translator {
    public static void main(String[] args) throws IOException {
        //https://cloud.yandex.com/en/services/translate
        System.out.println("Введите предложение на русском языке:");
        /*Scanner scanner = new Scanner(System.in);
        String sentenceToTranslate = scanner.nextLine();*/
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String sentenceToTranslate = reader.readLine();

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://translate.api.cloud.yandex.net/translate/v2/translate";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + "token");
        // for get token need to register with bank card https://cloud.yandex.com/en/docs/translate/quickstart

        /*Create a file with the request body (for example, body.json).
        {
            "folderId": "<Folder ID>",
                "texts": ["Hello", "World"],
            "targetLanguageCode": "ru"
        }*/
        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("folderID", "mskfnsjansjk"); // take folderId from registration on Yandex Translator Api
        jsonData.put("targetLanguageCode", "en");
        jsonData.put("text", "[" + sentenceToTranslate + "]");

        HttpEntity<Map<String, String>> request = new HttpEntity<>(jsonData);

        YandexResponse response = restTemplate.postForObject(url, request, YandexResponse.class);
        System.out.println(response);
        /*{
            "translations": [
            {
                "text": "Hello",
                    "detectedLanguageCode": "ru"
            }
            {
                "text": "World",
                    "detectedLanguageCode": "ru"
            }
          ]
        }*/
        System.out.println("Перевод: " + response.getTranslations().get(0).getText());
    }
}
