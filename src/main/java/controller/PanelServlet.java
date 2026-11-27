package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Evaluacion;
import model.Usuario;
import util.Conexion;

@WebServlet(name="PanelServlet", urlPatterns={"/InicioServlet"})
public class PanelServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession(false);
        Usuario user=session==null ? null : (Usuario)session.getAttribute("usuarioLogueado");
        if(user==null){response.sendRedirect("login.jsp?error=2");return;}
        boolean admin="ADMIN".equals(user.getRol());
        int[] counts=new int[4];
        List<Evaluacion> recent=new ArrayList<>();
        String summary="SELECT (SELECT COUNT(*) FROM empresas WHERE estado=1),"
            +"(SELECT COUNT(*) FROM evaluaciones WHERE (? OR id_usuario=?)),"
            +"(SELECT COUNT(*) FROM evaluaciones WHERE estado='PENDIENTE' AND (? OR id_usuario=?)),"
            +"(SELECT COUNT(*) FROM resultados r JOIN evaluaciones e ON e.id_evaluacion=r.id_evaluacion WHERE (? OR e.id_usuario=?))";
        try(Connection con=Conexion.getInstancia().getConexion()){
            try(PreparedStatement ps=con.prepareStatement(summary)){
                for(int i=1;i<=6;i+=2){ps.setBoolean(i,admin);ps.setInt(i+1,user.getIdUsuario());}
                try(ResultSet rs=ps.executeQuery()){if(rs.next())for(int i=0;i<4;i++)counts[i]=rs.getInt(i+1);}
            }
            try(PreparedStatement ps=con.prepareStatement(
                "SELECT e.id_evaluacion,e.fecha_evaluacion,e.estado,em.razon_social FROM evaluaciones e "
                +"JOIN empresas em ON em.id_empresa=e.id_empresa WHERE (? OR e.id_usuario=?) "
                +"ORDER BY e.fecha_evaluacion DESC,e.id_evaluacion DESC LIMIT 5")){
                ps.setBoolean(1,admin);ps.setInt(2,user.getIdUsuario());
                try(ResultSet rs=ps.executeQuery()){
                    while(rs.next()){
                        Evaluacion e=new Evaluacion();e.setIdEvaluacion(rs.getInt(1));
                        e.setFechaEvaluacion(rs.getTimestamp(2));e.setEstado(rs.getString(3));
                        e.setNombreEmpresa(rs.getString(4));recent.add(e);
                    }
                }
            }
        }catch(SQLException e){throw new ServletException("No fue posible cargar el resumen empresarial",e);}
        request.setAttribute("panelCounts",counts);
        request.setAttribute("panelRecent",recent);
        request.getRequestDispatcher("principal.jsp").forward(request,response);
    }
}
