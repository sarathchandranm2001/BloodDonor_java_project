import java.io.*;
import java.util.*;
import java.sql.*;
class DBSMD {
   public static void main(String args[]) {
	Connection con;
	Statement st;
	ResultSet rs;
	ResultSetMetaData rd;
	try {
	  Class.forName("com.mysql.jdbc.Driver");
	  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems_db?characterEncoding=utf8","root","");
	  st = con.createStatement();
	  String str = "select ename,esal from emp";
	  rs = st.executeQuery(str);
	  rd = rs.getMetaData();
	  int cc = rd.getColumnCount();
	  int i;
	  for(i=1;i <= cc;i++) {
		System.out.print(rd.getColumnName(i) + "\t");
	  }
	  System.out.println("");
	  while(rs.next()) {
		for(i = 1;i <= cc;i++) {
			System.out.print(rs.getString(i) + "\t");
		}
		System.out.println("");
	  }
 	}
	catch(Exception e) {
		System.out.println("Error " + e);
	}
  }
}