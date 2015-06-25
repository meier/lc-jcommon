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
 *        file: TimeStamp.java
 *
 *  Created on: Jun 17, 2011
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.time;

import gov.llnl.lc.parser.ParserUtils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**********************************************************************
 * A object that represents a specific date and time, with a resolution
 * down to the microsecond.  Fundamentally a convenience class that
 * provides a consistent way to perform a variety of time related functions. 
 * <p>
 * @see  java.util.GregorianCalendar
 * @see  java.text.DateFormat
 *
 * @author meier3
 * 
 * @version Jun 17, 2011 3:06:38 PM
 **********************************************************************/
public class TimeStamp implements Serializable
{
  // date and time, with a resolution of microseconds
  //
  // base this on a calendar, and then extend the resolution
  
  
  // Feb 28 15:33:14 635756
  
  // time, to a resolution of microseconds
  
    /**  describe serialVersionUID here **/
  private static final long serialVersionUID = -6185633501492614739L;

  private SimpleTimeZone PacificDaylightTimeZone = null;
  
  /** date formatting for 'toString' method **/
    private java.text.DateFormat dateFormat =
      new java.text.SimpleDateFormat("MMM dd HH:mm:ss");

      /** date formatting for the osm_log **/
      static java.text.DateFormat logFormat =
        new java.text.SimpleDateFormat("MMM dd HH:mm:ss yyyy");

      /** date formatting for timestamps **/
       static java.text.DateFormat timestampFormat =
        new java.text.SimpleDateFormat("MMM dd HH:mm:ss yyyy");

    /** calendar object storing the UTC date **/
    private java.util.GregorianCalendar localCalendar = null;

    /** microseconds past the minute (min is 0, max is 999999 **/
    private int microSeconds = 0;
    
     private static final int TIMESTAMP_LEN = 20;
    private static final int MICROSECOND_POS = 16;

    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    /*~~~           Constructors                                          !!!*/
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

    //-------------------------------------------------------------------------
    // Method:
    //    TimeStamp
    /**
    * No argument constructor for class 'TimeStamp'.  This constructor should be
    * called by all other constructors for this class.
    **/
    public TimeStamp()
    {
      init(null);
    }

    //-------------------------------------------------------------------------
    // Method:
    //    TimeStamp
    /**
    * OSM Log Time constructor for class 'TimeStamp'.  This constructor creates a TimeStamp object when
    * provided a date string in a format specific to the opensm log.
    *
    * @see #getDateFromLogString
    **/
    public TimeStamp(String logTimeString)
    {
      init(logTimeString);
    }

      //-------------------------------------------------------------------------
      // Method:
      //    TimeStamp
      /**
      * OSM Time constructor for class 'TimeStamp'.  This constructor creates a TimeStamp object when
      * provided a long, as in file.lastModified()
      *
      **/
      public TimeStamp(long timeStamp)
      {
          init(null);
          this.setTimeInMillis(timeStamp);
      }


      //-------------------------------------------------------------------------
      // Method:
      //    TimeStamp
      /**
      * OSM Log Time constructor for class 'TimeStamp'.  This constructor creates a TimeStamp object when
      * provided a calendar object.  Calendar objects only have millisecond resolution.
      *
      * @see #getDateFromLogString
      **/
      public TimeStamp(java.util.GregorianCalendar calendar)
      {
          if(calendar != null)
          {
            this.localCalendar = calendar;
            this.dateFormat.setCalendar(this.localCalendar);
            // convert the milliseconds to micros
            this.microSeconds = this.localCalendar.get(Calendar.MILLISECOND) * 1000;
            // then zero the milliseconds, handled by micros now
            this.localCalendar.set(Calendar.MILLISECOND, 0);
          }
          else
              init(null);
      }


