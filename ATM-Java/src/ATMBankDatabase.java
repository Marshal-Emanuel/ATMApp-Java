import java.sql.*;

public class ATMBankDatabase {
    private Connection connection;

    public ATMBankDatabase() { 
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.jc.jdbc.Driver");

            // Establish a connection to the database
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm_db", "root", "");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } 
    }

    public String isAccountNumberAvailable(String accountNumberInput) {
        String query = "SELECT account_number FROM user_accounts WHERE account_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, accountNumberInput);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String actualAccountNo = resultSet.getString("account_number");
                System.out.println("Account Number from DB: "+ actualAccountNo);
                return actualAccountNo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String actualAccountNo = "Error";
        return actualAccountNo; // Account not found
    }

    //password check vslidator
    public String isPINValid(String accountNumberInput) throws SQLException {
        String query = "SELECT pin FROM user_accounts WHERE account_number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, accountNumberInput);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String actualPIN = rs.getString("pin");
                System.out.println(" db Pin returned: " + actualPIN);
                return actualPIN;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String actualPin = "Account Number Passed to PIN Validator" +accountNumberInput; // Default PIN in case of error
        return actualPin;
    }

    public double getBalance(String accountNumber) throws SQLException {
        String query = "SELECT balance FROM user_accounts WHERE account_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("balance");
            }
        }
        throw new SQLException("Account not found or balance retrieval error.");
    }

    public boolean updateBalance(String accountNumber, double newBalance) {
        String query = "UPDATE user_accounts SET balance = ? WHERE account_number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setString(2, accountNumber);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Failed to update account balance
        }
    }




    public UserAccount getUserAccount(String accountNumber) {
        String query = "SELECT * FROM user_accounts WHERE account_number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, accountNumber);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String dbAccountNumber = resultSet.getString("account_number");
                String pin = resultSet.getString("pin");
                double balance = resultSet.getDouble("balance");

                return new UserAccount(dbAccountNumber, pin, balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Account not found
    }

    public double getAvailableCash() {
        String query = "SELECT available_cash FROM atm_settings WHERE id = 1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getDouble("available_cash");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0; // Return 0.0 if there's an issue retrieving available cash
    }

    public boolean updateAvailableCash(double newBalance) {
        String query = "UPDATE atm_settings SET available_cash = ? WHERE id = 1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, newBalance);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Failed to update available cash balance
    }

    public boolean createNewUserAccount(UserAccount userAccount) {
        String query = "INSERT INTO user_accounts (account_number, pin, balance) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userAccount.getAccountNumber());
            preparedStatement.setString(2, userAccount.getPin());
            preparedStatement.setDouble(3, userAccount.getBalance());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Failed to create a new user account
    }

    public boolean updateAccountBalance(UserAccount userAccount) {
        String query = "UPDATE user_accounts SET balance = ? WHERE account_number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, userAccount.getBalance());
            preparedStatement.setString(2, userAccount.getAccountNumber());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Failed to update account balance
    }



    public boolean deleteAccount(String accountNumber) {
        String query = "DELETE FROM user_accounts WHERE account_number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, accountNumber);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Failed to delete the account
    }
}
