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
 *        file: EnvironmentVariable.java
 *
 *  Created on: Aug 7, 2012
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.system;

/**********************************************************************
 * Describe purpose and responsibility of EnvironmentVariable
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Aug 7, 2012 8:23:17 AM
 **********************************************************************/
public class EnvironmentVariable
{
  public String Name = null;
  public String Value = null;

  /************************************************************
   * Method Name:
   *  EnvironmentVariable
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   * @param   describe the parameters if any
   *
   * @param name
   * @param value
   ***********************************************************/
  public EnvironmentVariable(String name, String value)
  {
    super();
    Name = name;
    Value = value;
  }

  
}
