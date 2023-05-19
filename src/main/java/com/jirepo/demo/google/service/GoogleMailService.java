package com.jirepo.demo.google.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.auth.Credentials;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.jirepo.core.config.util.ApplicationContextHolder;
import com.jirepo.core.google.GoogleApiSettings;
import com.jirepo.core.google.GoogleBaseService;
import com.jirepo.core.util.ClassPathFileUtil;

@Service
public class GoogleMailService extends GoogleBaseService {

	private static final List<String> GMAIL_SCOPES = Arrays.asList(
			"https://www.googleapis.com/auth/gmail.compose",
			"https://www.googleapis.com/auth/gmail.labels",
			"https://www.googleapis.com/auth/gmail.readonly");
	private static final List<String> LABEL_IDS = Arrays.asList(
			"INBOX");

	public int getMailCount(String userId) throws Exception {

		// webemail : service account email
		// adminUserid 도메인 관리자아이디@gsuite.naonosft.kr
		//
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

		// 아래는 Service Account를 사용한 것이어서 OAuth에서는 제외
		// GoogleCredential credential = new GoogleCredential.Builder()
		// .setTransport(HTTP_TRANSPORT)
		// .setJsonFactory(JSON_FACTORY)
		// .setServiceAccountId(GoogleApiConstant.GSUITE_SERVICE_ACCOUNT_EMAIL) //
		// Service Account Email
		// //.setServiceAccountUser(GoogleApiConstant.GSUITE_ADMIN_USER_ID) // Admin
		// User's ID
		// .setServiceAccountUser(userId) // Admin User's ID
		// .setServiceAccountScopes(GMAIL_SCOPES)
		// .setServiceAccountPrivateKeyFromP12File(ClassPathFileUtil.getFileObject(GoogleApiConstant.GSUITE_SERVICE_ACCOUNT_KEY_FILE_PATH))
		// .build();

		Credential credential = getCredentialWithRefreshToken(userId);

		GoogleApiSettings settings = (GoogleApiSettings) ApplicationContextHolder.getBean("googleApiSettings");

		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
				.setApplicationName(settings.getApplicationName()).build();

				
		System.out.println(">>>>> ★★★★★★★★★★★★★★★★ OK");

		ListMessagesResponse response;
		List<Message> messages = new ArrayList<Message>();
		String query = "is:unread";
		response = service.users().messages()
				.list(userId)
				// .setQ(query)
				// .setLabelIds(LABEL_IDS)
				.setMaxResults(5L)
				.execute();// 5개
		if (response != null && response.getMessages() != null) {
			for (Message message : response.getMessages()) {
				System.out.println(message.getId());
			}
			return response.size();
		}
		return 0;
	}// :




	private Credentials newCredentials(@Nullable InputStream credentialsFile, List<String> authScopes)
			throws IOException {
		try {
			GoogleCredentials creds = credentialsFile == null
					? GoogleCredentials.getApplicationDefault()
					: GoogleCredentials.fromStream(credentialsFile);
			if (!authScopes.isEmpty()) {
				creds = creds.createScoped(authScopes);
			}
			return creds;
		} catch (IOException e) {
			String message = "Failed to init auth credentials: " + e.getMessage();
			throw new IOException(message, e);
		}
	}


	/**
	 * Service Account를 사용하여 구글 메일을 가져온다. 
	 * @param userId 사용자 아이디(gmail) 
	 * @return
	 * @throws Exception
	 */
	public int getMailCountUsingServiceAccount(String userId) throws Exception {
		// GoogleCredentials credentials = 
		//       ServiceAccountCredentials.fromStream(
		// 	      ClassPathFileUtil.readFileToInputStream("googleapi/sanghyeon-test-68700f95dc98.json"), 
		// 		  new HttpTransportFactory() {
		// 			@Override
		// 			public HttpTransport create() {
		// 				try { 
		// 					NetHttpTransport transport = GoogleNetHttpTransport.newTrustedTransport();
		// 					return transport;
		// 				}catch(Exception e) {
		// 					e.printStackTrace();
		// 					throw new RuntimeException(e); 
		// 				}
		// 			}
		// 		  });


		NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();				  
		GoogleCredentials credentials = 
		      ServiceAccountCredentials.fromStream(ClassPathFileUtil.readFileToInputStream("googleapi/sanghyeon-test-68700f95dc98.json"))
			     .createScoped(GMAIL_SCOPES)
		        .createDelegated("sanghyeon@gsuite.naonsoft.kr");
		HttpRequestInitializer requestInitializer = new HttpCredentialsAdapter(credentials);

		Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, requestInitializer).build();
		ListMessagesResponse response;
		String query = "is:unread";
		response = service.users().messages()
				.list("sanghyeon@gsuite.naonsoft.kr")
				//.setQ(query)
				//.setLabelIds(LABEL_IDS)
				.setMaxResults(5L)
				.execute();// 5개
		if(response != null  && response.getMessages() != null) {
			for(Message message: response.getMessages()) {
				System.out.println(message.getId());
				Message gmailMessage = service.users().messages().get("sanghyeon@gsuite.naonsoft.kr", message.getId()).execute();
				java.util.List<com.google.api.services.gmail.model.MessagePart> list = gmailMessage.getPayload().getParts();
				MessagePart part = list.get(0);
				String body = part.getBody().getData();
				System.out.println(body);
				
			}
			return response.size();
		}

		return 0;
	}// :

}/// ~
