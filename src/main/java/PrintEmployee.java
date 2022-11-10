import java.sql.*;

public class PrintEmployee {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        ResultSet rs = getEmployees();
        int colNum = rs.getMetaData().getColumnCount();
        for (int i = 1; i <= colNum; i++ ) {
            if (i != colNum) {
                System.out.print(rs.getMetaData().getColumnLabel(i) + ";");
            } else {
                System.out.print(rs.getMetaData().getColumnLabel(i));
            }
        }

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

    }


    public static ResultSet getEmployees () throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.log.Slf4JLogger");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/hr?" + "user=root&password=123456789");
        Statement statement = connection.createStatement();
        return  statement.executeQuery("SELECT * FROM employees");

    }
}
