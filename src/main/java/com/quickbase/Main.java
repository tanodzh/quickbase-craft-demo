package com.quickbase;

import com.quickbase.devint.ConcreteStatService;
import com.quickbase.devint.DBManager;
import com.quickbase.devint.DBManagerImpl;
import com.quickbase.devint.PopulationService;
import com.quickbase.devint.PopulationServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

/**
 * The main method of the executable JAR generated from this repository. This is to let you
 * execute something from the command-line or IDE for the purposes of demonstration, but you can choose
 * to demonstrate in a different way (e.g. if you're using a framework)
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting.");
        System.out.print("Getting DB Connection...");

        DBManager dbm = new DBManagerImpl();
        Connection c = dbm.getConnection();
        if (null == c) {
            System.out.println("failed.");
            System.exit(1);
        }

        System.out.println("--------------------");
        System.out.println("Country populations");
        System.out.println("--------------------");

        try {
            PopulationService populationService = new PopulationServiceImpl(dbm, new ConcreteStatService());
            populationService.getCountryPopulations().forEach(p ->
                    System.out.printf(Locale.ROOT, "%s %,d%n", p.getLeft(), p.getRight()));
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
