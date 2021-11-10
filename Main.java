package com.company;

import java.sql.*;
import java.util.*;
import java.util.Map;

public class Main {
    private static Object Statement;
    Connection connect = null;
    private List<String> governor;
    private List<String> minister;
    private List<String> region;
    private List<String> division;
    private List<String> language;
    private List<String> capitalreg;
    private List<String> capitaldiv;
    private Map<String, String> map;
    private static List<String> inputStore;
    private List<String[]> Ministry;

    public static List<String> EVADE_LIST = new ArrayList<>();

    public Main() {
        Statement MyStatement = null;
        ResultSet MyResultSet = null;

        this.governor = new ArrayList();
        this.minister = new ArrayList();
        this.region = new ArrayList();
        this.division = new ArrayList();
        this.language = new ArrayList();
        this.capitalreg = new ArrayList();
        this.map = new HashMap();
        this.capitaldiv = new ArrayList();
        this.inputStore = new ArrayList<>();
        this.Ministry = new ArrayList<>();


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
                this.map.put(MyResultSet.getString("abbr"), MyResultSet.getString("ministry_name"));
            }

            MyResultSet = MyStatement.executeQuery("SELECT language_spoken FROM cameroon.language");
            while (MyResultSet.next()) {
                this.language.add(MyResultSet.getString("language_spoken").toUpperCase());
            }
            MyResultSet = MyStatement.executeQuery("SELECT capital FROM cameroon.region");
            while (MyResultSet.next()) {
                this.capitalreg.add(MyResultSet.getString("capital").toUpperCase());
            }
            MyResultSet = MyStatement.executeQuery("SELECT division FROM cameroon.division");
            while (MyResultSet.next()) {
                this.capitaldiv.add(MyResultSet.getString("division").toUpperCase());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public static boolean matchMinistry(String text, String ministry){
        for(String word : text.toLowerCase().replaceAll("[?.,]", "").split(" ")){
            System.out.println(word);
            String [] list = {"who", "is", "the", "minister", "ministers", "ministry", "ministries", "of"};
            EVADE_LIST = Arrays.asList(list);
            if (!EVADE_LIST.contains(word)){
                if (ministry.contains(word)) {
                    return true;
                }
            } else {
                //System.out.println("Skipped: " + word);
            }
        }
        return false;
    }
    public static String input() {
        Scanner scanner = new Scanner(System.in);

        String requestInput = scanner.nextLine().trim().toLowerCase();
        Main.inputStore.add(requestInput);
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = (java.sql.Statement) statement.executeQuery("INSERT INTO cameroon.inputstore (`id_input`, `input`) VALUES ('"+inputStore.size()+"','"+requestInput+"')");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                inputStore.add(resultSet.getString("input").toUpperCase());
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return requestInput;
    }


    public static void main(String[] args) throws SQLException {
        /**
        this code answers only five questions which are;
        -who is the governor of (region)
        -what language is spoken in which region
        -who is the minister of (ministry)
        -languages in cameroon
        -capital cities of regions
        -capital cities of divisions
        -governors in cameroon
        -ministers in cameroon
         */


        Main BotProp = new Main();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello, you are welcome");
        System.out.println("how can i call you please");
        String name = scanner.nextLine();
        System.out.println(name + " what can i do for you?");
        System.out.println(inputStore);


        


        while (true) {
             input();
            System.out.println(BotProp.inputStore.add(input()));
            System.out.println(BotProp.inputStore.size());
            if (input().isEmpty()) {
                System.out.println("please " + name + " i didn't understand you sorry");
                continue;
            } else {
                if (input() .contains("nothing") || input() .contains("no thanks") || input() .contains("bye")) {
                    System.out.println("alright, hope i helped");
                    break;
                } else {
                    if (input() .contains("who")) {
                        System.out.println("here");
                        if (input() .contains("governor")|| input() .contains("governors")) {
                            for (String word : input() .replaceAll("[?.,]", "").split(" ")) {
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
                                }else if (input().contains("cameroon")){
                                    ResultSet myst = BotProp.connect.createStatement().executeQuery("SELECT * FROM cameroon.governors");
                                    while (myst.next()) {
                                        String YourAns = myst.getString("Governor_name");
                                        System.out.println(YourAns);                                    //running six times
                                    }
                                    System.out.println("what else can i do for you");
                                    break;
                                }
                            }
                        } else {
                            if (input() .contains("minister")|| input() .contains("ministers")) {
                                // Getting an iterator
                                Iterator it = BotProp.map.entrySet().iterator();
                                // Iterate through the hashmap
                                while (it.hasNext()) {
                                    Map.Entry mapElement = (Map.Entry) it.next();
                                    String ministry = (String) mapElement.getValue();
                                    if (input() .contains(ministry.toLowerCase())) {
                                        ResultSet myst = BotProp.connect.createStatement().executeQuery("SELECT minister_name FROM cameroon.ministers" +
                                                " JOIN ministry1" +
                                                " ON ministers.id = ministry1.ID" +
                                                " WHERE ministry_name LIKE '%" + ministry + "%'");
                                        while (myst.next()) {
                                            String YourAns = myst.getString("minister" +
                                                    "_name");
                                            System.out.println("the minister of " + ministry + " is " + YourAns);
                                            System.out.println("What else can I do for you? ");
                                        }
                                    } else if (input() .contains("cameroon")) {
                                        ResultSet myst = BotProp.connect.createStatement().executeQuery("SELECT * FROM cameroon.ministers");
                                        while (myst.next()) {
                                            String YourAns = myst.getString("minister_name");
                                            System.out.println(YourAns);
                                        }
                                        System.out.println("what else can i do for you");
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        if (input() .contains("what")|| input() .contains("which")) {
                            if (input() .contains("language")|| input() .contains("languages")){
                                for (String word : input() .replaceAll("[?.,]", "").split(" ")) {
                                    if (BotProp.region.contains(word.toUpperCase())) {
                                        ResultSet myst = BotProp.connect.createStatement().executeQuery("SELECT language_spoken FROM language " +
                                                "JOIN region " +
                                                "ON language.id = region.language " +
                                                "WHERE reg_name LIKE '" + word + "'");
                                        while (myst.next()) {
                                            String YourAns = myst.getString("language_spoken");
                                            System.out.println("the language spoken in " + word + " is " + YourAns);
                                            System.out.println("What else can I do for you? ");
                                        }
                                    }else if (input() .toLowerCase().contains("cameroon")){
                                        ResultSet myst = BotProp.connect.createStatement().executeQuery("select * from cameroon.language");
                                        while (myst.next()) {
                                            String YourAns = myst.getString("language_spoken");
                                            System.out.println(YourAns);
                                        }
                                        System.out.println("What else can I do for you? ");
                                        break;
                                    }
                                }
                            } else if (input() .contains("capital")|| input() .contains("capitals")) {
                                for (String word : input() .replaceAll("[?.,]", "").split(" ")) {
                                    if (BotProp.region.contains(word.toUpperCase())) {
                                        ResultSet myst = BotProp.connect.createStatement().executeQuery("SELECT capital FROM cameroon.region" +
                                                " where reg_name like '" + word + "'");
                                        while (myst.next()) {
                                            String YourAns = myst.getString("capital");
                                            System.out.println("the capital of " + word + " is " + YourAns);
                                            System.out.println("What else can I do for you? ");
                                        }
                                    }else if (BotProp.capitaldiv.contains(word.toUpperCase())){
                                        ResultSet myst = BotProp.connect.createStatement().executeQuery("SELECT capital FROM cameroon.division" +
                                                " where division like '" + word + "'");
                                        while (myst.next()) {
                                            String YourAns = myst.getString("capital");
                                            System.out.println("the capital of " + word + " is " + YourAns);
                                            System.out.println("What else can I do for you? ");
                                            break;
                                        }
                                    }
                                    if (input() .contains("regions")&& input() .contains("cameroon")){
                                        word.toUpperCase();
                                        ResultSet myst = BotProp.connect.createStatement().executeQuery("SELECT reg_name, capital FROM cameroon.region");
                                        while (myst.next()) {
                                            String YourAns = myst.getString("reg_name");
                                            String YourAns1 = myst.getString("capital");
                                            System.out.println("Region: "+ YourAns);System.out.println("capital "+YourAns1);
                                        }
                                        System.out.println("what else can i do?");
                                        return;
                                    }else if (input() .contains("divisions")&& input() .contains("cameroon")){
                                        word.toUpperCase();
                                        ResultSet myst = BotProp.connect.createStatement().executeQuery("SELECT division, capital FROM cameroon.region");
                                        while (myst.next()) {
                                            String YourAns = myst.getString("division");
                                            String YourAns1 = myst.getString("capital");
                                            System.out.println("division:"+YourAns);
                                            System.out.println("capital"+YourAns1);
                                            System.out.println("What else can I do for you? ");
                                        }
                                    }
                                }

                            }else if(input() .contains("regions")){
                                System.out.println(BotProp.region);
                                System.out.println("what else can i so for you");
                            }
                            else if (input() .contains("divisions")){
                                System.out.println(BotProp.capitaldiv);
                            }
                        }
                    }
                }
            }

        }
    }
}







