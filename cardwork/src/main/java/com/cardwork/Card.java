package com.cardwork;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cardwork.entity.*;
import com.cardwork.dbio.CardPayMng;
import com.cardwork.dbio.CardPayMngMapper;

/**
 * 카드업무처리 class
 * 
 */

@RestController
public class Card {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final static TextEncryptor encryptor = Encryptors.text("1234", KeyGenerators.string().generateKey());

    @Autowired
    CardPayMngMapper cardPayMngMapper;

	/**
	 * 카드승인
	 * 
	 */
	@PostMapping("/card")
	public Card01Vo paymentCard(@RequestBody Card01Vo card01vo) {
		
		Card01Vo rtnVo = new Card01Vo();
		
		logger.info("## 카드승인 요청");

		try {
			
			String cardNo    = card01vo.getCardNo();    //카드번호
			String validDate = card01vo.getValidDate(); //유효기간
			String cvc       = card01vo.getCvc();       //cvc
			String payMonth  = card01vo.getPayMonth();  //할부개월수
			String amt       = card01vo.getAmt();       //결제금액
			String amtTax    = card01vo.getAmtTax();    //부가가치세
			
			//카드번호 null 체크
			if(cardNo == null || "".equals(cardNo)) {
				logger.warn("!!! 카드번호 null");
				rtnVo.setRtnCode("01");
				rtnVo.setRtnCodeNm(Code.getCodeNm("01"));
				return rtnVo;
			}
			
			//카드길이 체크
			int cardNoLen = cardNo.length();
			if(cardNoLen <10 || cardNoLen > 16) {
				logger.warn("!!! 카드 길이 오류");
				rtnVo.setRtnCode("01");
				rtnVo.setRtnCodeNm(Code.getCodeNm("01"));
				return rtnVo;				
			}

			//카드번호 숫자형태 체크
			if(!chkNum(cardNo)) {
				logger.warn("!!! 카드번호 숫자 오류");
				rtnVo.setRtnCode("01");
				rtnVo.setRtnCodeNm(Code.getCodeNm("01"));
				return rtnVo;				
			}

			//유효기간 체크
			if(validDate == null || "".equals(validDate)) {
                logger.warn("!!! 유효기간 null");
				rtnVo.setRtnCode("02");
				rtnVo.setRtnCodeNm(Code.getCodeNm("02"));
				return rtnVo;
			}

			//유효기간 길이 체크
			int validDateLen = validDate.length();
			if(validDateLen != 4) {
				logger.warn("!!! 유효기간 길이 오류");
				rtnVo.setRtnCode("02");
				rtnVo.setRtnCodeNm(Code.getCodeNm("02"));
				return rtnVo;				
			}
			
			//유효기간 숫자형태 체크
			if(!chkNum(validDate)) {
				logger.warn("!!! 유효기간 숫자 오류");
				rtnVo.setRtnCode("02");
				rtnVo.setRtnCodeNm(Code.getCodeNm("02"));
				return rtnVo;				
			}
			
			//cvc 체크
			if(cvc == null || "".equals(cvc)) {
                logger.warn("!!! cvc null");
				rtnVo.setRtnCode("03");
				rtnVo.setRtnCodeNm(Code.getCodeNm("03"));
				return rtnVo;
			}

			//cvc 길이 체크
			int cvcLen = cvc.length();
			if(cvcLen != 3) {
				logger.warn("!!! 카드길이 오류");
				rtnVo.setRtnCode("03");
				rtnVo.setRtnCodeNm(Code.getCodeNm("03"));
				return rtnVo;				
			}
			
			//cvc 숫자형태 체크
			if(!chkNum(cvc)) {
				logger.warn("!!! cvc 숫자 오류");
				rtnVo.setRtnCode("03");
				rtnVo.setRtnCodeNm(Code.getCodeNm("03"));
				return rtnVo;				
			}
			
			//할부개월수 체크
			if(payMonth == null || "".equals(payMonth)) {
                logger.warn("!!! 할부개월수 null");
				rtnVo.setRtnCode("04");
				rtnVo.setRtnCodeNm(Code.getCodeNm("04"));
				return rtnVo;
			}
			
			//할부개월수 범위 체크
			int chkPayMonth = Integer.parseInt(payMonth);
			if(chkPayMonth > 12) {
                logger.warn("!!! 할부개월수 오류");
				rtnVo.setRtnCode("04");
				rtnVo.setRtnCodeNm(Code.getCodeNm("04"));
				return rtnVo;				
			}

			//결제금액 체크
			if(amt == null || "".equals(amt)) {
                logger.warn("!!! 결제금액 null");
				rtnVo.setRtnCode("05");
				rtnVo.setRtnCodeNm(Code.getCodeNm("05"));
				return rtnVo;
			}

			//결제금액 체크
			long amtLong = Long.parseLong(amt);
			if(amtLong < 100 || amtLong > 1000000000) {
                logger.warn("!!! 결제금액 오류");
				rtnVo.setRtnCode("05");
				rtnVo.setRtnCodeNm(Code.getCodeNm("05"));
				return rtnVo;					
			}
			
			//부가가치세 체크(옵션)
			long amtTaxLong = 0;
			boolean taxCalcYn = false;
			if(amtTax == null || "".equals(amtTax)) {
				//부가가치세 계산
				taxCalcYn = true;
			}else {
				amtTaxLong = Long.parseLong(amtTax);
				if(amtLong <= amtTaxLong ) {
					taxCalcYn = true;
				}
			}
			
			//부가세 계산
			if(taxCalcYn) {
				amtTaxLong = Math.round(amtLong/11);
				card01vo.setAmtTax(amtTaxLong+"");
			}

			//관리번호 생성
			String manageNo = genMngNo();
			
			card01vo.setManageNo(manageNo);
			
			//카드사전송데이터
			String cardData = makeSendData01(card01vo);
			
			//데이터 저장
			CardPayMng cardPayMng = new CardPayMng(manageNo, "1", cardData, "", "", "");
			int cnt = cardPayMngMapper.insertCard(cardPayMng);
			
			logger.info("### "+cnt+"건 저장 완료");

			//return 데이터
			rtnVo.setRtnCode("00");
			rtnVo.setRtnCodeNm(Code.getCodeNm("00"));
			rtnVo.setManageNo(manageNo);
			rtnVo.setCardSendData(cardData);
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("!!! 카드승인 요청 Exception : "+e.toString());
			
			rtnVo.setRtnCode("99");
			rtnVo.setRtnCodeNm(Code.getCodeNm("99"));
			return rtnVo;
		}

		return rtnVo;
	}

