Feature: Karate Basic 2 Todos 
Background:
    * def urlBase = apiURL
Scenario: get list users
    * url urlBase
    * header Accept = 'application/json'
    * method get
    * status 200