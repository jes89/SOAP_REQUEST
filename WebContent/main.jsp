<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<script  src="http://code.jquery.com/jquery-latest.min.js"></script>

	  <script type="text/javascript" language="javascript">
        
        var RemainTime = 60000;	// 3분(180000) 후 인증체크 종료 * 테스트 1분
        var refreshIntervalId = "";		//반복실행 변수
        	
        callAuth();
        
        function callAuth() {
            $.ajax({                
                type : "POST", //전송방식을 지정한다 (POST,GET)
                data : JSON.stringify({
               	  	code : "BGLRT",
                    id : "test1",
                    otp: 567432,
                    site:1
                }),
                /*data :{
                  
                },*/
                url : "http://localhost:8080/requestSample.jsp",	
                dataType : "json",
                error : authError,
                success : authSuccess
            });
        }
        
        function authSuccess(retData) {
            
        	console.log(retData);
            
            if(retData.status == 1) {
                setTimeout(function() {callResult(retData.value)}, 3000); //3초 후 결과 페이지 호출
            }
        }
        
        function authError(request,status,error) {

            console.log(request);
            console.log(status);
            console.log(error);
        }
        
        function callResult(strIdx) {
            refreshIntervalId = setInterval(function() {repeatResultChk(strIdx)}, 1000);	//1초간격으로 반복 실행
        }
        
        function repeatResultChk(strIdx) {
            if (RemainTime < 0) {
                 alert("인증 시간이 종료되어 있습니다.!!!");
                clearInterval(refreshIntervalId);   // 타이머 해제
            } else {
                 $.ajax({
                     type : "GET", //전송방식을 지정한다 (POST,GET)
                     data :{
                         idx : strIdx
                     },
                     url : "https://bio.bglrt.com:21443/module/result/",//호출 URL을 설정한다. GET방식일경우 뒤에 파라티터를 붙여서 사용해도된다.
                     dataType : "json",	//호출한 페이지의 형식이다. xml,json,html,text등의 여러 방식을 사용할 수 있다.
                     error : resultError,
                     success : resultSuccess
                 });
                 
                 RemainTime = RemainTime - 1000;  //남은시간 -1초
            }
        }
        
        function resultSuccess(retData) {
            //alert("결과 통신 결과값 ==> result : "+ retData.result + "description : "+ retData.description);
            if(retData.result == 1) {
                //location.href = "/rois_bgl/index.jsp";
                location.href = "/rois_bgl/roism.jsp";
            }
        }
        
        function resultError(request,status,error) {
            alert("인증결과 통신실패!!!!");
            alert("code = "+ request.status + " message = " + request.responseText + " error = " + error); // 실패 시 처리
        }
        
    </script>
</body>
</html>