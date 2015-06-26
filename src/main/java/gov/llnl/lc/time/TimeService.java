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
 *        file: TimeService.java
 *
 *  Created on: Oct 27, 2011
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.time;

import gov.llnl.lc.logging.CommonLogger;

import java.util.concurrent.TimeUnit;

/**********************************************************************
 * Describe purpose and responsibility of TimeService
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Oct 27, 2011 1:52:20 PM
 **********************************************************************/
public class TimeService implements Runnable, TimeUpdater, CommonLogger
{
  /** the synchronization object **/
  private static Boolean semaphore            = new Boolean( true );

  /** the one and only <code>TimeService</code> Singleton **/
  private volatile static TimeService globalTimeService  = null;

  /** boolean specifying whether the thread should continue **/
  private volatile static boolean Continue_Thread = true;
  
  /** boolean specifying whether the thread has been created **/
  protected static boolean Thread_Exists = false;

  /** boolean specifying whether the thread is running **/
  protected static boolean Thread_Running = false;

  /** thread responsible for listening for events **/
  private static java.lang.Thread Listener_Thread;
  
  /** a list of Listeners, interested in knowing when a second has passed **/
  private static java.util.ArrayList <TimeListener> Time_Listeners =
    new java.util.ArrayList<TimeListener>();

  private TimeService()
  {
     createThread();
  }
  
  /**************************************************************************
   *** Method Name:
   ***     createThread
   **/
   /**
   *** Summary_Description_Of_What_createThread_Does.
   *** <p>
   ***
   *** @see          Method_related_to_this_method
   ***
   *** @param        Parameter_name  Description_of_method_parameter__Delete_if_none
   ***
   *** @return       Description_of_method_return_value__Delete_if_none
   ***
   *** @throws       Class_name  Description_of_exception_thrown__Delete_if_none
   **************************************************************************/

   private void createThread()
   {
     if (!Thread_Exists)
     {
       // set up the thread to listen
       Listener_Thread = new Thread(this);
       Listener_Thread.setDaemon(true);
       Listener_Thread.setName("TimeService");
       
       logger.info("Creating the " + Listener_Thread.getName() + " Thread");

       Thread_Exists = true;
     }
   }

  /**************************************************************************
   *** Method Name:
   ***     startThread
   **/
   /**
   *** Summary_Description_Of_What_startThread_Does.
   *** <p>
   ***
   *** @see          Method_related_to_this_method
   ***
   *** @param        Parameter_name  Description_of_method_parameter__Delete_if_none
   ***
   *** @return       Description_of_method_return_value__Delete_if_none
   ***
   *** @throws       Class_name  Description_of_exception_thrown__Delete_if_none
   **************************************************************************/

   private boolean startThread()
   {
     logger.info("Starting the " + Listener_Thread.getName() + " Thread");
     Listener_Thread.start();
     return true;
   }
   /*-----------------------------------------------------------------------*/

   /**************************************************************************
    *** Method Name:
    ***     stopThread
    **/
    /**
    *** Summary_Description_Of_What_startThread_Does.
    *** <p>
    ***
    *** @see          Method_related_to_this_method
    ***
    *** @param        Parameter_name  Description_of_method_parameter__Delete_if_none
    ***
    *** @return       Description_of_method_return_value__Delete_if_none
    ***
    *** @throws       Class_name  Description_of_exception_thrown__Delete_if_none
    **************************************************************************/

    private void stopThread()
    {
      logger.info("Stopping the " + Listener_Thread.getName() + " Thread");
      Continue_Thread = false;
    }
    /*-----------------------------------------------------------------------*/


    public void destroy()
    {
      logger.info("Terminating the TimeService");
      stopThread();
    }
    
   /**************************************************************************
  *** Method Name:
  ***     getInstance
  **/
  /**
  *** Get the singleton TimeService. This can be used if the application wants
  *** to share one manager across the whole JVM.  Currently I am not sure
  *** how this ought to be used.
  *** <p>
  ***
  *** @return       the GLOBAL (or shared) TimeService
  **************************************************************************/

  public static TimeService getInstance()
  {
    synchronized( TimeService.semaphore )
    {
      if ( globalTimeService == null )
      {
        globalTimeService = new TimeService( );
      }
      return globalTimeService;
    }
  }
  /*-----------------------------------------------------------------------*/

  public Object clone() throws CloneNotSupportedException
  {
    throw new CloneNotSupportedException(); 
  }

  
  @Override
  public void run()
  {    
    // check the Thread Termination Flag, and continue if Okay
    while(Continue_Thread)
    {
      try
      {
        TimeUnit.MILLISECONDS.sleep(999);
       }
      catch (Exception e)
      {
        // nothing to do yet
      }
      updateAllListeners();
    }
    logger.info("Terminating the " + Listener_Thread.getName() + " Thread");
    /* fall through, must be done! */
  }
  
  /**************************************************************************
   *** Method Name:
   ***     updateAllListeners
   ***
   **/
   /**
   *** Notifies all listeners that some event has occurred.
   *** <p>
   ***
   **************************************************************************/
   private synchronized void updateAllListeners()
   {
     TimeStamp time = new TimeStamp();
     for( int i = 0; i < Time_Listeners.size(); i++ )
     {
       TimeListener listener = (TimeListener)Time_Listeners.get( i );
       listener.timeUpdate(time);
     }
   }
   /*-----------------------------------------------------------------------*/


  /************************************************************
   * Method Name:
   *  addListener
  **/
  /**
   * Add the provided listener to the list of subscribers interested
   * in being notified of time changes.  
   *
   * @param listener
   ***********************************************************/
  
  @Override
  public synchronized void addListener(TimeListener listener)
  {
    // add the listener, and its set of events
    if(listener != null)
    {
      Time_Listeners.add(listener);
      
      // conditionally start the listener thread if not already running
      if(!Thread_Running)
        startThread();
    }
  }

  /************************************************************
   * Method Name:
   *  removeListener
  **/
  /**
   * Describe the method here
   *
   * @see gov.llnl.lc.infiniband.opensm.plugin.event.OsmEventUpdater#removeListener(gov.llnl.lc.infiniband.opensm.plugin.event.OsmEventListener)
  
   * @param   describe the parameters
   *
   * @return  describe the value returned
   * @param listener
   * @return
   ***********************************************************/
  
  @Override
  public synchronized boolean removeListener(TimeListener listener)
  {
    if (Time_Listeners.remove(listener))
    {
     }
    return true;
  }
}
