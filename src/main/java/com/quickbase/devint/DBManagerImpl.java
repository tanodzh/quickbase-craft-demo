package com.quickbase.devint;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This DBManager implementation provides a connection to the database containing population data.
 * <p>
 * Created by ckeswani on 9/16/15.
 */
public class DBManagerImpl implements DBManager {

    private PreparedStatement countryPopulationsStatement;

    public Connection getConnection() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:resources/data/citystatecountry.db");
            System.out.println("Opened database successfully");

        } catch (ClassNotFoundException cnf) {
            System.err.println("could not load driver");
        } catch (SQLException sqle) {
            System.err.println("sql exception:" + sqle.getStackTrace());
        }
        return c;
    }

    @Override
    public Collection<Pair<String, Integer>> getCountryPopulations() {
        Collection<Pair<String, Integer>> populations = new ArrayList<>();

        try {
            if (countryPopulationsStatement == null) {
                countryPopulationsStatement = getConnection().prepareStatement("select " +
                        "   ct.CountryName, sum(c.Population)" +
                        "from " +
                        "   Country ct " +
                        "   left join State s on s.CountryId = ct.CountryId " +
                        "   left join City c on c.StateId = s.StateId " +
                        "group by ct.CountryName");
            }

            ResultSet rs = countryPopulationsStatement.executeQuery();
            while(rs.next()){
                populations.add(ImmutablePair.of(rs.getString(1), rs.getInt(2)));
            }
        } catch (SQLException e) {
            System.err.println("sql exception:" + e.getStackTrace());
        }

        return populations;
    }
}
