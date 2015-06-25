/********************************************************************
 * Copyright (C) 2013, Lawrence Livermore National Security, LLC.
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
 *        file: UserGroups.java
 *
 *  Created on: Feb 12, 2013
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.system.useraccount;

import gov.llnl.lc.system.CommandLineArguments;
import gov.llnl.lc.system.CommandLineExecutor;
import gov.llnl.lc.system.CommandLineResults;

/**********************************************************************
 * Describe purpose and responsibility of UserGroups
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Feb 12, 2013 2:31:50 PM
 **********************************************************************/
public class UserGroups
{
  public static String[] getGroups(String userId)
  {
    CommandLineExecutor cmdExecutor = new CommandLineExecutor(new CommandLineArguments("groups " + userId));
    try
    {
      cmdExecutor.runCommand();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    CommandLineResults Results = cmdExecutor.getResults();
    if(Results.getReturnCode() != 0)
      return null;
    
    if((Results.getOutput() != null) && (Results.getOutput().contains(":")))
      return (Results.getOutput().split(":"))[1].trim().split(" ");
    return null;
  }
  
  public static String toGroupsString(String userId, String[] groups)
  {
    // returns a list of groups for the user
    boolean match = false;
    
    if((userId == null) || (groups == null))
      return null;

    StringBuffer buff = new StringBuffer();
    
    for(String g: groups)
    {
      if(UserGroups.isMemberOfGroup(userId, g))
      {
        if(match)
          buff.append(" ");
        buff.append(g);
        match = true;
      }
    }
    return buff.toString();
  }
  
  public static String toGroupsString(String userId)
  {
    
    String[] groups = UserGroups.getGroups(userId);
    return UserGroups.toGroupsString(userId, groups);
  }
  
  public static boolean isMemberOfGroups(String userId, String[] groups)
  {
    // true if a member of ANY of the supplied groups
    boolean match = false;
    if(groups != null)
      for(String g: groups)
      {
        if(UserGroups.isMemberOfGroup(userId, g))
        {
          match = true;
          break;
        }
      }
    return match;
  }
  
  public static boolean isMemberOfGroup(String userId, String group)
  {
    // true if a member of supplied group
    boolean match = false;
    if((userId == null) || (group == null))
      return match;
    
    String[] groups = UserGroups.getGroups(userId);
    if(groups != null)
      for(String g: groups)
      {
        if(g.compareToIgnoreCase(group) == 0)
        {
          match = true;
          break;
        }
      }
    return match;
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

  }

}
