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
 *        file: WhatsupQuery.java
 *
 *  Created on: Feb 11, 2013
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.system.whatsup;

import gov.llnl.lc.system.CommandLineArguments;
import gov.llnl.lc.system.CommandLineExecutor;
import gov.llnl.lc.system.CommandLineResults;

import java.io.IOException;

/**********************************************************************
 * Describe purpose and responsibility of WhatsupQuery
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Feb 11, 2013 10:14:59 AM
 **********************************************************************/
public class WhatsUpQuery
{
  private String QueryCommand;
  private CommandLineResults Results;
  static final String COMMA_QUERY          = "whatsup -c";

  /************************************************************
   * Method Name:
   *  WhatsUpQuery
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   ***********************************************************/
  public WhatsUpQuery()
  {
    super();
    // TODO Auto-generated constructor stub
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

  public int query() throws Exception
  {
    return query(COMMA_QUERY);
  }
  
  public int query(String queryCommand) throws Exception
  {
    QueryCommand = queryCommand;
    CommandLineExecutor cmdExecutor = new CommandLineExecutor(new CommandLineArguments(queryCommand));    
    cmdExecutor.runCommand();
    Results = cmdExecutor.getResults();
    return Results.getReturnCode();    
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
    return "WhatsUpQuery [QueryCommand=" + QueryCommand + ", Results=" + Results + "]";
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
    WhatsUpQuery query = new WhatsUpQuery();
    WhatsUpInfo whatsup = null;
    int exitStatus = 0;
    
      try
      {
        exitStatus = query.query(COMMA_QUERY);
      }
      catch (Exception e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
        System.err.println("query");
      }
      
      WhatsUpParser parser = new WhatsUpParser();
      try
      {
        parser.parseString(query.Results.getOutput());
      }
      catch (IOException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
        System.err.println("parser");
      }
      whatsup = parser.getWhatsUp();
      exitStatus = query.Results.getReturnCode();
    
    System.out.println("The Query");    
    System.out.println(query.toString());    
    System.out.println("The WhatsUpInfo");    
    System.out.println(whatsup.toString());    
    System.out.println("Exit status: " + exitStatus);
  }
}
