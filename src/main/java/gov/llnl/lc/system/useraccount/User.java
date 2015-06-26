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
 *        file: User.java
 *
 *  Created on: Oct 6, 2014
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.system.useraccount;

import gov.llnl.lc.system.CommandLineArguments;
import gov.llnl.lc.system.CommandLineExecutor;
import gov.llnl.lc.system.CommandLineResults;

import java.io.Serializable;

/**********************************************************************
 * Describe purpose and responsibility of User
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Oct 6, 2014 12:01:28 PM
 **********************************************************************/
public class User implements Serializable
{
  /**  describe serialVersionUID here **/
  private static final long serialVersionUID = 5532466936535543747L;
  
  public String UserName      = null;
  public String UserHome      = null;
  public String UserDir       = null;
  public String UserId        = null;
  
  /************************************************************
   * Method Name:
   *  User
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   ***********************************************************/
  public User()
  {
    UserName      = getUserName();
    UserHome      = getUserHome();
    UserDir       = getUserDir();
    UserId        = getUserId();
  }

  public static String getUserName()
  {
    return System.getProperty("user.name");
  }
  
  public static String getUserHome()
  {
    return System.getProperty("user.home");
  }
  
  public static String getUserDir()
  {
    return System.getProperty("user.dir");
  }
  
  public static String whoAmI()
  {
    CommandLineExecutor cmdExecutor = new CommandLineExecutor(new CommandLineArguments("whoami"));
    try
    {
      cmdExecutor.runCommand();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    
    CommandLineResults Results = cmdExecutor.getResults();
    
    // if the "whoami" command didn't work, try the java environment variable
    if((Results.getReturnCode() != 0) || (Results.getOutput() == null))
      return User.getUserName();
    
    return Results.getOutput().trim();
  }
  
  public static String getUserId()
  {
    return getUserId(null);
  }
  
  public static String getUserId(String userId)
  {
    String cmd = "id";
    if(userId != null)
      cmd = "id " + userId;
    CommandLineExecutor cmdExecutor = new CommandLineExecutor(new CommandLineArguments(cmd));
    try
    {
      cmdExecutor.runCommand();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    
    CommandLineResults Results = cmdExecutor.getResults();
    
    if((Results.getReturnCode() != 0) || (Results.getOutput() == null))
      return null;
    
    return Results.getOutput().trim();
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
   ***********************************************************/
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
    User luser = new User();
    
    System.out.println("LId (" + luser.UserId + ")");
    System.out.println("LUser Home (" + luser.UserHome + ")");
    System.out.println("LUser Dir (" + luser.UserDir + ")");
    System.out.println("LUN (" + luser.UserName + ")");
    String[] lgroups = UserGroups.getGroups(luser.UserName);
    System.out.println("LUG (" + lgroups.length + ")");

    System.out.println("WAI (" + User.whoAmI() + ")");
    System.out.println("GUN (" + User.getUserName() + ")");
    System.out.println("User Home (" + User.getUserHome() + ")");
    System.out.println("User Dir (" + User.getUserDir() + ")");
    System.out.println("GUN (" + User.getUserName() + ")");
    String[] groups = UserGroups.getGroups(User.whoAmI());
    System.out.println("GUG (" + groups.length + ")");
    
    System.out.println(UserGroups.isMemberOfGroup("meier3", "CIAC"));
    System.out.println(UserGroups.toGroupsString("blar"));
    System.out.println(UserGroups.toGroupsString("meier3"));
    System.out.println(UserGroups.toGroupsString("meier3", groups));
    String[] uGroups = { "ica", "blah", "linuxdev"};
    System.out.println(UserGroups.isMemberOfGroups("meier3", uGroups));
    System.out.println("WAI (" + User.whoAmI() + ")");
    System.out.println("UID (" + User.getUserId() + ")");
    System.out.println("UID (" + User.getUserId("meier3") + ")");
    System.out.println("UID (" + User.getUserId("blah") + ")");
    System.out.println("UID (" + User.getUserId("meier3_r") + ")");
  }

}
