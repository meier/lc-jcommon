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
