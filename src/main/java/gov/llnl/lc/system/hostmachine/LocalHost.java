package gov.llnl.lc.system.hostmachine;

import java.io.Serializable;
import java.net.UnknownHostException;

public class LocalHost implements Serializable
{
  /**  describe serialVersionUID here **/
  private static final long serialVersionUID = 2081786341030635508L;
  
  public String OS_Name         = null;
  public String OS_Version      = null;
  public String OS_Architecture = null;
  public String HostName        = null;
  public String HostAddress     = null;
  
  
  /************************************************************
   * Method Name:
   *  LocalHost
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   ***********************************************************/
  public LocalHost()
  {
    OS_Name         = getOS_Name();
    OS_Version      = getOS_Version();
    OS_Architecture = getOS_Architecture();
    HostName        = getHostName();
    HostAddress     = getHostAddress();
  }

  public static String getOS_Name()
  {
    return System.getProperty("os.name");
  }
  
  public static String getOS_Version()
  {
    return System.getProperty("os.version");
  }
  
  public static String getOS_Architecture()
  {
    return System.getProperty("os.arch");
  }
  
  public static String getCanonicalHostName()
  {
    try
    {
      return java.net.InetAddress.getLocalHost().getCanonicalHostName();
    }
    catch (UnknownHostException e)
    {
      e.printStackTrace();
    }
    return "unknown host";
  }

  public static String getHostName()
  {
    try
    {
      return java.net.InetAddress.getLocalHost().getHostName();
    }
    catch (UnknownHostException e)
    {
      e.printStackTrace();
    }
    return "unknown host";
  }

  public static String getHostAddress()
  {
    try
    {
      return java.net.InetAddress.getLocalHost().getHostAddress();
    }
    catch (UnknownHostException e)
    {
      e.printStackTrace();
    }
    return "unknown address";
  }

  public static void main(String[] args)
  {
    /* there are two ways to use this class;
     * 1) instantiate it, then use the public member values
     * 2) use the static methods
     * 
     * Use #1 if you want to move the instance values around to different
     * hosts
     * 
     * Use #2 if you want the current values on the current host
     */
    LocalHost lhost = new LocalHost();
    
    System.out.println("Hostname of local machine: " + lhost.HostName);
    System.out.println("Address of local machine: " + lhost.HostAddress);
    System.out.println("OS Name of local machine: " + lhost.OS_Name);
    System.out.println("OS Version of local machine: " + lhost.OS_Version);
    System.out.println("OS Architecture of local machine: " + lhost.OS_Architecture);
    
    System.out.println("Hostname of local machine: " + LocalHost.getCanonicalHostName());
    System.out.println("Hostname of local machine: " + LocalHost.getHostName());
    System.out.println("Address of local machine: " + LocalHost.getHostAddress());
    System.out.println("OS Name of local machine: " + LocalHost.getOS_Name());
    System.out.println("OS Version of local machine: " + LocalHost.getOS_Version());
    System.out.println("OS Architecture of local machine: " + LocalHost.getOS_Architecture());
    
  }

}
