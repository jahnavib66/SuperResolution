package com.sr.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.sr.executor.ExecutorThread;
import com.sr.model.Worker;
import com.sr.util.Constants;

public class Step4Servlet extends HttpServlet
{

   private static final long serialVersionUID = 1L;
   private String filePath = Constants.TEMP;

   private int maxFileSize = 50000 * 1024;
   private int maxMemSize = 40000 * 1024;
   private File file;

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
         DiskFileItemFactory factory = new DiskFileItemFactory();
         factory.setSizeThreshold(maxMemSize);
         factory.setRepository(new File(Constants.TEMP));
         ServletFileUpload upload = new ServletFileUpload(factory);
         upload.setSizeMax(maxFileSize);
         List<FileItem> fileItems = upload.parseRequest(new ServletRequestContext(req));
         Iterator<FileItem> i = fileItems.iterator();
         while (i.hasNext())
         {
            FileItem fi = (FileItem) i.next();
            if (!fi.isFormField())
            {
               String fileName = fi.getName();
               String id = String.valueOf(System.currentTimeMillis());
               if (fileName.lastIndexOf("\\") >= 0)
               {
                  file = new File(filePath + id + fileName.substring(fileName.lastIndexOf("\\")));
               }
               else
               {
                  file = new File(filePath + id + fileName.substring(fileName.lastIndexOf("\\") + 1));
               }
               fi.write(file);
            }

         }

         Worker worker = new Worker();

         Properties props = new Properties();
         props.load(new InputStreamReader(new FileInputStream(new File("/home/ubuntu/app.properties"))));

         worker.setAppname(props.getProperty("appname"));
         worker.setContextroot(props.getProperty("context"));
         worker.setHost(props.getProperty("host"));
         worker.setPort(props.getProperty("port"));

         new ExecutorThread(req.getSession(), file.getAbsolutePath(), worker);

         req.getSession().setAttribute("step4done", "yes");
         resp.sendRedirect("result.jsp");

      }
      catch (Exception e)
      {
         e.printStackTrace();
         resp.sendRedirect("step4.jsp?msg=Error: " + e.getMessage());
      }
   }

}
