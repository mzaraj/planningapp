package zarajczyk.marcin.planningapp.common;

import org.springframework.http.HttpHeaders;

public class HeaderHelper {

    public static HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-MeetingApp-alert", message);
        headers.add("X-MeetingApp-params", param);
        return headers;
    }
}
