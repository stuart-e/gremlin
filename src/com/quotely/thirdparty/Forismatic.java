package com.quotely.thirdparty;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.quotely.config.Language;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Forismatic {
    private static final String API_URL = "https://api.forismatic.com/api/1.0/?method=getQuote&lang=%s&format=xml";

    public String getQuote(String language) throws Exception {
        URL url = new URL(buildForismaticXmlUrl(language));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            return parseXmlQuoteText(response.toString());

        } else {
            System.out.println("GET request failed. Response Code: " + responseCode);
        }
        return parseXmlQuoteText(connection.getResponseMessage());
    }

    private static String parseXmlQuoteText(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));

        document.getDocumentElement().normalize();

        NodeList nodeList = document.getElementsByTagName("quoteText");

        return nodeList.item(0).getTextContent();
    }

    private static String buildForismaticXmlUrl(String language) {
        if (language.equals(String.valueOf(Language.RUSSIAN))) {
            return String.format(API_URL, "ru");
        } else {
            return String.format(API_URL, "en");
        }
    }
}
