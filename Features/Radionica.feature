Feature: Radionica

  @Radionica_Test
  Scenario Outline: Radionica_Test

    Given Open Login page
    And Wait for login page to load
    #And Click on element by text "EN"
    And Wait for "5" seconds
    And Login to application using credentials from excel
    When Assert welcome back page has loaded
    And Click on element by class "menu-btn"

    #And Click on element by iconName "icon-home"
    And Click on element by tag "span" and contains text "Računi"

    And Assert element by contains text "Stanje"
    And Assert element by contains text "Rezervisano"
    And Assert element by contains text "Raspoloživo stanje"
    #icon-account
    #icon-home
    And Assert element by iconName "wallet-icon"
    And Assert element by iconName "refresh-icon"
    And Assert element by iconName "currency-icon"
    And Assert sorted by date desc
    And Click on body
    And Click on element by containing class "nav-next slick-arrow"
    And Assert right currency is displayed

    #transakcije iz 2025

    And Click on element by containing class "filter-icon-svg" with index "2"
    And Click on element by xpath "//div[@id='dk1']//div[@class='dk-selected']"
    And Click on element by text "2025"
    And Click on element by attribute "title" and value "Primeni filtere"


    #detalji racuna

    And Click on body
    And Click on element by containing class "nav-next slick-arrow"
    And Click on element by attribute "title" and value "Detalji računa"
    #provera detalja racuna iz eksela
    And Assert field value "Naziv računa" has value from excel "1" columnName "accountName"
    And Assert field value "Broj računa" has value from excel "1" columnName "accountNumber"

    #And Assert field value "Stanje" has value from excel "1" columnName "accountName"

    #And Assert element by text {string}

#
#    When Assert welcome back page has loaded
#    And Select "Operation" as space to continue
#    And Click on origination menu item
#    And Click on New application
#    And Select "SBB & PI lending" process
#    And Assert "SBB & PI lending" is selected
#    And Click on create button for process
#    And Select "CRN" as identifier type
#    And Enter "company_registration_number" from Excel
#    And Click on button "Continue"
#    And Assert company review page is loaded
#    And Assert field "Company name" in company review page has value from excel
#    And Assert field "Registration number" in company review page has value from excel
#    And Assert field "Tax identification number" in company review page has value from excel
#    And Assert field "Company address" in company review page has value from excel
#    And Assert input field for "email" in company review page has value from excel
#    And Assert offered mobile phone prefix is "+3816"
#    And Validate mobile phone in company review page
#    And Enter valid phone number in company review page
#    And Validate email field in company review page
#    And Enter valid email in company review page
#    And Click on button "Continue"
#    And Assert company data page is loaded
#    And Assert company owner in company data page has value from excel
#    And Check if continue button is disabled
#    And Assert I have read Notice on the processing of personal data is valid
#    And Assert company data page is loaded
#    And Click on the toggle slider to confirm that I have read Notice on the processing of personal data
#    And Click on button "Continue"
#    And Assert contact details page is loaded
#    And Enter valid phone number in company review page
#    And Enter valid user email in company review page
#    And Click on button "Continue"
#    And Get redirect link with sms otp from email
#    And Open link under key "link"
#    And Assert email confirmed page is loaded
#    And Click on button "Continue"
#    And Assert phone number verification page is loaded
#    And Enter otp from key "smsOtp"
#
#    Then Assert consent page is loaded
#    And Click on the toggle slider to consent to receive future offers from the bank regarding its products and services
#    And Check if continue button is disabled
#    And Click on the toggle slider to consent to electronic communication with the bank
#    And Click on button "Continue"
#    And Assert process is complete



    Examples:
      | rowindex |
      |        1 |


  @Radionica_Test_2
  Scenario Outline: Radionica_Test_2

    Given Open Login page
    And Wait for login page to load
    And Login to application using credentials from excel

    When Assert welcome back page has loaded
    And Select "Operation" as space to continue
    And Click on origination menu item
    And Click on New application
    And Select "SBB & PI lending" process
    And Assert "SBB & PI lending" is selected
    And Click on create button for process
    And Select "CRN" as identifier type
    And Enter "company_registration_number_invalid" from Excel
    And Click on button "Continue"
    And Assert company review page is loaded
    And Enter valid phone number in company review page
    #And Enter valid email in company review page
    And Click on button "Continue"
    And Assert company data page is loaded
    And Select first representative in company data page
    And Check if continue button is disabled
    And Assert I have read Notice on the processing of personal data is valid
    And Assert company data page is loaded
    And Click on the toggle slider to confirm that I have read Notice on the processing of personal data
    And Click on button "Continue"
    And Assert contact details page is loaded
    And Enter valid phone number in company review page
    And Enter valid user email in company review page
    And Click on button "Continue"
    And Get redirect link with sms otp from email
    And Open link under key "link"
    And Assert email confirmed page is loaded
    And Click on button "Continue"
    And Assert phone number verification page is loaded
    And Enter otp from key "smsOtp"

    Then Assert consent page is loaded
    And Click on the toggle slider to consent to receive future offers from the bank regarding its products and services
    And Check if continue button is disabled
    And Click on the toggle slider to consent to electronic communication with the bank
    And Click on button "Continue"
    And Assert that the application cannot be completed online



    Examples:
      | rowindex |
      |        1 |


  @Radionica_Test_3
  Scenario Outline: Radionica_Test_3

    Given Open Login page
    And Wait for login page to load
    And Login to application using credentials from excel

    When Assert welcome back page has loaded
    And Select "Operation" as space to continue
    And Click on origination menu item
    And Click on New application
    And Select "SBB & PI lending" process
    And Assert "SBB & PI lending" is selected
    And Click on create button for process
    And Select "CRN" as identifier type
    And Enter "company_registration_number" from Excel
    And Click on button "Continue"
    And Assert company review page is loaded
    And Assert field "Company name" in company review page has value from excel
    And Assert field "Registration number" in company review page has value from excel
    And Assert field "Tax identification number" in company review page has value from excel
    And Assert field "Company address" in company review page has value from excel
    And Assert input field for "email" in company review page has value from excel
    And Assert offered mobile phone prefix is "+3816"
    And Validate mobile phone in company review page
    And Enter valid phone number in company review page
    And Validate email field in company review page
    And Enter valid email in company review page
    And Click on button "Continue"
    And Assert company data page is loaded
    And Assert company owner in company data page has value from excel
    And Check if continue button is disabled
    And Assert I have read Notice on the processing of personal data is valid
    And Assert company data page is loaded
    And Click on the toggle slider to confirm that I have read Notice on the processing of personal data
    And Click on button "Continue"
    And Assert contact details page is loaded
    And Enter valid phone number in company review page
    And Enter valid user email in company review page
    And Click on button "Continue"
    And Get redirect link with sms otp from email
    And Open link under key "link"
    And Assert email confirmed page is loaded
    And Click on button "Continue"
    And Assert phone number verification page is loaded
    And Enter otp from key "smsOtp"

    Then Assert consent page is loaded
    And Click on the toggle slider to consent to receive future offers from the bank regarding its products and services
    And Check if continue button is disabled
    And Click on the toggle slider to consent to electronic communication with the bank
    And Click on button "Continue"
    And Assert that the application cannot be completed online



    Examples:
      | rowindex |
      |        1 |