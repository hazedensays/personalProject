<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/resources/css/login.css">
<title>social Login</title>
</head>
<body>
	<div id="wrap">
		<h2>소셜 로그인하기</h2>

		<a href="https://kauth.kakao.com/oauth/authorize?client_id=7c340b218caf1b7c5c6faed3222c37d3&redirect_uri=http://localhost:8080/social/klogin&response_type=code">
			<img src="/resources/images/kakao_login_medium_wide.png" alt="kakaoLogin IMG" />
		</a>
		<br />
		
		<a href="https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=YmF21lTRrhBHowHEvL4f&redirect_uri=http://localhost:8080/social/nlogin">
			<img src="/resources/images/naver_login_logo.png" alt="naverLogin IMG" />
		</a>
		<br />
		
		<a href="https://accounts.google.com/o/oauth2/auth?client_id=192595726733-k5cn43gdkr8oaus87c1avq98ro4roaoc.apps.googleusercontent.com&redirect_uri=http://localhost:8080/social/glogin&response_type=code&scope=https://www.googleapis.com/auth/userinfo.emailhttps://www.googleapis.com/auth/userinfo.profile">
			<img src="/resources/images/google_login_logo.png" alt="googleLogin IMG" />
		</a>
	</div>
</body>
</html>