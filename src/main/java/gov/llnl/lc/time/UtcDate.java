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
 *        file: UtcDate.java
 *
 *  Created on: Jun 29, 2011
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.time;

import java.io.Serializable;
import java.util.logging.Level;

/**********************************************************************
 * Describe purpose and responsibility of UtcDate
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Jun 29, 2011 4:01:09 PM
 **********************************************************************/
public class UtcDate implements Serializable
{
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~           Static Finals                                         !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~           Class Variables                                       !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~           Instance Variables                                    !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

  /**
   * Comment for <code>serialVersionUID</code>
   */
  private static final long serialVersionUID = 3256720701762255160L;

  /** logger for the class **/
  private static java.util.logging.Logger classLogger =
    java.util.logging.Logger.getLogger( "gov.llnl.lc.time.UtcDate" );

  /** date format for writing to or reading from the database **/
  static java.text.SimpleDateFormat databaseFormat =
    new java.text.SimpleDateFormat("yyyyMMddHHmmss");

  /** date format returned from NIST **/
  static java.text.SimpleDateFormat nistTimeFormat =
    new java.text.SimpleDateFormat("yy-MM-dd HH:mm:ss SSS z");

  /** date formatting for 'toString' method **/
  private java.text.DateFormat dateFormat =
    new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
      
  /** calendar object storing the UTC date **/
  private java.util.GregorianCalendar utcCalendar = null;

  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~           Constructors                                          !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

  //-------------------------------------------------------------------------
  // Method:
  //    UtcDate
  /**
  * No argument constructor for class 'UtcDate'.  This constructor should be
  * called by all other constructors for this class.
  **/
  public UtcDate()
  {
    init(null);
  }

  //-------------------------------------------------------------------------
  // Method:
  //    UtcDate
  /**
  * Nist Time constructor for class 'UtcDate'.  This constructor creates a UtcDate object when
  * provided a date string in a format specific to NIST.
*    The format of the time returned follows, both in its symbolic form and with a real value.
*    The most important pieces of information are at the start, including the Modified Julian Date,
*    two digits each for year, month, and day, along with the time in hours, minutes, and seconds. 

*    JJJJJ YR-MO-DA HH:MM:SS TT L H msADV UTC(NIST) OTM
*    52453 02-06-28 07:41:36 50 0 0 872.9 UTC(NIST) *
  *
  * @see #getDateFromNistString
  **/
  public UtcDate(String nistTimeString)
  {
    init(nistTimeString);
  }
  
  //-------------------------------------------------------------------------
  // Method:
  //    UtcDate
  /**
  * Nist Time constructor for class 'UtcDate'.  This constructor creates a UtcDate object when
  * provided a NistTime object
  *
  * @see #getNistTimeString
  **/
  public UtcDate(NistTime nt)
  {
    String nistTimeString = null;
    if(nt != null)
      nistTimeString = nt.getNistTimeString();
    init(nistTimeString);
  }
  
  //-------------------------------------------------------------------------
  // Method:
  //    init
  /**
  *
  * Most initialization functions necessary for the constructors are performed here.
**/
  private void init(String nistTimeString)
  {
    // specify a time zone using no daylight savings and GMT time (UTC)
    java.util.SimpleTimeZone timeZone = new java.util.SimpleTimeZone(0, "UTC");
      
    this.utcCalendar = new java.util.GregorianCalendar(timeZone);
    
    if(nistTimeString == null)
      this.dateFormat.setCalendar(this.utcCalendar);
    else
    {
      this.setTime(getDateFromNistString(nistTimeString));
      setDateFormat(dateFormat);

   }

    databaseFormat.setCalendar(this.utcCalendar);
  }

  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~           Property Methods                                      !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

  //-----------------------------------------------------------------------
  // Method:
  //    getDatabaseSelectString
  /**
  * Returns a date string suitable for creating a select SQL statement.
  * The string is of the format to_char({column name}, 'YYYYMMDDHH24MISS').
  * This is particularly useful for refering to a date in an SQL statement
  * because if you used a prepared statement and use 'setDate()' to bind
  * a date to a parameter, the hours, minutes and seconds are lost. 
  *
  * @see #getDatabaseString
  * 
  * @return   date string suitable for creating a select SQL statement
  **/
  public static String getDatabaseSelectString(String columnName)
  {
    return "to_char(" + columnName + ", 'YYYYMMDDHH24MISS')";
  }
  
