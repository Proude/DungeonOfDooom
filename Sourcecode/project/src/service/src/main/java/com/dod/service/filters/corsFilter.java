package com.dod.service.filters;

/**
 * Adds CORS filter to header, enabling cross-origin AJAX requests
 * Based on: https://stackoverflow.com/questions/28065963/how-to-handle-cors-using-jax-rs-with-jersey
 */
import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class corsFilter implements ContainerResponseFilter {

    /**
     * Adds CORS headers to the Response before sending it
     * @param request ContainerRequestContext
     * @param response ContainerResponseContext
     */
    @Override
    public void filter(ContainerRequestContext request,
                       ContainerResponseContext response) {
        response.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:63342");
        response.getHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization");
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
        response.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");
    }
}