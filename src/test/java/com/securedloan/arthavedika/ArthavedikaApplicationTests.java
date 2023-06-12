//package com.securedloan.arthavedika;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import org.aspectj.lang.annotation.Before;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.util.Assert;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.securedloan.arthavedika.model.User;
//import com.securedloan.arthavedika.response.Response;
//import com.securedloan.arthavedika.response.Result;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ArthavedikaApplicationTests {
//
//	private MockMvc mockMvc;
//	@Autowired
//	private WebApplicationContext context;
//
//	ObjectMapper objMapper = new ObjectMapper();
//
//	@Before
//	public void setUp() throws Exception {
//		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
//	}
//
//	@Test
//	public void signInTest() throws Exception {
//		User user = new User();
//		user.setUser_id("4az1gc");
//		user.setFirstname("ra");
//		user.setLastname("si");
//		user.setMobile_no("7432598880");
//		user.setEmail_id("singh.kk@gmail.com");
//		user.setCompanyName("arthavedika");
//		user.setPassword("abx12");
//		String jsonRequest = objMapper.writeValueAsString(user);
//		MvcResult result = mockMvc
//				.perform(post("/user/signIn").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(status().isOk()).andReturn();
//		String resultContent = result.getResponse().getContentAsString();
//		Result res = objMapper.readValue(resultContent, Result.class);
//		System.out.println(res.isStatus());
//		try {
//
//			Assert.assertTrue(res.isStatus() == Boolean.TRUE);
//
//		} catch (IllegalArgumentException e) {
//			Assert.fail(e.getMessage());
//			System.out.println("Exception occured");
//		}
//
//		// Assert.assertEquals(200, res.getCode());
//
//	}
//
//	@Test
//	public void loginTest() throws Exception {
//		User user = new User();
//		user.setMobile_no("7432598880");
//		user.setPassword("abx12");
//		String jsonRequest = objMapper.writeValueAsString(user);
//		MvcResult result = mockMvc
//				.perform(get("/user/login").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(status().isOk()).andReturn();
//		String resultContent = result.getResponse().getContentAsString();
//		Response response = objMapper.readValue(resultContent, Response.class);
//		System.out.println(response.isStatus());
//		try {
//
//			Assert.assertTrue(response.isStatus() == Boolean.TRUE);
//			System.out.println("Success !!!");
//
//		} catch (IllegalArgumentException e) {
//			Assert.fail(e.getMessage());
//			System.out.println("Failed !!!");
//		}
//
//
//	}
//	
//
//	@Test
//	public void forgot_passwordTest() throws Exception {
//		User user = new User();
//		user.setEmail_id("singh.kk@gmail.com");
//		String jsonRequest = objMapper.writeValueAsString(user);
//		MvcResult result = mockMvc
//				.perform(post("/user/forgot_password").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(status().isOk()).andReturn();
//		String resultContent = result.getResponse().getContentAsString();
//		Result res = objMapper.readValue(resultContent, Result.class);
//		System.out.println(res.isStatus());
//		try {
//
//			Assert.assertTrue(res.isStatus() == Boolean.TRUE);
//
//		} catch (IllegalArgumentException e) {
//			Assert.fail(e.getMessage());
//			System.out.println("Exception occured");
//		}
//
//		// Assert.assertEquals(200, res.getCode());
//
//	}
//	@Test
//	public void reset_passwordTest() throws Exception {
//		User user = new User();
//		user.setOtp("1452");
//		user.setPassword("14kjh85");
//		String jsonRequest = objMapper.writeValueAsString(user);
//		MvcResult result = mockMvc
//				.perform(post("/user/reset_password").content(jsonRequest).contentType(MediaType.APPLICATION_JSON_VALUE))
//				.andExpect(status().isOk()).andReturn();
//		String resultContent = result.getResponse().getContentAsString();
//		Result res = objMapper.readValue(resultContent, Result.class);
//		System.out.println(res.isStatus());
//		try {
//
//			Assert.assertTrue(res.isStatus() == Boolean.TRUE);
//
//		} catch (IllegalArgumentException e) {
//			Assert.fail(e.getMessage());
//			System.out.println("Exception occured");
//		}
//
//		// Assert.assertEquals(200, res.getCode());
//
//	}
//
//	
//
//}
