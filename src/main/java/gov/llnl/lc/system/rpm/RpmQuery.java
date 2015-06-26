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
 *        file: RpmQuery.java
 *
 *  Created on: Aug 15, 2012
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.system.rpm;

import gov.llnl.lc.system.CommandLineArguments;
import gov.llnl.lc.system.CommandLineExecutor;
import gov.llnl.lc.system.CommandLineResults;
import gov.llnl.lc.system.SoftwareComponent;

/**********************************************************************
 * Describe purpose and responsibility of RpmQuery
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Aug 15, 2012 8:12:32 AM
 **********************************************************************/
public class RpmQuery
{
  private String QueryCommand;
  private CommandLineResults Results;
  private SoftwareComponent RpmInfo;

  public int query(String queryCommand) throws Exception
  {
    QueryCommand = queryCommand;
    CommandLineExecutor cmdExecutor = new CommandLineExecutor(new CommandLineArguments(queryCommand));    
    cmdExecutor.runCommand();
    Results = cmdExecutor.getResults();
    return Results.getReturnCode();
    
  }
  
  public int queryInfo(String packageName) throws Exception
  {
    query("rpm -qi " + packageName);
    RpmParser parser = new RpmParser();
    parser.parseString(Results.getOutput());
    RpmInfo = parser.getRpmInfo();
    return Results.getReturnCode();
    
  }
  

  /************************************************************
   * Method Name:
   *  getQueryCommand
   **/
  /**
   * Returns the value of queryCommand
   *
   * @return the queryCommand
   *
   ***********************************************************/
  
  public String getQueryCommand()
  {
    return QueryCommand;
  }

  /************************************************************
   * Method Name:
   *  setQueryCommand
   **/
  /**
   * Sets the value of queryCommand
   *
   * @param queryCommand the queryCommand to set
   * @throws Exception 
   *
   ***********************************************************/
  public void setQueryCommand(String queryCommand) throws Exception
  {
    query(queryCommand);
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
   *  getRpmInfo
   **/
  /**
   * Returns the value of rpmInfo
   *
   * @return the rpmInfo
   *
   ***********************************************************/
  
  public SoftwareComponent getRpmInfo()
  {
    return RpmInfo;
  }

  /************************************************************
   * Method Name:
   *  RpmQuery
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   * @param   describe the parameters if any
   *
   ***********************************************************/
  public RpmQuery()
  {
    super();
    // TODO Auto-generated constructor stub
  }

  /************************************************************
   * Method Name:
   *  RpmQuery
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   * @param   describe the parameters if any
   *
   * @param packageName
   * @throws Exception 
   ***********************************************************/
  public RpmQuery(String packageName) throws Exception
  {
    super();
    this.queryInfo(packageName);
  }

  /************************************************************
   * Method Name:
   *  main
   **/
  /**
   * Describe the method here
   *
   * @see     describe related java objects

   * @param   describe the parameters
   *
   * @return  describe the value returned
   * @param args
   * @throws Exception 
   ***********************************************************/
  public static void main(String[] args)
  {
    RpmQuery query = new RpmQuery();
    int exitStatus = 0;
    try
    {
      exitStatus = query.queryInfo("llnl-ldapotp-clt-jni-auth-libs");
    }
    catch (Exception ioe)
    {
      System.out.println("Query exception: " + ioe.getMessage());
    }
    
    System.out.println(query.RpmInfo.toString());    
   
  }
}
