/********************************************************************
 * Copyright (C) 2011, Lawrence Livermore National Security, LLC.
 * Produced at Lawrence Livermore National Laboratory (cf, DISCLAIMER).
 * CODE-235483
 * 
 * For details, see http://www.llnl.gov/linux/
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
 *        file: CommandLineArguments.java
 *
 *  Created on: Aug 7, 2012
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.system;

import java.io.Serializable;
import java.util.List;

/**********************************************************************
 * Describe purpose and responsibility of CommandLineArguments
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Aug 7, 2012 10:41:41 AM
 **********************************************************************/
public class CommandLineArguments implements Serializable
{
  /**  describe serialVersionUID here **/
  private static final long serialVersionUID = -4670155712014233030L;

  private String CommandLine;
  private String WorkingDirectory;
  private List <EnvironmentVariable> EnvironmentVarList;

  
  /************************************************************
   * Method Name:
   *  getCommandLine
   **/
  /**
   * Returns the value of commandLine
   *
   * @return the commandLine
   *
   ***********************************************************/
  
  public String getCommandLine()
  {
    return CommandLine;
  }


  /************************************************************
   * Method Name:
   *  setCommandLine
   **/
  /**
   * Sets the value of commandLine
   *
   * @param commandLine the commandLine to set
   *
   ***********************************************************/
  public void setCommandLine(String commandLine)
  {
    CommandLine = commandLine;
  }


  /************************************************************
   * Method Name:
   *  getWorkingDirectory
   **/
  /**
   * Returns the value of workingDirectory
   *
   * @return the workingDirectory
   *
   ***********************************************************/
  
  public String getWorkingDirectory()
  {
    return WorkingDirectory;
  }


  /************************************************************
   * Method Name:
   *  setWorkingDirectory
   **/
  /**
   * Sets the value of workingDirectory
   *
   * @param workingDirectory the workingDirectory to set
   *
   ***********************************************************/
  public void setWorkingDirectory(String workingDirectory)
  {
    WorkingDirectory = workingDirectory;
  }


  /************************************************************
   * Method Name:
   *  getEnvironmentVarList
   **/
  /**
   * Returns the value of environmentVarList
   *
   * @return the environmentVarList
   *
   ***********************************************************/
  
  public List<EnvironmentVariable> getEnvironmentVarList()
  {
    return EnvironmentVarList;
  }


  /************************************************************
   * Method Name:
   *  setEnvironmentVarList
   **/
  /**
   * Sets the value of environmentVarList
   *
   * @param environmentVarList the environmentVarList to set
   *
   ***********************************************************/
  public void setEnvironmentVarList(List<EnvironmentVariable> environmentVarList)
  {
    EnvironmentVarList = environmentVarList;
  }


  /************************************************************
   * Method Name:
   *  CommandLineArguments
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   * @param   describe the parameters if any
   *
   * @param commandLine
   ***********************************************************/
  public CommandLineArguments(String commandLine)
  {
    this(commandLine, null, null);
  }

  /************************************************************
   * Method Name:
   *  CommandLineArguments
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   * @param   describe the parameters if any
   *
   * @param commandLine
   * @param workingDirectory
   * @param environmentVarList
   ***********************************************************/
  public CommandLineArguments(String commandLine, String workingDirectory,
      List<EnvironmentVariable> environmentVarList)
  {
    super();
    CommandLine = commandLine;
    WorkingDirectory = workingDirectory;
    EnvironmentVarList = environmentVarList;
  }
}
