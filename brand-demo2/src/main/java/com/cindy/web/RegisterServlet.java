package com.cindy.web;

import com.cindy.pojo.User;
import com.cindy.service.UserService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    private UserService service=new UserService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");

        String checkCode = request.getParameter("checkCode");

        HttpSession session = request.getSession();
        String code = (String)session.getAttribute("checkCode");

        if (!code.equalsIgnoreCase(checkCode)){
            request.setAttribute("register_msg","Incorrect Code");
            request.getRequestDispatcher("/register.jsp").forward(request,response);
            return;
        }

        User newUser=new User();
        newUser.setPassword(password);
        newUser.setUsername(username);

        boolean flag = service.register(newUser);

        if(flag){
            request.setAttribute("register_msg","Registered Successfully");
            request.getRequestDispatcher("/login.jsp").forward(request,response);
        }else {
            request.setAttribute("register_msg","Username already exists");
            request.getRequestDispatcher("/register.jsp").forward(request,response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
}
