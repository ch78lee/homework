package com.cardwork.entity;

public class Card02Vo {

	private String rtnCode ="";      //응답코드
	private String rtnCodeNm ="";    //응답코드명
	private String amt ="";          //결제금액
	private String amtTax ="";       //부가가치세
	private String manageNo ="";     //관리번호
	private String cardSendDate =""; //카드사데이터
	
	public String getRtnCode() {
		return rtnCode;
	}
	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}
	public String getRtnCodeNm() {
		return rtnCodeNm;
	}
	public void setRtnCodeNm(String rtnCodeNm) {
		this.rtnCodeNm = rtnCodeNm;
	}
	public String getAmt() {
		return amt;
	}
	public void setAmt(String amt) {
		this.amt = amt;
	}
	public String getAmtTax() {
		return amtTax;
	}
	public void setAmtTax(String amtTax) {
		this.amtTax = amtTax;
	}
	public String getManageNo() {
		return manageNo;
	}
	public void setManageNo(String manageNo) {
		this.manageNo = manageNo;
	}
	public String getCardSendDate() {
		return cardSendDate;
	}
	public void setCardSendDate(String cardSendDate) {
		this.cardSendDate = cardSendDate;
	}

}
