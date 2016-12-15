package com.dod.bot.communicators;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ContextResolver;

import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A base class that handles generic communication to/from the server.
 */
public class CommunicatorBase {
    private static WebTarget target;
    private static String sessionId;

    private static String username;
    private static String password;

    private static final String apiAddress = "http://localhost:8080";

    protected static WebTarget getTarget() {
        if(target == null)
            init();
        return target;
    }

    /**
     * Initialises the static web service connection and picks a random user/pass combination.
     */
    private static void init() {
        Map<String, String> namespacePrefixMapper = new HashMap<String, String>();
        namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
        MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig()
                .setNamespacePrefixMapper(namespacePrefixMapper)
                .setNamespaceSeparator(':');

        final ContextResolver<MoxyJsonConfig> jsonConfigResolver = moxyJsonConfig.resolver();
        Client c = ClientBuilder.newBuilder()
                .register(MoxyJsonFeature.class)
                .register(jsonConfigResolver)
                .build();

        //Generate random user/pass
        username = UUID.randomUUID().toString();
        password = UUID.randomUUID().toString();

        target = c.target(apiAddress);
        sessionId = registerUserAndGetSessionId(username, password);
    }

    /**
     * Registers the specified user/pass combination with the web service.
     * @param username String the desired username
     * @param password String the desired password
     * @return String the session ID
     */
    private static String registerUserAndGetSessionId(String username, String password) {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", username);
        formData.add("password", password);
        Response registerResponse = getTarget().path("player/register").request().post(Entity.form(formData));

        //get the sessionId so we can send authorised session cookies with requests
        return registerResponse.getCookies().get("JSESSIONID").getValue();
    }

    /**
     * Generates a request to the specified path
     * @param path String the path to request
     * @return Invocation.Builder a Builder that generates an Invocation of the specified web resource.
     */
    protected Invocation.Builder request(String path) {
        Invocation.Builder request = getTarget().path(path).request();
        request.cookie("JSESSIONID",sessionId);

        return request;
    }

    /**
     * Posts a web request to the specified path with the specified parameters as form parameters.
     * @param path String the path to send the POST request to
     * @param params MultiValuedHashMap<String, String> the parameters to send with the POST request
     * @return Response the response from the service
     */
    protected Response post(String path, MultivaluedMap<String, String> params) {
        return post(request(path), params);
    }

    /**
     * Invokes the request as a POST request with the specified parameters as form parameters.
     * @param request Invocation.Builder a Builder that generates an Invocation of a particular web resource.
     * @param params MultiValuedHashMap<String, String> the parameters to send with the POST request
     * @return Response the response from the service
     */
    protected Response post(Invocation.Builder request, MultivaluedMap<String, String> params) {
        return request.post(Entity.form(params));
    }

    /**
     * Sends a GET request to a particular path on the web service
     * @param path String the path to send the GET request to
     * @return Response the response from the server
     */
    protected Response get(String path) {
        return get(request(path));
    }

    /**
     * Invokes the specified Request as a GET request
     * @param request Invocation.Builder a Builder that generates an Invocation of a particular web resource.
     * @return
     */
    protected Response get(Invocation.Builder request) {
        return request.get();
    }
}
