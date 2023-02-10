package gov.iti.presistance;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import gov.iti.dao.ClientDao;
import gov.iti.model.Message;
import gov.iti.model.User;

public class ClientImpl extends UnicastRemoteObject implements ClientDao{

    protected ClientImpl(int arg0) throws RemoteException {
        super(arg0);
    }

    @Override
    public void recievedContactMessage(Message message) throws RemoteException {
        
    }

    @Override
    public void recievedGroupMessage(Message message) throws RemoteException {
        
    }

    @Override
    public void recievedContactInvitation(User sender) throws RemoteException {
        
    }
    
}
