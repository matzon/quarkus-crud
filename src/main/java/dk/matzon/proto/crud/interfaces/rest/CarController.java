package dk.matzon.proto.crud.interfaces.rest;

import dk.matzon.proto.crud.application.persistence.CarService;
import dk.matzon.proto.crud.model.Car;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Path("/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @POST
    public Response create(@Valid Car car) throws URISyntaxException {
        Car createdCar = carService.addCar(car);
        return Response.created(new URI("/cars/" + createdCar.getVin())).entity(createdCar).build();
    }

    @GET
    public List<Car> read() {
        return carService.getCars();
    }

    @GET
    @Path("/{vin}")
    public Response read(@PathParam("vin") String vin) {
        Car car = carService.getCar(vin);
        return Response.ok(car).header(HttpHeaders.LAST_MODIFIED, DateTimeFormatter.RFC_1123_DATE_TIME.format(car.getUpdateDateTime())).build();
    }

    @PUT
    @Path("/{vin}")
    public Response update(@PathParam("vin") String vin, @Valid Car car) throws URISyntaxException {
        boolean existingCar = !StringUtils.isEmpty(car.getVin());
        Car updatedCar = carService.updateCar(vin, car);

        if (existingCar) {
            return Response.ok().entity(updatedCar).build();
        } else {
            return Response.created(new URI("/cars/" + updatedCar.getVin())).entity(updatedCar).build();
        }
    }

    @DELETE
    @Path("/{vin}")
    public Response delete(@PathParam("vin") String vin) {
        carService.deleteCar(vin);
        return Response.noContent().build();
    }
}
