Feature: Running Newman

Background:

Scenario:
    * def command = "cmd.exe /c newman run https://api.postman.com/collections/15895208-53a6de53-7624-45fc-acb9-6e5e796329c8?access_key=PMAT-01HYARN2RTRC59HJT1X381NSWA -e ./mockapi_env.postman_environment.json --reporters junit --reporter-junit-export ./target/newman-reports/report.xml"
    * def process = java.lang.Runtime.getRuntime().exec(command)
    * karate.log("Process : " + process)
    * def inputStream = process.inputStream
    * karate.log("inputStream : " + inputStream) 
    * def BufferedReader = Java.type('java.io.BufferedReader')
    * def InputStreamReader = Java.type('java.io.InputStreamReader')
    * def reader = new BufferedReader(new InputStreamReader(inputStream))
    * def content = reader.lines().collect(java.util.stream.Collectors.joining("\n"))
    * match content contains ''