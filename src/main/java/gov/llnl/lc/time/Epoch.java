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
 *        file: Epoch.java
 *
 *  Created on: Jun 29, 2011
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.time;

import gov.llnl.lc.logging.CommonLogger;

import java.text.ParseException;
import java.util.Date;

/**********************************************************************
*** The Epoch class is a little utility helper class that can be used for
*** converting between Integers that represent Time, and Time Strings.  Refer to
*** the <code>usage()</code> method for details of use at the command line.
*** <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Jun 29, 2011 3:58:42 PM
 **********************************************************************/
public class Epoch implements CommonLogger
{
  /** The primary software version number, incremented manually, as deemed appropriate
   *** for the degree of cummulative change.
   **/
     static String Version  = "0.90";

   /** A secondary software version number maintained by the version control system,
   *** which incrementally grows independent of the version number. Check out or
   *** touch the file to force a change.
   **/  
     static String Revision = "$Revision$";

   /** The date of this version or build, which is maintained by the version control
   *** system, and reflects the last modification time.
   **/  
     static String RevDate = "$Date$";
     
     static String FILE_CONFIG_EXT = "cnfg";
     
     // if true, then time is date is to be in UTC, if false then PST
     static boolean UTC_FLAG = false;
     
     // if true, then time on command line is specified as millisecs from epoch
     // if flase, then time on command line is specified as a date, with time zone
     //           specified by the UTC_FLAG condition
     static boolean EPOCH_FLAG = true;
     
     static boolean ACQUIRE_FLAG = false;
     
     static int MAX_NUM_TIMES = 10;
     static int NumStrings = 0;    
     static String[] TimeArray = new String[MAX_NUM_TIMES];

     /** date formatting for 'toString' method **/
     private static java.text.DateFormat localDateFormat =
       new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

     /** date formatting for 'toString' method **/
     private java.text.DateFormat dateFormat =
       new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

   public Epoch()
   {
     this(null);
   }

   public Epoch(java.io.File configFile)
   {
     super();
     
     // do something with the configFile....
     
     // if there is an array of times, then convert them...
     
     doWork(NumStrings, TimeArray);
   }

   /**
    * @param args
    */
   private void doWork(int num, String[] times)
   {
     // determine the desired date format
     // want in simple date format, but must determine time zone
     if(UTC_FLAG)
     {
       // universal time coordinate
       this.dateFormat = new UtcDate().getDateFormat();
     }
     else
     {
       // local time
       this.dateFormat = Epoch.localDateFormat;          
     }
     
     if(ACQUIRE_FLAG)
     {
       UtcDate utc = new UtcDate(new NistTime());
       if(EPOCH_FLAG)
       {
         // just want in ms since epoch
         System.out.println(""+ utc.getTimeInMillis() +" ");
        
       }
       else
       {
         // want in simple date format, in desired time zone
         System.out.println(dateFormat.format( utc.getTime() ));          
       }
     }
     else
     {
       // check how many time values to convert
       if(num > 0)
       {
         // walk through the TimeArray, and convert from epoch to UTC or Local Time
         for(int i = 0; i < num; i++)
         {
//           System.out.println(times[i]);
           if(EPOCH_FLAG)
           {
             System.out.println( convertEpochToDateString(times[i], UTC_FLAG));
             
           }
           else
           {
             System.out.println( convertDateStringToEpoch(times[i], UTC_FLAG));
             
           }
         }
       }
    }    
  }
   
   private String convertDateStringToEpoch(String epoch, boolean utc)
   {
     // using one of the two date formats, convert to ms since epoch, if possible
     String val = "NaN";
     try
     {
       Date dt = null;
       synchronized(dateFormat)
       {
         dt = dateFormat.parse(epoch);
       }
       val = "" + dt.getTime();
     }
     catch (ParseException pe)
     {
       System.out.println("parse exception");
     }
     return val;          
   }

   private String convertEpochToDateString(String epoch, boolean utc)
   {
     long e = 0L;
     
     try
     {
       e = Long.parseLong(epoch);
     }
     catch (NumberFormatException nfe)
     {
       System.out.println("Long Parse Error");
       return "NAN";
     }
     
     UtcDate ud = new UtcDate();
     ud.setTimeInMillis(e);
  
     return dateFormat.format( ud.getTime() );          
   }

