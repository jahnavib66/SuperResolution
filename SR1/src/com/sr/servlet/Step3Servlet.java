package com.sr.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Step3Servlet extends HttpServlet
{

   private static final long serialVersionUID = 1L;

   @Override
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   {
      doPost(req, resp);
   }

   @Override
   protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
   {
      try
      {
         //String emailCode = req.getParameter("emailCode");
         String mobileCode = req.getParameter("mobileCode");

         //String actualEmailCode = (String) req.getSession().getAttribute("emailCode");
         String actualMobileCode = (String) req.getSession().getAttribute("mobileCode");
         
         if (mobileCode.equals(actualMobileCode))
         {
            req.getSession().setAttribute("step3done", "yes");          
            resp.sendRedirect("step4.jsp");
         }
         else
         {
            resp.sendRedirect("step3.jsp?msg=Verification Failed");
         }
      }
      catch (Exception e)
      {
         e.printStackTrace();
         resp.sendRedirect("step3.jsp?msg=Error: " + e.getMessage());
      }
   }

}