	/**
	 * 카드취소
	 * 
	 */
	@PutMapping("/card")
	public Card02Vo cancelCard(@RequestBody Card02Vo card02vo) {
		
		Card02Vo rtnVo = new Card02Vo();
		
		logger.info("## 카드취소 요청");
		
		try {
			
			String manageNo = card02vo.getManageNo();
			String amt      = card02vo.getAmt();
			String amtTax   = card02vo.getAmtTax();

			//관리번호 체크
			if(manageNo == null|| "".equals(manageNo)) {
				logger.warn("!!! 관리번호 null");
				rtnVo.setRtnCode("07");
				rtnVo.setRtnCodeNm(Code.getCodeNm("07"));
				return rtnVo;
			}
			
			//취소여부 체크
			CardPayMng carPayMng = cardPayMngMapper.selectCard(manageNo);
			
			if(carPayMng == null || "".equals(carPayMng)) {
				logger.warn("!!! 데이터 없음");
				rtnVo.setRtnCode("07");
				rtnVo.setRtnCodeNm(Code.getCodeNm("07"));
				return rtnVo;					
			}
			
			String cancelYn = carPayMng.getCancelYn();
			if("1".equals(cancelYn)) {
				logger.warn("!!! 이미 취소된 건");
				rtnVo.setRtnCode("08");
				rtnVo.setRtnCodeNm(Code.getCodeNm("08"));
				return rtnVo;				
			}

			//취소금액 체크
			if(amt == null || "".equals(amt) || "0".equals(amt)) {
                logger.warn("!!! 취소금액 null");
				rtnVo.setRtnCode("05");
				rtnVo.setRtnCodeNm(Code.getCodeNm("05"));
				return rtnVo;
			}
			
			long amtLong = Long.parseLong(amt);

			//부가가치세 체크(옵션)
			if(amtTax == null|| "".equals(amtTax)) {
				//부가가치세 계산
				long amtTaxLong = Math.round(amtLong/11);
				card02vo.setAmtTax(amtTaxLong+"");
			}
			
			//관리번호 생성
			String cnlManageNo = genMngNo();
			
			//카드사전송데이터
			String cardData = makeSendData02(card02vo, cnlManageNo);
			
			//취소 데이터 저장
			CardPayMng cardPayMng = new CardPayMng(cnlManageNo, "2", cardData, "", "", "");
			int cnt = cardPayMngMapper.insertCard(cardPayMng);
			logger.info("### "+cnt+"건 입력 완료");
			
			//원거래 데이터 update
			cardPayMng = new CardPayMng(manageNo, "", "", "1", cnlManageNo, "");
			cnt = cardPayMngMapper.updateCard(cardPayMng);
			logger.info("### "+cnt+"건 업데이트 완료");

			//return 데이터
			rtnVo.setRtnCode("00");
			rtnVo.setRtnCodeNm(Code.getCodeNm("00"));
			rtnVo.setManageNo(cnlManageNo);
			rtnVo.setCardSendDate(cardData);

		}catch(Exception e) {
			e.printStackTrace();
			logger.error("!!! 카드취소 요청 Exception : "+e.toString());
			
			rtnVo.setRtnCode("99");
			rtnVo.setRtnCodeNm(Code.getCodeNm("99"));
			return rtnVo;			
		}

		return rtnVo;
	}

