Feature: Delete user

Background:
    * url apiURL
Scenario: Delete user
    #Delete user
        * path userid
        * method delete
        * status 200