  //-----------------------------------------------------------------------
  // Method:
  //    getDatabaseString
  /**
  * Returns a date string suitable for creating an insert or update
  * SQL statement.
  * The string is of the format to_date({date}, 'YYYYMMDDHH24MISS').
  * This is particularly useful for refering to a date in an SQL statement
  * because if you used a prepared statement and use 'setDate()' to bind
  * a date to a parameter, the hours, minutes and seconds are lost. 
  *
  * @see #getDatabaseSelectString
  * 
  * @return date string suitable for creating an insert or update SQL statement
  **/
  public String getDatabaseString()
  {
    return "to_date(" +
      databaseFormat.format( this.utcCalendar.getTime() ) +
      ", 'YYYYMMDDHH24MISS')";
  }
  
  //-----------------------------------------------------------------------
  // Method:
  //    getDateFormat
  /**
  * Returns the 'DateFormat' object being used by the 'toString' method.
  *
  * @see      #setDateFormat
  * @see      #toString
  *
  * @return   the 'DateFormat' object being used
  **/
  public java.text.DateFormat getDateFormat()
  {
    return this.dateFormat;
  }

  //-----------------------------------------------------------------------
  // Method:
  //    setDateFormat
  /**
  * Sets the 'DateFormat' object being used by the 'toString' method.
  *
  * @see      #getDateFormat
  * @see      #toString
  *
  * @param format   the 'DateFormat' object being used
  **/
  public void setDateFormat(java.text.DateFormat format)
  {
    // Assign a clone because if the user of this method reuses the 'DateFormat'
    // for another 'UtcDate', for some unknown reason all of the 'UtcDate'
    // objects 'toString' methods return the time associated with the 1st
    // 'UtcDate' to use the 'DateFormat' from then on. 
    this.dateFormat = (java.text.DateFormat)format.clone();
    this.dateFormat.setCalendar(this.utcCalendar);
  }

  //-----------------------------------------------------------------------
  // Method:
  //    getSqlDate
  /**
  * Returns a 'java.sql.Date' representation of this 'UtcDate'.
  *
  * @return   'java.sql.Date' representation of this 'UtcDate'
  **/
  public java.sql.Date getSqlDate()
  {
    return new java.sql.Date( getTimeInMillis() );
  }
  
  //-----------------------------------------------------------------------
  // Method:
  //    getCalendar
  /**
  * Returns the current time in the local time zone.
  *
  * @see      java.util.Calendar#getTime
  *
  * @return   the current UTC date and time
  **/
  public java.util.Calendar getCalendar()
  {
    return this.utcCalendar;
  }

  //-----------------------------------------------------------------------
  // Method:
  //    getTime
  /**
  * Returns the current time in the local time zone.
  *
  * @see      java.util.Calendar#getTime
  *
  * @return   the current UTC date and time
  **/
  public java.util.Date getTime()
  {
    return this.utcCalendar.getTime();
  }

  //-----------------------------------------------------------------------
  // Method:
  //    setTime
  /**
  * Sets this 'UtcDate' object's current time to the given date.
  *
  * @see      java.util.Calendar#setTime
  * @see      #setTimeInUtc
  *
  * @param    date  the 'Date' object to set as the current time
  **/
  public void setTime(java.util.Date date)
  {
    this.utcCalendar.setTime(date);
  }

  //-----------------------------------------------------------------------
  // Method:
  //    setTime
  /**
  * Sets the values for the fields year, month, date, hour, minute and second.
  *
  * @param year     the year time field
  * @param month    the month time field
  * @param date     the date time field
  * @param hour     the hour of day time field
  * @param minute   the minute time field
  * @param second   the second time field
  **/
  public void setTime
  (
    int year,
    int month,
    int date,
    int hour,
    int minute,
    int second
  )
  {
    // clear all the time fields, in particular the milliseconds
    utcCalendar.clear();
    
    // the month is 0-based (e.g., 0 for January)
    utcCalendar.set(year, (month - 1), date, hour, minute, second);
  }

