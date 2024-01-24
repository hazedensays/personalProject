package com.cm.personalProject.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.cm.personalProject.domain.OauthId;
import com.cm.personalProject.entity.User;
import com.cm.personalProject.repository.UserRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class KakaoAPI {

	private final UserRepository repository;

	public String getAccessToken(String authorize_code) {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=7c340b218caf1b7c5c6faed3222c37d3");
			sb.append("&redirect_uri=http://localhost:8080/social/klogin");
			sb.append("&code=" + authorize_code);
			sb.append("&client_secret=MP2DGb1FU2Xa3QcDKodnB1zTzEQLzGn5");
			bw.write(sb.toString());
			bw.flush();

			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			// Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

			System.out.println("access_token : " + access_Token);
			System.out.println("refresh_token : " + refresh_Token);

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return access_Token;
	} // getAccessToken()

	// getUserInfo
	public String getUserInfo(String access_token) throws IOException {

		String reqUrl = "https://kapi.kakao.com/v2/user/me";

		URL url = new URL(reqUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");

		// 요청에 필요한 Header에 포함될 내용
		conn.setRequestProperty("Authorization", "Bearer " + access_token);

		int responseCode = conn.getResponseCode();
		log.info("[KakaoApi.getUserInfo] responseCode : {}", responseCode);

		BufferedReader br;
		if (responseCode >= 200 && responseCode <= 300) {
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}

		String line = "";
		StringBuilder responseSb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			responseSb.append(line);
		}
		String result = responseSb.toString();
		log.info("responseBody = {}", result);

		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(result);

		JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
		JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

		String nickname = properties.getAsJsonObject().get("nickname").getAsString();
		String email = kakao_account.getAsJsonObject().get("email").getAsString();
		String token_id = element.getAsJsonObject().get("id").getAsString();

		Optional<User> opt_user = repository.findById(new OauthId("kakao", token_id));

		if (!opt_user.isPresent()) {
			User user = new User();
			user.setUseremail(email);
			user.setUsername(nickname);
			user.setOauthtype("kakao");
			user.setOauthtoken(token_id);
			System.out.println("user!!!!!!!" + user);
			repository.save(user);
		} else {
			return "fail";
		}

		br.close();
		return "success";

	}

	public static User selectOne(String username) {
		return null;
	}

}
