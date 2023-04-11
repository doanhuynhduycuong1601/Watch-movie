package view;

import entity.User;

public class Account {
	public static User account = null;
	
	public static String id() {
		return account == null ? null : account.getId();
	}
	
}
