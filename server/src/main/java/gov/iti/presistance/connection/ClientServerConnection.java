package gov.iti.presistance.connection;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import gov.iti.dao.ServerDao;
import gov.iti.presistance.dao.ServerImpl;

public class ClientServerConnection {

    Registry reg;
    ServerDao chatRef;
    private static final ClientServerConnection connectionInstance = new ClientServerConnection();

    public static ClientServerConnection getConnectionInstance() {
        return connectionInstance;
    }

    private ClientServerConnection() {
        try{
            ServerImpl obj = new ServerImpl();
            Registry reg = LocateRegistry.createRegistry(8889);
            reg.rebind("ChatService", obj);
            } catch(Exception ex) { 
                ex.printStackTrace();
            }
    }

    public ServerDao getRegistry() {
        return chatRef;
    }
    
}
