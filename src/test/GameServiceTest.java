package test;

import org.junit.Before;
import org.junit.Test;
import sample.services.GameService;

import static org.junit.Assert.*;

public class GameServiceTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSingletonInstances(){
        assertNotEquals(GameService.getInstance(), null);
    }
}