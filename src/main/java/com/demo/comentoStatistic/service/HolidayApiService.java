package com.demo.comentoStatistic.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class HolidayApiService {

    @Value("${api.public.data.serviceKey}")
    private String serviceKey;

    private static final String API_URL = "http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo";

    public List<String> fetchHolidays(String startDate, String endDate) {
        Set<String> holidaySet = new HashSet<>();

        try {
            int startYear = Integer.parseInt(startDate.substring(0, 4));
            int endYear = Integer.parseInt(endDate.substring(0, 4));

            for (int year = startYear; year <= endYear; year++) {
                List<String> yearHolidays = callApiForYear(String.valueOf(year));
                holidaySet.addAll(yearHolidays);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> finalHolidayList = new ArrayList<>();
        for (String hDate : holidaySet) {
            if (hDate.compareTo(startDate) >= 0 && hDate.compareTo(endDate) <= 0) {
                finalHolidayList.add(hDate);
            }
        }

        finalHolidayList.sort(String::compareTo);
        return finalHolidayList;
    }

    private List<String> callApiForYear(String year) throws Exception {
        List<String> list = new ArrayList<>();
        StringBuilder urlBuilder = new StringBuilder(API_URL);
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode(year, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8"));
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(sb.toString());
        JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");

        if (itemsNode.isArray()) {
            for (JsonNode node : itemsNode) {
                addHolidayIfValid(node, list);
            }
        } else if (!itemsNode.isMissingNode()) {
            addHolidayIfValid(itemsNode, list);
        }

        return list;
    }

    private void addHolidayIfValid(JsonNode node, List<String> list) {
        String isHoliday = node.path("isHoliday").asText();
        String locdate = node.path("locdate").asText();

        if ("Y".equals(isHoliday)) {
            list.add(locdate);
        }
    }
}
