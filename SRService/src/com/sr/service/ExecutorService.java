package com.sr.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ExecutorService
{

   public static String runPythonScript(String scriptName, String scriptDir, String inputFileLocation, String outputFileLocation) throws Exception
   {
      List<String> command = new ArrayList<String>();
      command.add("python3");
      command.add(scriptName);
      command.add(inputFileLocation);
      command.add(outputFileLocation);
      ProcessBuilder builder = new ProcessBuilder(command);
      builder.directory(new File(scriptDir));

      final Process process = builder.start();
      InputStream is = process.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      BufferedReader br = new BufferedReader(isr);
      String line = "";
      String output = "";
      while ((line = br.readLine()) != null)
      {
         if (output.length() != 0)
            output += "\n";
         output += line;
      }
      return output;
   }

}
