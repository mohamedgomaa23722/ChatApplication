package gov.iti.presistance.connection;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import gov.iti.dao.ServerDao;

public class ClientServerConnection {

    private static Registry reg;
    private static ServerDao chatRef;
    private static ClientServerConnection connectionInstance = new ClientServerConnection();

    public static ClientServerConnection getConnectionInstance() {
        return connectionInstance;
    }

    private ClientServerConnection() {
        try {
            reg = LocateRegistry.getRegistry("localhost", 8889);
            chatRef = (ServerDao) reg.lookup("ChatService");

            System.out.println("connected");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ServerDao getServerDao() {
        return chatRef;
    }

    public Registry getRegistry() {
        return reg;
    }

    public static boolean reConnect() {
        try {
            reg = LocateRegistry.getRegistry("localhost", 8889);
            chatRef = (ServerDao) reg.lookup("ChatService");
            return true;
        } catch (Exception ex) {
            // ex.printStackTrace();
            return false;
        }
    }
}
