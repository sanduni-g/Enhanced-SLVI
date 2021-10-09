package com.example.slvi;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void LoginPageSignUpTest(){
        Boolean LoginPageSignUpTestTrue = Login.buttonClickValidation("SignUp");
        assertEquals(true, LoginPageSignUpTestTrue);
    }

    @Test
    public void SignUpPageLoginTest(){
        Boolean SignUpPageLoginTestTrue = SignUp.buttonClickValidation("Login");
        assertEquals(true, SignUpPageLoginTestTrue);
    }

    @Test
    public void VehicleNumberValidationTest(){
        Boolean VehicleNumberValidationTestTrue = ViewVehicleDetails.vehicleNumberValidation(7);
        assertEquals(true, VehicleNumberValidationTestTrue);

        Boolean VehicleNumberValidationTestFalse = ViewVehicleDetails.vehicleNumberValidation(5);
        assertEquals(false, VehicleNumberValidationTestFalse);
    }

    @Test
    public void RevenueVehicleNumberValidationTest(){
        Boolean RevenueVehicleNumberValidationTestTrue = RevenueDetails.vehicleNumberValidation(7);
        assertEquals(true, RevenueVehicleNumberValidationTestTrue);

        Boolean RevenueVehicleNumberValidationTestFalse = RevenueDetails.vehicleNumberValidation(5);
        assertEquals(false, RevenueVehicleNumberValidationTestFalse);
    }

    @Test
    public void MainActivityNavTest(){
        Boolean NavigateViewVehicleDetailsTrue = MainActivity.MainActivityNavigationTest("ViewVehicleDetails");
        assertEquals(true, NavigateViewVehicleDetailsTrue);

        Boolean NavigateRevenueDetailsTrue = MainActivity.MainActivityNavigationTest("RevenueDetails");
        assertEquals(true, NavigateRevenueDetailsTrue);

        Boolean NavigateOngoingVehicleNumberTrue = MainActivity.MainActivityNavigationTest("OngoingVehicleNumber");
        assertEquals(true, NavigateOngoingVehicleNumberTrue);

        Boolean NavigateProfileTrue = MainActivity.MainActivityNavigationTest("Profile");
        assertEquals(true, NavigateProfileTrue);

        Boolean NavigateFeedbackTrue = MainActivity.MainActivityNavigationTest("Feedback");
        assertEquals(true, NavigateFeedbackTrue);

        Boolean NavigateContactUsTrue = MainActivity.MainActivityNavigationTest("ContactUs");
        assertEquals(true, NavigateContactUsTrue);
    }
}