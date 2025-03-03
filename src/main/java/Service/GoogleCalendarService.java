package Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Event;  //

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

public class GoogleCalendarService {
    private static final String APPLICATION_NAME = "Gym Management App";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/client_secret_1018108248903-2e3v55sun8gurdi0qt5qrghhu4sdmbhs.apps.googleusercontent.com.json";

    public static Credential getCredentials(final HttpTransport HTTP_TRANSPORT) throws Exception {
        InputStreamReader in = new InputStreamReader(new FileInputStream(new File(CREDENTIALS_FILE_PATH)));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, in);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public static Calendar getCalendarService() throws Exception {
        HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static void addEvent(String eventName, String eventLocation, LocalDate eventDate) {
        try {
            Calendar service = getCalendarService();

            Event event = new Event()
                    .setSummary(eventName)
                    .setLocation(eventLocation)
                    .setDescription("Imported from JavaFX application");

            // Convert LocalDate to Google DateTime format
            DateTime startDateTime = new DateTime(eventDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone(TimeZone.getDefault().getID());

            event.setStart(start);
            event.setEnd(start); // One-day event

            String calendarId = "primary"; // Using the user's primary Google Calendar
            service.events().insert(calendarId, event).execute();
            System.out.println("Event added: " + eventName);

        } catch (Exception e) {
            System.err.println("Error adding event to Google Calendar: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void addEvent(Event googleEvent) {

    }
}