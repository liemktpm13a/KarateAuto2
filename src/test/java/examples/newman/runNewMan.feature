Feature: Running Newman

Background:

Scenario:
    # * def command = "cmd.exe /c newman run https://api.postman.com/collections/15895208-71136bfa-1d7a-48f1-b825-a5d4ba7531b9?access_key=PMAT-01HYDDJYES08EAMP4J2MC3Y32F -e ./mockapi_env.postman_environment.json --global-var 'csv-data=$(D:\\output.csv)' --reporters junit --reporter-junit-export ./target/newman-reports/report.xml"
    # * def command = "cmd.exe /c newman run https://api.postman.com/collections/15895208-71136bfa-1d7a-48f1-b825-a5d4ba7531b9?access_key=PMAT-01HYDDJYES08EAMP4J2MC3Y32F -e ./mockapi_env.postman_environment.json --global-var csv-data=\"$( D:\\output.csv)\" --reporters junit --reporter-junit-export ./target/newman-reports/report.xml"
    # * def process = java.lang.Runtime.getRuntime().exec(command)
    # * karate.log("Process : " + process)
    # * def inputStream = process.inputStream
    # * karate.log("inputStream : " + inputStream) 
    # * def BufferedReader = Java.type('java.io.BufferedReader')
    # * def InputStreamReader = Java.type('java.io.InputStreamReader')
    # * def reader = new BufferedReader(new InputStreamReader(inputStream))
    # * def content = reader.lines().collect(java.util.stream.Collectors.joining("\n"))
    * def content = ''
    * match content contains ''