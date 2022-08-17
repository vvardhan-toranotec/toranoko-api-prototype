Feature: Authenticate Tests
  This feature includes tests that test the authenticate RESTFul services

  @TestCaseId=TOR2D-XXXX
  @smokeTest
  Scenario: Verify whether token is being generated
    Given Provide valid url
    When Generate the token for specific uer
    Then Verify status code
    And Verify whether Token is generated


    