   public static void main(String[] args)
   {
     java.io.File configFile = parseCommandLineArguments(args);
     new Epoch(configFile);

   }

  
   /**************************************************************************
   *** Method Name:
   ***     parseCommandLineArguments
   **/
   /**
   *** This accepts a string of options and arguments to help setup and configure
   *** the this application.  This method is intended to perform the functions
   *** needed when command line options are supplied to the main() method.
   ***  This method becomes a convienient mechanism to use when setting many values
   *** at once.
   *** Please refer to <code>usage</code> method for additional details.
   ***
   *** @see          #usage
   ***
   *** @param        args  command line options and host names
   ***
   *** @return       a configuration file or <code>null</code>
   ***
   **************************************************************************/

   public static java.io.File parseCommandLineArguments(String[] args)
   {
     // these are the default values
     boolean configFileFlag      = false;   // default option
     java.io.File configFile     = null;

 /**
     ***   where possible options include:
       ***          -?     print this help message 
       ***          -h     print this help message 
       ***          -f   * next argument is a configuration file 
       ***          -utc   * utc time 
       ***          -pst   * pacific standard time (default)
       ***          -e   * value is ms since epoch (default) 
       ***          -d  * value is date in specified time zone 
       ***          -t     show the current time 
       ***          -v     print the version 
 **/
     
     // parse the command line arguments
     for (int i = 0; i < args.length; i++)
     {
       if (args[i].startsWith("-utc"))
       {
         // put in utc mode, instead of local 
         UTC_FLAG = true;
       } 
       else if (args[i].startsWith("-pst"))
       {
         // put in local mode, instead of utc 
         UTC_FLAG = false;
       } 
       else if (args[i].startsWith("-e"))
       {
         // put in ms since epoc mode 
         EPOCH_FLAG = true;
       } 
       else if (args[i].startsWith("-d"))
       {
         // put in date mode, instead of ms since epoc mode 
         EPOCH_FLAG = false;
       } 
       else if (args[i].startsWith("-f"))
       {
         // next arg is the config file 
         configFileFlag  = true;
       }
       else if (args[i].startsWith("-t"))
       {
         // get the current time 
         ACQUIRE_FLAG  = true;
       }
       else if (args[i].startsWith("-?"))
       {
         usage();
       }
       else if (args[i].startsWith("-h"))
       {
         usage();
       }
       else if (args[i].startsWith("-v"))
       {
         System.out.println(" Epoch ( Version " + getVersionNumber() + ")");
         System.exit(0);
       }
       // if not one of the valid command line args, then may be a parameter for
       // one, so check for those!
       else if (args[i] != null)
       {
         if (configFileFlag)
         {
           // this will override all other configuration methods
           String configFileName = args[i];  // this should be an absolute directory path
           
           if ( (configFile = setConfigFile(configFileName)) != null )
           {
             logger.info("Setting the configuration file (" + configFileName + ")");
           }
           else
           {
             logger.severe("Ignoring your illegal configuration file (" + configFileName + ")");
           }
           // no need to reset the flag, because this is the default
         }
         else
         {
           // don't understand this argument, so it might be a value to convert
           addTime(args[i]);
         }
       }
       else
       {
         // don't understand this argument, so it might be a value to convert
         addTime(args[i]);
       }
     }
     return configFile;
   }
   /*-----------------------------------------------------------------------*/

 
   /**************************************************************************
    *** Method Name:
    ***     usage
    **/
    /**
    *** Provides helpful information when this Class is used as a command line
    *** utility.
    *** <p>
    *** <PRE>
    *** 
    ***  Epoch ( version )
    ***
    ***  Usage: java gov.llnl.lc.time.Epoch (options)
    ***
    ***   where possible options include:
    ***          -?     print this help message 
    ***          -h     print this help message 
    ***          -f   * next argument is a configuration file 
    ***          -utc   * utc time 
    ***          -pst   * pacific standard time (default)
    ***          -e   * value is ms since epoch (default) 
    ***          -d  * value is date in specified time zone 
    ***          -t  * show the current time 
    ***          -v     print the version 
    ***   
    ***  
    ***   note: * options and arguments are ...
    ***  
    ***   examples:
    ***      java gov.llnl.lc.time.Epoch 1113807600
    ***      java gov.llnl.lc.time.Epoch -d "2005-10-28 16:59:36 PDT" "2005-10-28 16:59:36 UTC"
    ***      java gov.llnl.lc.time.Epoch -d -t -pst"
    ***      java gov.llnl.lc.time.Epoch -t"
    *** 
    *** </PRE>
    *** <p>
    *** Note:  Exits upon completion
    *** <p>
    ***
    **************************************************************************/

