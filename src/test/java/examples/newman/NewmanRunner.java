package examples.newman;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.intuit.karate.junit5.Karate;
import java.io.FileReader;
import java.util.List;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


class NewmanRunner {
    
    @Karate.Test
    Karate testTodos() {
        getDataFromDBAndWriteToCSVFile();
        // String cmd = getCommandWithData();
        executeCommand();
        return Karate.run("callNewman").relativeTo(getClass());
    } 
    public void getDataFromDBAndWriteToCSVFile(){
         try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@//172.18.14.33:1521/orcl2", "hr", "hr");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERAPI2");
            FileWriter csvWriter = new FileWriter("D:\\output.csv");
            while (resultSet.next()) {
                String userid = resultSet.getString("USER_ID");
                System.out.println("User id: " + userid);
                String username = resultSet.getString("USER_NAME");
                System.out.println("User name: " + username);
                String skill = resultSet.getString("SKILL");
                System.out.println("User kill: " + skill);
                String age = resultSet.getString("AGE");
                System.out.println("User age: " + age);
                csvWriter.append(username + "," + age +  "," + skill + "," + userid + "\n");
            }

            csvWriter.flush();
            csvWriter.close();

            System.out.println("Record to file successfully");
            System.out.println();
            
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    public String getDataFromCSVFile(String csvFilePath){
        StringBuilder csvContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                csvContent.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String csvData = csvContent.toString().replace("\"", "\\\"");
        return csvData;
    }
    
    public void executeCommand(){
        String jsonFilePath = "./src/test/java/examples/newman/collections.json";
        try (FileReader reader = new FileReader(jsonFilePath)) {
            Gson gson = new Gson();
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                String collection = jsonObject.get("collection").getAsString();
                String environment = jsonObject.get("environment").getAsString();
                String csvDataFilePath = jsonObject.get("csvDataFilePath").getAsString();
                String reportName = jsonObject.get("reportName").getAsString();
                // int iteration = jsonObject.get("iteration").getAsInt();

                System.out.println();
                System.out.println("Object " + (i + 1) + ":");
                System.out.println("Collection: " + collection);
                System.out.println("Environment: " + environment);
                System.out.println("CSV File Path: " + csvDataFilePath);
                System.out.println();

                String csvData = getDataFromCSVFile(csvDataFilePath);
                String command = "cmd.exe /c newman run \"" + collection + "\" -e \"" + environment + "\" --global-var csv-data=\"" + csvData + "\" --reporters junit --reporter-junit-export ./target/newman-reports/\"" + reportName + "\".xml";
            
                System.out.println(command);
                Process process = java.lang.Runtime.getRuntime().exec(command);
                process.waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //  try {
        //     String csvData = getDataFromCSVFile("D:\\output.csv");
        //     String command = "cmd.exe /c newman run https://api.postman.com/collections/15895208-71136bfa-1d7a-48f1-b825-a5d4ba7531b9?access_key=PMAT-01HYDDJYES08EAMP4J2MC3Y32F -e ./mockapi_env.postman_environment.json --global-var csv-data=\"" + csvData + "\" --reporters junit --reporter-junit-export ./target/newman-reports/report.xml";
            
        //     System.out.println(command);
        //     Process process = java.lang.Runtime.getRuntime().exec(command);
        //     process.waitFor();

        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
    }   
}
