package com.dal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.pojo.BankAccount;
import com.util.DBUtil;

public class BankAcctImpl implements BankDal {

	private Connection con;
	private Statement stmt;
	private PreparedStatement pst1,pst2,pst3; //to execute queries
	ResultSet rset; // for moving cursor row by row 
	private CallableStatement cstmt; //to call procedure
	
	
	public BankAcctImpl() throws SQLException {
		//comman connection
		con=DBUtil.getCon();
		//
		stmt=con.createStatement();
		System.out.println("--statement crated for static sql---");
		
		pst1=con.prepareStatement("insert into accounts values(?,?,?,?)");
		pst2=con.prepareStatement("update accounts set name=? , type=? ,bal=? where id=?");
		pst3=con.prepareStatement("delete from accounts where id=?");
		//procedure 
		cstmt=con.prepareCall("{call transfer_funds(?,?,?,?,?)}");
		
		//parameter no 4 and 5 data type is double JVM send this info to DB
		cstmt.registerOutParameter(4, Types.DOUBLE);
		cstmt.registerOutParameter(5, Types.DOUBLE);
		
		
	}

	

	@Override
	public List<BankAccount> getallaccounts() {
		//select * from accounts;
		
		List<BankAccount> allacct=new ArrayList<BankAccount>();
		try {
			
			ResultSet rset=stmt.executeQuery("select * from accounts");
			
			while(rset.next()) {
				allacct.add(new BankAccount(rset.getInt(1),
						rset.getString(2),
						rset.getString(3),
						rset.getDouble(4)));
			}
			return allacct;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int addAccount(BankAccount obj) {
//		insert into accounts values(?,?,?,?)
		try {
			pst1.setInt(1, obj.getId());
			pst1.setString(2, obj.getName());
			pst1.setString(3, obj.getType());
			pst1.setDouble(4, obj.getBalance());
			
			//execute statment on db 
			int i=pst1.executeUpdate();
			System.out.println("---inserted obj:"+obj);
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateAccount(BankAccount obj) {
		// TODO Auto-generated method stub
		
		//update accounts set name=? , type=? ,bal=? where id=?
		try {
			pst2.setString(1, obj.getName());
			pst2.setString(2, obj.getType());
			pst2.setDouble(3, obj.getBalance());
			pst2.setInt(4, obj.getId());
			
		//execute stmt
			int i= pst2.executeUpdate();
			System.out.println("updated obj : "+obj);
			
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public int deleteAccount(int id) {
		// TODO Auto-generated method stub
//		delete from accounts where id=?
		
		try {
			pst3.setInt(1, id);
			int i=pst3.executeUpdate();
			return i;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public String moneyTransfer(int sid, int did, double amount) throws SQLException {
		// TODO Auto-generated method stub
		
		cstmt.setInt(1, sid);//1st IN parameter
		cstmt.setInt(2, did);//2nd IN parameter
		cstmt.setDouble(3, amount);//3rd in parameter
		
		//execute Stored Procedure
		cstmt.execute();
		return  "Money Transfer : Sender Balance="+cstmt.getDouble(4)+"   Reciver Balance:"+cstmt.getDouble(5);
	}


	@Override
	public void depositeMoney(int id,double amount) {
		// TODO Auto-generated method stub
		String query="select bal from accounts where id=?";
		
		try {
			cstmt=con.prepareCall(query);
			
			cstmt.setInt(1, id);
			rset=cstmt.executeQuery();
			
			if (rset.next()) {
				Double temp=rset.getDouble(1)+amount;
				query="update accounts set bal=? where id=?";
				CallableStatement cstmt1=con.prepareCall(query);
				cstmt1.setDouble(1, temp);
				cstmt1.setInt(2, id);
				cstmt1.executeUpdate();
			}
			else {
				System.out.println("acctId Invalid");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void withdrawMoney(double amount) {
		// TODO Auto-generated method stub
		
	}
	
}
