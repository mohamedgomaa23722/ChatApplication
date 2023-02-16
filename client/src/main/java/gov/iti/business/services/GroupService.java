package gov.iti.business.services;

import java.nio.channels.SelectableChannel;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gov.iti.dao.ServerDao;
import gov.iti.model.Group;
import gov.iti.model.User;
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
        return ClientServerConnection.getConnectionInstance().getServerDao().getGroupLastId();
    } catch (RemoteException | SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return -1;
    }
   }
    public Group creatGroupService(Group group) {
        try {
            ClientServerConnection.getConnectionInstance().getServerDao().creatGroup(group);
            group.setGroupId(ClientServerConnection.getConnectionInstance().getServerDao().getGroupLastId());
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
            return ClientServerConnection.getConnectionInstance().getServerDao().addGroupMember(groupId, MemberPhoneNumber);
        } catch (RemoteException e) {
            
            e.printStackTrace();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return false;
    }
    public List<Group> getContactGroups () {
        try {
            return ClientServerConnection.getConnectionInstance().getServerDao().selectGroups(CurrentUser.getCurrentUser().getPhoneNumber().get());
        } catch (RemoteException e) {
            
            e.printStackTrace();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return new ArrayList<Group>();
    }
    public List<String> selectGroupMembers(int groupId){
        try {
            return ClientServerConnection.getConnectionInstance().getServerDao().selectGroupMembers(groupId);
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

    public boolean leaveGroup(int groupId, String phoneNumber) {
        try {
            return ClientServerConnection.getConnectionInstance().getServerDao().leaveGroup(groupId, phoneNumber);
        } catch (RemoteException | SQLException e) {
            
            e.printStackTrace();
            return false;
        }
    }
}