    //-------------------------------------------------------------------------
    // Method:
    //    init
    /**
    *
    * Most initialization functions necessary for the constructors are performed here.
  **/
    private void init(String logTimeString)
    {
      // specify a time zone using no daylight savings and GMT time (UTC)
//        java.util.SimpleTimeZone timeZone = new java.util.SimpleTimeZone(0, "UTC");
//        this.localCalendar = new java.util.GregorianCalendar(timeZone);
        
//   // get the supported ids for GMT-08:00 (Pacific Standard Time)
//      String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
//      // if no ids were returned, something is wrong. get out.
//      if (ids.length == 0)
//          System.exit(0);
//
//      // create a Pacific Standard Time time zone
//      PacificDaylightTimeZone = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);
//
//      // set up rules for daylight savings time
//      PacificDaylightTimeZone.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
//      PacificDaylightTimeZone.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
//
//      // create a GregorianCalendar with the Pacific Daylight time zone
//      // and the current date and time
//      this.localCalendar = new GregorianCalendar(PacificDaylightTimeZone);
//      
      // use current, and local time zone
        this.localCalendar = new java.util.GregorianCalendar();

//        /** date formatting for 'toString' method **/
//        dateFormat = new java.text.SimpleDateFormat("MMM dd HH:mm:ss");
//
//        /** date formatting for the osm_log **/
//        logFormat = new java.text.SimpleDateFormat("MMM dd HH:mm:ss yyyy");
//
//        /** date formatting for timestamps **/
//        timestampFormat = new java.text.SimpleDateFormat("MMM dd HH:mm:ss yyyy");
//
//      /** microseconds past the minute (min is 0, max is 999999 **/
//      microSeconds = 0;

      if((logTimeString == null) || (logTimeString.length() != TIMESTAMP_LEN))
      {
        this.timestampFormat.setCalendar(this.localCalendar);
        // convert the milliseconds to micros
        this.microSeconds = this.localCalendar.get(Calendar.MILLISECOND) * 1000;
        // then zero the milliseconds, handled by micros now
        this.localCalendar.set(Calendar.MILLISECOND, 0);
      }
      else
      {
        // assume the format "Mar 03 11:12:37 773070" and blow up otherwise
        this.setTime(getDateFromLogString(logTimeString, this.localCalendar));
        setDateFormat(timestampFormat);
        
        // set the microseconds (min 0, max 999999)
        // leading zeros are possible in the string, which causes havoc
        String micro = ParserUtils.trimLeadingZeros(logTimeString.substring(MICROSECOND_POS).trim());
        this.microSeconds = Integer.decode(micro);
        
//        this.microSeconds = this.microSeconds > 999999 ? 999999: this.microSeconds;
//        this.microSeconds = this.microSeconds < 0 ? 0: this.microSeconds;
     }
    }
    
    /************************************************************
     * Method Name:
     *  setPacificTimeZone
    **/
    /**
     * Converts the current timestamp to use the Pacific time zone
     * and attempts to adjust for daylight savings time.
     *
     * @see     java.util.TimeZone
     ***********************************************************/
    protected void setPacificTimeZone()
    {
      // get the supported ids for GMT-08:00 (Pacific Standard Time)
      String[] ids = TimeZone.getAvailableIDs(-8 * 60 * 60 * 1000);
      // if no ids were returned, something is wrong. get out.
      if (ids.length == 0)
          System.exit(0);

      // create a Pacific Standard Time time zone
      SimpleTimeZone pdt = new SimpleTimeZone(-8 * 60 * 60 * 1000, ids[0]);

      // set up rules for daylight savings time
      pdt.setStartRule(Calendar.APRIL, 1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);
      pdt.setEndRule(Calendar.OCTOBER, -1, Calendar.SUNDAY, 2 * 60 * 60 * 1000);

      // create a GregorianCalendar with the Pacific Daylight time zone
      // and the current date and time
      Calendar calendar = new GregorianCalendar(pdt);
      Date trialTime = new Date();
      calendar.setTime(trialTime);
    }

    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    /*~~~           Property Methods                                      !!!*/
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/


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
    private void setDateFormat(java.text.DateFormat format)
    {
      // Assign a clone because if the user of this method reuses the 'DateFormat'
      // for another 'TimeStamp', for some unknown reason all of the 'TimeStamp'
      // objects 'toString' methods return the time associated with the 1st
      // 'TimeStamp' to use the 'DateFormat' from then on.
      this.dateFormat = (java.text.DateFormat)format.clone();
      this.dateFormat.setCalendar(this.localCalendar);
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
      return this.localCalendar;
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
      return this.localCalendar.getTime();
    }

    //-----------------------------------------------------------------------
    // Method:
    //    setTime
    /**
    * Sets this 'TimeStamp' object's current time to the given date.
    *
    * @see      java.util.Calendar#setTime
    * @see      #setTimeInUtc
    *
    * @param    date  the 'Date' object to set as the current time
    **/
    public void setTime(java.util.Date date)
    {
      this.localCalendar.setTime(date);
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
      localCalendar.clear();

      // the month is 0-based (e.g., 0 for January)
      localCalendar.set(year, (month - 1), date, hour, minute, second);
    }

