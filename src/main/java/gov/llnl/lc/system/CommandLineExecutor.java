/************************************************************
 * Copyright (c) 2015, Lawrence Livermore National Security, LLC.
 * Produced at the Lawrence Livermore National Laboratory.
 * Written by Timothy Meier, meier3@llnl.gov, All rights reserved.
 * LLNL-CODE-673346
 *
 * This file is part of the OpenSM Monitoring Service (OMS) package.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License (as published by
 * the Free Software Foundation) version 2.1 dated February 1999.
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * OUR NOTICE AND TERMS AND CONDITIONS OF THE GNU GENERAL PUBLIC LICENSE
 *
 * Our Preamble Notice
 *
 * A. This notice is required to be provided under our contract with the U.S.
 * Department of Energy (DOE). This work was produced at the Lawrence Livermore
 * National Laboratory under Contract No.  DE-AC52-07NA27344 with the DOE.
 *
 * B. Neither the United States Government nor Lawrence Livermore National
 * Security, LLC nor any of their employees, makes any warranty, express or
 * implied, or assumes any liability or responsibility for the accuracy,
 * completeness, or usefulness of any information, apparatus, product, or
 * process disclosed, or represents that its use would not infringe privately-
 * owned rights.
 *
 * C. Also, reference herein to any specific commercial products, process, or
 * services by trade name, trademark, manufacturer or otherwise does not
 * necessarily constitute or imply its endorsement, recommendation, or favoring
 * by the United States Government or Lawrence Livermore National Security,
 * LLC. The views and opinions of authors expressed herein do not necessarily
 * state or reflect those of the United States Government or Lawrence Livermore
 * National Security, LLC, and shall not be used for advertising or product
 * endorsement purposes.
 *
 *        file: CommandLineExecutor.java
 *
 *  Created on: Aug 7, 2012
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.system;

import gov.llnl.lc.io.AsyncStreamReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**********************************************************************
 * Describe purpose and responsibility of CommandLineExecutor
 * <p>
 * Usage of following class can go as ...
 * <P><PRE><CODE>
 *    CommandLineExecutor cmdExecutor = new CommandLineExecutor();    
 *    int exitStatus = cmdExecutor.runCommand(commandLine);
 * 
 *    CommandLineResults results = cmdExecutor.getResults();
 *    String cmdOutput = results.getOutput(); 
 *    String cmdError = results.getError(); 
 *    String cmdRtnCd = results.getReturnCode(); 
 * </CODE></PRE></P> 

 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Aug 7, 2012 8:05:44 AM
 **********************************************************************/
public class CommandLineExecutor
{
  private String commandLine;
  private String WorkingDirectory = null;
  private List<EnvironmentVariable> EnvironmentVarList = null;
  
  private int CmdReturnCode = -1;
  private StringBuffer CmdOutput = null;
  private StringBuffer CmdError = null;
  private AsyncStreamReader CmdOutputThread = null;
  private AsyncStreamReader CmdErrorThread = null; 
  
  public void setWorkingDirectory(String workingDirectory)
  {
    WorkingDirectory = workingDirectory;
  }

  public void setEnvironmentVar(String name, String value)
  {
    if (EnvironmentVarList == null)
      EnvironmentVarList = new ArrayList<EnvironmentVariable>();

    EnvironmentVarList.add(new EnvironmentVariable(name, value));
  }

  public int runCommand() throws Exception
  {
    if (this.commandLine == null)
      return -1;
    return runCommand(this.commandLine);
  }

  public int runCommand(String commandLine) throws Exception
  {
    /* run command */
    Process process = runCommandHelper(commandLine);

    /* start output and error read threads */
    startOutputAndErrorReadThreads(process.getInputStream(), process.getErrorStream());

    /* wait for command execution to terminate */
    int exitStatus = -1;
    try
    {
      exitStatus = process.waitFor();
      TimeUnit.MILLISECONDS.sleep(1L);  // add a tiny sleep to slow things down a bit

    }
    catch (Throwable ex)
    {
      throw new Exception(ex.getMessage());

    }
    finally
    {
      /* notify output and error read threads to stop reading */
      notifyOutputAndErrorReadThreadsToStopReading();
    }
    CmdReturnCode = exitStatus;
    return exitStatus;
  }

  private Process runCommandHelper(String commandLine) throws IOException
  {
    Process process = null;
    if (WorkingDirectory == null)
      process = Runtime.getRuntime().exec(commandLine, getEnvTokens());
    else
      process = Runtime.getRuntime().exec(commandLine, getEnvTokens(), new File(WorkingDirectory));

    return process;
  }

  private void startOutputAndErrorReadThreads(InputStream processOut, InputStream processErr)
  {
    CmdOutput = new StringBuffer();
    CmdOutputThread = new AsyncStreamReader(processOut, CmdOutput, "OUTPUT");
    CmdOutputThread.start();

    CmdError = new StringBuffer();
    CmdErrorThread = new AsyncStreamReader(processErr, CmdError, "ERROR");
    CmdErrorThread.start();
  }

