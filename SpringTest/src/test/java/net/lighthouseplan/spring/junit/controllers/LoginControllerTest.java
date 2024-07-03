package net.lighthouseplan.spring.junit.controllers;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import net.lighthouseplan.spring.junit.services.AccountService;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

	//注意需要test的这个class有没有用其他的class的方法
	
	@Autowired
	private MockMvc mockMvc;
	
	//外部依存の解除
	@MockBean
	private AccountService accountService;
	
	// サービスクラスを使ったデータ作成
	//准备数据
	@BeforeEach
	public void prepareData() {
	// ログイン成功： username "Alice", password "12345678"  ⇒true
		when(accountService.validateAccount("Alice", "12345678")).thenReturn(true);
		// ログイン失敗： username="Ana", password="123"  ⇒false
		when(accountService.validateAccount(eq("Ana"), eq("123"))).thenReturn(false);
	}
	
	// ログイン画面を正しく取得するテスト
	@Test
	public void testGetLoginPage_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.get("/login");
		mockMvc.perform(request)
				.andExpect(view().name("login.html"));
	}
		
	// ログインが成功した場合のテスト
	// ログインが成功したら「helo.html」に遷移すること、入力された値が渡されていることのテスト
	@Test
	public void testLogin_ExistingUserName_True() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders
				.post("/login")
				.param("username", "Alice")
				.param("password", "12345678");
		mockMvc.perform(request)
				.andExpect(view().name("hello.html"));
	}
	
	// ログインが失敗した場合のテスト
	// ログインが失敗したら画面が遷移しないこと、入力された値が渡されていることのテスト
	@Test
	public void testLogin_NewUserNameAndNewPassword_false() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders
				.post("/login")
				.param("username", "Ana")
				.param("password", "123");
		mockMvc.perform(request)
		.andExpect(model().attribute("error", true))
		.andExpect(view().name("login.html"));
	}
	
}
