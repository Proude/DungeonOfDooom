package communicators;

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
 * Handles communication to/from the server
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

    private static String registerUserAndGetSessionId(String username, String password) {
        MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
        formData.add("username", username);
        formData.add("password", password);
        Response registerResponse = target.path("player/register").request().post(Entity.form(formData));

        //get the sessionId so we can send authorised session cookies with requests
        return registerResponse.getCookies().get("JSESSIONID").getValue();
    }

    protected Invocation.Builder request(String path) {
        Invocation.Builder request = target.path("match/join").request();
        request.cookie("JSESSIONID",sessionId);

        return request;
    }

    protected Response post(String path, MultivaluedMap<String, String> params) {
        return post(request(path), params);
    }

    protected Response post(Invocation.Builder request, MultivaluedMap<String, String> params) {
        return request.post(Entity.form(params));
    }

    protected Response get(String path) {
        return get(request(path));
    }

    protected Response get(Invocation.Builder request) {
        return request.get();
    }
}