  //-----------------------------------------------------------------------
  // Method:
  //    getDateFromNistString
  /**
  * Sets the current UTC time from a Nist Time string.
  * The format is 
  * 'SimpleDateFormat' 'yyyyMMddHHmmss'.
//    The format of the time returned follows, both in its symbolic form and with a real value.
//    The most important pieces of information are at the start, including the Modified Julian Date,
//    two digits each for year, month, and day, along with the time in hours, minutes, and seconds. 

//    JJJJJ YR-MO-DA HH:MM:SS TT L H msADV UTC(NIST) OTM
//    52453 02-06-28 07:41:36 50 0 0 872.9 UTC(NIST) *
  *
  * @param nistTimeString
  * 
  * @throws java.text.ParseException  could not parse the date string
  **/
  public static java.util.Date getDateFromNistString(String nistTimeString)
  {
    java.util.Date date = null;
    
    //    throw away the Juliean date and everything after the decimal point
//    System.out.println(nistTimeString);
    
    int beginIndex = nistTimeString.indexOf(" ") + 1;   // the first space
//    int endIndex   = nistTimeString.indexOf(" ", beginIndex + 10);        // the third space
//    int endIndex  = nistTimeString.indexOf(" 50 0 0 ");  // this is 8 chars long
    int endIndex  = nistTimeString.indexOf(" 0 0 ") - 3;  // this is 8 chars long
    int pointIndex  = nistTimeString.indexOf(".");        // the first period    

    String parsableTime = nistTimeString.substring(beginIndex,endIndex);
    beginIndex =  endIndex + 8;
//    beginIndex = nistTimeString.indexOf(" ", pointIndex - 5) + 1;
    endIndex = nistTimeString.indexOf(".");
    String msTime = nistTimeString.substring(beginIndex, endIndex);
//    System.out.println("(" + parsableTime + ")");
//    System.out.println("(" + msTime + ")");
    String newTime = new String(parsableTime + " " + msTime + " " + "UTC");
//    System.out.println("(" + newTime + ")");

    try
    {
      synchronized(nistTimeFormat)
      {
        date = nistTimeFormat.parse(newTime);        
      }
    }
    catch (java.text.ParseException e)
    {
      if ( classLogger.isLoggable(Level.WARNING) )
      {
        classLogger.warning("Error parsing date.\n");
        classLogger.warning(e + "\n");
      }
    }
    return date;
  }
  //-----------------------------------------------------------------------
  // Method:
  //    setTimeFromDatabaseString
  /**
  * Sets the current UTC time from a database string.
  * The format is the Oracle format 'YYYYMMDDHH24MISS' or java
  * 'SimpleDateFormat' 'yyyyMMddHHmmss'.
  *
  * @param databaseString
  * 
  * @throws java.text.ParseException  could not parse the date string
  **/
  public void setTimeFromDatabaseString(String databaseString) throws
    java.text.ParseException
  {
    java.util.Date date = null;
    try
    {
      synchronized(databaseFormat)
      {
        date = databaseFormat.parse(databaseString);
      }
      setTime( date );
    }
    catch (java.text.ParseException e)
    {
      if ( classLogger.isLoggable(Level.WARNING) )
      {
        classLogger.warning("Error parsing date.\n");
        classLogger.warning(e + "\n");
      }
      throw e;
    }
  }
  
  //-----------------------------------------------------------------------
  // Method:
  //    getTimeInMillis
  /**
  * Returns the UTC time in milliseconds from the epoch (00:00:00 GMT on
  * January 1, 1970).
  *
  * @see      #setTimeInMillis
  * @see      java.util.Calendar#getTimeInMillis
  *
  * @return   UTC time in milliseconds from the epoch
  **/
  public long getTimeInMillis()
  {
    return this.utcCalendar.getTimeInMillis();
  }

