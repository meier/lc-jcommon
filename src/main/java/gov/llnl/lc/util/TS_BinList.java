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
 *        file: BinList.java
 *
 *  Created on: Nov 9, 2011
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.util;

import gov.llnl.lc.time.KeyedTimeStampList;
import gov.llnl.lc.time.TimeStampedObject;

/**********************************************************************
 * Describe purpose and responsibility of TS_BinList
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Nov 9, 2011 4:30:01 PM
 **********************************************************************/
public class TS_BinList<T>
{
  /** a list of TimeStampedObjects **/
  private java.util.ArrayList <KeyedTimeStampList<T>> Bin_List = new java.util.ArrayList<KeyedTimeStampList<T>>();
  
  /** the number of Bins, not the size of each Bin **/
  private int maxListSize = 100;


  public synchronized boolean addObject(TimeStampedObject<T> tso)
  {
    // if the top of the list contains this objects key, add it to its bin
    // otherwise create a new bin, and make it the new top of the list/stack
    if((tso != null) && (tso.getObj() != null))
    {
      // check the first element in the list, if it matches
      if(Bin_List.size()== 0 || !(Bin_List.get(0).getKey().equals(tso.getObj())))
        return Bin_List.add(new KeyedTimeStampList(tso));
      else
        return Bin_List.get(0).addObject(tso);
  }
    return false;
  }

  public synchronized boolean removeObject(TimeStampedObject<T> tso)
  {
    // remove an entire bin from the list
    if((tso != null) && (tso.getObj() != null))
    {
      // TODO find the bin with the matching key, and remove it
      return Bin_List.remove(tso);
    }
    return false;
  }

  /************************************************************
   * Method Name:
   *  toString
  **/
  /**
   * Describe the method here
   *
   * @see java.lang.Object#toString()
  
   * @param   describe the parameters
   *
   * @return  describe the value returned
   * @return
   ***********************************************************/
  
  @Override
  public String toString()
  {
    return "TS_BinList [Bin_List=" + Bin_List + ", maxListSize=" + maxListSize + "]" + ", ListSize=" + Bin_List.size() + "]";
  }

}
