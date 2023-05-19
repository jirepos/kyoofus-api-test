package com.jirepo.demo.google.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.jirepo.core.config.util.ApplicationContextHolder;
import com.jirepo.core.google.GoogleApiSettings;
import com.jirepo.core.google.GoogleBaseService;
import com.jirepo.core.web.exception.BaseBizErrorCode;
import com.jirepo.core.web.exception.BaseBizException;

/**
 * Google Calendar API 테스트용 서비스 
 */
@Service
public class GoogleCalendarBaseService extends GoogleBaseService {
	
    
//    public static Credential createCredentialWithRefreshToken(HttpTransport transport, JsonFactory jsonFactory, TokenResponse tokenResponse) {
//    	
//    	    return new Credential.Builder(BearerToken.authorizationHeaderAccessMethod()).setTransport(transport)
//    	        .setJsonFactory(jsonFactory)
//    	        .setTokenServerUrl(
//    	            new GenericUrl("https://server.example.com/token"))
//    	        .setClientAuthentication(new BasicAuthentication("s6BhdRkqt3", "7Fjfp0ZBr1KtDRbnfVdmIw"))
//    	        .build()
//    	        .setFromTokenResponse(tokenResponse);
//    	
//    }
    
    
    
//    /**
//     * Creates an authorized Credential object.
//     * @param HTTP_TRANSPORT The network HTTP Transport.
//     * @return An authorized Credential object.
//     * @throws IOException If the credentials.json file cannot be found.
//     */
//    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
//    
//        // Load client secrets.
//        InputStream in = GoogleCalendarService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
//        if (in == null) {
//            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
//        }
//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
//        
//        // Build flow and trigger user authorization request.
//        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
//                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
//                .setAccessType("offline")
//                .build();
//        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
//        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
//    }
//    
    /**
     * 기본캘린더의 이벤트 목록 조회 
     * @param gmailId  Gmail ID
     */
	public List<Event> getPrimaryEventList(String gmailId) {


		GoogleApiSettings settings = (GoogleApiSettings)ApplicationContextHolder.getBean("googleApiSettings");


		try {
			
			Credential credential = getCredentialWithRefreshToken(gmailId);
//			String path;
//			GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(path));
//			credentials.refreshAccessToken();
//			
		    // Build a new authorized API client service.
	        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential  /*getCredentials(HTTP_TRANSPORT) */)
	                .setApplicationName( settings.getApplicationName())
	                .build();

	        // List the next 10 events from the primary calendar.
	        DateTime now = new DateTime(System.currentTimeMillis());
	        Events events = service.events().list("primary")
	                .setMaxResults(10)
	                .setTimeMin(now)
	                .setOrderBy("startTime")
	                .setSingleEvents(true)
	                .execute();
	        List<Event> items = events.getItems();
	        if (items.isEmpty()) {
	            System.out.println("No upcoming events found.");
	        } else {
	            System.out.println("Upcoming events");
	            for (Event event : items) {
	                DateTime start = event.getStart().getDateTime();
	                if (start == null) {
	                    start = event.getStart().getDate();
	                }
	                System.out.printf("%s (%s)\n", event.getSummary(), start);
	            }
	        }
			
            return items; 
		}catch(Exception e) {
			e.printStackTrace();
            //throw new CustomException(ErrorCode.INTERAL_SERVER_ERROR); '
			throw new BaseBizException(BaseBizErrorCode.INTERNAL_SERVER_ERROR); 
		}
	}//:

}///~

