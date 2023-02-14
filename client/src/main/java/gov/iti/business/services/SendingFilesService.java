package gov.iti.business.services;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import gov.iti.dao.ServerDao;
import gov.iti.presistance.connection.ClientServerConnection;

public class SendingFilesService implements Serializable {

    static SendingFilesService sendingFilesService=new SendingFilesService();

    ServerDao chatReg;

    private final ReentrantLock lock = new ReentrantLock();

    //private final ReentrantLock lock2 = new ReentrantLock();

    private final ReentrantLock lock3 = new ReentrantLock();

    public static SendingFilesService getSendingFilesService() {
        return sendingFilesService;
    }

    private SendingFilesService() {
        chatReg=ClientServerConnection.getConnectionInstance().getServerDao();
    }

    public synchronized List<Boolean> sendFiles(List <File> files, String reciever) {

        //lock.lock();

        List<Boolean> result = new ArrayList<>();

        for(File file:files) {
            
            new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    result.add(sendFile(file,reciever));
                }
            }).start();
            
        }
        
        //lock.unlock();

        return result;
     }

     public boolean sendFile(File file, String reciever) {

        //final boolean res;

        String fileName=file.getName(); 

        System.out.println("sender file name "+fileName);

        //final byte[] fileNameBytes = fileName.getBytes();

        System.out.println("sender file path "+file.toPath());

                // Next, obtain a channel to that file within a try-with-resources block.
        lock3.lock();
        int count=0;
        try (SeekableByteChannel fChan = Files.newByteChannel(file.toPath())) {
            // Allocate a buffer.
            ByteBuffer mBuf = ByteBuffer.allocate(1024);

            final byte[] bytes = new byte[1024];

            mBuf = ByteBuffer.wrap(bytes);

            do { // Read a buffer.
                count = fChan.read(mBuf);
                // Stop when end of file is reached.
                if (count != -1) {
                // Rewind the buffer so that it can be read.
                    mBuf.rewind();
                    
                    // Read bytes from the buffer and show them on the screen as characters.
                    chatReg.sendFile(bytes,count,reciever,fileName);
                    System.out.println("reading " + fileName+" count "+count);
                    //if(res==false) break;
                    //for (int i = 0; i < count; i++)
                    //System.out.print((char) mBuf.get()); 
                }   
            } while (count != -1);

            System.out.println("done reading " + fileName);

            lock3.unlock();

        } catch (IOException e) {
            System.out.println("I/O Error " + e);
            return false;
        }
        return true;
    }
    
}
