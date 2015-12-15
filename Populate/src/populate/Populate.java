package populate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Populate {
public static void main(String args[]) {
    Populate ppl = new Populate();
    String[] s = {"yelp_checkin.json", "yelp_business.json",  "yelp_review.json",  "yelp_user.json"};
    args = s;
    ppl.run(args);
}

public void run(String args[]) {
    Connection con = null;
    try {
        con = openConnection(); 
        if (args.length < 3) {
                System.err.println("Do not have enough input files");
		System.exit(-1);            
	}
        boolean[] fileName = new boolean[4];
            for (int i = 0; i < args.length; i++) {
                String file = args[i];
                switch(file) {
                    case "yelp_business.json": 
                        fileName[0] = true;
                        break;
                    case "yelp_user.json":
                        fileName[1] = true;
                        break;
                    case "yelp_review.json":
                        fileName[2] = true;
                        break;
                    case "yelp_checkin.json":
                        fileName[3] = true;
                        break;
                }
            }
            if (fileName[0] & fileName[1] & fileName[2] & fileName[3]){
                deleteRows(con);
                business(con);
                users(con);
                attributes(con);
                hours(con);
                categories(con);
                review(con);
            }else{
                System.err.println("Do not have right input files");
		System.exit(-1);  
            }
    } catch (SQLException e) {
        System.err.println("Errors occurs when communicating with the database server: " + e.getMessage());
    } catch (ClassNotFoundException e) {
        System.err.println("Cannot find the database driver");
    } finally {
        closeConnection(con);
    }
}
private void deleteRows (Connection con) throws SQLException{
    Statement stmt = con.createStatement();
    try{
        System.out.println("Deleting previous data...");
        stmt.executeUpdate("Delete from review");
        stmt.executeUpdate("Delete from categories");
        stmt.executeUpdate("Delete from hours");
        stmt.executeUpdate("Delete from users");
        stmt.executeUpdate("Delete from attributes");
        stmt.executeUpdate("Delete from business");
        System.out.println("Deleted");
    }catch(SQLException e){
        System.out.println("SQL error: " + e.getMessage());
    }
}
private void business(Connection con) throws SQLException{
    Statement stmt = con.createStatement();
    File file = new File("yelp_business.json");
    BufferedReader reader = null;
    try{
        System.out.println("Inserting into business.");
        reader = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = reader.readLine()) != null){
            JSONObject jObj = parse(line);
            StringBuffer sb = new StringBuffer();
            sb.append("insert into business values('");
            sb.append(jObj.get("business_id"));
            sb.append("','");
            sb.append(jObj.get(findQuote(jObj.get("full_address").toString())));
            sb.append("','");
            sb.append(jObj.get("hours"));
            sb.append("','");
            sb.append(jObj.get("open"));
            sb.append("','");
            sb.append(findQuote(jObj.get("categories").toString()));
            sb.append("','");
            sb.append(jObj.get("city"));
            sb.append("','");
            sb.append(jObj.get("state"));
            sb.append("',");
            sb.append(jObj.get("review_count"));
            sb.append(",'");
            sb.append(findQuote(jObj.get("name").toString()));
            sb.append("','");
            sb.append(findQuote(jObj.get("neighborhoods").toString()));
            sb.append("',");
            sb.append(jObj.get("stars"));
            sb.append(",'");
            sb.append(findQuote(jObj.get("attributes").toString()));
            sb.append("','");
            sb.append(jObj.get("type"));
            sb.append("')");
            stmt.executeUpdate(sb.toString());
        }
    }catch(SQLException e){
        System.out.println("SQL error: " + e.getMessage());
    }catch(ParseException e){
        System.out.println("Parse error: " + e.getMessage());
    }catch( FileNotFoundException e){
        System.out.println("file not found");
    }catch(IOException e){
        System.out.println("IOException" + e.getMessage());
    }
}
private void users (Connection con) throws SQLException{
    Statement stmt = con.createStatement();
    File file = new File("yelp_user.json");
    BufferedReader reader = null;
    try{
        reader = new BufferedReader(new FileReader(file));
        String line = new String();
        System.out.println("Inserting into users.");
        while ((line = reader.readLine()) != null){
            JSONObject jObj = parse(line);
            StringBuffer sb = new StringBuffer();
            sb.append("insert into users values('");
            sb.append(jObj.get("user_id"));
            sb.append("','");
            sb.append(findQuote(jObj.get("name").toString()));
            sb.append("')");
            stmt.executeUpdate(sb.toString());
        }
    }catch(SQLException e){
        System.out.println("SQL error: " + e.getMessage());
    }catch(ParseException e){
        System.out.println("Parse error: " + e.getMessage());
    }catch( FileNotFoundException e){
        System.out.println("file not found");
    }catch(IOException e){
        System.out.println("IOException" + e.getMessage());
    }
}

