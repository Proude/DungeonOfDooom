package dod.test.unit.service;

import com.dod.db.repositories.IPlayerRepository;
import com.dod.models.Player;
import com.dod.service.model.LoginModel;
import com.dod.service.service.AuthenticationService;
import com.dod.service.service.IAuthenticationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Tests for the AuthenticationService
 */
public class AuthenticationServiceTests {

    private IAuthenticationService service;
    private IPlayerRepository repository;

    private final String testPlayername = "test";
    private final String testPassword = "testPassword";
    private final String incorrectTestPassword = "incorrectTestPassword";

    //These two are calculated by the hashing algorithm from testPassword so should always work
    private final byte[] testSalt = new byte[] {-77,14,44,-103,-37,0,60,-41,54,60,-24,-69,-10,-14,101,-17,101,
            95,16,50,60,81,34,-90,-85,123,88,88,-18,71,80,93};
    private final String testHashedPassword =
            "sw4smdsAPNc2POi79vJl72VfEDI8USKmq3tYWO5HUF0=vNgKzsYRou5lhm4l8i7pFYsYeqeicv/5O5KeplB2rLY=";

    @Before
    public void Setup() throws Exception {
        repository = mock(IPlayerRepository.class);
        service = new AuthenticationService(repository);
    }

    @Test
    public void whenUsernameDoesNotExistRegisterShouldCreatePlayerAndReturnTrue() throws Exception {
        when(repository.get(any(Player.class))).thenReturn(null);

        boolean result = service.Register(new LoginModel(testPlayername, testPassword));

        verify(repository).insert(any(Player.class));
        Assert.assertEquals(true, result);
    }

    @Test
    public void whenUsernameDoesExistRegisterShouldReturnFalse() throws Exception {
        when(repository.get(any(Player.class))).thenReturn(new Player(testPlayername, testPassword, new byte[0]));

        boolean result = service.Register(new LoginModel(testPlayername, testPassword));

        Assert.assertEquals(false, result);
    }

    @Test
    public void whenDetailsAreValidLoginShouldReturnTrue() throws Exception {
        when(repository.get(any(Player.class))).thenReturn(new Player(testPlayername, testHashedPassword, testSalt));

        boolean result = service.Login(new LoginModel(testPlayername, testPassword));

        Assert.assertEquals(true, result);
    }

    @Test
    public void whenPlayerDoesNotExistLoginShouldReturnFalse() throws Exception {
        when(repository.get(any(Player.class))).thenReturn(null);

        boolean result = service.Login(new LoginModel(testPlayername, testPassword));

        Assert.assertEquals(false, result);
    }

    @Test
    public void whenPasswordIsWrongLoginShouldReturnFalse() throws Exception {
        when(repository.get(any(Player.class))).thenReturn(new Player(testPlayername, testHashedPassword, testSalt));

        boolean result = service.Login(new LoginModel(testPlayername, incorrectTestPassword));

        Assert.assertEquals(false, result);
    }
}
