package dk.matzon.proto.crud.model.exceptions;

import dk.matzon.proto.crud.interfaces.rest.exceptions.ApiError;

import javax.ws.rs.core.Response;

public class CarAlreadyExistsException extends RuntimeException {
    private String vin;

    public CarAlreadyExistsException(String vin) {
        this.vin = vin;
    }

    public String getVin() {
        return vin;
    }

    public ApiError asApiError() {
        return new ApiError(Response.Status.BAD_REQUEST, "Car already exists", "Non unique VIN: " + vin);
    }
}
