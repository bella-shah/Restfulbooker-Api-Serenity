package com.herokuapp.restful_booker.testbase;

import com.herokuapp.restful_booker.constants.Path;
import com.herokuapp.restful_booker.utils.PropertyReader;
import io.restassured.RestAssured;
import org.junit.BeforeClass;

public class TestBaseBooking {
    public static PropertyReader propertyReader;

    @BeforeClass
    public static void init() {
        propertyReader = PropertyReader.getInstance();
        RestAssured.baseURI = propertyReader.getProperty("baseUrl");
       RestAssured.basePath = Path.BOOKING;

    }
}



