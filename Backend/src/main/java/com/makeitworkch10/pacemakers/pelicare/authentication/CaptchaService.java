package com.makeitworkch10.pacemakers.pelicare.authentication;

import com.makeitworkch10.pacemakers.pelicare.exception.InvalidReCaptchaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * @author Ruben de Vries
 * Validates the captcha response from the frontend through the Google ReCaptcha server.
 */

@Service
@RequiredArgsConstructor
public class CaptchaService {
    private final CaptchaSettings captchaSettings;

    public static final String googleUrl = "https://www.google.com/recaptcha/api/siteverify";
    private final static String USER_AGENT = "Mozilla/5.0";

    private static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");

    public boolean processResponse(String captchaResponse){
        String postParameters = "secret=" + captchaSettings.getSecret() + "&response=" + captchaResponse;

        if(!responseSanityCheck(captchaResponse)){
            throw new InvalidReCaptchaException("Captcha Response contains invalid characters.");
        }

        try{
            HttpsURLConnection connection = getHttpsURLConnection();

            addParametersToConnection(connection, postParameters);

            logResponse(connection, postParameters);

            StringBuilder response = convertResponseToString(connection);

            JsonObject jsonObject = convertStringToJson(response);

            return responseIsSuccess(jsonObject);

        } catch (Exception message){
            message.printStackTrace();
        }
        return false;
    }

    private static boolean responseIsSuccess(JsonObject jsonObject) {
        if(!jsonObject.getBoolean("success")){
            throw new InvalidReCaptchaException("reCaptcha was not successfully validated");
        }
        else {
            return true;
        }
    }

    private static JsonObject convertStringToJson(StringBuilder response) {
        JsonReader jsonReader = Json.createReader(new StringReader(response.toString()));
        JsonObject jsonObject = jsonReader.readObject();
        jsonReader.close();
        return jsonObject;
    }

    private static StringBuilder convertResponseToString(HttpsURLConnection connection) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(
                connection.getInputStream()
        ));

        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = input.readLine()) != null){
            response.append(inputLine);
        }
        input.close();

        System.out.println(response);
        return response;
    }

    private void addParametersToConnection(HttpsURLConnection connection, String postParameters) throws IOException {
        connection.setDoOutput(true);
        DataOutputStream write = new DataOutputStream(connection.getOutputStream());
        write.writeBytes(postParameters);
        write.flush();
        write.close();
    }

    private void logResponse(HttpsURLConnection connection, String postParameters) throws IOException {
        int responseCode = connection.getResponseCode();
        System.out.println("\nSending 'POST' request to URL: " + googleUrl);
        System.out.println("Post parameters: " + postParameters);
        System.out.println("Response Code: " + responseCode);
    }

    private HttpsURLConnection getHttpsURLConnection() throws IOException {
        URL googleRequestUrl = new URL(googleUrl);
        HttpsURLConnection connection = (HttpsURLConnection) googleRequestUrl.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        return connection;
    }

    private boolean responseSanityCheck(String captchaResponse) {
        return StringUtils.hasLength(captchaResponse) && RESPONSE_PATTERN.matcher(captchaResponse).matches();
    }

}
