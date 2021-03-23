package com.cardwork.dbio;

public class CardPayMng {

	private String mng_no;
	
	private String data_div;
	
	private String card_data;
	
	private String cancel_yn;
	
	private String cancel_mng_no;
	
	private String inputdate;
	
	public CardPayMng(){
		
	}

	public CardPayMng(String mng_no, String data_div, String card_data, String cancel_yn, String cancel_mng_no, String inputdate){
		this.mng_no = mng_no;
		this.data_div = data_div;
		this.card_data = card_data;
		this.cancel_yn = cancel_yn;
		this.cancel_mng_no = cancel_mng_no;
		this.inputdate = inputdate;
	}
	
	public String getMngNo() {
		return mng_no;
	}

	public String getDataDiv() {
		return data_div;
	}

	public String getCardData() {
		return card_data;
	}

	public String getCancelYn() {
		return cancel_yn;
	}

	public String getCancelMngNo() {
		return cancel_mng_no;
	}

	public String getInputdate() {
		return inputdate;
	}

}
