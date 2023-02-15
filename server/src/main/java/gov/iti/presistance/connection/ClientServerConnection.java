package gov.iti.presistance.connection;

import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import static gov.iti.presistance.dao.ServerImpl.clients;

import gov.iti.dao.ClientDao;
import gov.iti.dao.ServerDao;
import gov.iti.presistance.dao.ServerImpl;

public class ClientServerConnection {

    static int portNumber = 8889;

    private ServerDao chatRef;
    private static ServerImpl obj;
    private static Registry reg;
    private static final ClientServerConnection connectionInstance = new ClientServerConnection();

    public static ClientServerConnection getConnectionInstance() {
        return connectionInstance;
    }

    private ClientServerConnection() {
        try{
            obj = new ServerImpl();
            reg = LocateRegistry.createRegistry(portNumber);
            reg.rebind("ChatService", obj);
            } catch(Exception ex) { 
                ex.printStackTrace();
            }
    }

    public ServerDao getRegistry() {
        return chatRef;
    }

    public static void stopServer() {
        tellAllClients();

        try {
            reg.unbind("ChatService");
            System.out.println("server is down");
        } catch (NotBoundException | RemoteException e) {
            System.out.println("unable to unbind xxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            e.printStackTrace();
        } finally {
            try {
                UnicastRemoteObject.unexportObject(obj, true);
            } catch (NoSuchObjectException e) {
                System.out.println("can't unexport the Remot Object");
                e.printStackTrace();
            }
        }

    }

    private static void tellAllClients() {
        for (ClientDao client : clients.values())
            try {
                client.serverDown();
            } catch (RemoteException e) {
                System.out.println("can't tell clients");
                e.printStackTrace();
            }
    }   

    public static void startServer() {
        try {
            reg.rebind("ChatService", obj);
            UnicastRemoteObject.exportObject(obj, portNumber);
            System.out.println("server is running ");
        } catch (RemoteException e) {
            System.out.println("can't rebind xxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
            e.printStackTrace();
        }
    }
    
}
