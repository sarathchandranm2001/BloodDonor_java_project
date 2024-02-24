import java.sql.*;
import java.util.Scanner;

public class blood {

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

            String createTableSQL = "CREATE TABLE IF NOT EXISTS donors (name VARCHAR(255), bloodgroup VARCHAR(5), gender VARCHAR(10), weight FLOAT, phonenumber VARCHAR(15), address VARCHAR(255), age INT)";
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
        String name = scanner.next().toUpperCase();

        // Validate blood group
        String bloodGroup;
        while (true) {
            System.out.print("Enter blood group (A+, A-, B+, B-, AB+, AB-, O+, O-): ");
            bloodGroup = scanner.next().toUpperCase();
            if (isValidBloodGroup(bloodGroup)) {
                break;
            } else {
                System.out.println("Invalid blood group. Please enter a relevant blood group.");
            }
        }

        // Validate age
        int age;
        while (true) {
            System.out.print("Enter age: ");
            age = scanner.nextInt();
            if (age >= 18) {
                break;
            } else {
                System.out.println("Age must be 18 or above for blood donation.");
                System.out.print("Do you want to continue (Y/N)? ");
                String continueChoice = scanner.next().toUpperCase();
                if (!continueChoice.equals("Y")) {
                    System.out.println("Registration cancelled.");
                    return;
                }
            }
        }

        // Validate gender
        String gender;
        while (true) {
            System.out.print("Enter gender (Male/Female): ");
            gender = scanner.next().toUpperCase();
            if (gender.equals("MALE") || gender.equals("FEMALE")) {
                break;
            } else {
                System.out.println("Invalid gender. Please enter Male or Female.");
            }
        }

        // Validate weight based on gender
        float weight;
        while (true) {
            System.out.print("Enter weight (kg): ");
            weight = scanner.nextFloat();
            if ((gender.equals("MALE") && weight >= 55) || (gender.equals("FEMALE") && weight >= 50)) {
                break;
            } else {
                System.out.println("Minimum weight for donating blood: Male - 55kg, Female - 50kg.");
                System.out.print("Do you want to continue (Y/N)? ");
                String continueChoice = scanner.next().toUpperCase();
                if (!continueChoice.equals("Y")) {
                    System.out.println("Registration cancelled.");
                    return;
                }
            }
        }

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.next();
        System.out.print("Enter address: ");
        String address = scanner.next().toUpperCase();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String insertSQL = "INSERT INTO donors (name, bloodgroup, gender, weight, phonenumber, address, age) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, bloodGroup);
                preparedStatement.setString(3, gender);
                preparedStatement.setFloat(4, weight);
                preparedStatement.setString(5, phoneNumber);
                preparedStatement.setString(6, address);
                preparedStatement.setInt(7, age);
                preparedStatement.executeUpdate();
                System.out.println("Donor registered successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchDonor(Scanner scanner) {
        System.out.print("Enter blood group to search: ");
        String searchBloodGroup = scanner.next().toUpperCase();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String selectSQL = "SELECT * FROM donors WHERE bloodgroup = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
                preparedStatement.setString(1, searchBloodGroup);
                ResultSet resultSet = preparedStatement.executeQuery();

                System.out.println("Donors with Blood Group " + searchBloodGroup + ":");
                while (resultSet.next()) {
                    System.out.println("Name: " + resultSet.getString("name") +
                            ", Age: " + resultSet.getInt("age") +
                            ", Gender: " + resultSet.getString("gender") +
                            ", Weight: " + resultSet.getFloat("weight") +
                            ", Phone Number: " + resultSet.getString("phonenumber") +
                            ", Address: " + resultSet.getString("address"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidBloodGroup(String bloodGroup) {
        String[] relevantBloodGroups = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
        for (String bg : relevantBloodGroups) {
            if (bg.equals(bloodGroup)) {
                return true;
            }
        }
        return false;
    }
}