  //-----------------------------------------------------------------------
  // Method:
  //    setTimeInMillis
  /**
  * Sets the UTC time in milliseconds from the epoch (00:00:00 GMT on
  * January 1, 1970).
  *
  * @see      #getTimeInMillis
  * @see      java.util.Calendar#setTimeInMillis
  *
  * @param    millis  UTC time in milliseconds from the epoch
  **/
  public void setTimeInMillis(long millis)
  {
    if(utcCalendar == null)
      init(null);
    utcCalendar.setTimeInMillis(millis);
  }
  
  //-----------------------------------------------------------------------
  // Method:
  //    getTimeInSeconds
  /**
  * Returns the UTC time in seconds from the epoch (00:00:00 GMT on
  * January 1, 1970).
  *
  * @see      #setTimeInSeconds
  * @see      java.util.Calendar#getTimeInMillis
  *
  * @return   UTC time in seconds from the epoch
  **/
  public long getTimeInSeconds()
  {
    return this.utcCalendar.getTimeInMillis() / 1000;
  }
  
  //-----------------------------------------------------------------------
  // Method:
  //    setTimeInSeconds
  /**
  * Sets the UTC time in seconds from the epoch (00:00:00 GMT on
  * January 1, 1970).
  *
  * @see #getTimeInSeconds
  * @see java.util.Calendar#setTimeInMillis
  *
  * @param seconds  UTC time in seconds from the epoch
  **/
  public void setTimeInSeconds(long seconds)
  {
    this.utcCalendar.setTimeInMillis(1000 * seconds);
  }
  
  //-----------------------------------------------------------------------
  // Method:
  //    getTimeInUtc
  /**
  * Returns a 'Date' object with the time values set to the values of this
  * 'UtcDate' ignoring the fact that the date is in the local time zone.  This
  * is useful for use in graphics displays and for using standard date editors
  * who use the 'Date' class, but don't incorporate 'Calendar' to establish
  * the time zone.
  *
  * @see #setTimeInUtc
  * @see #getDatabaseSelectString
  * @see #getDatabaseString
  * @see #getTime
  * @see #getTimeInMillis
  * @see #getTimeInSeconds
  *
  * @return 'Date' object with the time values set to the values of this
  *         'UtcDate' independent of the actual time zone
  **/
  public java.util.Date getTimeInUtc()
  {
    // specify a time zone using no daylight savings and GMT time (UTC)
    java.util.SimpleTimeZone timeZone = new java.util.SimpleTimeZone(0, "UTC");
      
    // calendar for extracting value for UTC time
    java.util.GregorianCalendar calendar = new java.util.GregorianCalendar(timeZone);
//    java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
    
    // the date treated as a utc date
    java.util.Date utcDate;
    
    // clear all the time fields, in particular the milliseconds
    calendar.clear();
    
    calendar.set
    (
      this.utcCalendar.get(java.util.Calendar.YEAR),
      this.utcCalendar.get(java.util.Calendar.MONTH),
      this.utcCalendar.get(java.util.Calendar.DATE),
      this.utcCalendar.get(java.util.Calendar.HOUR),
      this.utcCalendar.get(java.util.Calendar.MINUTE),
      this.utcCalendar.get(java.util.Calendar.SECOND)
    );

    utcDate = calendar.getTime();
    
    return utcDate;
  }
  
  //-----------------------------------------------------------------------
  // Method:
  //    setTimeInUtc
  /**
  * Sets this 'UtcDate' object's current time to the given date treated as
  * a UTC date & time, even though it is a local date.  This is useful for
  * interpreting a UTC date read from a database using JDBC and for using
  * standard date editors and graphic displays that use the 'Date' class, but
  * don't incorporate 'Calendar' to establish a timezone.
  *
  * @see #getTimeInUtc
  * @see #setTime
  * @see #setTimeFromDatabaseString
  * @see #setTimeInMillis
  *
  * @param    date  the 'Date' object to set as the current time
  **/
  public void setTimeInUtc(java.util.Date date)
  {
    // calendar for extracting value for UTC time
    java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();
    
    calendar.setTime(date);
    
    setTime
    (
      calendar.get(java.util.Calendar.YEAR),
      calendar.get(java.util.Calendar.MONTH) + 1,     // months start at zero
      calendar.get(java.util.Calendar.DAY_OF_MONTH),
      calendar.get(java.util.Calendar.HOUR_OF_DAY),
      calendar.get(java.util.Calendar.MINUTE),
      calendar.get(java.util.Calendar.SECOND)
    );
  }

  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~           Event Methods                                         !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~           General Methods                                       !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  
  //-----------------------------------------------------------------------
  // Method:
  //    addHours
  /**
  * Adds the specified number of hours to the UTC time.
  *
  * @see #addMilliseconds
  * @see #addMinutes
  * @see #addSeconds
  * @see java.util.GregorianCalendar#add
  *
  * @param hours  amount of time in hours to add
  **/
  public void addHours(int hours)
  {
    this.utcCalendar.add(java.util.Calendar.HOUR, hours);
  }

