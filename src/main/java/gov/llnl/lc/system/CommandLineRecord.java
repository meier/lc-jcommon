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
 *        file: CommandLineRecord.java
 *
 *  Created on: Aug 14, 2012
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.system;

import gov.llnl.lc.time.TimeStamp;

import java.io.Serializable;

/**********************************************************************
 * The CommandLineRecord object represents a complete command/result
 * action.  A command is issued at a specific time, and a result is
 * returned at a specific time.  This object holds the complete interaction.
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Aug 14, 2012 3:39:34 PM
 **********************************************************************/
public class CommandLineRecord implements Serializable
{
  /**  describe serialVersionUID here **/
  private static final long serialVersionUID = -2197642118651901057L;

  /** the command that was issued **/
  private CommandLineArguments Command;
  /** the time the command was issued **/
  private TimeStamp CommandExecutionTime;
  /** the results of the command **/
  private CommandLineResults Results;
  /** the time the results were returned **/
  private TimeStamp CommandResultTime;
  /************************************************************
   * Method Name:
   *  CommandLineRecord
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   * @param   describe the parameters if any
   *
   * @param command
   * @param commandExecutionTime
   * @param results
   * @param commandResultTime
   ***********************************************************/
  public CommandLineRecord(CommandLineArguments command, TimeStamp commandExecutionTime,
      CommandLineResults results, TimeStamp commandResultTime)
  {
    super();
    Command = command;
    CommandExecutionTime = commandExecutionTime;
    Results = results;
    CommandResultTime = commandResultTime;
  }
  /************************************************************
   * Method Name:
   *  getCommand
   **/
  /**
   * Returns the value of command
   *
   * @return the command
   *
   ***********************************************************/
  
  public CommandLineArguments getCommand()
  {
    return Command;
  }
  /************************************************************
   * Method Name:
   *  getCommandExecutionTime
   **/
  /**
   * Returns the value of commandExecutionTime
   *
   * @return the commandExecutionTime
   *
   ***********************************************************/
  
  public TimeStamp getCommandExecutionTime()
  {
    return CommandExecutionTime;
  }
  /************************************************************
   * Method Name:
   *  getResults
   **/
  /**
   * Returns the value of results
   *
   * @return the results
   *
   ***********************************************************/
  
  public CommandLineResults getResults()
  {
    return Results;
  }
  /************************************************************
   * Method Name:
   *  getCommandResultTime
   **/
  /**
   * Returns the value of commandResultTime
   *
   * @return the commandResultTime
   *
   ***********************************************************/
  
  public TimeStamp getCommandResultTime()
  {
    return CommandResultTime;
  }
  
  
}
