package sample.services;

import lombok.Setter;
import sample.models.Board;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * TCP connection controller, provide non-blocking (from JavaFX application point of view) communication
 */
public abstract class TCPConnectionService {
    /**
     * called every time when new data received
     */
    @Setter
    public Consumer<Serializable> onReceiveNewData;
    /**
     * called if connection is Established
     */
    @Setter
    public Consumer<String> onConnectionEstablished;
    @Setter
    /**
     * called when connection is broken (consumer contain error description)
     */
    public Consumer<String> onConnectionBroken;

    /**
     * called if other exceptions occur (mainly for debug purpose)
     */
    @Setter
    public Consumer<String> onOtherExceptions;

    public abstract  boolean isServer();
    public abstract int getPort();
    public abstract String getIp();
    private TCPConnectionThread thread;


    /**
     * Sent object via connection
     * @param objectToSent - some serializable object
     */
    public void sendObject(Serializable objectToSent){
        try {
            synchronized (thread) {
                thread.out.writeObject(objectToSent);
                thread.out.flush();
            }
            System.out.println("Sending : \n"+objectToSent);
        }
        catch (Exception e) {
            if (onOtherExceptions != null) {
                onOtherExceptions.accept("Exception in sendObject: " + e.toString());
            }
        }
    }

    /**
     * Close connection - makes socket free
     */
    public void closeConnection(){
        try{
            thread.socket.close();
        }
        catch (Exception e){
            if (onOtherExceptions != null) {
                onOtherExceptions.accept("Exception in closeConnection: " + e.toString());
            }
        }
    }

    /**
     * Start new connection if other run call closeConnection first
     */
    public void startConnection(){
        thread=new TCPConnectionThread();
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Class that extends Thread and execute sending/receiving operation independent from main thread
     */
    private class TCPConnectionThread extends Thread{
        private Socket socket;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        @Override
        public void run(){
            try{
                System.out.println("begin");
                System.out.println(getIp()+":"+getPort());
                if(isServer()==true){
                    ServerSocket serverSocket=new ServerSocket(getPort());
                    socket=serverSocket.accept();
                }
                else{
                    socket=new Socket(getIp(),getPort());
                }

                out=new ObjectOutputStream(socket.getOutputStream());
                in=new ObjectInputStream(socket.getInputStream());


                socket.setKeepAlive(true);
                socket.setTcpNoDelay(true);

                System.out.println("ready");

                if(onConnectionEstablished!=null) {
                    onConnectionEstablished.accept("Connection establishment done as " + (isServer() == true ? "server" : "client"));
                }


                while (true){

                    Serializable data=(Serializable) in.readObject();

                    if(onReceiveNewData!=null) {
                        onReceiveNewData.accept(data);
                    }

                }
            }
            catch(Exception e){
                if(onConnectionBroken!=null) {
                    onConnectionBroken.accept(e.toString());
                }
                socket=null;
                out=null;
                in=null;
            }
        }
    }
}
