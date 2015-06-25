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