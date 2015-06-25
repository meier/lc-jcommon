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
 *        file: TimeStampedObject.java
 *
 *  Created on: Nov 9, 2011
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.time;

/**********************************************************************
 * Describe purpose and responsibility of TimeStampedObject
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Nov 9, 2011 3:06:41 PM
 **********************************************************************/
public class TimeStampedObject<T>
{
  private TimeStamp timeStamp = null;
  
  private T Obj;

  /************************************************************
   * Method Name:
   *  TimeStampedObject
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   * @param   describe the parameters if any
   *
   * @param timeStamp
   * @param obj
   ***********************************************************/
  public TimeStampedObject(TimeStamp timeStamp, T obj)
  {
    super();
    this.timeStamp = timeStamp;
    Obj = obj;
  }
  
  /************************************************************
   * Method Name:
   *  getTimeStamp
   **/
  /**
   * Returns the value of timeStamp
   *
   * @return the timeStamp
   *
   ***********************************************************/
  
  public TimeStamp getTimeStamp()
  {
    return timeStamp;
  }

  /************************************************************
   * Method Name:
   *  getObj
   **/
  /**
   * Returns the value of obj
   *
   * @return the obj
   *
   ***********************************************************/
  
  public T getObj()
  {
    return Obj;
  }

  /************************************************************
   * Method Name:
   *  TimeStampedObject
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   * @param   describe the parameters if any
   *
   * @param timeStamp
   * @param obj
   ***********************************************************/
  public TimeStampedObject(T obj)
  {
    this(new TimeStamp(), obj);
  }
  
  public boolean isObjectEqual(TimeStampedObject<T> tso)
  {
    if(this.Obj != null)
      return this.Obj.equals(tso.getObj());
    return false;
  }
}
