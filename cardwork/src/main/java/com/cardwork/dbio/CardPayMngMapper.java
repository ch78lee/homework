package com.cardwork.dbio;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CardPayMngMapper {
	public CardPayMng selectCard(String mng_no);
	public int insertCard(CardPayMng cardPayMng);
	public int updateCard(CardPayMng cardPayMng);
}
