package rtx.smart_boutique.Model;


import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseDriver {
    private static final String DB_URL = "jdbc:sqlite:Database/Smart_boutique.db?busy_timeout=10000";

    public DatabaseDriver() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                conn.createStatement().execute("PRAGMA journal_mode=WAL;");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        conn.setAutoCommit(true);
        return conn;
    }

    public Employee getEmployeeData(String username) {
        String sql = "SELECT * FROM Employees WHERE Username = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultEmployee = preparedStatement.executeQuery()) {
                if (resultEmployee.next()) {
                    return new Employee(
                            resultEmployee.getString("FirstName"),
                            resultEmployee.getString("LastName"),
                            resultEmployee.getString("Username"),
                            resultEmployee.getInt("ID"),
                            resultEmployee.getString("DOB"),
                            resultEmployee.getString("Password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Admin getAdminData(String username) {
        String sql = "SELECT * FROM Employees WHERE Username = ? AND Admin = 1";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultAdmin = preparedStatement.executeQuery()) {
                if (resultAdmin.next()) {
                    return new Admin(
                          0,  //resultAdmin.getInt("Id"),
                            resultAdmin.getString("Username"),
                            resultAdmin.getString("Password")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateEmployeeRecord(String firstName, String lastName, String userName, LocalDate selectedDate, String nationalId, String password, String Email) {
        String sql = "INSERT INTO Employees(FirstName, LastName, Username, DOB, ID, Password, Email) VALUES (?, ?, ?, ?, ?, ?,?)";
        for (int i = 0; i < 5; i++) {
            try (Connection connect = getConnection();
                 PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
                preparedStatement.setString(1, firstName);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, userName);
                preparedStatement.setString(4, selectedDate.toString());
                preparedStatement.setString(5, nationalId);
                preparedStatement.setString(6, password);
                preparedStatement.setString(7, Email);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                if (e.getMessage().contains("database is locked")) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    e.printStackTrace();
                    break;
                }
            }
        }
        return false;
    }

    public List<Employee> QueryEmployeeData(String searchText) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employees WHERE Username LIKE ? OR FirstName LIKE ? OR LastName LIKE ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            String search = "%" + searchText + "%";
            preparedStatement.setString(1, search);
            preparedStatement.setString(2, search);
            preparedStatement.setString(3, search);

            try (ResultSet resultEmployee = preparedStatement.executeQuery()) {
                while (resultEmployee.next()) {
                    employees.add(new Employee(
                            resultEmployee.getString("FirstName"),
                            resultEmployee.getString("LastName"),
                            resultEmployee.getString("Username"),
                            resultEmployee.getInt("ID"),
                            resultEmployee.getString("DOB")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error querying employee data: " + e.getMessage());
        }

        return employees;
    }

    public void deleteEmployee(int employeeId) {
        String sql = "DELETE FROM Employees WHERE ID = ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, employeeId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addItemsRecord(String Name, double unit_price, int quantity, String description) {
        String sql = "INSERT INTO Items(Name, Price, Quantity, Description) VALUES (?, ?, ?, ?)";
        for (int i = 0; i < 5; i++) {
            try (Connection connect = getConnection();
                 PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
                preparedStatement.setString(1, Name);
                preparedStatement.setDouble(2, unit_price);
                preparedStatement.setInt(3, quantity);
                preparedStatement.setString(4, description);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                if (e.getMessage().contains("database is locked")) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    e.printStackTrace();
                    break;
                }
            }
        }
        return false;
    }

    public List<ItemRecord> QueryStockData(String searchText) {
        List<ItemRecord> availableStock = new ArrayList<>();
        String sql = "SELECT * FROM Items WHERE Name LIKE ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            String search = "%" + searchText + "%";
            preparedStatement.setString(1, search);

            try (ResultSet stock = preparedStatement.executeQuery()) {
                while (stock.next()) {
                    availableStock.add(new ItemRecord(
                            stock.getString("Description"),
                            stock.getString("Name"),
                            stock.getInt("Quantity"),
                            stock.getDouble("Price")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error querying employee data: " + e.getMessage());
        }

        return availableStock;
    }

    public void deleteItemrecord(String Name) {
        String sql = "DELETE FROM Items WHERE Name = ?;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, Name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateItemrecord(String Name, double price, int quantity, String description, String oldName) {
        String sql = "UPDATE Items SET Name = ?, Price = ?, Quantity = ?, Description = ? WHERE Name = ?;";
        for (int i = 0; i < 5; i++) {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, Name);
                preparedStatement.setDouble(2, price);
                preparedStatement.setInt(3, quantity);
                preparedStatement.setString(4, description);
                preparedStatement.setString(4, oldName);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                if (e.getMessage().contains("database is locked")) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    e.printStackTrace();
                    break;
                }
            }
        }
        return false;
    }

    public boolean commitSalesDatabase(String Time, String ItemName, String Employee, double price, String Date, int quantity, double Total_Price) {
        String sqlInsertSales = "INSERT INTO Sales(Time, ItemName, Employee, Price, Date, Quantity, Total_Price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String sqlUpdateItems = "UPDATE Items SET Quantity = Quantity - ? WHERE Name = ?";
        for (int i = 0; i < 5; i++) {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatementInsert = connection.prepareStatement(sqlInsertSales);
                 PreparedStatement preparedStatementUpdate = connection.prepareStatement(sqlUpdateItems)) {

                preparedStatementInsert.setString(1, Time);
                preparedStatementInsert.setString(2, ItemName);
                preparedStatementInsert.setString(3, Employee);
                preparedStatementInsert.setDouble(4, price);
                preparedStatementInsert.setString(5, Date);
                preparedStatementInsert.setInt(6, quantity);
                preparedStatementInsert.setDouble(7, Total_Price);
                int rowsAffectedInsert = preparedStatementInsert.executeUpdate();

                preparedStatementUpdate.setInt(1, quantity);
                preparedStatementUpdate.setString(2, ItemName);
                int rowsAffectedUpdate = preparedStatementUpdate.executeUpdate();


                if (rowsAffectedInsert > 0 && rowsAffectedUpdate > 0) {
                    return true;
                }
            } catch (SQLException e) {
                if (e.getMessage().contains("database is locked")) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    e.printStackTrace();
                    break;
                }
            }
        }
        return false;
    }

    public Double updateEmployeeRank(String date, String name) {
        String sql = "SELECT SUM(Total_Price) AS Total_Price FROM Sales WHERE Date = ? AND Employee = ?";
        Double totalSales = 0.0;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, date);
            preparedStatement.setString(2, name);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalSales = resultSet.getDouble("Total_Price"); // Adjusted to index 1
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error fetching total sales", e);
        }

        return totalSales;
    }

    public Sales updateEmpHighestSale(String date, String name) {
        String sql = "SELECT  SUM(Total_Price) AS HighestSale, Time FROM Sales WHERE Date = ? AND Employee = ? GROUP BY Time ORDER BY HighestSale DESC LIMIT 1";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, date);
            preparedStatement.setString(2, name);

            try (ResultSet highestSale = preparedStatement.executeQuery()) {
                if (highestSale.next()) {
                    return new Sales(
                            highestSale.getString("Time") != null ? highestSale.getString("Time") : "Unknown",
                            "",
                            "",
                            0.0,
                            "",
                            0,
                            highestSale.getDouble("HighestSale")
                    );
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error fetching highest sale", e);
        }

        return null;
    }

    public List<Sales> QueryEmployeeSales(String searchDate, String name) {
        List<Sales> salesList = new ArrayList<>();
        String sql = "SELECT * FROM Sales WHERE Date = ?  AND  Employee = ?;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, searchDate);
           preparedStatement.setString(2, name);
            try (ResultSet resultSales = preparedStatement.executeQuery()) {
                while (resultSales.next()) {
                    salesList.add(new Sales(
                            resultSales.getString("Time"),
                            resultSales.getString("ItemName"),
                            resultSales.getString("Employee"),
                            resultSales.getDouble("Price"),
                            resultSales.getString("Date"),
                            resultSales.getInt("Quantity"),
                            resultSales.getDouble("Total_Price")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error querying sales data: " + e.getMessage());
        }

        return salesList;
    }

    public Double updateTotalSalesAdmin(String date) {
        String sql = "SELECT SUM(Total_Price) AS Total_Price FROM Sales WHERE Date = ? ";
        Double totalSales = 0.0;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, date);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalSales = resultSet.getDouble("Total_Price"); // Adjusted to index 1
                }
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error fetching total sales", e);
        }
        return totalSales;
    }
    public List<Sales> updateEmployeeRank(String date) {
        List<Sales> rankedSales = new ArrayList<>();

        String sql = "SELECT *, SUM(Total_Price) AS TotalSales " +
                "FROM Sales " +
                "WHERE Date = ? " +
                "GROUP BY Employee " +
                "ORDER BY TotalSales DESC";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, date);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Sales sale = new Sales(
                            resultSet.getString("Time"),
                            resultSet.getString("ItemName"),
                            resultSet.getString("Employee"),
                            resultSet.getDouble("Price"),
                            resultSet.getString("Date"),
                            resultSet.getInt("Quantity"),
                            resultSet.getDouble("TotalSales")

                    );
                    rankedSales.add(sale);

                }
            }

        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error calculating ranks for employees", e);
        }

        return rankedSales;
    }

    public List<Sales> QueryTotalSales(String searchDate) {
        List<Sales> salesList = new ArrayList<>();
        String sql = "SELECT * FROM Sales WHERE Date = ?;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, searchDate);
            try (ResultSet resultSales = preparedStatement.executeQuery()) {
                while (resultSales.next()) {
                    salesList.add(new Sales(
                            resultSales.getString("Time"),
                            resultSales.getString("ItemName"),
                            resultSales.getString("Employee"),
                            resultSales.getDouble("Price"),
                            resultSales.getString("Date"),
                            resultSales.getInt("Quantity"),
                            resultSales.getDouble("Total_Price")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error querying sales data: " + e.getMessage());
        }

        return salesList;
    }
    public boolean setAdmin(int employeeId) {
        String sql = "UPDATE Employees SET Admin = 1 WHERE ID = ?";
        for (int i = 0; i < 5; i++) {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, employeeId);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                if (e.getMessage().contains("database is locked")) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    e.printStackTrace();
                    break;
                }
            }
        }
        return false;
    }
    public boolean rmAdmin(int employeeId) {
        String sql = "UPDATE Employees SET Admin = 0 WHERE ID = ?";
        for (int i = 0; i < 5; i++) {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, employeeId);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                if (e.getMessage().contains("database is locked")) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    e.printStackTrace();
                    break;
                }
            }
        }
        return false;
    }
    public static String getEmailByUsername(String username) {
        String query = "SELECT email FROM Employees WHERE Username = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("email");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean resetPassword(String Password,String Email) {
        String sql = "UPDATE Employees SET Password = ? WHERE Email = ?";
        for (int i = 0; i < 5; i++) {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1,Password);
                preparedStatement.setString(2, Email);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                if (e.getMessage().contains("database is locked")) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    e.printStackTrace();
                    break;
                }
            }
        }
        return false;
    }
    public boolean changePassword(String Password,String Username) {
        String sql = "UPDATE Employees SET Password = ? Username = ?";
        for (int i = 0; i < 5; i++) {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1,Password);
                preparedStatement.setString(2, Username);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                if (e.getMessage().contains("database is locked")) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    e.printStackTrace();
                    break;
                }
            }
        }
        return false;
    }
    public boolean changeEmail(String Email,String Username) {
        String sql = "UPDATE Employees SET Email = ? Username = ?";
        for (int i = 0; i < 5; i++) {
            try (Connection connection = getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1,Email);
                preparedStatement.setString(2, Username);
                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                if (e.getMessage().contains("database is locked")) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    e.printStackTrace();
                    break;
                }
            }
        }
        return false;
    }
}
