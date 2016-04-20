package sample.services;

/**
 * TCP server controller, provide non-blocking (from JavaFX application point of view) communication
 */
public class TCPServerConnectionService extends TCPConnectionService {
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

    @Override
    public int getPort(){
        return port;
    }

    @Override
    public String getIp(){
        return null;
    }
}
