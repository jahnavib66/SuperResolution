package com.sr.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.sr.service.ExecutorService;
import com.sr.util.Constants;
import com.sr.util.Util;

@Path("/sr")
public class ExecutorController
{
   @POST
   @Path("/run")
   @Consumes(MediaType.MULTIPART_FORM_DATA)
   public Response uploadFile(
            @FormDataParam("file") InputStream uploadedInputStream,
            @FormDataParam("file") FormDataContentDisposition fileDetail)
   {
      String fileLocation = Constants.SCRIPT_DIR + fileDetail.getFileName();
      try
      {
         FileOutputStream out = new FileOutputStream(new File(fileLocation));
         int read = 0;
         byte[] bytes = new byte[1024];
         out = new FileOutputStream(new File(fileLocation));
         while ((read = uploadedInputStream.read(bytes)) != -1)
         {
            out.write(bytes, 0, read);
         }
         out.flush();
         out.close();
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }

      String id = Util.generateID();
      String resLocation = Constants.RESULT_DIR + id + "." + Constants.FORMAT;
      try
      {
         ExecutorService.runPythonScript(Constants.SCRIPT_NAME, Constants.SCRIPT_DIR, fileLocation, resLocation);
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      Properties props = new Properties();
      String host = "";
      String port = "";
      String context = "";
      String appname = "";
      try
      {
         props.load(new InputStreamReader(new FileInputStream(new File("/home/ubuntu/app2.properties"))));
         host = props.getProperty("host");
         appname = props.getProperty("appname");
         port = props.getProperty("port");
         context = props.getProperty("context");
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }

      return Response.status(200).entity(
               Constants.PROTOCOL + "://" + host + ":" + port + "/" + appname + "/" + context + "?filename=" + id + "." + Constants.FORMAT)
               .build();
   }
}
