package test;

import org.junit.Before;
import org.junit.Test;
import sample.services.NetValidator;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class NetValidatorTest {

    private NetValidator netValidator;

    @Before
    public void setUp() throws Exception {
        netValidator = new NetValidator();
    }

    @Test
    public void testCheckIPAddressWithNullValue() throws Exception {
        String ip = null;
        boolean returnBool = netValidator.checkIPAddress(ip);

        assertEquals(returnBool, false);
    }

    @Test
    public void testCheckIPAddressWithProperValue() throws Exception {
        String ip = "192.168.43.153";
        boolean returnBool = netValidator.checkIPAddress(ip);

        assertEquals(returnBool, true);
    }

    @Test
    public void testCheckIpAddressesWithRandomValue() {
        Random r = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            char c = (char) (r.nextInt((int) (Character.MAX_VALUE)));
            sb.append(c);
        }
        String ip = sb.toString();

        boolean returnBool = netValidator.checkIPAddress(ip);

        assertEquals(returnBool, false);
    }

    @Test
    public void testCheckIpAddressesWithMaxValue() {
        String ip = "255.255.255.255";

        boolean returnBool = netValidator.checkIPAddress(ip);

        assertEquals(returnBool, false);
    }

    @Test
    public void testCheckIpAddressesWithMinValue() {
        String ip = "0.0.0.0";

        boolean returnBool = netValidator.checkIPAddress(ip);

        assertEquals(returnBool, false);
    }

    @Test
    public void testCheckIpAddressesWithInproperValue1() {
        String ip = "0.0.0.1000000";

        boolean returnBool = netValidator.checkIPAddress(ip);

        assertEquals(returnBool, false);
    }

    @Test
    public void testCheckIpAddressesWithInproperValue2() {
        String ip = "0.0.0.abcd";

        boolean returnBool = netValidator.checkIPAddress(ip);

        assertEquals(returnBool, false);
    }

    @Test
    public void testCheckIpAddressesWithInproperValue3() {
        String ip = "0.0.0.0.0";

        boolean returnBool = netValidator.checkIPAddress(ip);

        assertEquals(returnBool, false);
    }

    @Test
    public void testCheckIpAddressesWithInproperValue4() {
        String ip = "0.0.0.0.";
        boolean returnBool = netValidator.checkIPAddress(ip);
        assertEquals(returnBool, false);
    }

    @Test
    public void testCheckPortWithNullValue() throws Exception {
        String port = null;
        boolean returnPort = netValidator.checkPort(port);
        assertEquals(returnPort, false);
    }

    @Test
    public void testCheckPortWithProperValue() throws Exception {
        String port = "50000";
        boolean returnPort = netValidator.checkPort(port);
        assertEquals(returnPort, true);
    }

    @Test
    public void testCheckPortWithRandomValue() throws Exception {
        String port = new String(String.valueOf(new Random(600).nextInt()));
        System.out.print(port);
        boolean returnPort = netValidator.checkPort(port);
        assertEquals(returnPort, false);
    }

    @Test
    public void testCheckPortWithMaxValue() throws Exception {
        String port = "65535";
        boolean returnPort = netValidator.checkPort(port);
        assertNotEquals(returnPort, true);
    }

    @Test
    public void testCheckPortWithMinValue() throws Exception {
        String port = "49152";
        boolean returnPort = netValidator.checkPort(port);
        assertNotEquals(returnPort, true);

    }
}