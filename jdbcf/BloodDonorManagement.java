import java.sql.*;
import java.util.Scanner;

public class BloodDonorManagement {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/sarath_db?characterEncoding=utf8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Sarath@9747";

    public static void main(String[] args) {
        initializeDatabase();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Blood Donor Management System");
            System.out.println("1. Register Donor");
            System.out.println("2. Search Donor");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    registerDonor(scanner);
                    break;
                case 2:
                    searchDonor(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Connecting to database...");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connected to database successfully!");
    
            String createTableSQL = "CREATE TABLE IF NOT EXISTS donors (name VARCHAR(255), bloodgroup VARCHAR(5), phonenumber VARCHAR(15), address VARCHAR(255), age INT)";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTableSQL);
                System.out.println("Table created successfully!");
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    

    private static void registerDonor(Scanner scanner) {
        System.out.print("Enter donor name: ");
        String name = scanner.next();
        System.out.print("Enter blood group: ");
        String bloodGroup = scanner.next();
        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.next();
        System.out.print("Enter address: ");
        String address = scanner.next();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String insertSQL = "INSERT INTO donors (name, bloodgroup, phonenumber, address, age) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, bloodGroup);
                preparedStatement.setString(3, phoneNumber);
                preparedStatement.setString(4, address);
                preparedStatement.setInt(5, age);
                preparedStatement.executeUpdate();
                System.out.println("Donor registered successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchDonor(Scanner scanner) {
        System.out.print("Enter blood group to search: ");
        String searchBloodGroup = scanner.next();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String selectSQL = "SELECT * FROM donors WHERE bloodgroup = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
                preparedStatement.setString(1, searchBloodGroup);
                ResultSet resultSet = preparedStatement.executeQuery();

                System.out.println("Donors with Blood Group " + searchBloodGroup + ":");
                while (resultSet.next()) {
                    System.out.println("Name: " + resultSet.getString("name") +
                            ", Age: " + resultSet.getInt("age") +
                            ", Phone Number: " + resultSet.getString("phonenumber") +
                            ", Address: " + resultSet.getString("address"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
