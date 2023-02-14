package gov.iti.presistance;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.ReentrantLock;

import gov.iti.business.services.ChatService;
import gov.iti.business.services.InvitationService;
import gov.iti.dao.ClientDao;
import gov.iti.model.Invitation;
import gov.iti.model.Message;
import gov.iti.model.User;

public class ClientImpl extends UnicastRemoteObject implements ClientDao{

    String fileName;

    private final ReentrantLock lock = new ReentrantLock();

    public ClientImpl() throws RemoteException {
    }

    @Override
    public void recievedContactMessage(Message message) throws RemoteException {
        
    }

    @Override
    public void recievedGroupMessage(Message message) throws RemoteException {
        
    }

    @Override
    public void recievedContactInvitation(Invitation invitation) throws RemoteException {
        InvitationService.getInstance().receiveInvitation(invitation);
    }

    @Override
    public void UpdateOnContact(User user) throws RemoteException {
        // TODO Auto-generated method stub
        ChatService.getInstance().UpdateContanctList(user);
    }

    @Override
    public synchronized boolean downLoadFile(byte[] buffer, int count, String fileName) {
        //lock.lock();
        ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
            try (FileChannel channel = FileChannel.open(Paths.get(fileName), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                channel.write(byteBuffer);
            } catch (IOException e ) {
                e.printStackTrace();
                return false;
            } 
            System.out.println("download file name: "+fileName+" count"+count);
            //for (int i = 0; i < count; i++)
            //System.out.print((char) buffer[i]);
        //lock.unlock();
        return true;
    }
    
}
