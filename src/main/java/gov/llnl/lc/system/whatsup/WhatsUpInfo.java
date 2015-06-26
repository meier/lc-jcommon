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
 *        file: WhatsUpInfo.java
 *
 *  Created on: Feb 11, 2013
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.system.whatsup;

import java.io.Serializable;
import java.util.Arrays;


/**********************************************************************
 * Describe purpose and responsibility of WhatsUpInfo
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Feb 11, 2013 10:10:37 AM
 **********************************************************************/
public class WhatsUpInfo implements Serializable
{
  /**  describe serialVersionUID here **/
  private static final long serialVersionUID = -5321673893128104317L;

  private String [] UpNodes      = null;
  private String [] DownNodes    = null;
  private String [] UnknownNodes = null;
  private int ReturnCode         = 0;
  private String ReturnMessage   = null;

  /************************************************************
   * Method Name:
   *  getUpNodes
   **/
  /**
   * Returns the value of upNodes
   *
   * @return the upNodes
   *
   ***********************************************************/
  
  public String[] getUpNodes()
  {
    return UpNodes;
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
   *  getReturnMessage
   **/
  /**
   * Returns the value of returnMessage
   *
   * @return the returnMessage
   *
   ***********************************************************/
  
  public String getReturnMessage()
  {
    return ReturnMessage;
  }

  /************************************************************
   * Method Name:
   *  setUpNodes
   **/
  /**
   * Sets the value of upNodes
   *
   * @param upNodes the upNodes to set
   *
   ***********************************************************/
  public void setUpNodes(String[] upNodes)
  {
    UpNodes = upNodes;
  }

  /************************************************************
   * Method Name:
   *  getDownNodes
   **/
  /**
   * Returns the value of downNodes
   *
   * @return the downNodes
   *
   ***********************************************************/
  
  public String[] getDownNodes()
  {
    return DownNodes;
  }

  /************************************************************
   * Method Name:
   *  setDownNodes
   **/
  /**
   * Sets the value of downNodes
   *
   * @param downNodes the downNodes to set
   *
   ***********************************************************/
  public void setDownNodes(String[] downNodes)
  {
    DownNodes = downNodes;
  }

  /************************************************************
   * Method Name:
   *  getUnknownNodes
   **/
  /**
   * Returns the value of unknownNodes
   *
   * @return the unknownNodes
   *
   ***********************************************************/
  
  public String[] getUnknownNodes()
  {
    return UnknownNodes;
  }

  /************************************************************
   * Method Name:
   *  setUnknownNodes
   **/
  /**
   * Sets the value of unknownNodes
   *
   * @param unknownNodes the unknownNodes to set
   *
   ***********************************************************/
  public void setUnknownNodes(String[] unknownNodes)
  {
    UnknownNodes = unknownNodes;
  }

  /************************************************************
   * Method Name:
   *  WhatsUpInfo
  **/
  /**
   * This is just a container class with static heper functions,
   * dont do anything in the constructor.
   *
   * @see     describe related java objects
   *
   ***********************************************************/
  public WhatsUpInfo()
  {
    super();
  }
  
  public static WhatsUpInfo createWhatsUpInfo() throws Exception
  {
    WhatsUpQuery query = new WhatsUpQuery();
    WhatsUpParser parser = new WhatsUpParser();
    WhatsUpInfo whatsup = null;
    int exitStatus = query.query();
    parser.parseString(query.getResults().getOutput());
    whatsup = parser.getWhatsUp();
    whatsup.ReturnCode = query.getResults().getReturnCode();
    whatsup.ReturnMessage = query.getResults().getError();
    return whatsup;
  }

  /************************************************************
   * Method Name:
   *  main
   **/
  /**
   * Describe the method here
   *
   * @see     describe related java objects
   *
   * @param args
   * @throws Exception 
   ***********************************************************/
  public static void main(String[] args) throws Exception
  {
    // what's up
    System.out.println("Getting whatsup info");
    WhatsUpInfo whatsUp = WhatsUpInfo.createWhatsUpInfo();
    System.out.println(whatsUp.toString());
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
    return "WhatsUpInfo [UpNodes=" + Arrays.toString(UpNodes) + ", DownNodes="
        + Arrays.toString(DownNodes) + ", UnknownNodes=" + Arrays.toString(UnknownNodes)
        + ", ReturnCode=" + ReturnCode + ", ReturnMessage=" + ReturnMessage + "]";
  }  

}
