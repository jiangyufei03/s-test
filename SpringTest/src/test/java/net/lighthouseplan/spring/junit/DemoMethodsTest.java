package net.lighthouseplan.spring.junit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.lighthouseplan.spring.junit.models.Account;

public class DemoMethodsTest {
//目標：事前にインスタンス作成したい
	private static DemoMethods demo;
	
	// テストの実行前にインスタンスを作成
	@BeforeEach
	public void prepare() {
		demo = new DemoMethods();
	}
	
	//奇数を入れた場合、trueになるてすと
	@Test
	// 命名規則：testメソッド名_奇数を入れるテストですよ_true
	//                    どんな(今回は奇数Odd)　　   //結果(大文字)
	public void testIsOdd_OddNumber_True() {
		//trueかどうかを判断する（assert）	//7＝＞引数
		//DemoMEthodｓのisOddメソッドを呼び出す
		assertTrue(demo.isOdd(7));
	}
	
	// 偶数を入れた場合、falseになるテスト
	//必ず宣言
	@Test
	public void testIsOdd_EvenNumber_False() {
		assertFalse(demo.isOdd(8));
	}
	
	//4パタンを考えられる
	//時間の都合上、3つ書く
	// 名前の長さが4文字以上ではない場合、nullになるテスト
	@Test
	public void testBuildAccount_InvalidUserName_null() {
		// アカウント情報の作成
		//引数　id(long必ず[L]をつける), userName,password
		Account account = demo.buildAccount(1L, "Ana", "12345678");
		assertNull(account);
	}
	
	// パスワードの長さが8文字以上ではない場合、nullになるテスト
	@Test
	public void testBuildAccount_InvalidPassword_null() {
		Account account = demo.buildAccount(2L, "Alice", "123456");
		assertNull(account);
	}
	// 名前の長さが4文字以上 かつ パスワードの長さが8文字以上の場合、
	// notNullで成功するテスト
	@Test
	public void testBuildAccount_ValidUserNameAndPassword_NotNull() {
		Account account = demo.buildAccount(3L, "Dave", "12345678");
		//也可以用assertThat	
		assertNotNull(account);
		//可以确认是否一致
		assertEquals(account.getUsername(), "Alice");
	}
}