  private void notifyOutputAndErrorReadThreadsToStopReading()
  {
    CmdOutputThread.stopReading();
    CmdErrorThread.stopReading();
  }

  private String[] getEnvTokens()
  {
    if (EnvironmentVarList == null)
      return null;

    String[] envTokenArray = new String[EnvironmentVarList.size()];
    Iterator<EnvironmentVariable> envVarIter = EnvironmentVarList.iterator();
    int nEnvVarIndex = 0;
    while (envVarIter.hasNext() == true)
    {
      EnvironmentVariable envVar = (EnvironmentVariable) (envVarIter.next());
      String envVarToken = envVar.Name + "=" + envVar.Value;
      envTokenArray[nEnvVarIndex++] = envVarToken;
    }

    return envTokenArray;
  }

  public CommandLineResults getResults()
  {
    return new CommandLineResults(this.CmdOutput.toString(), this.CmdError.toString(),
        this.CmdReturnCode);
  }

  /************************************************************
   * Method Name: main
   **/
  /**
   * Describe the method here
   * 
   * @see describe related java objects
   * 
   * @param describe
   *          the parameters
   * 
   * @return describe the value returned
   * @param args
   ***********************************************************/
  public static void main_orig(String[] args)
  {
    String s = null;

    try
    {

      // run the Unix "ps -ef" command

      Process p = Runtime.getRuntime().exec("ps -ef");

      BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

      BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

      // read the output from the command

      System.out.println("Here is the standard output of the command:\n");
      while ((s = stdInput.readLine()) != null)
      {
        System.out.println(s);
      }

      // read any errors from the attempted command

      System.out.println("Here is the standard error of the command (if any):\n");
      while ((s = stdError.readLine()) != null)
      {
        System.out.println(s);
      }

      System.exit(0);
    }
    catch (IOException e)
    {
      System.out.println("exception happened - here's what I know: ");
      e.printStackTrace();
      System.exit(-1);
    }
  }

  /************************************************************
   * Method Name: CommandLineExecutor
   **/
  /**
   * Describe the constructor here
   * 
   * @see describe related java objects
   * 
   * @param describe
   *          the parameters if any
   * 
   ***********************************************************/
  public CommandLineExecutor()
  {
    this(null, null, null);
  }

  /************************************************************
   * Method Name: CommandLineExecutor
   **/
  /**
   * Describe the constructor here
   * 
   * @see describe related java objects
   * 
   * @param describe
   *          the parameters if any
   * 
   * @param commandLine
   * @param workingDirectory
   * @param EnvironmentVarList
   ***********************************************************/
  public CommandLineExecutor(String commandLine, String workingDirectory,
      List<EnvironmentVariable> EnvironmentVarList)
  {
    super();
    this.commandLine = commandLine;
    WorkingDirectory = workingDirectory;
    this.EnvironmentVarList = EnvironmentVarList;
  }

  /************************************************************
   * Method Name: CommandLineExecutor
   **/
  /**
   * Describe the constructor here
   * 
   * @see describe related java objects
   * 
   * @param describe
   *          the parameters if any
   * 
   * @param args
   ***********************************************************/
  public CommandLineExecutor(CommandLineArguments args)
  {
    this(args.getCommandLine(), args.getWorkingDirectory(), args.getEnvironmentVarList());
  }

  /************************************************************
   * Method Name: main
   **/
  /**
   * Describe the method here
   * 
   * @see describe related java objects
   * 
   * @param describe
   *          the parameters
   * 
   * @return describe the value returned
   * @param args
   * @throws InterruptedException 
   * @throws Exception
   ***********************************************************/
  public static void main(String[] args) throws InterruptedException
  {
    CommandLineExecutor cmdExecutor = new CommandLineExecutor();
    int exitStatus;
    try
    {
      exitStatus = cmdExecutor.runCommand("ps -ef");
      CommandLineResults results = cmdExecutor.getResults();
      System.out.println("Here is the standard output of the command:\n");
      System.out.println(results.getOutput());

      System.out.println("Here is the standard error of the command (if any):\n");
      System.out.println(results.getError());

      System.out.println("Here is the return code:\n");
      System.out.println(results.getReturnCode());

    }
    catch (Exception e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.err.println(e.getMessage());
    }
    System.out.println("=============================================================================\n");
    
    // seems to need a sleep, between threads?  Allow to finish??
    TimeUnit.SECONDS.sleep(1);

    CommandLineExecutor cmdExecutor1 = new CommandLineExecutor(new CommandLineArguments(
        "ls -lart /home/meier3"));
    try
    {
      exitStatus = cmdExecutor1.runCommand();
      CommandLineResults results1 = cmdExecutor1.getResults();
      System.out.println("Here is the standard output of the command:\n");
      System.out.println(results1.getOutput());

      System.out.println("Here is the standard error of the command (if any):\n");
      System.out.println(results1.getError());

      System.out.println("Here is the return code:\n");
      System.out.println(results1.getReturnCode());

    }
    catch (Exception e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.err.println(e.getMessage());
    }

  }
}
