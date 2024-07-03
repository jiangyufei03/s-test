package net.lighthouseplan.spring.junit;

import net.lighthouseplan.spring.junit.models.Account;

public class DemoMethods {
//	奇数か偶数
	public boolean isOdd(int num) {
		if (num % 2 == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	//アカウント作る
	public Account buildAccount(Long id, String username, String password) {
		if (username.length() >= 4 && password.length() >= 8) {
			//インスタンス作成
			return Account.builder()
					.id(id)
					.username(username)
					.password(password)
					.build();
		} else {
			return null;
		}
	}
}
