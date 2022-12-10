package data.DAL;

import java.sql.*;

public class CreateOnStart {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public void createTableDevices() {
        try {

            connect = DriverManager.getConnection("jdbc:sqlite:dataBase.db");

            statement = connect.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS \"Devices\" (\n" + "\t\"id\"\tINTEGER NOT NULL,\n" + "\t\"name\"\tTEXT NOT NULL,\n" + "\t\"startDate\"\tTEXT,\n" + "\t\"endDate\"\tTEXT NOT NULL,\n" + "\t\"deviceLocation\"\tTEXT, \n" + "\t\"specs\"\tTEXT NOT NULL,\n" + "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" + ")";
            resultSet = statement.executeQuery(query);


            if (connect != null) connect.close();
            if (statement != null) statement.close();
            if (preparedStatement != null) preparedStatement.close();
            if (resultSet != null) resultSet.close();


        } catch (Exception ignored) {

        }
    }

}