    //-----------------------------------------------------------------------
    // Method:
    //    getDateFromLogString
    /**
    * Sets the current UTC time from a Log Time string.
    * The format is
    *
    * @param logTimeString
    *
    * @throws java.text.ParseException  could not parse the date string
    **/
    public static java.util.Date getDateFromLogString(String logTimeString, java.util.GregorianCalendar localCalendar)
    {
      java.util.Date date = null;
      int year  = localCalendar.get(java.util.Calendar.YEAR);
      
        // strip off the microseconds, and replace with the current year
      String newTimeString = logTimeString.substring(0, MICROSECOND_POS) + year;

      try
      {
        synchronized(logFormat)
        {
          date = logFormat.parse(newTimeString);
        }
      }
      catch (java.text.ParseException e)
      {
      }
      return date;
    }

    private void setMicroSeconds(int microSeconds) {
    this.microSeconds = microSeconds;
  }

  public int getMicroSeconds() {
    return microSeconds;
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
      
      return localCalendar == null ? 0 : localCalendar.getTimeInMillis();
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
      if(localCalendar == null)
        init(null);
      localCalendar.setTimeInMillis(millis);
    }


    //-----------------------------------------------------------------------
    // Method:
    //            getTimeInSeconds
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
      return this.localCalendar.getTimeInMillis() / 1000;
    }

    //-----------------------------------------------------------------------
    // Method:
    //            setTimeInSeconds
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
      this.localCalendar.setTimeInMillis(1000 * seconds);
    }

    //-----------------------------------------------------------------------
    // Method:
    //            getTimeInUtc
    /**
    * Returns a 'Date' object with the time values set to the values of this
    * 'TimeStamp' ignoring the fact that the date is in the local time zone.  This
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
    *         'TimeStamp' independent of the actual time zone
    **/
    public java.util.Date getTimeInUtc()
    {
      // specify a time zone using no daylight savings and GMT time (UTC)
      java.util.SimpleTimeZone timeZone = new java.util.SimpleTimeZone(0, "UTC");

      // calendar for extracting value for UTC time
      java.util.GregorianCalendar calendar = new java.util.GregorianCalendar(timeZone);

      // the date treated as a utc date
      java.util.Date utcDate;

      // clear all the time fields, in particular the milliseconds
      calendar.clear();

      calendar.set
      (
        this.localCalendar.get(java.util.Calendar.YEAR),
        this.localCalendar.get(java.util.Calendar.MONTH),
        this.localCalendar.get(java.util.Calendar.DATE),
        this.localCalendar.get(java.util.Calendar.HOUR),
        this.localCalendar.get(java.util.Calendar.MINUTE),
        this.localCalendar.get(java.util.Calendar.SECOND)
      );

      utcDate = calendar.getTime();

      return utcDate;
    }

    //-----------------------------------------------------------------------
    // Method:
    //    setTimeInUtc
    /**
    * Sets this 'TimeStamp' object's current time to the given date treated as
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
      this.localCalendar.add(java.util.Calendar.HOUR, hours);
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
      // convert to microseconds, then add
      this.addMicroseconds(milliseconds * 1000);
    }

    //-----------------------------------------------------------------------
    // Method:
    //    addMicroseconds
    /**
    * Adds the specified number of microseconds to the UTC time.
    *
    * @see #addMinutes
    * @see #addSeconds
    * @see #addHours
    * @see java.util.GregorianCalendar#add
    *
    * @param milliseconds  amount of time in milliseconds to add
    **/
    public void addMicroseconds(int microseconds)
    {
      int newMicro = this.getMicroSeconds() + microseconds;
      this.addSeconds(newMicro/1000000);
      this.setMicroSeconds(newMicro%1000000);
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
      this.localCalendar.add(java.util.Calendar.MINUTE, minutes);
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
      this.localCalendar.add(java.util.Calendar.SECOND, seconds);
    }

    //-----------------------------------------------------------------------
    // Method:
    //    after
    /**
    * Compares this 'TimeStamp' object with another 'TimeStamp' object to see if
    * it is after the specified object.
    *
    * @see      #before
    *
    * @param    when  the 'TimeStamp' to compare with this 'TimeStamp'
    *
    * @return   true if this 'TimeStamp' is after the 'when' 'TimeStamp'
    **/
    public boolean after(TimeStamp when)
    {
      // true is compare returns more than 0
        return (this.compareTo(when) > 0);
    }

    //-----------------------------------------------------------------------
    // Method:
    //    before
    /**
    * Compares this 'TimeStamp' object with another 'TimeStamp' object to see if
    * it is before the specified object.
    *
    * @see      #after
    *
    * @param    when  the 'TimeStamp' to compare with this 'TimeStamp'
    *
    * @return   true if this 'TimeStamp' is before the 'when' 'TimeStamp'
    **/
    public boolean before(TimeStamp when)
    {
      //true if compare returns less than 0
      return (this.compareTo(when) < 0);
    }

    //-----------------------------------------------------------------------
    // Method:
    //            compareTo
    /**
    * Compares two 'TimeStamp' objects for ordering.
    *
    * @see      java.util.Date#compareTo
    *
    * @param    when  'TimeStamp' object to compare with this 'TimeStamp'
    *
    * @return   0 if the argument 'when' is equal;
    *           a value less than 0 if this 'TimeStamp' is before 'when';
    *           a value greater than 0 if this 'TimeStamp' is after 'when'
    **/
    public int compareTo(TimeStamp when)
    {
      if(when == null)
        throw new NullPointerException();
      
      int rtrnval = this.localCalendar.getTime().compareTo( when.localCalendar.getTime() );
      
      return (rtrnval != 0) ? rtrnval: Integer.signum(this.getMicroSeconds() - when.getMicroSeconds());
    }

    //-----------------------------------------------------------------------
    // Method:
    //            compoundTimeString
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
    //            equals
    /**
    * Returns true if the UTC time in milliseconds from the epoch
    * (00:00:00 GMT on January 1, 1970) are equal, and if the microseconds
    * are equal.
    *
    * @param    utc   the 'TimeStamp' object to compare with this 'TimeStamp'
    *
    * @return   true if the UTC times are equal and microseconds
    **/
    @Override
    public boolean equals(Object obj) {
      return ((obj != null) && (obj instanceof TimeStamp) && (this.compareTo((TimeStamp)obj)==0));
    }

      //-----------------------------------------------------------------------
      // Method:
      //            getContent
      //
      // Non-Javadoc Header
      //
      // Prints the Timestamp in a form identical to that found in the log file
      //
      // @see java.lang.Object#toString
      //
      // @return  timestamp in log form
    public String getContent() 
    {
      // attempt to mimic the original output (primarily, provide leading zeros)
      String timeStr = null;
      synchronized(this.dateFormat)
      {
        timeStr = this.dateFormat.format( this.localCalendar.getTime());
      }
          return timeStr + " " + String.format("%06d", this.getMicroSeconds());
    }

    //-----------------------------------------------------------------------
    // Method:
    //            toString
    //
    // Non-Javadoc Header
    //
    // Prints a human readable version of the timestamp.
    //
    // @see java.lang.Object#toString
    //
    // @return  time in GMT
    public String toString()
    {
      String timeStr = "Uninitialized TimeStamp";
      if(localCalendar != null)
      {
        synchronized(TimeStamp.timestampFormat)
        {
          timeStr = TimeStamp.timestampFormat.format( this.localCalendar.getTime() );
        }
      }
      return timeStr;
    }

    //-----------------------------------------------------------------------
    // Method:
    //            main
    /**
    * Testing of the 'TimeStamp' class.
    *
    * @param args   command line arguments - not being used
    **/
    public static void main(String[] args)
    {
//    TimeStamp duplicate = new TimeStamp("Mar 03 11:12:32 773070");      // make with the same time as 'utc'
        TimeStamp duplicate = new TimeStamp();      // make with the same time as 'utc'
      int     errorCount = 0;                 // number of errors detected
      TimeStamp fifteenAfter = new TimeStamp("Mar 03 11:12:32 000003");   // ~ 15 minutes after current
      TimeStamp secondAfter = new TimeStamp("Mar 03 11:12:32 773070");    // ~ 1 second after current
      TimeStamp tenBefore = new TimeStamp("Mar 03 11:12:32 773070");      // ~ 10 minutes before current
      TimeStamp utc = new TimeStamp("Mar 03 11:12:32 773070");

      tenBefore.addSeconds(-10*60);
      duplicate.setTimeInMillis( utc.getTimeInMillis() );
      secondAfter.addMilliseconds(1000);
      fifteenAfter.addMinutes(15);
      fifteenAfter.addMicroseconds(123456);
      
      duplicate.setTime(new Date());


        System.out.println("Current UTC time is " + duplicate);
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
//      The writeObject method is responsible for writing the state of the object for its particular class so that the corresponding readObject method can restore it.
       out.writeLong(this.getTimeInMillis());
    }
  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException
  {
  //The readObject method is responsible for reading from the stream and restoring the classes fields
    long timeInMillis = in.readLong();
    this.setTimeInMillis(timeInMillis);
  }
  

}
