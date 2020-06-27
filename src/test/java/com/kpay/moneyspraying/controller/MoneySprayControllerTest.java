package com.kpay.moneyspraying.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpay.common.Constants.ResponseCode;
import com.kpay.common.domain.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MoneySprayControllerTest {

	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(MoneySprayControllerTest.class);

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;
	
    @Autowired
    private ObjectMapper objectMapper;

	@Before
	public void setup() {
		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
		this.mockMvc = builder.build();
	}

	@Test
	public void testSprayMoney() throws Exception {

		// given
		Map<String, String> params = new HashMap<>();
		params.put("totalAmount", "10000");
		params.put("totalCount", "3");
		String content = objectMapper.writeValueAsString(params);
		
		// when
		final ResultActions actions = mockMvc.perform(post("/v1/kpay/moneyspraying")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("X-USER-ID", "373707")
				.header("X-ROOM-ID", "R01")
				.content(content));
		
		// then
		actions
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(jsonPath("$.code", is(ResponseCode.SUCCESS)))
			.andDo(print());
	}

	@Test
	public void testTakeMoney() throws Exception {
		
		// given
		String senderId = "373707";
		String receiverId = "12345";
		String roomId = "R01";
		
		Map<String, String> params = new HashMap<>();
		params.put("totalAmount", "10000");
		params.put("totalCount", "3");
		
		MvcResult result = mockMvc.perform(post("/v1/kpay/moneyspraying")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("X-USER-ID", senderId)
				.header("X-ROOM-ID", roomId)
				.content(objectMapper.writeValueAsString(params)))
				.andReturn();

		// get token
		Response res = objectMapper.readValue(result.getResponse().getContentAsString(), Response.class);
		Map<String, String> resultMap = (Map<String, String>) res.getResult();
		
		String token = resultMap.get("token");
		
		// when
		final ResultActions actions = mockMvc.perform(post("/v1/kpay/takeMoney")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("X-USER-ID", receiverId)
				.header("X-ROOM-ID", roomId)
				.header("X-TOKEN", token));
		
		// then
		actions
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.code", is(ResponseCode.SUCCESS)))
				.andDo(print());
	}

	@Test
	public void testDescribeMoneySpraying() throws Exception {
		
		String userId = "373707";
		String roomId = "R01";
		
		Map<String, String> params = new HashMap<>();
		params.put("totalAmount", "10000");
		params.put("totalCount", "3");
		
		MvcResult result = mockMvc.perform(post("/v1/kpay/moneyspraying")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.header("X-USER-ID", userId)
				.header("X-ROOM-ID", roomId)
				.content(objectMapper.writeValueAsString(params)))
				.andReturn();

		Response res = objectMapper.readValue(result.getResponse().getContentAsString(), Response.class);
		Map<String, String> resultMap = (Map<String, String>) res.getResult();
		
		String token = resultMap.get("token");
		
		mockMvc.perform(get("/v1/kpay/describe")
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.header("X-USER-ID", userId)
					.header("X-ROOM-ID", roomId)
					.header("X-TOKEN", token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$.code", is(ResponseCode.SUCCESS)))
				.andDo(print());
	}

}
