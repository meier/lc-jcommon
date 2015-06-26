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
 *        file: LocalHost.java
 *
 *  Created on: Aug 7, 2012
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.system.hostmachine;

import java.io.Serializable;
import java.net.UnknownHostException;

public class LocalHost implements Serializable
{
  /**  describe serialVersionUID here **/
  private static final long serialVersionUID = 2081786341030635508L;
  
  public String OS_Name         = null;
  public String OS_Version      = null;
  public String OS_Architecture = null;
  public String HostName        = null;
  public String HostAddress     = null;
  
  
  /************************************************************
   * Method Name:
   *  LocalHost
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   ***********************************************************/
  public LocalHost()
  {
    OS_Name         = getOS_Name();
    OS_Version      = getOS_Version();
    OS_Architecture = getOS_Architecture();
    HostName        = getHostName();
    HostAddress     = getHostAddress();
  }

  public static String getOS_Name()
  {
    return System.getProperty("os.name");
  }
  
  public static String getOS_Version()
  {
    return System.getProperty("os.version");
  }
  
  public static String getOS_Architecture()
  {
    return System.getProperty("os.arch");
  }
  
  public static String getCanonicalHostName()
  {
    try
    {
      return java.net.InetAddress.getLocalHost().getCanonicalHostName();
    }
    catch (UnknownHostException e)
    {
      e.printStackTrace();
    }
    return "unknown host";
  }

  public static String getHostName()
  {
    try
    {
      return java.net.InetAddress.getLocalHost().getHostName();
    }
    catch (UnknownHostException e)
    {
      e.printStackTrace();
    }
    return "unknown host";
  }

  public static String getHostAddress()
  {
    try
    {
      return java.net.InetAddress.getLocalHost().getHostAddress();
    }
    catch (UnknownHostException e)
    {
      e.printStackTrace();
    }
    return "unknown address";
  }

  public static void main(String[] args)
  {
    /* there are two ways to use this class;
     * 1) instantiate it, then use the public member values
     * 2) use the static methods
     * 
     * Use #1 if you want to move the instance values around to different
     * hosts
     * 
     * Use #2 if you want the current values on the current host
     */
    LocalHost lhost = new LocalHost();
    
    System.out.println("Hostname of local machine: " + lhost.HostName);
    System.out.println("Address of local machine: " + lhost.HostAddress);
    System.out.println("OS Name of local machine: " + lhost.OS_Name);
    System.out.println("OS Version of local machine: " + lhost.OS_Version);
    System.out.println("OS Architecture of local machine: " + lhost.OS_Architecture);
    
    System.out.println("Hostname of local machine: " + LocalHost.getCanonicalHostName());
    System.out.println("Hostname of local machine: " + LocalHost.getHostName());
    System.out.println("Address of local machine: " + LocalHost.getHostAddress());
    System.out.println("OS Name of local machine: " + LocalHost.getOS_Name());
    System.out.println("OS Version of local machine: " + LocalHost.getOS_Version());
    System.out.println("OS Architecture of local machine: " + LocalHost.getOS_Architecture());
    
  }

}
