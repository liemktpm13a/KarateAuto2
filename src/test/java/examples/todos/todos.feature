Feature: Karate Basic Todos

Background:
    * def urlBase = apiURL
    
  Scenario: get list users
    * url urlBase
    * header Accept = 'application/json'
    * method get
    * status 200
  Scenario: Manage a user
    #Create user
    * url urlBase
    * request { user-name: 'Helen', age: 30,skill:[{skill-name:"reading",rate:"6"},{skill-name:"coding",rate:"9"}],userid:"4"}  
    * method post
    * status 201
    * match response == { user-name: 'Helen', age: 30,skill:[{skill-name:"reading",rate:"6"},{skill-name:"coding",rate:"9"}],userid:"4"}

    * def userName = response['user-name']
    * def age = response.age
    * def skills = response.skill
    * def userid = response.userid
    * def skillData = []
    * eval karate.forEach(skills, function(s){ karate.appendTo(skillData, { 'skill-name': s['skill-name'], 'rate': s.rate }) })


    #Get user
    * path userid
    * method get
    * status 200
    * match response == {user-name: '#(userName)', age: '#(age)', skill: '#(skillData)', userid: '#(userid)'}

    #Get all users to make sure the new user is created
    * url urlBase
    * method get
    * status 200
    * def createdUser = response[3]
    * match createdUser["user-name"] == 'Helen'

    #Update user
    * path userid
    * request {user-name: 'Huyenly'}
    * method put
    * status 200
    * def updatedUser = response
    * match updatedUser["user-name"] == 'Huyenly'

    * call read('deleteUsers.feature')
    # #Delete user
    # * path userid
    # * method delete
    # * status 200


