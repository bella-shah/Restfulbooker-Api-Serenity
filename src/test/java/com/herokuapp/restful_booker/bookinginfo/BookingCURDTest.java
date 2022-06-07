package com.herokuapp.restful_booker.bookinginfo;

import com.herokuapp.restful_booker.constants.EndPoints;
import com.herokuapp.restful_booker.model.BookingDatesPojo;
import com.herokuapp.restful_booker.model.BookingPojo;
import com.herokuapp.restful_booker.testbase.TestBaseBooking;
import com.herokuapp.restful_booker.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasValue;
@RunWith(SerenityRunner.class)
public class BookingCURDTest extends TestBaseBooking {
    static String firstname = "Bella Shah" + TestUtils.getRandomValue();
    static String lastname = "Shah" + TestUtils.getRandomValue();
    static Integer totalprice = 150;
    static Boolean depositpaid = true;
    static Date checkin = new Date(2018,01,01);
    static Date checkout = new Date(2019,01,01);
    static String additionalneeds = "Breakfast";
    static int bookingID;

    @Title("This will create booking")
    @Test
    public void test001() {
       BookingPojo bookingPojo = new BookingPojo();
       BookingDatesPojo bookingDatesPojo = new BookingDatesPojo();
       bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingDatesPojo.setCheckin(checkin);
        bookingDatesPojo.setCheckout(checkout);
        bookingPojo.setAdditionalneeds(additionalneeds);

        HashMap<String, Object> header1 = new HashMap<>();
        header1.put("Content-Type", "application/json");
        header1.put("Accept", "application/json");
        header1.put("token", "987450a7263c4f9");
        SerenityRest.given().log().all()
                .headers(header1)
                .body(bookingPojo)
                .when()
                .post(EndPoints.CREATE_ALL_BOOKINGS)
                .then().statusCode(201).log().all();
    }

    @Title("Verify if the  booking information with booking firstname = Riya")
    @Test
    public void test002() {
        firstname = "Riya";
        String p1 = "findAll{it.name=='";
        String p2 = "'}.get(0)";
        HashMap<String, Object> userMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_BOOKINGS_BY_NAME)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + firstname + p2);
        Assert.assertThat(userMap, hasValue(firstname));
        bookingID = (int) userMap.get("id");
        System.out.println(bookingID);
    }

    @Title("Updating booking information with bookingID")
    @Test
    public void test003() {
        firstname = "Sila";
       lastname = "Shah";
        totalprice = 150;
       depositpaid = true;
       checkin = new Date(2018,01,01);
        checkout = new Date(2019,01,01);
        additionalneeds = "Meal";


        BookingPojo bookingPojo = new BookingPojo();
        BookingDatesPojo bookingDatesPojo = new BookingDatesPojo();
        bookingPojo.setFirstname(firstname);
        bookingPojo.setLastname(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingDatesPojo.setCheckin(checkin);
        bookingDatesPojo.setCheckout(checkout);
        bookingPojo.setAdditionalneeds(additionalneeds);

        HashMap<String, Object> header1 = new HashMap<>();
        header1.put("Accept", "application/json");
        header1.put("token", "987450a7263c4f9");

        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .headers(header1)
                .pathParam("BookingId", bookingID)
                .body(bookingPojo)
                .when()
                .put(EndPoints.UPDATE_BOOKING_BY_ID)
                .then().statusCode(200).log().body().body("firstname", equalTo(firstname), "lastname", equalTo(lastname));



    }

    @Title("Deleting booking information with bookingID")
    @Test
    public void test004() {
    bookingID = 5474;

        HashMap<String, Object> header1 = new HashMap<>();
        header1.put("Accept", "application/json");
        header1.put("token", "987450a7263c4f9");

        SerenityRest.given().log().all()
                .headers(header1)
                .pathParam("BookingId", bookingID)
                .when()
                .delete(EndPoints.DELETE_BOOKING_BY_ID)
                .then().statusCode(204)
                .log().status();

        SerenityRest.given().log().all()
                .pathParam("BookingId", bookingID)
                .when()
                .get(EndPoints.GET_SINGLE_BOOKING_BY_ID)
                .then()
                .statusCode(404).log().status();
    }

}


