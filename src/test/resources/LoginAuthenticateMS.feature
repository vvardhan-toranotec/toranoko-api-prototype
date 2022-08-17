Feature: LoginAuthenticateMs Tests in MS
  This feature includes tests that test the LoginAuthenticateMs RESTFul services

  @TestCaseId=MS-667
  @smokeTest
  @Bhanu
  Scenario: Verify whether token is being generated in MS.
    Given Provide valid url for generating the token
#    Given Need to generate Token prior to start any API
    When Generate the token for specific Device Id
    Then Verify status code in Login API
    And Get the Token from response


    


