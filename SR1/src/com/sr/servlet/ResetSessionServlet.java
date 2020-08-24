package com.sr.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResetSessionServlet extends HttpServlet
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
         req.getSession().removeAttribute("step1done");
         req.getSession().removeAttribute("step2done");
         req.getSession().removeAttribute("step3done");
         req.getSession().removeAttribute("step4done");
         req.getSession().removeAttribute("fname");
         req.getSession().removeAttribute("lname");
         req.getSession().removeAttribute("email");
         req.getSession().removeAttribute("mobile");
         req.getSession().removeAttribute("emailCode");
         req.getSession().removeAttribute("mobileCode");
         req.getSession().invalidate();
         resp.sendRedirect("step1.jsp");
      }
      catch (Exception e)
      {
         e.printStackTrace();
         resp.sendRedirect("step1.jsp?msg=Error: " + e.getMessage());
      }
   }

}