private void review (Connection con) throws SQLException{
    PreparedStatement stmt = con.prepareStatement("insert into review values " + "(?,?,?,?,?,?)");
    File file = new File("yelp_review.json");
    BufferedReader reader = null;
    try{
        System.out.println("Inserting into reviews.");
        reader = new BufferedReader(new FileReader(file));
        String line = new String();
        while ((line = reader.readLine()) != null){
            JSONObject jObj = parse(line);
            stmt.setString(1, jObj.get("user_id").toString());
            stmt.setString(2, jObj.get("review_id").toString());
            stmt.setInt(3, Integer.parseInt(jObj.get("stars").toString()));
            stmt.setString(4, jObj.get("date").toString());
            stmt.setString(5, cut(jObj.get("text").toString()));
            stmt.setString(6, jObj.get("business_id").toString());
            stmt.executeUpdate();
        }
    }catch(SQLException e){
        System.out.println("SQL error: " + e.getMessage());
    }catch(ParseException e){
        System.out.println("Parse error: " + e.getMessage());
    }catch( FileNotFoundException e){
        System.out.println("file not found");
    }catch(IOException e){
        System.out.println("IOException" + e.getMessage());
    }
}

private void attributes (Connection con) throws SQLException{
    PreparedStatement stmt = con.prepareStatement("insert into attributes values " + "(?,?)");
    File file = new File("yelp_business.json");
    BufferedReader reader = null;
    try{
        System.out.println("Inserting into attributes.");
        reader = new BufferedReader(new FileReader(file));
        String line = new String();
        while ((line = reader.readLine()) != null){
            JSONObject jObj = parse(line);
            ArrayList attrList = attrParse(line);
            for (int i = 0; i < attrList.size(); i++){
                stmt.setString(1, jObj.get("business_id").toString());
                stmt.setString(2, attrList.get(i).toString());
                stmt.executeUpdate();
            }
        }
    }catch(SQLException e){
        System.out.println("SQL error: " + e.getMessage());
    }catch(ParseException e){
        System.out.println("Parse error: " + e.getMessage());
    }catch( FileNotFoundException e){
        System.out.println("file not found");
    }catch(IOException e){
        System.out.println("IOException" + e.getMessage());
    }
}
private void hours (Connection con) throws SQLException{
    PreparedStatement stmt = con.prepareStatement("INSERT INTO HOURS VALUES" + "(?, ?, TO_TIMESTAMP(?, 'HH24:MI'), TO_TIMESTAMP(?, 'HH24:MI'))");
    File file = new File("yelp_business.json");
    BufferedReader reader = null;
    try{
        System.out.println("Inserting into hours.");
        reader = new BufferedReader(new FileReader(file));
        String line = new String();
        while ((line = reader.readLine()) != null){
            JSONObject obj1 = parse(line);
            JSONObject obj = (JSONObject)obj1.get("hours");
                for(Iterator iterator = obj.keySet().iterator(); iterator.hasNext();) {
                    String key = (String)iterator.next();
                    stmt.setString(1, obj1.get("business_id").toString());
                    stmt.setString(2, key);
                    stmt.setString(3, ((JSONObject)obj.get(key)).get("close").toString());
                    stmt.setString(4, ((JSONObject)obj.get(key)).get("open").toString());
                    stmt.executeUpdate();
        }
        }
         
    }catch(SQLException e){
        System.out.println("SQL error: " + e.getMessage());
    }catch(ParseException e){
        System.out.println("Parse error: " + e.getMessage());
    }catch( FileNotFoundException e){
        System.out.println("file not found");
    }catch(IOException e){
        System.out.println("IOException" + e.getMessage());
    }
}
private ArrayList attrParse (String s){
    ArrayList attrList = new ArrayList();
    try{
        JSONObject obj1 = parse(s);
        JSONObject obj = (JSONObject)obj1.get("attributes");        
        for(Iterator iterator = obj.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            if(obj.get(key) instanceof JSONObject){
                JSONObject obj2 = (JSONObject)obj.get(key);
                for(Iterator iterator2 = obj2.keySet().iterator(); iterator2.hasNext();){
                    String key2 = (String) iterator2.next();
                    String s2 = key2 + ": " + obj2.get(key2).toString();
                    attrList.add(s2);
                }
            }else{
                String s1 = key +": "+ obj.get(key).toString();
                attrList.add(s1);
            }
        }
    }catch(Exception e){
        System.out.println("error: " +e.getMessage());
    }
    return attrList;
}


