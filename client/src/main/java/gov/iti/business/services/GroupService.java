package gov.iti.business.services;

import java.nio.channels.SelectableChannel;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gov.iti.dao.ServerDao;
import gov.iti.model.Group;
import gov.iti.presentation.dtos.CurrentUser;
import gov.iti.presistance.connection.ClientServerConnection;

public class GroupService {

    ServerDao chatReg;
    static GroupService groupService = new GroupService();

    public static GroupService getGroupService() {
        return groupService;
    }

    private GroupService() {
        chatReg=ClientServerConnection.getConnectionInstance().getServerDao();
    }
   public int getGroupLastId(){
    try {
        return chatReg.getGroupLastId();
    } catch (RemoteException | SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return -1;
    }
   }
    public Group creatGroupService(Group group) {
        try {
            chatReg.creatGroup(group);
            group.setGroupId(chatReg.getGroupLastId());
            return group;
        } catch (RemoteException e) {
            
            e.printStackTrace();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return null;
    }
    public boolean addGroupMemberService(int groupId,String MemberPhoneNumber) {
        try {
            return chatReg.addGroupMember(groupId, MemberPhoneNumber);
        } catch (RemoteException e) {
            
            e.printStackTrace();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return false;
    }
    public List<Group> getContactGroups () {
        try {
            return chatReg.selectGroups(CurrentUser.getCurrentUser().getPhoneNumber().get());
        } catch (RemoteException e) {
            
            e.printStackTrace();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return new ArrayList<Group>();
    }
    public List<String> selectGroupMembers(int groupId){
        try {
            return chatReg.selectGroupMembers(groupId);
        } catch (RemoteException e) {
            
            e.printStackTrace();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return new ArrayList<String>();

    }
    
    public void addGroup(Group group){
        try {
            ClientServerConnection.getConnectionInstance().getServerDao()
            .notifyCreatingGroup(group);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
   
    }
}