import java.io.*;
import java.util.*;
import java.sql.*;
class DBD {

   public static void main(String args[]) {
	Connection con;
	Statement st;
	int no;
	Scanner s = new Scanner(System.in);
	try {
	  Class.forName("com.mysql.jdbc.Driver");
	  System.out.println("1");
	  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sarath_db?characterEncoding=utf8","root","Sarath@9747");
	  System.out.println("2");
	  st = con.createStatement();
	  System.out.println("3");
	  System.out.println("Enter no to be deleted");
	  no = s.nextInt();
	  String str = "delete from emp where eno=" + no;
	  System.out.println(str);
	  st.executeUpdate(str);
	  System.out.println("4");
	  System.out.println("One Record is Deleted..");
 	}
	catch(Exception e) {
		System.out.println("Error " + e);
	}
  }
}