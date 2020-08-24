package com.sr.servlet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sr.util.Constants;

public class DownloadServlet extends HttpServlet
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
      resp.setContentType("text/html");
      PrintWriter out = resp.getWriter();
      String filename = req.getParameter("filename");
      String filepath = Constants.RESULT_DIR;
      resp.setContentType("APPLICATION/OCTET-STREAM");
      resp.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

      FileInputStream fileInputStream = new FileInputStream(filepath + filename);

      int i;
      while ((i = fileInputStream.read()) != -1)
      {
         out.write(i);
      }
      fileInputStream.close();
      out.close();
   }

}
