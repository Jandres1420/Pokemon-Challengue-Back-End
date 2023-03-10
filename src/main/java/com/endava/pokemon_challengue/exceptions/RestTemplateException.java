package com.endava.pokemon_challengue.exceptions;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import java.io.IOException;

@Builder
public class RestTemplateException implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR
                || httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            throw ExceptionGenerator.getException(ExceptionType.NOT_FOUND, "Server Error");
        } else if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw ExceptionGenerator.getException(ExceptionType.NOT_FOUND, "The Pokemon you asked for does not exist");
            }
        }
    }
}
