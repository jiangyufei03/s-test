package net.lighthouseplan.spring.junit.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import net.lighthouseplan.spring.junit.models.Account;
import net.lighthouseplan.spring.junit.repositories.AccountRepository;

@SpringBootTest
public class AccountServiceTest {

	@MockBean
	private AccountRepository repository;
	
	//因为是AccountServiceTest所以可以直接用AccountService
	@Autowired
	private AccountService accountService;
	
	@BeforeEach
	public void prepareData() {
		//serviceの repository.findByUsernameメソッド
		//ユーザー情報を作成
		Account alice = new Account(1L, "Alice", "ABC12345");
		
		//login失敗、username が何でもよい場合=null
		when(repository.findByUsername(any())).thenReturn(null);
		// ログインが成功：usernameが"Alice"の場合、Entityの内容を返す（返回上面创建的alice的数据）
		when(repository.findByUsername("Alice")).thenReturn(alice);
	}
	
	// ユーザー名とパスワードが一致していてtrueになるテスト
	@Test
	public void testValidateAccount_true() {
		assertTrue(accountService.validateAccount("Alice",  "ABC12345"));
	}
	
	   // ユーザー名とパスワードが一致していない場合、falseになるテスト
    @Test
    public void testValidateAccount_false() {
        assertFalse(accountService.validateAccount("Alice", "wrongPassword"));
        assertFalse(accountService.validateAccount("Bob", "ABC12345"));
    }

    // アカウント作成が成功する場合のテスト
    @Test
    public void testCreateAccount_success() {
        when(repository.findByUsername("Bob")).thenReturn(null);
        assertTrue(accountService.createAccount("Bob", "XYZ12345"));
        verify(repository).save(new Account("Bob", "XYZ12345"));
    }

    // アカウント作成が失敗する場合のテスト（すでに存在するユーザー名）
    @Test
    public void testCreateAccount_failure() {
        assertFalse(accountService.createAccount("Alice", "ABC12345"));
    }
}