  //-----------------------------------------------------------------------
  // Method:
  //    addMilliseconds
  /**
  * Adds the specified number of milliseconds to the UTC time.
  *
  * @see #addMinutes
  * @see #addSeconds
  * @see #addHours
  * @see java.util.GregorianCalendar#add
  *
  * @param milliseconds  amount of time in milliseconds to add
  **/
  public void addMilliseconds(int milliseconds)
  {
    this.utcCalendar.add(java.util.Calendar.MILLISECOND, milliseconds);
  }

  //-----------------------------------------------------------------------
  // Method:
  //    addMinutes
  /**
  * Adds the specified number of minutes to the UTC time.
  *
  * @see #addMilliseconds
  * @see #addSeconds
  * @see #addHours
  * @see java.util.GregorianCalendar#add
  *
  * @param minutes  amount of time in minutes to add
  **/
  public void addMinutes(int minutes)
  {
    this.utcCalendar.add(java.util.Calendar.MINUTE, minutes);
  }

  //-----------------------------------------------------------------------
  // Method:
  //    addSeconds
  /**
  * Adds the specified number of seconds to the UTC time.
  *
  * @see #addMilliseconds
  * @see #addSeconds
  * @see #addMinutes
  * @see #addHours
  * @see java.util.GregorianCalendar#add
  *
  * @param seconds  amount of time in seconds to add
  **/
  public void addSeconds(int seconds)
  {
    this.utcCalendar.add(java.util.Calendar.SECOND, seconds);
  }

  //-----------------------------------------------------------------------
  // Method:
  //    after
  /**
  * Compares this 'UtcDate' object with another 'UtcDate' object to see if
  * it is after the specified object.
  *
  * @see      #before
  *
  * @param    when  the 'UtcDate' to compare with this 'UtcDate'
  * 
  * @return   true if this 'UtcDate' is after the 'when' 'UtcDate'
  **/
  public boolean after(UtcDate when)
  {
    return this.utcCalendar.after(when.utcCalendar);
  }

  //-----------------------------------------------------------------------
  // Method:
  //    before
  /**
  * Compares this 'UtcDate' object with another 'UtcDate' object to see if
  * it is before the specified object.
  *
  * @see      #after
  *
  * @param    when  the 'UtcDate' to compare with this 'UtcDate'
  * 
  * @return   true if this 'UtcDate' is before the 'when' 'UtcDate'
  **/
  public boolean before(UtcDate when)
  {
    return this.utcCalendar.before(when.utcCalendar);
  }

  //-----------------------------------------------------------------------
  // Method:
  //    compareTo
  /**
  * Compares two 'UtcDate' objects for ordering.
  *
  * @see      java.util.Date#compareTo
  *
  * @param    when  'UtcDate' object to compare with this 'UtcDate'
  * 
  * @return   0 if the argument 'when' is equal;
  *           a value less than 0 if this 'UtcDate' is before 'when';
  *           a value greater than 0 if this 'UtcDate' is after 'when'   
  **/
  public int compareTo(UtcDate when)
  {
    return
      this.utcCalendar.getTime().compareTo( when.utcCalendar.getTime() );
  }
  
