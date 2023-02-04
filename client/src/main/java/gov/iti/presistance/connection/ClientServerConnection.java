package gov.iti.presistance.connection;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import gov.iti.dao.ServerDao;

public class ClientServerConnection {

    Registry reg;
    ServerDao chatRef;
    private static ClientServerConnection connectionInstance = new ClientServerConnection();

    public static ClientServerConnection getConnectionInstance() {
        return connectionInstance;
    }

    private ClientServerConnection() {
        try{
            reg = LocateRegistry.getRegistry();
            chatRef =(ServerDao) reg.lookup("ChatService");
           
            } catch(Exception ex) { 
                ex.printStackTrace();
            }
    }

    public ServerDao getRegistry() {
        return chatRef;
    }
    
}
