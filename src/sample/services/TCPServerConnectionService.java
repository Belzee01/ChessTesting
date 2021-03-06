package sample.services;

import lombok.Getter;

/**
 * TCP server controller, provide non-blocking (from JavaFX application point of view) communication
 */
public class TCPServerConnectionService extends TCPConnectionService {
    @Getter
    private int port;

    /**
     * @param port -server listening port
     */
    public TCPServerConnectionService(int port){
        super();
        this.port=port;
    }

    @Override
    public boolean isServer(){
        return true;
    }

    /**
     * Unused field
     * @return
     */
    @Override
    public String getIp(){
        return null;
    }
}