	/**
	 * 카드조회
	 * 
	 */
	@GetMapping("/card")
	public Card03Vo selectCard(@RequestBody Card03Vo card03vo) {
		
		Card03Vo rtnVo = new Card03Vo();
		
		logger.info("## 카드조회 요청");
		
		try {
			
			String manageNo = card03vo.getManageNo();

			//관리번호 체크
			if(manageNo == null || "".equals(manageNo)) {
				logger.warn("!!! 관리번호 null");
				rtnVo.setRtnCode("07");
				rtnVo.setRtnCodeNm(Code.getCodeNm("07"));
				return rtnVo;
			}
			
			//데이터 조회
			CardPayMng carPayMng = cardPayMngMapper.selectCard(manageNo);
			
			if(carPayMng == null || "".equals(carPayMng)) {
				logger.warn("!!! 데이터 없음");
				rtnVo.setRtnCode("07");
				rtnVo.setRtnCodeNm(Code.getCodeNm("07"));
				return rtnVo;					
			}
			
			manageNo = carPayMng.getMngNo();
			String dataDiv = carPayMng.getDataDiv();
			String cardData = carPayMng.getCardData();
			String cancelYn = carPayMng.getCancelYn();
			String assoManageNo = carPayMng.getCancelMngNo();

	        PacketWork packetWork = new PacketWork();
	        packetWork.addItem(DataVo.create("dataLen", 4, null));
	        packetWork.addItem(DataVo.create("payDiv", 10, null));
	        packetWork.addItem(DataVo.create("mngNo", 20, null));
	        packetWork.addItem(DataVo.create("cardNo", 20, null));
	        packetWork.addItem(DataVo.create("payMonth", 2, null));
	        packetWork.addItem(DataVo.create("validDate", 4, null));
	        packetWork.addItem(DataVo.create("cvc", 3, null));
	        packetWork.addItem(DataVo.create("amt", 10, null));
	        packetWork.addItem(DataVo.create("amtTax", 10, null));
	        packetWork.addItem(DataVo.create("originMngNo", 20, null));
	        
	        if("1".equals(dataDiv)){ //결제데이터
	        	//packetWork.addItem(DataVo.create("encCardData", 300, null)); //TODO table insert시 right trim 현상 해결 필요.
	        	packetWork.addItem(DataVo.create("encCardData", 96, null));
	        }
	        packetWork.parse(cardData);
	        
	        
	        String payDiv = packetWork.getItem("payDiv").raw().trim();
	        String amt = packetWork.getItem("amt").raw().trim();
	        String amtTax = Long.parseLong(packetWork.getItem("amtTax").raw())+"";
	        
	        String cardNo = null;
	        String validDate = null;
	        String cvc = null;
	        if("1".equals(dataDiv)){ //결제데이터
	        	String[] decCardInfo = encryptor.decrypt(packetWork.getItem("encCardData").raw()).split("\\|");
	        	cardNo = masking("1",decCardInfo[0]); //카드번호
	        	validDate = decCardInfo[1]; //유효기간
	        	cvc = decCardInfo[2]; //cvc
	        }

			//return 데이터
			rtnVo.setRtnCode("00");
			rtnVo.setRtnCodeNm(Code.getCodeNm("00"));
			rtnVo.setManageNo(manageNo);
			rtnVo.setCardNo(cardNo);
			rtnVo.setValidDate(validDate);
			rtnVo.setCvc(cvc);
			rtnVo.setPayDiv(payDiv);
			rtnVo.setAmt(amt);
			rtnVo.setAmtTax(amtTax);
			rtnVo.setCancelYn(cancelYn);
			rtnVo.setAssoManageNo(assoManageNo);
			
		}catch(Exception e) {
            e.printStackTrace();
			logger.error("!!! 카드조회 요청 Exception : "+e.toString());
			rtnVo.setRtnCode("99");
			rtnVo.setRtnCodeNm(Code.getCodeNm("99"));
			return rtnVo;	
			
		}

		return rtnVo;
	}

/**************************************** UTIL *********************************************************/
	
