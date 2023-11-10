package Common;

import java.net.InetAddress;

public class ClientHandlerBase implements Runnable {
    public InetAddress ipClient;
    public int portClient;

    public ClientHandlerBase(InetAddress ipClient, int portClient){
        this.ipClient = ipClient;
        this.portClient = portClient;
    }
    @Override
    public void run() {

    }
}
