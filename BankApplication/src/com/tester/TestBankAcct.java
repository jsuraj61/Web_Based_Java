package com.tester;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.dal.BankAcctImpl;
import com.pojo.BankAccount;
import com.util.DBUtil;

public class TestBankAcct {

	public static void main(String[] args) {

		try {
			DBUtil.openConnection();

			Scanner sc = new Scanner(System.in);

			BankAcctImpl bankimpl=new BankAcctImpl();
			
			boolean exit = false;
			while (!exit) {
				System.out.println("Options: 1.Show all Accounts \n"
						+ "2.Add New Account "
						+ "\n3.Update/Edit Account"
						+ "\n4.Delete account "
						+ "\n5.Deposite for specific account"
						+ "\n6.Withdraw for specific account"
						+ "\n7.Transfer Money from one account to Other Account"
						+ "8.exit");
				System.out.println("Enter your choice");
				switch (sc.nextInt()) {
				case 1:
						List<BankAccount> list=bankimpl.getallaccounts();
						list.forEach(a -> System.out.println(a));
					break;

				case 2:
//						insert account
					System.out.println("enter AcctID, Name,Type,Balance");
					BankAccount dto=new BankAccount(sc.nextInt(), sc.next(), sc.next(), sc.nextDouble());
					int i=bankimpl.addAccount(dto);
					if(i>0) {
						System.out.println("Account inserted");
					}
					break;
				case 3:
//						update account
					System.out.println("edit : acctID, Newname,Newtype,Newbalace ");
					
					dto=new BankAccount(sc.nextInt(), sc.next(), sc.next(), sc.nextDouble());
					
					 i=bankimpl.updateAccount(dto);
					if (i>0) {
						System.out.println("account updated");
					}
					break;
				case 4:
//					delete account
					System.out.println("Enter account id to delete");
					int id=sc.nextInt();
					i=bankimpl.deleteAccount(id);
					if (i>0) {
						System.out.println("account deleted");
					}
					break;
				case 5:
//					Deposite for specific account
					System.out.println("Enter senderID , Receiver ID , Amount to transfer");
					
					
					
					break;
				case 6:
//					Withdraw for specific account
					
					
					break;
				case 7:
//					Transfer Money from one account to Other Account					
					System.out.println("Enter senderID , Receiver ID , Amount to transfer");
					
					String status=bankimpl.moneyTransfer(sc.nextInt(), sc.nextInt(), sc.nextDouble());
					System.out.println(status);
					
				break;
				case 8:
					exit=true;
				break;

				default:
					break;
				}
			}
			

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
