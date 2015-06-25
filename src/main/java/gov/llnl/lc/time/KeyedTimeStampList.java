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
 *        file: KeyedTimeStampList.java
 *
 *  Created on: Nov 9, 2011
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.time;



/**********************************************************************
 * Describe purpose and responsibility of KeyedTimeStampList
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Nov 9, 2011 3:26:21 PM
 **********************************************************************/
public class KeyedTimeStampList<T>
{
  /** a list of TimeStampedObjects **/
  private java.util.ArrayList <TimeStampedObject<T>> TS_Objects = new java.util.ArrayList<TimeStampedObject<T>>();
  
  private T Key = null;
  
  private int numElements = 100;

  /************************************************************
   * Method Name:
   *  KeyedTimeStampList
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   * @param   describe the parameters if any
   *
   * @param tS_Objects
   * @param numElements
   ***********************************************************/
  public KeyedTimeStampList(TimeStampedObject<T> tso)
  {
    this(tso.getObj());
    this.addObject(tso);
  }

  /************************************************************
   * Method Name:
   *  KeyedTimeStampList
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   * @param   describe the parameters if any
   *
   * @param key
   * @param numElements
   ***********************************************************/
  public KeyedTimeStampList(T key)
  {
    this(key, 100);
  }

  /************************************************************
   * Method Name:
   *  KeyedTimeStampList
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   * @param   describe the parameters if any
   *
   * @param key
   * @param numElements
   ***********************************************************/
  public KeyedTimeStampList(T key, int numElements)
  {
    super();
    Key = key;
    this.numElements = numElements;
  }

  public synchronized boolean addObject(TimeStampedObject<T> tso)
  {
    // only add the tso if the object matches the key
    if((tso != null) && (tso.getObj() != null) && tso.getObj().equals(Key))
    {
      return TS_Objects.add(tso);
    }
    return false;
  }

  public synchronized boolean removeObject(TimeStampedObject<T> tso)
  {
    // only remove the tso if the object matches the key
    if((tso != null) && (tso.getObj() != null) && tso.getObj().equals(Key))
    {
      return TS_Objects.remove(tso);
    }
    return false;
  }

  /************************************************************
   * Method Name:
   *  getTS_Objects
   **/
  /**
   * Returns the value of tS_Objects
   *
   * @return the tS_Objects
   *
   ***********************************************************/
  
  public java.util.ArrayList<TimeStampedObject<T>> getTS_Objects()
  {
    return TS_Objects;
  }

  /************************************************************
   * Method Name:
   *  getKey
   **/
  /**
   * Returns the value of key
   *
   * @return the key
   *
   ***********************************************************/
  
  public Object getKey()
  {
    return Key;
  }

  /************************************************************
   * Method Name:
   *  getNumElements
   **/
  /**
   * Returns the value of numElements
   *
   * @return the numElements
   *
   ***********************************************************/
  
  public int getNumElements()
  {
    return TS_Objects.size();
  }


}
