package sample.services;

import lombok.Getter;

/**
 * TCP client  controller, provide non-blocking (from JavaFX application point of view) communication
 */
public class TCPClientConnectionService extends TCPConnectionService {
    @Getter
    private String ip;
    @Getter
    private int port;

    /**
     * @param ip server IP number (IPv4)
     * @param port server port number
     */
    public TCPClientConnectionService(String ip, int port){
        super();
        this.ip=ip;
        this.port=port;
    }

    @Override
    public boolean isServer(){
        return false;
    }

    /*
    @Override
    public int getPort(){
        return port;
    }

    @Override
    public String getIp(){
        return ip;
    }
    */
}
