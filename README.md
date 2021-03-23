# 1. 개발환경
    JAVA 1.8
    Spring ( SpringBoot v2.3.9 )
    H2 DB v1.4.197
    Eclipse 202012 (plug-in : Spring Tools 4)
    
# 2. 개발내용

## 2.1. 테이블

|컬럼         |컬럼한글명  |PK  |데이터타입  |길이|NULL  |설명          | 
|:------------|:-----------|:--:|:-----------|---:|:-------|:-------------|
|MNG_NO       |관리번호    |Y   |VARCHAR     |20  |NOT NULL|              |
|DATA_DIV     |데이터구분  |    |VARCHAR     |1   |NOT NULL|1:승인, 2:취소 |
|CARD_DATA    |전송데이터  |    |CHAR        |450 |NOT NULL|              |
|CANCEL_YN    |취소여부    |    |VARCHAR     |1   |NULL    |1:취소        |
|CANCEL_MNG_NO|취소관리번호|    |VARCHAR     |20  |NULL    |              |
|INPUTDATE    |입력일시    |    |TIMESTAMP   |    |NOT NULL|              |

관리번호가 승인,취소 인지에 대한 관리 항목 추가

승인건에 대해 취소여부를 쉽게 확인하기 위해 취소여부 컬럼과,취소관리번호 관리 항목 추가

## 2.2. 설명
각 서비스API HTTP METHOD는  카드결제(POST), 결제취소(PUT), 결제정보조회(GET)를 사용함.

입력값에 대해 유효값 체크를 하고 유효값 불충족시 사전에 정의한 리턴코드와 메세지는 리턴함. 

리턴코드는 `com.cardwork.Code`에 정의 되어있음.

카드정보 (카드번호|유효기간|cvc) 암복호화는 spring-security 라이브러리에있는 `org.springframework.security.crypto.*` 의 패키지 들을 사용함.

은행에 전송할 string데이터를 핸들링 하기위에 아래의 모듈을 별도로 개발함.
 * 데이터 조합 :` com.cardwork.Card`에 있는 `fillRight(), fillLeft()`을 이용
 * 데이터 분해 : `com.cardwork.PackWork`

결제정보 조회시 카드번호 마스킹은 정규표현식을 이용하여 처리함.

# 3. 실행방법
Eclipse : Project Run As > Spring Boot App

테스트URL : [http://localhost:8080/card](http://localhost:8080/card)

Call Method :  결재(POST), 취소(PUT), 조회(GET) ex) Postman 등 이용하여 call
