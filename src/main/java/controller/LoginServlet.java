/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.UsuarioDAO;
import model.Usuario; 

import java.io.IOException; 
import jakarta.servlet.ServletException; 
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Humberto
 */

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO;
    @Override
    public void init() throws ServletException{
        usuarioDAO = new UsuarioDAO();
    }
    
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException{
        
        request.setCharacterEncoding("UTF-8");
        
        String usuario = request.getParameter("usuario");
        String password = request.getParameter("password");
        if (usuario == null || password == null
                || usuario.trim().isEmpty()
                || password.trim().isEmpty()){
            response.sendRedirect("login.jsp?error=1"); return;
        }
        
        Usuario user = usuarioDAO.autenticar(
                usuario.trim(),
                password
        );
        
        if (user != null){
            HttpSession session = request.getSession();
        request.changeSessionId();
            session.setAttribute("usuarioLogueado", user);
            response.sendRedirect("principal.jsp");
        } else {
            response.sendRedirect("login.jsp?error=1");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException{
        response.sendRedirect("login.jsp");
    }
}