private void categories (Connection con) throws SQLException{
    PreparedStatement stmt = con.prepareStatement("insert into categories values " + "(?,?)");
    File file = new File("yelp_business.json");
    BufferedReader reader = null;
    try{
        System.out.println("Inserting into categories.");
        reader = new BufferedReader(new FileReader(file));
        String line = new String();
        while ((line = reader.readLine()) != null){
            JSONObject jObj = parse(line);
            ArrayList cateList = (ArrayList)jObj.get("categories");
            for (int i = 0; i < cateList.size(); i++){
                stmt.setString(1, jObj.get("business_id").toString());
                stmt.setString(2, cateList.get(i).toString());
                stmt.executeUpdate();
            }
        }
    }catch(SQLException e){
        System.out.println("SQL error: " + e.getMessage());
    }catch(ParseException e){
        System.out.println("Parse error: " + e.getMessage());
    }catch( FileNotFoundException e){
        System.out.println("file not found");
    }catch(IOException e){
        System.out.println("IOException" + e.getMessage());
    }
}

private JSONObject parse(String s) throws ParseException{
    JSONParser parser = new JSONParser();
    JSONObject jObj = (JSONObject) parser.parse(s);
    return jObj;
}
private String findQuote(String s){
    int len = s.length();
    for (int i = 0; i< len; i++){
        if(s.charAt(i) == '\''){
            String temp = s.substring(0,i + 1) + "\'" + s.substring(i + 1, len);
            i = i + 1;
            len = len + 1;
            s = temp;
        }
    }
    return s;
}
private String cut (String s){
    if (s.length() > 1000){
        s = s.substring(0, 999);
    }
    return s;
}


/**
*
* @return a database connection
* @throws SQLException when there is an error when trying to connect database
* @throws ClassNotFoundException when the database driver is not found.
*/
private Connection openConnection() throws SQLException, ClassNotFoundException {
// Load the Oracle database driver
DriverManager.registerDriver(new oracle.jdbc.OracleDriver());


String host = "localhost";
String port = "1521";
String dbName = "orcl";    
String userName = "system";  
String password = "123456";

// Construct the JDBC URL
String dbURL = "jdbc:oracle:thin:@" + host + ":" + port + ":" + dbName;
return DriverManager.getConnection(dbURL, userName, password);
}

/**
* Close the database connection
* @param con
*/
private void closeConnection(Connection con) {
try {
con.close();
} catch (SQLException e) {
System.err.println("Cannot close connection: " + e.getMessage());
}
}
}