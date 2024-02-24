import java.io.*;
import java.util.*;
import java.sql.*;
class DBI {

   public static void main(String args[]) {
	Connection con;
	Statement st;
	int no;
	String name;
	float sal;
	Scanner s = new Scanner(System.in);
	try {
	  Class.forName("com.mysql.jdbc.Driver");
	  System.out.println("1");
	  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems_db?characterEncoding=utf8","root","");
	  System.out.println("2");
	  st = con.createStatement();
	  System.out.println("3");
	  System.out.println("Enter no name and salary");
	  no = s.nextInt();
	  s.nextLine();
	  name = s.nextLine();


	  sal = s.nextFloat();
	  //String str = "insert into emp values(2,'ddd',40000)";
	  String str = "insert into emp values(";
	  str = str + no + ",'";
	  str = str + name + "',";
	  str = str + sal +")";
	  System.out.println(str);
	  st.executeUpdate(str);
	  System.out.println("4");
	  System.out.println("One Record is Inserted..");
 	}
	catch(Exception e) {
		System.out.println("Error " + e);
	}
  }
}