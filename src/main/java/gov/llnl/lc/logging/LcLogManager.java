package gov.llnl.lc.logging;

import java.io.IOException;
import java.util.logging.LogManager;

public class LcLogManager extends java.util.logging.LogManager
                           implements gov.llnl.lc.logging.CommonLogger
{
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    /*~~~     Static Finals                                               !!!*/
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

    
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    /*~~~     Class Variables                                             !!!*/
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

    
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    /*~~~     Instance Variables                                          !!!*/
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

    
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    /*~~~     Constructors                                                !!!*/
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/


    /**************************************************************************
    *** Method Name:
    ***     LcLogManager
    **/
    /**
    *** This constructor adds the HPC Handler to the HPC logger, and then
    *** sets the Verbosity level of both.  It also inhibits the use of the
    *** Parent Handler, so we don't get multiple log messages.
    *** <p>
    ***
    *** @see       gov.llnl.lc.logging.CommonLogger
    *** @see       java.util.logging.Logger#addHandler
    *** @see       java.util.logging.Logger#setUseParentHandlers
    *** @see       #setVerbose
    ***
    **************************************************************************/

    public LcLogManager()
    {
      super();
      java.util.logging.Handler hpcHandlerF = hpcHandler;
      
      /* use the global handlers specified in the property file */
      String handlers = LogManager.getLogManager().getProperty("handlers");
      if(handlers != null)
      {
        // replace the default Console handler with the desired ones, but only
        // support Console and File
        String [] Handlers = handlers.split(",\\s");
        
        for(int i = 0; i < Handlers.length;i++)
        {
          /* ignore console handler, just inherit the default */
          if(Handlers[i].contains("FileHandler"))
          {
            /* create the file handler, configure it, and add it */
            String fHpattern = LogManager.getLogManager().getProperty("java.util.logging.FileHandler.pattern");
            String pattern = fHpattern == null? filePattern: fHpattern;
//            System.err.println("Java Logging to file: (" + pattern + ")");
            try
            {
              hpcHandlerF = new java.util.logging.FileHandler(pattern, true);
            }
            catch (SecurityException e)
            {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
            catch (IOException e)
            {
              // TODO Auto-generated catch block
              e.printStackTrace();
            }
          }
          else
            hpcHandlerF = hpcHandler;
          hpcHandlerF.setLevel(java.util.logging.Level.ALL);
          logger.addHandler(hpcHandlerF);            
        }
      }
      else
      {
        /* no handlers specified, so use the default console */
        hpcHandlerF.setLevel(java.util.logging.Level.ALL);
        logger.addHandler(hpcHandlerF);            
      }
      /* the "global" ones would be redundant */
      logger.setUseParentHandlers(false);
      
//      this.setVerbose(false);  // set logging level for handler and logger
    }
    /*-----------------------------------------------------------------------*/

    
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    /*~~~     Property Methods                                            !!!*/
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

    
    /**************************************************************************
    *** Method Name:
    ***     setVerbose
    **/
    /**
    *** This primitive method sets the logging level to either WARNING (default) or
    *** ALL (verbose) depending on the boolean argument.  
    *** <p>
    ***
    *** @see          java.util.logging.Handler#setLevel
    *** @see          java.util.logging.Logger#setLevel
    ***
    *** @param        isVerbose  if true, sets the logger level to ALL
    **************************************************************************/

    public static void setVerbose(boolean isVerbose)
    {
      
    // now set the Level of the Logger to one of two values  
      if (isVerbose)
      {
        logger.setLevel(java.util.logging.Level.ALL);   // this is like turning the trace on
      }
      else
      {
        logger.setLevel(DEFAULT_LEVEL);// this is the normal default
      }
    }

    /*-----------------------------------------------------------------------*/

    
    /**************************************************************************
    *** Method Name:
    ***     setLevel
    **/
    /**
    *** This method sets the Logging Level of both the Handler and the Logger
    *** to the specified value.  This provides a mechanism to ensure that all
    *** filters are set to the same known value, and should simplify how the Java
    *** Logging API works.
    *** <p>
    *** It is normally not necessary (or desired) to set Levels using this method
    *** of the LogManager.  In general, the <code><strong>setLevel()</strong></code>
    *** method of the HPC Logger (<strong>logger</strong>)should be directly used.
    *** <p>
    ***
    *** @see          java.util.logging.Handler#setLevel
    *** @see          java.util.logging.Logger#setLevel
    *** @see          java.util.logging.Level
    ***
    *** @param        level  the desired logging (filter) level
    **************************************************************************/

    public static void setLevel(java.util.logging.Level level)
    {
      // the logger is the first Level Filter, if a message gets past this
      // then it goes to the Handlers
      logger.setLevel(level);

      // the handlers have another opportunity to filter out the message.  They
      // should be set to allow messages from the logger through.
      hpcHandler.setLevel(level);
    }

    /*-----------------------------------------------------------------------*/

    
    /**************************************************************************
    *** Method Name:
    ***     getLevel
    **/
    /**
    *** This method gets the Logging Level of the HPC Logger.
    *** <p>
    *** It is normally not necessary (or desired) to get Levels using this method
    *** of the LogManager.  In general, the <code><strong>getLevel()</strong></code>
    *** method of the HPC Logger (<strong>logger</strong>)should be directly used.
    *** <p>
    ***
    *** @see          java.util.logging.Logger#getLevel
    *** @see          java.util.logging.Level
    ***
    *** @return       the current logging (filter) level
    **************************************************************************/

    public static java.util.logging.Level getLevel()
    {
      // the logger is the first Level Filter, if a message gets past this
      // then it goes to the Handlers.  Both levels should be set the same
      return logger.getLevel();
    }

    /*-----------------------------------------------------------------------*/

    
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    /*~~~     Event Methods                                               !!!*/
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

    
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    /*~~~     General Methods                                             !!!*/
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

    } /* end of LcLogManager Class */

