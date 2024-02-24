import java.io.*;
import java.sql.*;
import java.util.*;
class CDataBase {
  Connection con;
  Statement st;
  public CDataBase(String dbname) {
    try {
	Class.forName("com.mysql.jdbc.Driver");
	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbname +"?characterEncoding=utf8","root","");
	st = con.createStatement();
    }
    catch(Exception e) {
	System.out.println("Error :" + e);
    }
   }
   public void idu(String sql) {
	try {
		st.executeUpdate(sql);
	}
	catch(Exception e) {
		System.out.println("Error :" + e);
	}
    }
    public ResultSet select(String sql) {
	ResultSet rs = null;
	try {
		rs = st.executeQuery(sql);
	}
	catch(Exception e) {
		System.out.println("Error :" + e);
	}
	return rs;
   }
}
class MDataBase {
	public static void main(String args[]) {
          try {
		CDataBase objDB = new CDataBase("ems_db");
		ResultSet rs = null;
		int no;
		String name;
		float sal;
		Scanner s = new Scanner(System.in);
		System.out.println("Enter no name and salary");
	  	no = s.nextInt();
	  	s.nextLine();
	  	name = s.nextLine();
		sal = s.nextFloat();
	  	String str = "insert into emp values(";
	  	str = str + no + ",'";
	  	str = str + name + "',";
	  	str = str + sal +")";
	  	System.out.println(str);
		objDB.idu(str);
		rs = objDB.select("select * from emp");
	 	while(rs.next()) {
			System.out.println(rs.getString("eno") + "  " + rs.getString("ename") + "  " + rs.getString("esal"));
	  	}
	 }
	 catch(Exception e) {
		System.out.println("Error :" + e);
	}
    }
}