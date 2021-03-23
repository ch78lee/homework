package com.cardwork;

import java.util.HashMap;

/**
 * 코드관리 class
 * @author lch
 */
public class Code {
	
	private static HashMap<String, String> code1 = new HashMap<String,String>();
	static {
		code1.put("00","SUCCESS");
		code1.put("01","CARDNO ERROR");
		code1.put("02","VAILD DATE ERROR");
		code1.put("03","CVC ERROR");
		code1.put("04","PAY MONTH ERROR");
		code1.put("05","AMT ERROR");
		code1.put("06","TAX ERROR");
		code1.put("07","MANAGENO ERROR");
		code1.put("08","CANCEL MANAGENO");
		code1.put("99","SYSTEM ERROR");
	}

	/**
	 * 코드명 조회
	 * @param code
	 * @return codeNm
	 */
	public static String getCodeNm(String code) {

		return code1.get(code);
		
	}
}
