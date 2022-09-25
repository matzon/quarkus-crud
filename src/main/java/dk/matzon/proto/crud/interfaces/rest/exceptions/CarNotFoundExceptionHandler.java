package dk.matzon.proto.crud.interfaces.rest.exceptions;

import dk.matzon.proto.crud.model.exceptions.CarNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CarNotFoundExceptionHandler implements ExceptionMapper<CarNotFoundException> {
    @Override
    public Response toResponse(CarNotFoundException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(e.asApiError())
                .build();
    }
}
