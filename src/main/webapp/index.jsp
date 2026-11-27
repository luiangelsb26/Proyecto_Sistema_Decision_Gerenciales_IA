<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%
    jakarta.servlet.http.HttpSession sesionActual = request.getSession(false);
    String destino = sesionActual != null
            && sesionActual.getAttribute("usuarioLogueado") != null
            ? "InicioServlet" : "login.jsp";
    response.sendRedirect(request.getContextPath() + "/" + destino);

%>
