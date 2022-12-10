package data.DAL;

import data.primitive.Device;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeviceDAL {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public List<Device> readDevices() {
        List<Device> devices = new ArrayList<>();
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:dataBase.db");
            statement = connect.createStatement();
            String query = "SELECT * FROM Devices order by name;";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Device device = new Device();
                device.setId(resultSet.getInt("id"));
                device.setName(resultSet.getString("name"));
                device.setStartDate(resultSet.getString("startDate"));
                device.setEndDate(resultSet.getString("endDate"));
                device.setDeviceLocation(resultSet.getString("deviceLocation"));
                device.setSpecs(resultSet.getString("specs"));
                devices.add(device);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return devices;
    }

    public List<Device> readDevicesByFragment(String fragment) {
        List<Device> Devices = new ArrayList<>();
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:dataBase.db");
            statement = connect.createStatement();
            String query = "SELECT * FROM Devices where name like '%" + fragment + "%' order by name;";
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Device Device = new Device();
                Device.setId(resultSet.getInt("id"));
                Device.setName(resultSet.getString("name"));
                Device.setStartDate(resultSet.getString("startDate"));
                Device.setEndDate(resultSet.getString("endDate"));
                Device.setSpecs(resultSet.getString("specs"));
                Devices.add(Device);
            }
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Devices;
    }

    private void deleteRelation(Device device) {
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:dataBase.db");

            String query = "DELETE from Devices where id = " + device.getId();

            preparedStatement = connect.prepareStatement(query);
            preparedStatement.executeUpdate();

            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void insertDevice(Device device) {

        try {
            connect = DriverManager.getConnection("jdbc:sqlite:dataBase.db");

            String query = "INSERT INTO Devices(name,startDate,endDate,deviceLocation,specs)" + "VALUES (?,?,?,?,?);";

            preparedStatement = connect.prepareStatement(query);

            preparedStatement.setString(1, device.getName());
            preparedStatement.setString(2, device.getStartDate());
            preparedStatement.setString(3, device.getEndDate());
            preparedStatement.setString(4, device.getDeviceLocation());
            preparedStatement.setString(5, device.getSpecs());

            preparedStatement.executeUpdate();

            close();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public void deleteDevice(Device device) {

        try {
            connect = DriverManager.getConnection("jdbc:sqlite:dataBase.db");

            String query = "DELETE FROM Devices WHERE `id` = ?";
            preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, device.getId());
            preparedStatement.executeUpdate();

            System.out.println("Device-ul " + device.getId() + " a fost eliminat");

            deleteRelation(device);

            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDevice(Device device) {
        try {

            connect = DriverManager.getConnection("jdbc:sqlite:dataBase.db");

            String query = "UPDATE Devices SET \n" + "name = ? \n," + "startDate = ? \n," + "endDate = ? \n," + "deviceLocation = ? \n," + "specs = ? \n" + "WHERE `id` = ? ;";
            preparedStatement = connect.prepareStatement(query);

            preparedStatement.setString(1, device.getName());
            preparedStatement.setString(2, device.getStartDate());
            preparedStatement.setString(3, device.getEndDate());
            preparedStatement.setString(4, device.getDeviceLocation());
            preparedStatement.setString(5, device.getSpecs());
            preparedStatement.setInt(6, device.getId());

            preparedStatement.executeUpdate();
            close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void close() {
        try {
            if (connect != null) connect.close();
            if (statement != null) statement.close();
            if (preparedStatement != null) preparedStatement.close();
            if (resultSet != null) resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
