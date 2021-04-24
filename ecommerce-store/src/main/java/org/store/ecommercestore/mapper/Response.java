package org.store.ecommercestore.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private HttpStatus httpStatus;
    private String message;
    private boolean status;
    private String [] messages;
    private List<String> errors;


    public Response(HttpStatus httpStatus, String message, List<String> errors) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.errors = errors;
    }

    public Response(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public Response(String message, HttpStatus httpStatus, boolean status) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.status = status;
    }

    public Response(HttpStatus httpStatus, boolean status, List<String> messages) {
        this.httpStatus = httpStatus;
        this.status = status;
        this.messages = messages.toArray(new String[0]);

    }
}
