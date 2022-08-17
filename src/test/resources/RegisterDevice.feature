Feature: Register Device Tests
  This feature includes tests that test the register device RESTFul services

  @TestCaseId=MS-669
  @smokeTest
    @Vishnu
  Scenario: Register a device in MS with valid data
    Given I have a valid header for device
    When I register a device with valid data
    Then The device is registered successfully
    And Verify the response for registered device

  @TestCaseId=TOR2D-XXXX
    @smokeTest
    @Vishnu
  Scenario Outline: Register a device with existing deviceId
    Given I have a valid header for device
    When I register a device with valid data
    Then The device is registered successfully
    When I register a device with existing device
    Then The register device response code is <code> with an error message of <message>
    Examples:
      | code  | message                       |
      | 409   | The device ID already exists  |

  @TestCaseId=TOR2D-XXXX
  @smokeTest
    @Vishnu
  Scenario Outline: Register a device with missing or whitespace data
    Given I have a valid header for device
    When I register a device with whitespace string for the field <field> and value <value>
    Then The register device response code is <code> with an error message of <message>
    Examples:
      | field     | value | code  | message                                 |
      | deviceId  | empty | 400   | size must be between 1 and 128          |
      | password  | empty | 400   | size must be between 1 and 128          |
      | email     | empty | 400   |  size must be between 5 and 2147483647  |