  //-----------------------------------------------------------------------
  // Method:
  //    compoundTimeString
  /**
  * Produces a compound time string (hours, minutes, seconds) from a time
  * interval in milliseconds.
  *
  * @param milliSeconds   time interval in milliseconds
  * 
  * @return the compound time string
  **/
  public static String compoundTimeString(long milliSeconds)
  {
    // hours portion
    long hours;
    
    // minutes portion
    long minutes;
    
    // seconds portion
    double seconds;
    
    hours = milliSeconds / 3600000;
    milliSeconds %= 3600000;
    minutes = milliSeconds / 60000;
    milliSeconds %= 60000;
    seconds = milliSeconds / 1000.0;
    
    return hours + " hours " + minutes + " minutes " + seconds + " seconds";
  }
  
  //-----------------------------------------------------------------------
  // Method:
  //    equals
  /**
  * Returns true if the UTC time in milliseconds from the epoch
  * (00:00:00 GMT on January 1, 1970) are equal.
  *
  * @param    utc   the 'UtcDate' object to compare with this 'UtcDate'
  * 
  * @return   true if the UTC times are equal
  **/
  public boolean equals(UtcDate utc)
  {
    return this.getTimeInMillis() == utc.getTimeInMillis();
  }
  
  //-----------------------------------------------------------------------
  // Method:
  //    toString
  //
  // Non-Javadoc Header
  //
  // Prints a human readable version of the time in GMT.
  //
  // @see java.lang.Object#toString
  //
  // @return  time in GMT
  public String toString()
  {
      String timeStr = "Uninitialized UtcDate";
      if(this.utcCalendar != null)
      {
        synchronized(this.dateFormat)
        {
          timeStr = this.dateFormat.format( this.utcCalendar.getTime() );
        }
      }
      return timeStr;
  }
  
  //-----------------------------------------------------------------------
  // Method:
  //    main
  /**
  * Testing of the 'UtcDate' class.
  *
  * @param args   command line arguments - not being used
  **/
  public static void main(String[] args)
  {
    UtcDate duplicate = new UtcDate();      // make with the same time as 'utc'
    int     errorCount = 0;                 // number of errors detected
    UtcDate fifteenAfter = new UtcDate();   // ~ 15 minutes after current
    UtcDate secondAfter = new UtcDate();    // ~ 1 second after current
    UtcDate tenBefore = new UtcDate();      // ~ 10 minutes before current
    UtcDate utc = new UtcDate();
    
    tenBefore.addSeconds(-10*60);
    duplicate.setTimeInMillis( utc.getTimeInMillis() );
    secondAfter.addMilliseconds(1000);
    fifteenAfter.addMinutes(15);
    
    System.out.println("Current UTC time is " + utc);

    System.out.println
    (
      "The UTC count in milliseconds is " + utc.getTimeInMillis()
    );
    
    System.out.println("Approximately 10 minutes before = " + tenBefore);
    System.out.println("Approximately 1 second after = " + secondAfter);
    
    System.out.println
    (
      "Approximately 15 minutes afer = " + fifteenAfter + "\n"
    );

    if ( utc.after(fifteenAfter) )
    {
      ++errorCount;
      System.out.println("Error using method 'after'.");
    }
    
    if ( utc.before(tenBefore) )
    {
      ++errorCount;
      System.out.println("Error using method 'before'.");
    }
    
    if ( (utc.compareTo(tenBefore) <= 0) ||
         (utc.compareTo(duplicate) != 0) ||
         (utc.compareTo(fifteenAfter) >= 0)
       )
    {
      ++errorCount;
      System.out.println("Error using method 'compareTo'."); 
    }
    
    if (utc.equals(duplicate) == false)
    {
      ++errorCount;
      System.out.println("Error using method 'equal'.  Should be equal.");
    }
    
    if ( utc.equals(tenBefore) )
    {
      ++errorCount;
      System.out.println("Error using method 'equal'.  Should not be equal.");
    }
    
    System.out.println
    (
      "\nNumber of automatically detected errors = " + errorCount
    );
  }
  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException
  {
//    The writeObject method is responsible for writing the state of the object for its particular class so that the corresponding readObject method can restore it.    
     out.writeLong(this.getTimeInMillis());
  }
private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException
{
//The readObject method is responsible for reading from the stream and restoring the classes fields
  long timeInMillis = in.readLong();
  this.setTimeInMillis(timeInMillis);
}

}
