package com.sr.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sr.mail.MailThread;
import com.sr.mobile.SendMessage;
import com.sr.util.VerificationCode;

public class Step2Servlet extends HttpServlet
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
         String email = req.getParameter("email");
         String mobile = req.getParameter("mobile");
         req.getSession().setAttribute("email", email);
         req.getSession().setAttribute("mobile", mobile);

         String fname = (String) req.getSession().getAttribute("fname");
         String lname = (String) req.getSession().getAttribute("lname");

         //String emailCode = VerificationCode.generateVerificationCodeForEmail();
         String mobileCode = VerificationCode.generateVerificationCodeForMobile();

         //req.getSession().setAttribute("emailCode", emailCode);
         req.getSession().setAttribute("mobileCode", mobileCode);
         //System.out.println("Email Code: "+emailCode);
         System.out.println("Mobile Code: "+mobileCode);
//         String sub = "Verification code from Super Resolution Application";
//         String body = "Dear " + fname + " " + lname;
//         body += "<br/>Your Email Verification code from Super Resolution Application is below";
//         body += "<br/><br/><br/><span style='padding:10px; font-size: 28px; font-weight: bold; letter-spacing: 6px; background-color: lightgray; color: black;'>" + emailCode + "</span>";
//         List<String> to = new ArrayList<String>();
//         to.add(email);
//         new MailThread(body, sub, to);

         String mobileMsg = "Super Resolution Verification Code is: " + mobileCode;
         SendMessage.sendSms(mobile, mobileMsg);
         req.getSession().setAttribute("step2done", "yes");
         resp.sendRedirect("step3.jsp");
      }
      catch (Exception e)
      {
         e.printStackTrace();
         resp.sendRedirect("step2.jsp?msg=Error: " + e.getMessage());
      }
   }

}
