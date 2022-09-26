package dk.matzon.proto.crud.interfaces.rest;

import dk.matzon.proto.crud.application.persistence.CarRepository;
import dk.matzon.proto.crud.application.persistence.CarService;
import dk.matzon.proto.crud.model.Car;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
class CarControllerTest {

    @Inject
    CarRepository carRepository;

    @Inject
    CarService carService;

    @AfterEach
    void tearDown() {
        carRepository.deleteAll();
    }

    @Test
    void testCreate() {

        Car car = new Car("5FNRL382X7B001853", "Honda", "Odyssey", 1337);

        given()
                .when()
                    .body(car)
                    .contentType(ContentType.JSON)
                    .post("/cars")
                .then()
                    .statusCode(HttpStatus.SC_CREATED)
                    .body("vin", equalTo(car.getVin()));
    }

    @Test
    void testCreateConstraintValidation() {

        Car car = new Car("JH4CC2560RC008414", "Acura", "Vigor", 1337);
        carService.addCar(car);

        given()
                .when()
                    .body(car)
                    .contentType(ContentType.JSON)
                    .post("/cars")
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void testCreateValidationModelFail() {

        Car car = new Car("JH4DA9370MS016526", "", "Integra", 1337);

        given()
                .when()
                    .body(car)
                    .contentType(ContentType.JSON)
                    .post("/cars")
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    void testReadEmpty() {
        given()
                .when()
                    .get("/cars")
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body("", equalTo(Collections.emptyList()));
    }

    @Test
    void testReadSpecific() {

        Car car = new Car("2C4GM68475R667819", "Chrysler", "Pacifica", 1337);
        carService.addCar(car);

        given()
                .when()
                    .get("/cars/" + car.getVin())
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body("vin", equalTo(car.getVin()));
    }

    @Test
    void testReadSpecificMissing() {

        given()
                .when()
                    .get("/cars/5FNRL382X7B001853")
                .then()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    void testReadAll() {

        carService.addCar(new Car("JH4KA7650PC002520", "Acura", "Legend", 1337));
        carService.addCar(new Car("JNKCV51E03M018631", "Infiniti", "G35", 1337));

        given()
                .when()
                    .get("/cars")
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body("", hasSize(2));
    }

    @Test
    void testUpdate() {

        Car car = new Car("1G6CD1184H4323745", "Cadillac", "DeVille", 1337);
        carService.addCar(car);

        car.setMileage(2674);

        given()
                .when()
                    .body(car)
                    .contentType(ContentType.JSON)
                    .put("/cars/" + car.getVin())
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body("mileage", equalTo(car.getMileage()));
    }

    @Test
    void testDelete() {

        Car car = new Car("1G4AW69N2DH524774", "Buick", "Electra", 1337);
        carService.addCar(car);

        given()
                .when()
                    .delete("/cars/" + car.getVin())
                .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test
    void testHead() {
        Car car = new Car("5N3AA08D68N901917", "Infiniti", "QX56", 1337);
        carService.addCar(car);

        given()
                .when()
                    .get("/cars/" + car.getVin())
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .header(HttpHeaders.LAST_MODIFIED, notNullValue());
    }
}