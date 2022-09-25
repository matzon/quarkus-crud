package dk.matzon.proto.crud.interfaces.rest.exceptions;

import dk.matzon.proto.crud.model.exceptions.CarAlreadyExistsException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CarAlreadyExistsExceptionHandler implements ExceptionMapper<CarAlreadyExistsException> {
    @Override
    public Response toResponse(CarAlreadyExistsException e) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(e.asApiError())
                .build();
    }
}
