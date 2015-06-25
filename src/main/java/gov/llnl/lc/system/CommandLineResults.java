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
 *        file: CommandLineResults.java
 *
 *  Created on: Aug 7, 2012
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.system;

import java.io.Serializable;

/**********************************************************************
 * Describe purpose and responsibility of CommandLineResults
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Aug 7, 2012 9:30:02 AM
 **********************************************************************/
public class CommandLineResults implements Serializable
{
  /**  describe serialVersionUID here **/
  private static final long serialVersionUID = -2578433169929287068L;

  private int ReturnCode;
  private String Output;
  private String Error;
  /************************************************************
   * Method Name:
   *  CommandLineResults
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   * @param   describe the parameters if any
   *
   * @param output
   * @param error
   * @param returnCode
   ***********************************************************/
  public CommandLineResults(String output, String error, int returnCode)
  {
    super();
    Output = output;
    Error = error;
    ReturnCode = returnCode;
  }
  /************************************************************
   * Method Name:
   *  getReturnCode
   **/
  /**
   * Returns the value of returnCode
   *
   * @return the returnCode
   *
   ***********************************************************/
  
  public int getReturnCode()
  {
    return ReturnCode;
  }
  /************************************************************
   * Method Name:
   *  getOutput
   **/
  /**
   * Returns the string received from STDOUT
   *
   * @return the output
   *
   ***********************************************************/
  
  public String getOutput()
  {
    return Output;
  }
  /************************************************************
   * Method Name:
   *  getError
   **/
  /**
   * Returns the string received from STDERR
   *
   * @return the error
   *
   ***********************************************************/
  
  public String getError()
  {
    return Error;
  }
  
  
  
  /************************************************************
   * Method Name:
   *  toString
  **/
  /**
   * Describe the method here
   *
   * @see java.lang.Object#toString()
   *
   * @return
   ***********************************************************/
  
  @Override
  public String toString()
  {
    return "CommandLineResults [ReturnCode=" + ReturnCode + ", Output=" + Output + ", Error="
        + Error + "]";
  }
  
  
}
