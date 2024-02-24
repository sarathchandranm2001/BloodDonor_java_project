import java.io.*;
import java.util.*;
import java.sql.*;
class DBU {

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
	  System.out.println("Enter no and new name and salary");
	  no = s.nextInt();
	  s.nextLine();
	  name = s.nextLine();
	  sal = s.nextFloat();
	  String str = "update emp set ename='";
	  str = str + name + "',esal=";
	  str = str + sal +" where eno=" + no;
	  System.out.println(str);
	  st.executeUpdate(str);
	  System.out.println("4");
	  System.out.println("One Record is Updated..");
 	}
	catch(Exception e) {
		System.out.println("Error " + e);
	}
  }
}