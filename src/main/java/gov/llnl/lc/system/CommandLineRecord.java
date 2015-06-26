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
