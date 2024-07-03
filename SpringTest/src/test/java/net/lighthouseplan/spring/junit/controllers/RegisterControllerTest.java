package net.lighthouseplan.spring.junit.controllers;

import static org.mockito.ArgumentMatchers.any;
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
public class RegisterControllerTest {
	@Autowired
	private MockMvc mockMvc;

	// accountService自体は使いたくない
	// 単体テストは一つのクラスだけをテスト
	// 模擬的に使う（实际上没有使用）
	@MockBean
	private AccountService accountService;

	// サービスクラスを使ったデータ作成
	@BeforeEach
	public void prepareData() {
		// serviceのcreateAccountメソッド ：
		// 登録できる場合 "Alice", "Alice1234" true
		// 引数 username、password(設定)
		when(accountService.createAccount("Alice", "Alice1234")).thenReturn(true);
		// 登録できない場合 username="Ana", パスワードはどんな値でも良い false
		when(accountService.createAccount(eq("Ana"), any())).thenReturn(false);
	}
	//まずGetMapping,登録画面が正常に表示できるテスト
	//	例外が発生するパタン throw
	@Test
	public void testGetRegisterPage_Succeed() throws Exception {
		//httpの模擬
		//MockMvcRequestBuildersのmethod改行
		//GetMappingからgetメソッド、确认是否能够返回register.html
		//request是引数
		RequestBuilder request = MockMvcRequestBuilders
				.get("/register");
		mockMvc.perform(request)
				.andExpect(view().name("register.html"));
	}
	
	//ユーザーの登録が成功するかのテスト
	//PostMapping,    param 引数(controllerと同じ)
	//上面指定的Alice,"Alice1234"  => true
	@Test
	public void testRegister_NewUserName_Succeed() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders
				.post("/register")
				.param("username", "Alice")
				.param("password", "Alice1234");
		mockMvc.perform(request)
				.andExpect(view().name("login.html"));
	}
	
	//ユーザーの登録が失敗するかのテスト
	//usernameは既に存在する
	//PostMapping
	//引数 name Ana
	//password 何でもいい
	//error messageの確認  => model().attribute("error", true)
	@Test
	public void testRegister_ExistingUserName_False() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders
				.post("/register")
				.param("username", "Ana")
				.param("password", "1234");
		mockMvc.perform(request)
				.andExpect(model().attribute("error", true))
				.andExpect(view().name("register.html"));
	}
}
