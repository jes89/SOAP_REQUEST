<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- https://bio.bglrt.com:21443/module/auth -->


<%
//HTTP GET request private void sendGet(String targetUrl) throws Exception { URL url = new URL(targetUrl); HttpURLConnection con = (HttpURLConnection) url.openConnection(); con.setRequestMethod("GET"); // optional default is GET con.setRequestProperty("User-Agent", USER_AGENT); // add request header int responseCode = con.getResponseCode(); BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); String inputLine; StringBuffer response = new StringBuffer(); while ((inputLine = in.readLine()) != null) { response.append(inputLine); } in.close(); // print result System.out.println("HTTP 응답 코드 : " + responseCode); System.out.println("HTTP body : " + response.toString()); } // HTTP POST request private void sendPost(String targetUrl, String parameters) throws Exception { URL url = new URL(targetUrl); HttpsURLConnection con = (HttpsURLConnection) url.openConnection(); con.setRequestMethod("POST"); // HTTP POST 메소드 설정 con.setRequestProperty("User-Agent", USER_AGENT); con.setDoOutput(true); // POST 파라미터 전달을 위한 설정 // Send post request DataOutputStream wr = new DataOutputStream(con.getOutputStream()); wr.writeBytes(parameters); wr.flush(); wr.close(); int responseCode = con.getResponseCode(); BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream())); String inputLine; StringBuffer response = new StringBuffer(); while ((inputLine = in.readLine()) != null) { response.append(inputLine); } in.close(); // print result System.out.println("HTTP 응답 코드 : " + responseCode); System.out.println("HTTP body : " + response.toString()); }

%>

