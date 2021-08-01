/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.AccountModel;
import util.DBConnectionHandler;

/**
 *
 * @author SCUBE
 */
public class AccountDao {
    
    public AccountModel insertAmountData(AccountModel accountModel) {
        AccountModel outModel = new AccountModel();
        Connection oConn = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            oConn = DBConnectionHandler.getConVentionalConnection();

            String sql = "INSERT INTO ROOT.ACCOUNT (ID,AMOUNT,REMARKS,FLAG) VALUES (?,?,?,?)";

            PreparedStatement ps = oConn.prepareStatement(sql);

            ps.setString(1, accountModel.getId());
            ps.setString(2, accountModel.getAmount());
            ps.setString(3, accountModel.getRemarks());
            ps.setString(4, accountModel.getFlag());

            ps.executeUpdate();
            System.out.println("sql = " + sql);

            outModel.setId(accountModel.getId());
            outModel.setAmount(accountModel.getAmount());
            outModel.setRemarks(accountModel.getRemarks());
            outModel.setFlag(accountModel.getFlag());
            
            outModel.setOutCode("0");
            outModel.setOutMessage("Amount inserted in the account Successfully");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (oConn != null) {
                DBConnectionHandler.releaseConnection(oConn);
            }
        }
        return outModel;

    }
    
    public List<AccountModel> getAllPendingTransaction(AccountModel accountModel) {
        List<AccountModel> list = new ArrayList<AccountModel>();
        Connection oConn = null;

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            oConn = DBConnectionHandler.getConVentionalConnection();

            String sql = "SELECT * FROM ROOT.ACCOUNT WHERE FLAG = 'Pending' ";

            PreparedStatement ps = oConn.prepareStatement(sql);

           
            

            ResultSet rs = ps.executeQuery();
            System.out.println("sql = " + sql);

            while (rs.next()) {
                
                AccountModel outModel = new AccountModel();
                
                outModel.setId(rs.getString("id"));
                outModel.setAmount(rs.getString("amount"));
                outModel.setRemarks(rs.getString("remarks"));
                
                list.add(outModel);

                

            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (oConn != null) {
                DBConnectionHandler.releaseConnection(oConn);
            }
        }
        return list;

    }
    
    
    public AccountModel approveTransaction(AccountModel accountModel) {
        AccountModel outModel = new AccountModel();
        Connection oConn = null;

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            oConn = DBConnectionHandler.getConVentionalConnection();

            String sql = "UPDATE ROOT.ACCOUNT SET FLAG=?  WHERE ID=?";

            PreparedStatement ps = oConn.prepareStatement(sql);

            ps.setString(1, accountModel.getFlag());
            ps.setString(2, accountModel.getId());

            ps.executeUpdate();
            System.out.println("sql = " + sql);

            outModel.setFlag(accountModel.getFlag());
            outModel.setId(accountModel.getId());
            outModel.setOutCode("0");
            outModel.setOutMessage("Approval Successfull");

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (oConn != null) {
                DBConnectionHandler.releaseConnection(oConn);
            }
        }
        return outModel;

    }
    
}
