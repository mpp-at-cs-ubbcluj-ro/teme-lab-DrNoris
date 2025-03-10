package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CarsDBRepository implements CarRepository{

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public CarsDBRepository(Properties props) {
        logger.info("Initializing org.example.CarsDBRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public List<Car> findByManufacturer(String manufacturerN) {
        logger.traceEntry("retrieving cars by manufacturer: {}", manufacturerN);
        List<Car> cars = new ArrayList<>();
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement(
                "SELECT * FROM cars WHERE manufacturer = ?")) {
            preStmt.setString(1, manufacturerN);

            try (ResultSet resultSet = preStmt.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String manufacturer = resultSet.getString("manufacturer");
                    String model = resultSet.getString("model");
                    int year = resultSet.getInt("year");

                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
            logger.trace("Found {} cars", cars.size());
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }

        logger.traceExit();
        return cars;
    }


    @Override
    public List<Car> findBetweenYears(int min, int max) {
        logger.traceEntry("retrieving cars between years {} and {}", min, max);
        List<Car> cars = new ArrayList<>();
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement(
                "SELECT * FROM cars WHERE year BETWEEN ? AND ?")) {
            preStmt.setInt(1, min);
            preStmt.setInt(2, max);

            try (ResultSet resultSet = preStmt.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String manufacturer = resultSet.getString("manufacturer");
                    String model = resultSet.getString("model");
                    int year = resultSet.getInt("year");

                    Car car = new Car(manufacturer, model, year);
                    car.setId(id);
                    cars.add(car);
                }
            }
            logger.trace("Found {} cars", cars.size());
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }

        logger.traceExit();
        return cars;
    }


    @Override
    public void add(Car elem) {
        logger.traceEntry( "saving task {} ", elem);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement( "insert into cars (manufacturer, model, year) values (?,?,?)")){
            preStmt.setString(  1, elem.getManufacturer());
            preStmt.setString( 2, elem.getModel());
            preStmt.setInt( 3, elem.getYear());
            int result=preStmt.executeUpdate();
            logger.trace("Saved {} instances", result);
        }catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer id, Car elem) {
        logger.traceEntry("updating car with id {} ", id);
        Connection con = dbUtils.getConnection();
        try (PreparedStatement preStmt = con.prepareStatement(
                "UPDATE cars SET manufacturer = ?, model = ?, year = ? WHERE id = ?")) {
            preStmt.setString(1, elem.getManufacturer());
            preStmt.setString(2, elem.getModel());
            preStmt.setInt(3, elem.getYear());
            preStmt.setInt(4, id);
            int result = preStmt.executeUpdate();
            logger.trace("Updated {} instances", result);
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }
        logger.traceExit();
    }


    @Override
    public Iterable<Car> findAll() {
        logger.traceEntry("retrieving all cars");
        List<Car> cars = new ArrayList<>();
        Connection con = dbUtils.getConnection();

        try (PreparedStatement preStmt = con.prepareStatement("SELECT * FROM cars");
             ResultSet resultSet = preStmt.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String manufacturer = resultSet.getString("manufacturer");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");

                Car car = new Car(manufacturer, model, year);
                car.setId(id);
                cars.add(car);
            }
            logger.trace("Found {} cars", cars.size());
        } catch (SQLException ex) {
            logger.error(ex);
            System.err.println("Error DB " + ex);
        }

        logger.traceExit();
        return cars;
    }

}
