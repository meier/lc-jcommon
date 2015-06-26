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
 *        file: DateRange.java
 *
 *  Created on: Jun 29, 2011
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.time;

import gov.llnl.lc.logging.CommonLogger;

import java.util.Calendar;
import java.util.Date;

/**********************************************************************
 * Describe purpose and responsibility of DateRange
 * <p>
 * 
 * @see related classes and interfaces
 * 
 * @author meier3
 * 
 * @version Jun 29, 2011 3:56:05 PM
 **********************************************************************/
public class DateRange implements CommonLogger
{
  /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
  /* ~~~ Static Finals !!! */
  /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */

  /** Interval Format String for display **/
  static final String GUI_SHORT_INTERVAL_FORMAT = "m' mins, 's' secs, 'S' msecs'";

  /** Date Format String for reports **/
  static final String PORTAL_DATE_FORMAT        = "MM/dd/yyyy";
  static final String IR_REPORT_DATE_FORMAT     = "MMM dd yyyy";
  static final String GUI_REPORT_DATE_FORMAT    = "MMM yyyy";
  static final String REPORT_DATE_FORMAT        = "MMMyyyy";
  static final String GUI_DATE_TIME_FORMAT      = "MMMM dd, yyyy hh:mm:ss aa z";

  /** Date Format String for Workgroups **/
  static final String GUI_WORKGROUP_DATE_FORMAT = "MMyyyy";

  /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
  /* ~~~ Class Variables !!! */
  /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */

  /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
  /* ~~~ Instance Variables !!! */
  /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */

  private Calendar    ScatchCalendar            = Calendar.getInstance();
  private Date        FromDate                  = ScatchCalendar.getTime();
  private Date        ToDate                    = ScatchCalendar.getTime();

  private long        Millis;
  private long        Secs;
  private long        Mins;
  private long        Hours;
  private long        Days;

  /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
  /* ~~~ Constructors !!! */
  /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */

  // -------------------------------------------------------------------------
  // Method:
  // DateRange
  /**
   * No argument constructor for class 'DateRange'.
   * 
   * @see Method_related_to_this_method
   * 
   * @throws Class_name
   *           Description_of_exception_thrown__Delete_if_none
   **/
  public DateRange()
  {
    super();
  }

  public DateRange(java.util.Calendar fromDate, java.util.Calendar toDate)
  {
    super();
    if ((fromDate != null) && (toDate != null))
    {
      FromDate = fromDate.getTime();
      ToDate = toDate.getTime();
    }
  }

  /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
  /* ~~~ Property Methods !!! */
  /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */

  /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
  /* ~~~ Event Methods !!! */
  /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */

