package com.cardwork.entity;

public class Card01Vo {
    
	private String rtnCode ="";      //응답코드
	private String rtnCodeNm ="";    //응답코드명
	private String cardNo ="";       //카드번호
	private String validDate ="";    //유효기간
	private String cvc ="";          //cvc
	private String payMonth ="";     //할부개월수
	private String amt ="";          //결제금액
	private String amtTax ="";       //부가가치세
	private String manageNo ="";     //관리번호
	private String cardSendData =""; //카드사데이터
	
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
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getValidDate() {
		return validDate;
	}
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}
	public String getCvc() {
		return cvc;
	}
	public void setCvc(String cvc) {
		this.cvc = cvc;
	}
	public String getPayMonth() {
		return payMonth;
	}
	public void setPayMonth(String payMonth) {
		this.payMonth = payMonth;
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
	public String getCardSendData() {
		return cardSendData;
	}
	public void setCardSendData(String cardSendData) {
		this.cardSendData = cardSendData;
	}


}
