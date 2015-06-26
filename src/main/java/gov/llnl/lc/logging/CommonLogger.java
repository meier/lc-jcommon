/*
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
 *        file: CommonLogger.java
 *
 *  Created on: Jun 25, 2015
 *      Author: meier3
 ********************************************************************/

package gov.llnl.lc.logging;

/******************************************************************************
*** The CommonLogger Interface defines the specific logger that should be used
*** by all Classes developed for <strong>hpc.llnl.gov</strong>.  This interface
*** should be implemented by all Classes that report error or status conditions
*** to the Global Logging facility named <strong>org.lc.hpc</strong>.  This
*** interface provides a custom Handler and LogManger for use with the logger.
*** In general, it should not be necessary to use the APIs for the Handler or
*** LogManager.
*** <p>
*** The default values for the Loggers and Handlers are contained in the file
*** <code>JRE/lib/org.lc.hpc.logging.properties</code>.  Settings for custom loggers and
*** handlers can be included there also.  Since the default level for the
*** ConsoleHandler is INFO, the hpcHandler was created to have a lower (more
*** verbose) level.
*** <p>
*** Please refer to the <code>Package</code> documentation for the <strong>logging</strong>
*** package.  It contains additional information, and an example showing the intended
*** use.
*** <p>
***
*** @see    java.util.logging.Logger
*** @see    java.util.logging.ConsoleHandler
***
*** @author Timothy A. Meier (Lawrence Livermore National Laboratory)
***
******************************************************************************/
public interface CommonLogger
{
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~     Static Finals                                               !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

  /** The Global Default Log Level is WARNING.  This can be changed using the
  *** <strong>logger</strong> or the <strong>lcLogManager</strong> **/
   static java.util.logging.Level DEFAULT_LEVEL = java.util.logging.Level.WARNING;

  /** The Global HPC Log, uses default values (changeable by a Log Manager) **/
   static java.util.logging.Logger logger  = java.util.logging.Logger.getLogger("LcCommonLogger");
   
  /** The Global HPC Handler used the by the logger.  By default, this is a Console
      handler, which uses stderr. **/
   static java.util.logging.Handler hpcHandler = new java.util.logging.ConsoleHandler();
    
  /** The Global HPC LogManager, which extends the normal LogManager.  It
      is simply a mechanism for setting up the default global configuration. **/
   static final gov.llnl.lc.logging.LcLogManager lcLogManager = new gov.llnl.lc.logging.LcLogManager();

   /** the default FileHandler.pattern, if none specified in the logging.properties file **/
   static final String filePattern = "%h/java%u.log";

  
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~     Property Methods                                            !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

  
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~     Event Methods                                               !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

  
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~     General Methods                                             !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

  } /* end of CommonLogger Interface */