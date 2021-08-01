/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import dao.AccountDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.AccountModel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author SCUBE
 */
@WebServlet(name = "AgentApi", urlPatterns = {"/AgentApi"})
public class AgentApi extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AgentApi</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AgentApi at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String requestCode = request.getParameter("requestCode");
        System.out.println("requestCode = " + requestCode);

        if ("1".equals(requestCode)) {

            AccountModel model = new AccountModel();
            
            try {
                List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
                AccountDao dao = new AccountDao();
                JSONArray jsonArray = new JSONArray();
                
                for(AccountModel userModel : dao.getAllPendingTransaction(model) ){
                    
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", userModel.getId());
                    jsonObject.put("amount", userModel.getAmount());
                    jsonObject.put("remarks", userModel.getRemarks());
                    
                    jsonArray.add(jsonObject);
                    
                }

                System.out.println("All Pending transaction List " + jsonArray);
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(jsonArray.toString());
                response.getWriter().flush();
            } catch (Exception ex) {
                Logger.getLogger(AgentApi.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        else if ("2".equals(requestCode)) {

            String flag = request.getParameter("flag");
            System.out.println("flag = " + flag);
            String id = request.getParameter("id");
            System.out.println("id = " + id);

            AccountModel model = new AccountModel();
            

            model.setFlag(flag);
            model.setId(id);

            try {
                JSONObject json = new JSONObject();
                AccountDao dao = new AccountDao();
                AccountModel Outmodel = dao.approveTransaction(model);

                json.put("flag",Outmodel.getFlag());
                json.put("id",Outmodel.getId());
                json.put("outCode", Outmodel.getOutCode());
                json.put("outMessage", Outmodel.getOutMessage());

                System.out.println("Transaction Approve Json " + json);
                response.addHeader("Access-Control-Allow-Origin", "*");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().print(json.toString());
                response.getWriter().flush();
            } catch (Exception ex) {
                Logger.getLogger(AgentApi.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
