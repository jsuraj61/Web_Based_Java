package com.dal;

import java.sql.SQLException;
import java.util.List;

import com.pojo.BankAccount;
public interface BankDal {

	
	//CRUD
		//select * from BankAccount;
	List<BankAccount> getallaccounts();
		//add new Account
		int addAccount(BankAccount obj);
		
//		//update Account
		int updateAccount(BankAccount obj);
//		//delete Account
		int  deleteAccount(int id);
	
		public String moneyTransfer(int sid,int did,double amount) throws SQLException;
		
		public void depositeMoney(double amount);
		
		public void withdrawMoney(double amount);
	
}
