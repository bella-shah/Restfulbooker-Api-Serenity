package com.herokuapp.restful_booker.bookinginfo;

import com.herokuapp.restful_booker.constants.EndPoints;
import com.herokuapp.restful_booker.model.BookingDatesPojo;
import com.herokuapp.restful_booker.model.BookingPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.ArrayList;
import java.util.HashMap;

public class BookingSteps {

    @Step("Creating Booking with firstname : {0}, lastname: {1}, totalprice: {2}, depositpaid: {3},bookingdates: {4},additionalneeds: {5}")
    public ValidatableResponse createBooking(String firstname, String lastname, int totalprice, boolean depositpaid, BookingDatesPojo bookingdates, String additionalneeds) {
        BookingPojo bookingPojo = BookingPojo.getBookingPojo(firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds);

        HashMap<String, Object> header1 = new HashMap<>();
        header1.put("Content-Type", "application/json");
        header1.put("Accept", "application/json");
        header1.put("token", "987450a7263c4f9");

        return SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .headers(header1)
                .body(bookingPojo)
                .when()
                .post(EndPoints.CREATE_ALL_BOOKINGS)
                .then();
    }

    @Step("Getting the booking information with booking firstname: {0}")
    public ArrayList<HashMap<String, Object>> getBookingInfoByFirstname(String firstname) {
        String p1 = "findAll{it.firstname='";
        String p2 = "'}.get(0)";
        return SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_BOOKINGS_BY_NAME)
                .then()
                .statusCode(200)
                .extract()
                .path(p1 + firstname+ p2);
    }

    @Step("Updating booking information with bookingID: {0}, firstname: {1}, lastname: {2}, totalprice: {3}, depositpaid: {4} and bookingdates: {5},additionalneeds: {6}")
    public ValidatableResponse updateBooking(int bookingID, String firstname, String lastname, int totalprice, boolean depositpaid, BookingDatesPojo bookingdates, String additionalneeds) {
        BookingPojo bookingPojo = BookingPojo.getBookingPojo(firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds);

        HashMap<String, Object> header1 = new HashMap<>();
        header1.put("Accept", "application/json");
        header1.put("token", "987450a7263c4f9");


        return SerenityRest.given().log().all()

                .contentType(ContentType.JSON)
                .headers(header1)
                .pathParam("bookingID", bookingID)
                .body(bookingPojo)
                .when()
                .put(EndPoints.UPDATE_BOOKING_BY_ID)
                .then();
    }

    @Step("Deleting booking information with bookingID: {0}")
    public ValidatableResponse deleteBooking(int bookingID) {


        HashMap<String, Object> header1 = new HashMap<>();
        header1.put("Accept", "application/json");
        header1.put("token", "987450a7263c4f9");

        return SerenityRest.given().log().all()
                .headers(header1)
                .pathParam("bookingID", bookingID)
                .when()
                .delete(EndPoints.DELETE_BOOKING_BY_ID)
                .then();
    }

    @Step("Getting booking information with bookingId: {0}")
    public ValidatableResponse getBookingById(int bookingID) {
        return SerenityRest.given().log().all()
                .pathParam("bookingID", bookingID)
                .when()
                .get(EndPoints.GET_SINGLE_BOOKING_BY_ID)
                .then();
    }


}


