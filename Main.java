package com.company;

import java.sql.*;
import java.util.*;
import java.util.Map;

public class Main {
    Connection connect = null;
    private List<String> governor;
    private List<String> minister;
    private List<String> region;
    private List<String> division;
//    private List<String> ministry1;
    private List<String> language;
    private List<String> inputStore;
    private Map<String,String> map;

    public Main() {
        Statement MyStatement = null;
        ResultSet MyResultSet = null;

        this.governor = new ArrayList();
        this.minister = new ArrayList();
        this.region = new ArrayList();
        this.division = new ArrayList();
//        this.ministry = new ArrayList();
        this.language = new ArrayList();
        this.map = new HashMap();


        try {
            this.connect = MySQLConnection.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            MyStatement = connect.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            MyResultSet = MyStatement.executeQuery("SELECT governor_name FROM cameroon.governors");
            while (MyResultSet.next()) {
                this.governor.add(MyResultSet.getString("governor_name").toUpperCase());
            }
            MyResultSet = MyStatement.executeQuery("SELECT minister_name FROM cameroon.ministers");
            while (MyResultSet.next()) {
                this.minister.add(MyResultSet.getString("minister_name").toUpperCase());
            }
            MyResultSet = MyStatement.executeQuery("SELECT reg_name FROM cameroon.region");
            while (MyResultSet.next()) {
                this.region.add(MyResultSet.getString("reg_name").toUpperCase());
            }
            MyResultSet = MyStatement.executeQuery("SELECT division FROM cameroon.division");
            while (MyResultSet.next()) {
                this.division.add(MyResultSet.getString("division").toUpperCase());
            }
            MyResultSet = MyStatement.executeQuery("SELECT abbr, ministry_name FROM cameroon.ministry1");
            while (MyResultSet.next()) {
                this.map.put(MyResultSet.getString("abbr"),MyResultSet.getString("ministry_name"));
            }
            MyResultSet = MyStatement.executeQuery("SELECT language_spoken FROM cameroon.language");
            while (MyResultSet.next()) {
                this.language.add(MyResultSet.getString("language_spoken").toUpperCase());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public static void main(String[] args) throws SQLException {
        /* this code answers only three questions which are;
        -who is the governor of (region)
        -what language is spoken in which division/region
        -who is the minister of (ministry)
         */
        Main BotProp = new Main();
        Scanner scanner = new Scanner(System.in);


        System.out.println("Hello, you are welcome");
        System.out.println("how can input call you please");
        String name = scanner.nextLine();
        System.out.println(name + " what can i do for you?");

        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.isEmpty()) {
                System.out.println("please " + name + " input didn't understand you sorry");
                continue;
            } else {
                if (input.contains("nothing") || input.contains("no thanks") || input.contains("bye")) {
                    System.out.println("alright, hope input helped");
                    break;
                } else {
                    if (input.contains("who")) {
//                        System.out.println("I am in the who's If");
                        if (input.contains("governor")) {
//                            System.out.println("I am in the govenor's If");
                            for (String word : input.replaceAll("[?.,]", "").split(" ")) {
                                if (BotProp.region.contains(word.toUpperCase())) {
                                    ResultSet myst = BotProp.connect.createStatement().executeQuery("SELECT Governor_name FROM cameroon.governors" +
                                            " JOIN region" +
                                            " ON governors.id = region.ID" +
                                            " WHERE reg_name LIKE '" + word + "'");
                                    while (myst.next()) {
                                        String YourAns = myst.getString("Governor_name");
                                        System.out.println("the governor of " + word + " is " + YourAns);
                                        System.out.println("What else can I do for you? ");
                                    }
                                }
                            }
                        } else {
                            if (input.contains("minister")) {
                                // Getting an iterator
                                Iterator it = BotProp.map.entrySet().iterator();

                                // Iterate through the hashmap

                                while (it.hasNext()) {
                                    Map.Entry mapElement = (Map.Entry) it.next();
                                    String ministry = (String) mapElement.getValue();
                                    if (input.contains(ministry.toLowerCase())) {
                                        ResultSet myst = BotProp.connect.createStatement().executeQuery("SELECT minister_name FROM cameroon.ministers" +
                                                " JOIN ministry1" +
                                                " ON ministers.id = ministry1.ID" +
                                                " WHERE ministry_name LIKE '%" + ministry + "%'");
                                        while (myst.next()) {
                                            String YourAns = myst.getString("minister" +
                                                    "_name");
                                            System.out.println("the minister of " + ministry + " is " + YourAns);
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (input.contains("what")) {
                            for (String word : input.replaceAll("[?.,]", "").split(" ")) {
                                if (BotProp.region.contains(word.toUpperCase())) {
 //                                   System.out.println("in what");
                                    ResultSet myst = BotProp.connect.createStatement().executeQuery("SELECT language_spoken FROM language " +
                                            "JOIN region " +
                                            "ON language.id = region.ID " +
                                            "WHERE reg_name LIKE '%" + word + "%'");
                                    while (myst.next()) {
                                        String YourAns = myst.getString("language_spoken");
                                        System.out.println("the language spoken in " + word + " is " + YourAns);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}






