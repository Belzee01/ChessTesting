package sample.services;

import lombok.Getter;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Serwis zapewniający logikę controlerom.
 * Metody serwisu są wywoływane w controlerach w celu uzyskania odpowiedzi wynikajacej z implementacji logiki
 */
public class GameCreatingService {
    /**
     * String reprezentujący aktualny adres ip hosta (inicjalizowany w konstruktorze)
     */
    @Getter
    private String currentHostIpAddress;

    public GameCreatingService() {
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();

            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    InetAddress addr = address.nextElement();
                    if (!addr.isLoopbackAddress() && addr.isSiteLocalAddress() && !(addr.getHostAddress().indexOf(":") > -1)) {
                            currentHostIpAddress = addr.getHostAddress();
                    }
                }
            }
            if (currentHostIpAddress == null) {
                currentHostIpAddress = "127.0.0.1";
            }

        } catch (SocketException e) {
            currentHostIpAddress = "127.0.0.1";
        }

    }
}
