import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PrintEmployee {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        menu();

    }


    public static ResultSet getEmployees () throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.log.Slf4JLogger");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hr?" + "user=root&password=123456789");
        Statement statement = connection.createStatement();
        return  statement.executeQuery("SELECT * FROM employees");

    }

    public static ResultSet getAvgSalaries () throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.log.Slf4JLogger");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hr?" + "user=root&password=123456789");
        Statement statement = connection.createStatement();
        return  statement.executeQuery("SELECT d.department_name AS Department, AVG(e.salary) AS 'Average salary' " +
                "FROM employees e LEFT JOIN departments d ON e.department_id = d.department_id " +
                "GROUP BY e.department_id " +
                "ORDER BY AVG(e.salary) DESC;");

    }

    private static void menu() throws SQLException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);

        menu_cycle:
        while (true) {
            System.out.println("Menu - choose action:\n1 - view employees\n2 - view average salaries by departments\n3 - exit");
            System.out.print("Your choice: ");
            int choice = 0;
            while (choice == 0) {
                try {
                    choice = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Try again.");
                    System.out.println("Menu - choose action:\n1 - view employees\n2 - view average salaries by departments\n3 - exit");
                    System.out.print("Your choice: ");
                    sc.next();
                }
            }

            switch (choice) {
                case 1:
                    print(getEmployees());
                    break;
                case 2:
                    print(getAvgSalaries());
                    break;
                case 3:
                    break menu_cycle;
            }

        }
    }

    public static void print (ResultSet rs) throws SQLException {
        int colNum = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= colNum; i++ ) {
            if (i != colNum) {
                System.out.print(rs.getMetaData().getColumnLabel(i) + ";");
            } else {
                System.out.print(rs.getMetaData().getColumnLabel(i));
            }
        }
        System.out.println();

        while (rs.next()) {
            for (int i = 1; i <= colNum; i++) {
                if (i != colNum) {
                    System.out.print(rs.getString(i) + ";");
                } else {
                    System.out.print(rs.getString(i));
                }
            }
            System.out.println();
        }

        System.out.println("================ END =================");

    }
}
