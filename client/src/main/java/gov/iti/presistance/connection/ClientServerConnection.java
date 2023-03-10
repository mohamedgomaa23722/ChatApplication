package gov.iti.presistance.connection;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import gov.iti.dao.ServerDao;

public class ClientServerConnection {

    public  static int portNumber = 8889;
    public  static String ipAddress;

    private static Registry reg;
    private static ServerDao chatRef;
    private final static ClientServerConnection connectionInstance = new ClientServerConnection();

    public static ClientServerConnection getConnectionInstance() {
        return connectionInstance;
    }

    private ClientServerConnection() {
        reConnect();
    }

    public ServerDao getServerDao() {
        return chatRef;
    }

    public Registry getRegistry() {
        return reg;
    }

    public static boolean reConnect() {
        try {
            reg = LocateRegistry.getRegistry(ipAddress, portNumber);
            chatRef = (ServerDao) reg.lookup("ChatService");
            return true;
        } catch (Exception ex) {
             ex.printStackTrace();
            return false;
        }
    }
    
}