    public static void usage()
    {
      System.out.println( " " );
      System.out.println( " Epoch ( Version " + getVersionNumber() + ")");
      System.out.println( " " );
      System.out.println("Usage: java gov.llnl.lc.time.Epoch (options)" );
      System.out.println( " " );
      System.out.println(" where possible options include:" );
      System.out.println("        -?     print this help message" ); 
      System.out.println("        -h     print this help message" ); 
      System.out.println("        -f     next argument is a configuration file" ); 
      System.out.println("        -utc   utc time " );
      System.out.println("        -pst   pacific standard time (default)" );
      System.out.println("        -e     value is ms since epoch (default) " );
      System.out.println("        -d     value is date in specified time zone " );
      System.out.println("        -t     show the current time" );
      System.out.println("        -v     print the version " );
      System.out.println( " " );
      System.out.println(" note: * options and arguments are ..." );
      System.out.println( " " );
      System.out.println(" examples:" );
      System.out.println("    java gov.llnl.lc.time.Epoch 1113807600" );
      System.out.println("    java gov.llnl.lc.time.Epoch -d \"2005-10-28 16:59:36 PDT\" \"2005-10-28 16:59:36 UTC\"" );
      System.out.println("    java gov.llnl.lc.time.Epoch -d -t -pst" );
      System.out.println("    java gov.llnl.lc.time.Epoch -t" );
      System.out.println( " " );
      System.exit(0);
    }
    /*-----------------------------------------------------------------------*/

    /**************************************************************************
     *** Method Name:
     ***     addTime
     **/
     /**
     ***   
     **************************************************************************/

     private static int addTime(String arg)
     {
       if(NumStrings < MAX_NUM_TIMES)
       {
         TimeArray[NumStrings] = new String(arg);
         NumStrings++;
       }
       return NumStrings;
     }
     /*-----------------------------------------------------------------------*/

  
   /**************************************************************************
   *** Method Name:
   ***     getVersionNumber
   **/
   /**
   *** Returns the Version and Revision numbers in a formatted string.
   *** The version is made up of a primary version number and a minor revision
   *** number.  It is in the form "v.v_rr", where v.v is the primary version
   *** number, and rr is the revision number provided by the version control
   *** system, and gets incremented independently of the version number.
   *** <p>
   ***
   *** @return       A Version String.
   **************************************************************************/

   public static String getVersionNumber()
   {
     String fullVersionString = "Bad Version";
     
     // it is the substring just after the space after the colon
     int startIndex = Revision.indexOf(" ") + 1;
     
     // and ends just before the dollar sign
     int endIndex   = Revision.length() -1;
     
     if (endIndex > startIndex)
     {
       // skip the major number and return just the minor number
       String minorVersion = Revision.substring(startIndex, endIndex);
       fullVersionString = Version + "_" + minorVersion;
     }
     return fullVersionString;
   }
   /*-----------------------------------------------------------------------*/

   
   /**************************************************************************
   *** Method Name:
   ***     getVersionDate
   **/
   /**
   *** Returns the last revision date as a String.  This coupled with the
   *** version number, should uniquely identify this version, and also
   *** give an indication of how old it is.  This date is automatically maintained
   *** by the version control system.
   *** <p>
   ***
   *** @return       A Version Date String.
   **************************************************************************/

   public static String getVersionDate()
   {
     String dateString = "Bad Version";
     
     // it is the substring just after the colon (skip colon and space)
     int startIndex = RevDate.indexOf(":") + 2;
     
     // and ends just before the dollar sign
     int endIndex   = RevDate.length() -1;
     
     if (endIndex > startIndex)
     {
       // skip the comma, and the next space
       dateString = RevDate.substring(startIndex, endIndex) + "UTC";
     }
     return dateString;
   }
   /*-----------------------------------------------------------------------*/
  
   /**************************************************************************
   *** Method Name:
   ***     setConfigFile
   **/
   /**
   *** A configuration file supplied at the command line takes precedent over all
   *** other methods of specifying a configuration.  This function checks the validity
   *** of the file name, and then replaces or defines the configuration file that will
   *** be used to set the attributes of Tips.
   *** <p>
   ***
   ***
   *** @param        configFileName  the full path name of the desired configuration file
   ***
   *** @return       true if the file is legal, and false otherwise.
   **************************************************************************/

   public static java.io.File setConfigFile(String configFileName)
   {
     // do a little validation testing on this name
     
     // must be a file, with the extension of .tips
     
     java.io.File configFile = new java.io.File(configFileName);
     
     if (configFile.isFile())
     {
       if (configFileName.endsWith(FILE_CONFIG_EXT))
       {
         // good enough for now, do more extensive checking latter
         
         // save the setting for next time
         return configFile;
       }
     }
     return null;
   }
   /*-----------------------------------------------------------------------*/


}