  /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
  /* ~~~ General Methods !!! */
  /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! */
  private static java.util.Calendar setFirstSecondInMonth(java.util.Calendar origCal)
  {
    // create a calendar copy
    Calendar cal = Calendar.getInstance();
    cal.setTime(origCal.getTime());

    // set the appropriate fields
    cal.set(Calendar.DAY_OF_MONTH, 1);
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 1);
    return cal;
  }

  private static java.util.Calendar setLastSecondInMonth(java.util.Calendar origCal)
  {
    // create a calendar copy
    Calendar cal = Calendar.getInstance();
    cal.setTime(origCal.getTime());

    // set the appropriate fields
    cal.set(Calendar.DAY_OF_MONTH, origCal.getActualMaximum(Calendar.DAY_OF_MONTH));
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    return cal;
  }

  private static java.util.Calendar setFirstSecondInYear(java.util.Calendar origCal)
  {
    // create a calendar copy
    Calendar cal = Calendar.getInstance();
    cal.setTime(origCal.getTime());

    // set the appropriate fields
    cal.set(Calendar.DAY_OF_YEAR, 1);
    cal = setFirstSecondInMonth(cal);
    return cal;
  }

  private static java.util.Calendar setLastSecondInYear(java.util.Calendar origCal)
  {
    // create a calendar copy
    Calendar cal = Calendar.getInstance();
    cal.setTime(origCal.getTime());

    // set the appropriate fields
    cal.set(Calendar.DAY_OF_YEAR, origCal.getActualMaximum(Calendar.DAY_OF_YEAR));
    cal = setLastSecondInMonth(cal);
    return cal;
  }

  // -----------------------------------------------------------------------
  // Method:
  // getMonthDateRange
  /**
   * Description_of_what_method_does.
   * 
   * @see Method_related_to_this_method
   * 
   * @param month
   * @param year
   * @return
   **/
  public void setMonthDateRange(java.util.Calendar cal)
  {
    // create a date range, a full month
    ScatchCalendar = setFirstSecondInMonth(cal);
    FromDate = ScatchCalendar.getTime();

    ScatchCalendar = setLastSecondInMonth(cal);
    ToDate = ScatchCalendar.getTime();
  }

  /*-----------------------------------------------------------------------*/

  // -----------------------------------------------------------------------
  // Method:
  // getMonthDateRange
  /**
   * Description_of_what_method_does.
   * 
   * @see Method_related_to_this_method
   * 
   * @param month
   * @param year
   * @return
   **/
  public void setMonthDateRange(int month, int year)
  {
    // create a calendar that represents the desired month and year
    Calendar scratchCalendar = Calendar.getInstance();
    scratchCalendar.setTime(new Date());

    // create a date range, a full month
    scratchCalendar.set(Calendar.MONTH, month);
    scratchCalendar.set(Calendar.YEAR, year);
    setMonthDateRange(scratchCalendar);
  }

  /*-----------------------------------------------------------------------*/

  // Method:
  // getMonthDateRange
  /**
   * Description_of_what_method_does.
   * 
   * @see Method_related_to_this_method
   * 
   * @param month
   * @param year
   * @return
   **/
  public static Double[] getMonthDateRange(java.util.Calendar cal)
  {
    DateRange dr = new DateRange();
    dr.setMonthDateRange(cal);
    return dr.getDateRange();
  }

  /*-----------------------------------------------------------------------*/

  // -----------------------------------------------------------------------
  // Method:
  // getMonthDateRange
  /**
   * Description_of_what_method_does.
   * 
   * @see Method_related_to_this_method
   * 
   * @param month
   * @param year
   * @return
   **/
  public static Double[] getMonthDateRange(int month, int year)
  {
    DateRange dr = new DateRange();
    dr.setMonthDateRange(month, year);
    return dr.getDateRange();
  }

  /*-----------------------------------------------------------------------*/

  // -----------------------------------------------------------------------
  // Method:
  // getIntervalInMillis
  /**
   * Description_of_what_method_does.
   * 
   * @see Method_related_to_this_method
   * 
   * @param month
   * @param year
   * @return
   **/
  public static long getIntervalInMillis(java.util.Calendar fromDate, java.util.Calendar toDate)
  {
    long interval = 0;
    // number of milliseconds between the two dates
    if ((fromDate != null) && (toDate != null))
    {
      DateRange dr = new DateRange(fromDate, toDate);
      interval = dr.getIntervalInMillis();
    }
    return interval;
  }

  // -----------------------------------------------------------------------
  // Method:
  // getIntervalInMillis
  /**
   * Description_of_what_method_does.
   * 
   * @see Method_related_to_this_method
   * 
   * @param month
   * @param year
   * @return
   **/
  public long getIntervalInMillis()
  {
    long interval = 0;
    // number of milliseconds between the two dates
    if ((FromDate != null) && (ToDate != null))
    {
      interval = ToDate.getTime() - FromDate.getTime();
    }
    return interval;
  }

  // -----------------------------------------------------------------------
  // Method:
  // getInterval
  /**
   * Description_of_what_method_does.
   * 
   * @see Method_related_to_this_method
   * 
   * @param month
   * @param year
   * @return
   **/
  public static String getInterval(java.util.Calendar fromDate, java.util.Calendar toDate)
  {
    // number of milliseconds between the two dates
    if ((fromDate != null) && (toDate != null))
    {
      DateRange dr = new DateRange(fromDate, toDate);
      return dr.getInterval();
    }
    return null;
  }

  // -----------------------------------------------------------------------
  // Method:
  // getInterval
  /**
   * Description_of_what_method_does.
   * 
   * @see Method_related_to_this_method
   * 
   * @param month
   * @param year
   * @return
   **/
  public String getInterval()
  {
    long interval = getIntervalInMillis();
    // number of days, hours, minutes, seconds, millis
    Millis = interval % 1000;
    Secs = (interval / 1000) % 60;
    Mins = (interval / 60000) % 60;
    Hours = (interval / 3600000) % 24;
    Days = interval / 86400000;

    String intervalString = "";

    if (Days > 0)
      intervalString += Days + " Days, ";

    if (Hours > 0)
      intervalString += Hours + " Hours, ";

    if (Mins > 0)
      intervalString += Mins + " Mins, ";

    if (Secs > 0)
      intervalString += Secs + " Secs, ";

    if (Millis > 0)
      intervalString += Millis + " millisecs, ";

    return intervalString;

  }

  /*-----------------------------------------------------------------------*/

  // -----------------------------------------------------------------------
  // Method:
  // getYearDateRange
  /**
   * Description_of_what_method_does.
   * 
   * @see Method_related_to_this_method
   * 
   * @param month
   * @param year
   * @return
   **/
  public void setYearDateRange(java.util.Calendar cal)
  {
    // create a date range, a full month
    ScatchCalendar = setFirstSecondInYear(cal);
    FromDate = ScatchCalendar.getTime();

    ScatchCalendar = setLastSecondInYear(cal);
    ToDate = ScatchCalendar.getTime();
  }

  /*-----------------------------------------------------------------------*/

  // -----------------------------------------------------------------------
  // Method:
  // getYearDateRange
  /**
   * Description_of_what_method_does.
   * 
   * @see Method_related_to_this_method
   * 
   * @param month
   * @param year
   * @return
   **/
  public void setYearDateRange(int year)
  {
    // create a calendar that represents the desired year
    Calendar scratchCalendar = Calendar.getInstance();
    scratchCalendar.setTime(new Date());

    // create a date range, a full year
    scratchCalendar.set(Calendar.YEAR, year);
    setYearDateRange(scratchCalendar);
  }

  /*-----------------------------------------------------------------------*/

  // -----------------------------------------------------------------------
  // Method:
  // getYearDateRange
  /**
   * Description_of_what_method_does.
   * 
   * @see Method_related_to_this_method
   * 
   * @param month
   * @param year
   * @return
   **/
  public static Double[] getYearDateRange(java.util.Calendar cal)
  {
    DateRange dr = new DateRange();
    dr.setYearDateRange(cal);
    return dr.getDateRange();
  }

  /*-----------------------------------------------------------------------*/

  // -----------------------------------------------------------------------
  // Method:
  // getYearDateRange
  /**
   * Description_of_what_method_does.
   * 
   * @see Method_related_to_this_method
   * 
   * @param month
   * @param year
   * @return
   **/
  public static Double[] getYearDateRange(int year)
  {
    DateRange dr = new DateRange();
    dr.setYearDateRange(year);
    return dr.getDateRange();
  }

  /*-----------------------------------------------------------------------*/

  // -----------------------------------------------------------------------
  // Method:
  // getDateRange
  /**
   * Description_of_what_method_does.
   * 
   * @see Method_related_to_this_method
   * 
   * @param month
   * @param year
   * @return
   **/
  public Double[] getDateRange()
  {
    /*
     * Double fromBound = ApiDateUtils.date2Double(FromDate); Double toBound =
     * ApiDateUtils.date2Double(ToDate); Double[] dateRange = new
     * Double[]{fromBound, toBound};
     * 
     * return dateRange;
     */
    return null;
  }

  /*-----------------------------------------------------------------------*/

  // -----------------------------------------------------------------------
  // Method:
  // getFromDate
  /**
   * Description_of_what_method_does.
   * 
   * @see Method_related_to_this_method
   * 
   * @param month
   * @param year
   * @return
   **/
  public java.util.Calendar getFromDate()
  {
    Calendar scratchCalendar = Calendar.getInstance();
    scratchCalendar.setTime(FromDate);
    return scratchCalendar;
  }

  /*-----------------------------------------------------------------------*/

  // -----------------------------------------------------------------------
  // Method:
  // getFromDate
  /**
   * Description_of_what_method_does.
   * 
   * @see Method_related_to_this_method
   * 
   * @param month
   * @param year
   * @return
   **/
  public java.util.Calendar getToDate()
  {
    Calendar scratchCalendar = Calendar.getInstance();
    scratchCalendar.setTime(ToDate);
    return scratchCalendar;
  }

  /*-----------------------------------------------------------------------*/

  // -----------------------------------------------------------------------
  // Method:
  // getTime
  /**
   * Description_of_what_method_does.
   * 
   * @see Method_related_to_this_method
   * 
   * @param month
   * @param year
   * @return
   **/
  public java.util.Date getTime()
  {
    return ToDate;
  }

  /*-----------------------------------------------------------------------*/

  /**************************************************************************
   *** Method Name: toString
   **/
  /**
   *** Summary_Description_Of_What_toString_Does.
   *** <p>
   *** 
   *** @see Method_related_to_this_method
   *** 
   *** @param Parameter_name
   *          Description_of_method_parameter__Delete_if_none
   *** 
   *** @return Description_of_method_return_value__Delete_if_none
   *** 
   *** @throws Class_name
   *           Description_of_exception_thrown__Delete_if_none
   **************************************************************************/

  public String toString()
  {
    java.text.SimpleDateFormat fMonth = new java.text.SimpleDateFormat(IR_REPORT_DATE_FORMAT);
    String rtnString = "from " + fMonth.format(FromDate) + " to " + fMonth.format(ToDate);
    return rtnString;
  }

  public static void main(String[] args)
  {
    Calendar scratchCalendar1 = Calendar.getInstance();
    try
    {
      Thread.sleep(3000);
    }
    catch (Exception e)
    {
      // nop
    }
    Calendar scratchCalendar2 = Calendar.getInstance();
    DateRange dr = new DateRange(scratchCalendar1, scratchCalendar2);
    System.out.println("Date Interval is:(" + dr.getInterval() + ")");
  }

}
