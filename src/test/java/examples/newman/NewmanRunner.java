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
class NewmanRunner {
    
    @Karate.Test
    Karate testTodos() {
        getDataFromDB();
        // String cmd = getCommandWithData();
        executeCommand();
        return Karate.run("callNewman").relativeTo(getClass());
    } 
    public void getDataFromDB(){
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
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    // public String getCommandWithData(){
    //     String csvFile = "D://output.csv";
    //     String csvFilePathCMd = "$(type D://output.csv)";
    //     try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
    //         List<String[]> rows = reader.readAll();
    //         StringBuilder csvData = new StringBuilder();
    //         for (String[] row : rows) {
    //             for (String cell : row) {
    //                 csvData.append(cell).append(",");
    //             }
    //             csvData.deleteCharAt(csvData.length() - 1); // Remove the last comma
    //             csvData.append("\n");
    //         }
    //         System.out.println("CSV Data:");
    //         System.out.println(csvData.toString());

    //         String command = "cmd.exe /c newman run https://api.postman.com/collections/15895208-71136bfa-1d7a-48f1-b825-a5d4ba7531b9?access_key=PMAT-01HYDDJYES08EAMP4J2MC3Y32F -e ./mockapi_env.postman_environment.json --global-var csv-data=\"" + csvFilePathCMd + "\""+" --reporters junit --reporter-junit-export ./target/newman-reports/report.xml";
           
    //         System.out.println(command);

    //         return command;
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     } catch (CsvException e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }
    public void executeCommand(){
         try {
            String csvFilePath = "D:\\output.csv";
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
            String command = "cmd.exe /c newman run https://api.postman.com/collections/15895208-71136bfa-1d7a-48f1-b825-a5d4ba7531b9?access_key=PMAT-01HYDDJYES08EAMP4J2MC3Y32F -e ./mockapi_env.postman_environment.json --global-var csv-data=\"" + csvData + "\" --reporters junit --reporter-junit-export ./target/newman-reports/report.xml";
            System.out.println(command);
            Process process = java.lang.Runtime.getRuntime().exec(command);
            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }   
}
