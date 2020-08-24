package com.sr.executor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpSession;

import com.sr.model.Worker;

public class ExecutorThread implements Runnable
{

   HttpSession session;
   Thread t;
   Worker worker;
   String filepath;

   public ExecutorThread(HttpSession session, String filepath, Worker worker)
   {
      this.session = session;
      this.filepath = filepath;
      this.worker = worker;
      t = new Thread(this);
      t.start();
   }

   @Override
   public void run()
   {

      try
      {
         session.setAttribute("status", "RUNNING");

         File fileToUpload = new File(filepath);
         URL serverUrl = new URL("http://" + worker.getHost() + ":" + worker.getPort() + "/" + worker.getAppname() + "/" + worker.getContextroot());
         System.out.println("Opening Connection: " + serverUrl.toString());
         HttpURLConnection urlConnection = (HttpURLConnection) serverUrl.openConnection();
         System.out.println("Target: " + serverUrl.toString());

         String boundaryString = "----SomeRandomText";

         // Indicate that we want to write to the HTTP request body
         urlConnection.setDoOutput(true);
         urlConnection.setRequestMethod("POST");
         urlConnection.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);

         OutputStream outputStreamToRequestBody = urlConnection.getOutputStream();
         BufferedWriter httpRequestBodyWriter = new BufferedWriter(new OutputStreamWriter(outputStreamToRequestBody));

         // Include value from the myFileDescription text area in the post data
         httpRequestBodyWriter.write("\n\n--" + boundaryString + "\n");
         httpRequestBodyWriter.write("Content-Disposition: form-data; name=\"myFileDescription\"");
         httpRequestBodyWriter.write("\n\n");
         httpRequestBodyWriter.write("Video Stream");

         // Include the section to describe the file
         httpRequestBodyWriter.write("\n--" + boundaryString + "\n");
         httpRequestBodyWriter.write("Content-Disposition: form-data;"
                  + "name=\"file\";"
                  + "filename=\"" + fileToUpload.getName() + "\""
                  + "\nContent-Type: text/plain\n\n");
         httpRequestBodyWriter.flush();

         // Write the actual file contents
         FileInputStream inputStreamToFile = new FileInputStream(fileToUpload);

         int bytesRead;
         byte[] dataBuffer = new byte[1024];
         while ((bytesRead = inputStreamToFile.read(dataBuffer)) != -1)
         {
            outputStreamToRequestBody.write(dataBuffer, 0, bytesRead);
         }

         outputStreamToRequestBody.flush();

         // Mark the end of the multipart http request
         httpRequestBodyWriter.write("\n--" + boundaryString + "--\n");
         httpRequestBodyWriter.flush();

         // Close the streams
         outputStreamToRequestBody.close();
         httpRequestBodyWriter.close();

         // Read response from web server, which will trigger the multipart HTTP request to be sent.
         BufferedReader httpResponseReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
         String lineRead;
         String output = "";
         while ((lineRead = httpResponseReader.readLine()) != null)
         {
            output += lineRead;
         }
         System.out.println("Output: " + output);
         inputStreamToFile.close();

         session.setAttribute("result", output);
         session.setAttribute("status", "COMPLETED");
      }

      catch (Exception e)
      {

      }

   }

}