	/**
	 * 관리번호 만들기
	 * 
	 * 년월일시분초 + 랜덤6자리 숫자
	 */
	private String genMngNo() {
		String mngNo = "";
        
		String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		
		StringBuffer buffer = new StringBuffer();
		Random random = new Random();
		 
		String chars[] = "1,2,3,4,5,6,7,8,9,0".split(",");
		 
		for(int i=0; i<6; i++){
		    buffer.append(chars[random.nextInt(chars.length)]);
		}
		mngNo = time+buffer.toString();
		
		return mngNo;
		
	}
	
	/**
	 * 전송데이터 만들기
	 */
	private String makeSendData01(Card01Vo card01vo) {

		String manageNo  = card01vo.getManageNo();  //관리번호
		String cardNo    = card01vo.getCardNo();    //카드번호
		String validDate = card01vo.getValidDate(); //유효기간
		String cvc       = card01vo.getCvc();       //cvc
		String payMonth  = card01vo.getPayMonth();  //할부개월수
		String amt       = card01vo.getAmt();       //결제금액
		String amtTax    = card01vo.getAmtTax();    //부가가치세
		
		String encCardInfo = encryptor.encrypt(cardNo+"|"+validDate+"|"+cvc);
		
		StringBuffer sendData = new StringBuffer();
		//Header
		sendData.append(fillLeft("446",4," "));    //데이터 길이
		sendData.append(fillRight("PAYMENT",10," "));//데이터 구분
		sendData.append(fillRight(manageNo,20," ")); //관리번호
		
		//Body
		sendData.append(fillRight(cardNo,20," "));      //카드번호
		sendData.append(fillLeft(payMonth,2,"0"));    //할부개월수
		sendData.append(fillRight(validDate,4," "));    //카드유효기간
		sendData.append(fillRight(cvc,3," "));          //cvc
		sendData.append(fillLeft(amt,10," "));         //거래금액
		sendData.append(fillLeft(amtTax,10,"0"));      //부가가치세
		sendData.append(fillRight("",20," "));          //원거래관리번호
		sendData.append(fillRight(encCardInfo,300," "));//암호화된카드정보
		sendData.append(fillRight("",47," "));          //예비

		return sendData.toString();
	}
	
	/**
	 * 취소 전송데이터 만들기
	 */
	private String makeSendData02(Card02Vo card02vo, String cnlManageNo){
		
		String amt = card02vo.getAmt();           //결제금액
		String amtTax = card02vo.getAmtTax();     //부가가치세
		String manageNo = card02vo.getManageNo(); //관리번호
		
		StringBuffer sendData = new StringBuffer();
		//Header
		sendData.append(fillLeft("446",4," "));         //데이터 길이
		sendData.append(fillRight("CANCEL",10," "));    //데이터 구분
		sendData.append(fillRight(cnlManageNo,20," ")); //관리번호
		
		//Body
		sendData.append(fillRight("",20," "));       //카드번호
		sendData.append(fillLeft("",2,"0"));         //할부개월수
		sendData.append(fillRight("",4," "));        //카드유효기간
		sendData.append(fillRight("",3," "));        //cvc
		sendData.append(fillLeft(amt,10," "));       //거래금액
		sendData.append(fillLeft(amtTax,10,"0"));    //부가가치세
		sendData.append(fillRight(manageNo,20," ")); //원거래관리번호
		sendData.append(fillRight("",300," "));      //암호화된카드정보
		sendData.append(fillRight("",47," "));       //예비
		
		return sendData.toString();
	}
	
	/**
	 * 오른쪽 바이트 체우기
	 */
    private static String fillRight(String value, int length, String item) {
        StringBuffer padded = new StringBuffer(value);
        while (padded.toString().getBytes().length < length) {
            padded.append(item);
        }
        return padded.toString();
    }	

	/**
	 * 왼쪽 바이트 체우기
	 */
    private static String fillLeft(String value, int length, String item) {
        StringBuffer padded = new StringBuffer();
        int fillByte = length - value.getBytes().length;
        while (padded.toString().getBytes().length < fillByte) {
            padded.append(item);
        }
        padded.append(value);
        
        return padded.toString();
    }
    
	/**
	 * 숫자여부 체크
	 */
    private static boolean chkNum(String chkNum){
    	
    	boolean rtn = false;
    	
    	if(chkNum.matches("^[0-9]+$")) {
    		rtn = true;
    	}
    	
    	return rtn;
    	
    }
    
	/**
	 * 마스킹
	 */
    private static String masking(String div, String originData) {
        String rtn = "";
        
        if("1".equals(div)) { //카드번호
        	rtn = originData.replaceAll("(?<=.{6}).(?=.*...)" , "*");
        }else {
        	rtn = originData;
        }
        
        return rtn;
    	
    }
    
}
