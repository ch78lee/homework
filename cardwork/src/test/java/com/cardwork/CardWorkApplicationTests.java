package com.cardwork;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.cardwork.entity.Card01Vo;
import com.cardwork.entity.Card02Vo;
import com.cardwork.entity.Card03Vo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
public class CardWorkApplicationTests {

	@Autowired
	private MockMvc mockmvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void paymentTest() throws Exception {
		
		String url = "/card";
		Card01Vo card01vo = new Card01Vo();
		card01vo.setCardNo("1234123412341234");
		card01vo.setManageNo("4321");
		String content = objectMapper.writeValueAsString(card01vo);
		
		mockmvc.perform(MockMvcRequestBuilders.post(url)
				        .content(content)
				        .contentType(MediaType.APPLICATION_JSON))
		       .andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void cancelTest() throws Exception {
		
		String url = "/card";
		Card02Vo card02vo = new Card02Vo();
		card02vo.setManageNo("4321");
		String content = objectMapper.writeValueAsString(card02vo);
		
		mockmvc.perform(MockMvcRequestBuilders.put(url)
				        .content(content)
				        .contentType(MediaType.APPLICATION_JSON))
		       .andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void selectTest() throws Exception {

		String url = "/card";
		Card03Vo card03vo = new Card03Vo();
		card03vo.setManageNo("54321543215432154321");
		String content = objectMapper.writeValueAsString(card03vo);
		
		mockmvc.perform(MockMvcRequestBuilders.get(url)
				        .content(content)
				        .contentType(MediaType.APPLICATION_JSON))
		       .andDo(MockMvcResultHandlers.print());
	}

}