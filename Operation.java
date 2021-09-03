package com.sqldemo;
import java.sql.*;
import java.util.Scanner;
public class Operation {
	static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        MySQLConnection1 mySQLConnection = new MySQLConnection1();
        Connection connection = mySQLConnection.getConnection();
//        createTable(connection);
//        insertData(connection);
//        showTable(connection);
//        updateData(connection);
//        showEmployeeByDate(connection);
//        setGender(connection);
//        analyzeTable(connection);
    }

    private static void analyzeTable(Connection connection) {
        System.out.print("What you want \n1-SUM \n2-AVG \n3-MIN \n4-MAX \n4-COUNT \n-> ");
        int data = sc.nextInt();

        System.out.print("Enter gender = ");
        String gender = sc.next();

        switch (data){
            case 1:
                sumData(connection,gender);
                break;
            case 2:
                avgData(connection,gender);
                break;
            case 3:
                minData(connection,gender);
                break;
            case 4:
                maxData(connection,gender);
                break;
            case 5:
                countData(connection,gender);
                break;
            default:
                System.out.println("ERROR");
        }


    }
    /**
     common in the all numeric numeric operations in sql like AVG SUM MIN MAX COUNT
     */

    private static void Operation(Connection connection, String gender, PreparedStatement preparedStatement, String sql, String s) {
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, gender);
            ResultSet set = preparedStatement.executeQuery();
            set.next();
            Double answer = set.getDouble(1);

            System.out.println(s + gender + " is = " + answer);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) connection.close();
                if (connection != null) connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
    COUNT BY EMPLOYEE BY GENDER
     */
    private static void countData(Connection connection, String gender) {
        String sql = "SELECT COUNT(name) FROM  EmployeePayroll where GENDER = ? GROUP BY GENDER ";
        PreparedStatement preparedStatement = null;
        Operation(connection,gender,preparedStatement,sql,"the total number of employee of gender ");
    }

    /**
     MAX SALARY OF EMPLOYEE BY GENDER
     */
    private static void maxData(Connection connection, String gender) {
        PreparedStatement preparedStatement = null;

        String sql = "SELECT MAX(SALARY) FROM EmployeePayroll WHERE gender = ? GROUP BY GENDER ";
        Operation(connection, gender, preparedStatement, sql, "the max of salaries for ");

    }
    /**
     MIN SALARY OF EMPLOYEE BY GENDER
     */
    private static void minData(Connection connection, String gender) {
        PreparedStatement preparedStatement = null;

        String sql = "SELECT MIN(SALARY) FROM EmployeePayroll WHERE gender = ? GROUP BY GENDER ";
        Operation(connection, gender, preparedStatement, sql, "min of salaries of ");

    }
    /**
     AVG OF SALARIES OF EMPLOYEE BY GENDER
     */

    private static void avgData(Connection connection, String gender) {
        PreparedStatement preparedStatement = null;

        String sql = "SELECT AVG(SALARY) FROM EmployeePayroll WHERE gender = ? GROUP BY GENDER ";
        Operation(connection,gender,preparedStatement,sql,"the avg of salaries for ");

    }
    /**
     SUM OF SALARIES OF EMPLOYEE BY GENDER
     */

    private static void sumData(Connection connection, String gender) {
        PreparedStatement preparedStatement = null;
        String sql = "SELECT SUM(SALARY) FROM EmployeePayroll where gender=? group by gender";
        Operation(connection,gender,preparedStatement,sql,"the sum of salaries for ");

    }
    /**
     TO SET THE GENDER OF EMPLOYEE AFTER ADDING GENDER COLUMN
     */
    private static void setGender(Connection connection) {
       while (true){
           System.out.print("Enter name = ");
           String name = sc.next();
           System.out.print("Enter gender(M/F) = ");
           String gender = sc.next();

           String sql = "update employeePayroll set gender = ? where name = ?";

           PreparedStatement preparedStatement = null;
           try {
               preparedStatement = connection.prepareStatement(sql);
               preparedStatement.setString(1,gender);
               preparedStatement.setString(2,name);
               int i = preparedStatement.executeUpdate();
               System.out.println("number of rows affected = "+ i);

           }catch (Exception e ){
               e.printStackTrace();
           }
           System.out.print("Do you want to update any more (yes/no) = ");
           String command = sc.next();
           if (command.equalsIgnoreCase("no")) {
               try { }
               finally {
                   try {
                       if (preparedStatement != null) connection.close();
                       if (connection != null) connection.close();

                   }catch (SQLException e){
                       e.printStackTrace();
                   }
               }
               break;
           }
       }

    }
    /**
     TO CHECK  NUMBER OF EMPLOYEES FORM START DATA TO CUSTOM DATE
     */
    private static void showEmployeeByDate(Connection connection) {
        System.out.print("Enter star date = ");
        String startDate =sc.next();
        System.out.print("Enter end date = ");
        String endDate = sc.next();

        String sql = "SELECT * FROM employeePayroll WHERE StartDate BETWEEN ? AND ?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,startDate);
            preparedStatement.setString(2,endDate);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                System.out.println("id = "+ resultSet.getInt(1)+ ", name = "+ resultSet.getString(2)+ ", salary = "+resultSet.getInt(3)
                        + ", Joining-date = "+ resultSet.getString(4));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (preparedStatement != null) connection.close();
                if (connection != null) connection.close();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    /**
     TO UPDATE ANY EMPLOYEE DETAIL IN TABLE
     */

    private static void updateData(Connection connection) {
        System.out.print("Enter name to change data = ");
        String name = sc.next();
        System.out.print("Enter new Salary = ");
        int salary = sc.nextInt();
        String sql = "update employeePayroll set salary = ? where name = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(2,name);
            preparedStatement.setInt(1,salary);
            System.out.println("The data updated successfully");
        }catch (SQLException e ){
            e.printStackTrace();
        }
    }

    /**
     TO SHOW ALL ROWS OF TABLE
     */

    private static void showTable(Connection connection) {
        String sql = "select * from employeePayroll";
        Statement statement = null;
        try {
            statement= connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                System.out.println("id = "+ resultSet.getInt(1)+ ", name = "+ resultSet.getString(2)+ ", salary = "+resultSet.getInt(3)
                + ", Joining-date = "+ resultSet.getString(4));
            }
        }catch (SQLException e ){
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) connection.close();
                if (connection != null) connection.close();

            }catch (SQLException e){
                e.printStackTrace();
            }

        }
    }

    /**
     TO ADD EMPLOYEE IN TABLE
     */

    private static void insertData(Connection connection) {
       while (true) {

           System.out.print("Enter id = ");
           int id = sc.nextInt();
           System.out.print("Enter name = ");
           String name = sc.next();
           System.out.print("Enter salary = ");
           int salary = sc.nextInt();
           System.out.print("Enter start date (YYYY-MM-DD) = ");
           String data = sc.next();

           String sql = "insert into EmployeePayroll(id, name , salary ,startDate ) values (?,?,?,?)";
           PreparedStatement preparedStatement = null;
           try {
               preparedStatement = connection.prepareStatement(sql);
               preparedStatement.setInt(1, id);
               preparedStatement.setString(2, name);
               preparedStatement.setInt(3, salary);
               preparedStatement.setString(4, data);
               int i = preparedStatement.executeUpdate();
               System.out.println("The data inserted successfully ");
               System.out.println("rows affected = " + i);
           } catch (SQLException e) {
               e.printStackTrace();
           }

           System.out.print("Do you want to enter data again (yes/no) = ");
           String command = sc.next();

           if (command.equalsIgnoreCase("no")) {
               try {

               }
               finally {
                   try {
                       if (preparedStatement != null) connection.close();
                       if (connection != null) connection.close();

                   }catch (SQLException e){
                       e.printStackTrace();
                   }

               }
               break;
           }

       }

    }
    /**
     TO CREATE TABLE
     */
    private static void createTable(Connection connection) {
        String sql = "create table EmployeePayroll(Id INT AUTO_INCREMENT Primary Key, Name VARCHAR(20) , Salary INT, StartDate DATE)";
        Statement statement = null ;
        try {
             statement = connection.createStatement();
            boolean execute = statement.execute(sql);
            System.out.println("table created successfully");

        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            try {
                if (statement != null) connection.close();
                if (connection != null) connection.close();

            }catch (SQLException e){
                e.printStackTrace();
            }

        }

    }
}

