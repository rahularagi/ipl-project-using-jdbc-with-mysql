import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws Exception{
        String url="jdbc:mysql://localhost:3306/IPL_Project";
        String uname="rahul";
        String pass="rahul";

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection= DriverManager.getConnection(url,uname,pass);
        Statement statement= connection.createStatement();

       findNumberOfMatchesPlayedPerYear(statement);
       findNumberOfMatchesWonOfAllTeamsOverAllYear(statement);
       findExtraRunsConcededPerTeam(statement);
       findTopEconomicalBowlers(statement);
        statement.close();
        connection.close();
    }
    public static void findNumberOfMatchesPlayedPerYear(Statement statement)throws Exception{
        String query="select season,count(*) from matches group by season";
        ResultSet result= statement.executeQuery(query);
        System.out.println("Number of matches played per year in ipl");
        while(result.next()){
            String name="year: "+result.getInt(1)+", matches: "+result.getString(2);
            System.out.println(name);
        }
        System.out.println();

    }

    public static void findNumberOfMatchesWonOfAllTeamsOverAllYear(Statement statement)throws Exception{
        String query="select winner,count(*) from matches where winner!='' group by winner";
        ResultSet result= statement.executeQuery(query);
        System.out.println("Number of matches won of all teams over all year");
        while(result.next()){
            String name="Team name: "+result.getString(1)+", Total number of times won: "+result.getInt(2);
            System.out.println(name);
        }
        System.out.println();

    }
    public static void findExtraRunsConcededPerTeam(Statement statement)throws Exception{
        String query="select batting_team,sum(extra_runs) from deliveries where match_id In (select id from matches where season=2016) group by batting_team";
        ResultSet result= statement.executeQuery(query);
        System.out.println("Extra runs conceded per team in the year 2016");
        while(result.next()){
            String name="Team name: "+result.getString(1)+", Total extra runs: "+result.getInt(2);
            System.out.println(name);
        }
        System.out.println();

    }
    public static void findTopEconomicalBowlers(Statement statement)throws Exception{
        String query="select bowler, sum(if(wide_runs=0 and noball_runs=0,1,0)) as balls,sum(wide_runs+noball_runs+batsman_runs) as runs,(sum(wide_runs+noball_runs+batsman_runs)/(sum(if(wide_runs=0 and noball_runs=0,1,0))/6)) as 'economy' from deliveries where match_id in (select id from matches where season=2015) group by bowler order by economy";
        ResultSet result= statement.executeQuery(query);
        System.out.println("For the year 2015 get the top economical bowlers");
        while(result.next()){
            String name="Bowler name: "+result.getString(1)+", Total balls: "+result.getInt(2)+", Total runs: "+result.getInt(3)+", Economy: "+result.getInt(4);
            System.out.println(name);
        }

    }
}