package com.cardwork.entity;

public class Card03Vo {
	private String rtnCode ="";      //응답코드
	private String rtnCodeNm ="";    //응답코드명
	private String manageNo ="";     //관리번호
	private String cardNo ="";       //카드번호
	private String validDate ="";    //유효기간
	private String cvc ="";          //cvc
	private String payDiv ="";       //결제/취소구분
	private String amt ="";          //금액
	private String amtTax ="";       //부가가치세
	private String cancelYn ="";      //취소여부
	private String assoManageNo =""; //관련관리번호
	
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
	public String getManageNo() {
		return manageNo;
	}
	public void setManageNo(String manageNo) {
		this.manageNo = manageNo;
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
	public String getPayDiv() {
		return payDiv;
	}
	public void setPayDiv(String payDiv) {
		this.payDiv = payDiv;
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
	public String getCancelYn() {
		return cancelYn;
	}
	public void setCancelYn(String cancelYn) {
		this.cancelYn = cancelYn;
	}
	public String getAssoManageNo() {
		return assoManageNo;
	}
	public void setAssoManageNo(String assoManageNo) {
		this.assoManageNo = assoManageNo;
	}

}
