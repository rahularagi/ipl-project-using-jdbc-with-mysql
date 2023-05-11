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
        Connection con= DriverManager.getConnection(url,uname,pass);
        Statement st= con.createStatement();

       findNumberOfMatchesPlayedPerYear(st);
       findNumberOfMatchesWonOfAllTeamsOverAllYear(st);
       findExtraRunsConcededPerTeam(st);
       findTopEconomicalBowlers(st);
        st.close();
        con.close();
    }
    public static void findNumberOfMatchesPlayedPerYear(Statement st)throws Exception{
        String query="select season,count(*) from matches group by season";
        ResultSet rs= st.executeQuery(query);
        System.out.println("Number of matches played per year in ipl");
        while(rs.next()){
            String name="year: "+rs.getInt(1)+", matches: "+rs.getString(2);
            System.out.println(name);
        }
        System.out.println();

    }

    public static void findNumberOfMatchesWonOfAllTeamsOverAllYear(Statement st)throws Exception{
        String query="select winner,count(*) from matches where winner!='' group by winner";
        ResultSet rs= st.executeQuery(query);
        System.out.println("Number of matches won of all teams over all year");
        while(rs.next()){
            String name="Team name: "+rs.getString(1)+", Total number of times won: "+rs.getInt(2);
            System.out.println(name);
        }
        System.out.println();

    }
    public static void findExtraRunsConcededPerTeam(Statement st)throws Exception{
        String query="select batting_team,sum(extra_runs) from deliveries where match_id In (select id from matches where season=2016) group by batting_team";
        ResultSet rs= st.executeQuery(query);
        System.out.println("Extra runs conceded per team in the year 2016");
        while(rs.next()){
            String name="Team name: "+rs.getString(1)+", Total extra runs: "+rs.getInt(2);
            System.out.println(name);
        }
        System.out.println();

    }
    public static void findTopEconomicalBowlers(Statement st)throws Exception{
        String query="select bowler, sum(if(wide_runs=0 and noball_runs=0,1,0)) as balls,sum(wide_runs+noball_runs+batsman_runs) as runs,(sum(wide_runs+noball_runs+batsman_runs)/(sum(if(wide_runs=0 and noball_runs=0,1,0))/6)) as 'economy' from deliveries where match_id in (select id from matches where season=2015) group by bowler order by economy";
        ResultSet rs= st.executeQuery(query);
        System.out.println("For the year 2015 get the top economical bowlers");
        while(rs.next()){
            String name="Bowler name: "+rs.getString(1)+", Total balls: "+rs.getInt(2)+", Total runs: "+rs.getInt(3)+", Economy: "+rs.getInt(4);
            System.out.println(name);
        }

    }
}