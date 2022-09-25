package dk.matzon.proto.crud.interfaces.rest.exceptions;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

public class ApiError {
    private final Response.Status status;
    private final String message;
    private final List<String> errors;

    public ApiError(Response.Status status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(Response.Status status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Collections.singletonList(error);
    }

    public Response.Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
