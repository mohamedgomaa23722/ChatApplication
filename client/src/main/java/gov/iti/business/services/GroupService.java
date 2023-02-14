
package gov.iti.business.services;

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
    public int creatGroupService(Group group) {
        try {
            chatReg.creatGroup(group);
            return chatReg.getGroupLastId();
        } catch (RemoteException e) {
            
            e.printStackTrace();
        } catch (SQLException e) {
            
            e.printStackTrace();
        }
        return -1;
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
    
}