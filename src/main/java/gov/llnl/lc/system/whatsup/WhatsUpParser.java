/********************************************************************
 * Copyright (C) 2013, Lawrence Livermore National Security, LLC.
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
 *        file: WhatsUpParser.java
 *
 *  Created on: Feb 11, 2013
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.system.whatsup;

import gov.llnl.lc.logging.CommonLogger;
import gov.llnl.lc.parser.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

/**********************************************************************
 * Describe purpose and responsibility of WhatsUpParser
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Feb 11, 2013 10:15:19 AM
 **********************************************************************/
public class WhatsUpParser implements Parser, CommonLogger
{
  protected String FileName;
  protected WhatsUpInfo WhatsUp;
  protected int linesParsed;
  
  static final String COLON              = ":";
  static final String COMMA              = ",";
  
  static final int NORMAL_STATE          = 0;
  static final int UP_STATE              = 1;
  static final int DOWN_STATE            = 2;
  static final int UNKNOWN_STATE         = 3;
  static final int NULL_STATE            = 13;

  static final String UP_PREFIX          = "up:";
  static final String DOWN_PREFIX        = "down:";
  static final String UNKNOWN_PREFIX     = "unknown:";

  
  protected void initParser()
  {
    // get rid of instance data
    FileName = "";
    linesParsed = 0;
  }

  public void parseString(String whatsupOutput) throws IOException
  {
    StringReader sr = new StringReader(whatsupOutput);
    
    BufferedReader br = new BufferedReader(sr);
    
    if ( logger.isLoggable(java.util.logging.Level.FINEST) )
    {
      logger.finest("Parsing String");
    }
    parse(br);
  }
  /************************************************************
   * Method Name:
   *  parse
   **/
  /**
   * Describe the method here
   *
   * @see gov.llnl.lc.parser.Parser#parse(java.io.BufferedReader)

   * @param   describe the parameters
   *
   * @return  describe the value returned
   * @param in
   * @throws IOException
   ***********************************************************/
  public void parse(BufferedReader in) throws IOException
  {
    int state = NULL_STATE;
    linesParsed = 0;
    String[] values = null;
    String line;  // get one line at a time
    
    WhatsUp = new WhatsUpInfo();
    
    while ((line = in.readLine()) != null)
    {
      // parse the input, line by line
      linesParsed++;
      if ( logger.isLoggable(java.util.logging.Level.FINEST) )
      {
        logger.finest(line);
      }
      
      if (line.length() == 0) 
      {
        state = NULL_STATE;
      }
      else if (line.startsWith(UP_PREFIX) )
      {
        state = UP_STATE;
      }
      else if (line.startsWith(DOWN_PREFIX))
      {
        state = DOWN_STATE;
      }
      else if (line.startsWith(UNKNOWN_PREFIX))
      {
        state = UNKNOWN_STATE;
      }
      else
        state = NORMAL_STATE;

      switch (state) 
      {
        case NULL_STATE:
          break;
          
        case NORMAL_STATE:
           break;
          
        case UP_STATE:
          values = line.split(COLON);
          if(values.length == 3)
          {
            // the nodes will be in the third string (after the 2nd colon) and comma separated
            WhatsUp.setUpNodes(values[2].trim().split(COMMA));
          }
          break;

        case DOWN_STATE:
          values = line.split(COLON);
          if(values.length == 3)
          {
            // the nodes will be in the third string (after the 2nd colon) and comma separated
            WhatsUp.setDownNodes(values[2].trim().split(COMMA));
          }
          break;
          
        case UNKNOWN_STATE:
          values = line.split(COLON);
          if(values.length == 3)
          {
            // the nodes will be in the third string (after the 2nd colon) and comma seperated
            WhatsUp.setUnknownNodes(values[2].trim().split(COMMA));
          }
           break;
          
          default:
            break;
      }
     }
  }
  

  /************************************************************
   * Method Name:
   *  getWhatsUp
   **/
  /**
   * Returns the value of whatsUp
   *
   * @return the whatsUp
   *
   ***********************************************************/
  
  public WhatsUpInfo getWhatsUp()
  {
    return WhatsUp;
  }

  public void parseFile(File file) throws IOException
  {
    BufferedReader fr = new BufferedReader(new FileReader(file));
    setFileName(file.getAbsolutePath());
    
    if ( logger.isLoggable(java.util.logging.Level.INFO) )
    {
      logger.info("Parsing File: " + this.getFileName());
    }
    parse(fr);
  }

  /************************************************************
   * Method Name:
   *  parseFile
   **/
  /**
   * Describe the method here
   *
   * @see gov.llnl.lc.parser.Parser#parseFile(java.lang.String)

   * @param   describe the parameters
   *
   * @return  describe the value returned
   * @param filename
   * @throws IOException
   ***********************************************************/

  public void parseFile(String filename) throws IOException
  {
      parseFile(new File(filename));
  }

  /************************************************************
   * Method Name:
   *  getFileName
   **/
  /**
   * Describe the method here
   *
   * @see gov.llnl.lc.parser.Parser#getFileName()

   * @param   describe the parameters
   *
   * @return  describe the value returned
   * @return
   ***********************************************************/

  @Override
  public String getFileName()
  {
    return FileName;
  }

  /************************************************************
   * Method Name:
   *  setFileName
   **/
  /**
   * Describe the method here
   *
   * @see gov.llnl.lc.parser.Parser#setFileName(java.lang.String)

   * @param   describe the parameters
   *
   * @return  describe the value returned
   * @param fileName
   ***********************************************************/

  @Override
  public void setFileName(String fileName)
  {
    // clear previous, prepare for a new file
    initParser();
    FileName = fileName;
  }

  /************************************************************
   * Method Name:
   *  getLinesParsed
   **/
  /**
   * Describe the method here
   *
   * @see gov.llnl.lc.parser.Parser#getLinesParsed()

   * @param   describe the parameters
   *
   * @return  describe the value returned
   * @return
   ***********************************************************/

  @Override
  public int getLinesParsed()
  {
    // TODO Auto-generated method stub
    return linesParsed;
  }

  /************************************************************
   * Method Name:
   *  main
   **/
  /**
   * Describe the method here
   *
   * @see     describe related java objects

   * @param   describe the parameters
   *
   * @return  describe the value returned
   * @param args
   ***********************************************************/
  public static void main(String[] args)
  {
    WhatsUpParser parser = new WhatsUpParser();
    int exitStatus = 0;
//    CommandLineExecutor cmdExecutor1 = new CommandLineExecutor(new CommandLineArguments("whatsup"));    
    try
    {
//      exitStatus = cmdExecutor1.runCommand();
//      CommandLineResults results = cmdExecutor1.getResults();
//      parser.parseString(results.getOutput());
      parser.parseFile("/g/g0/meier3/whatsup.txt");
    }
    catch (Exception ioe)
    {
      System.err.println("Parse exception: " + ioe.getMessage());
    }
    
    System.out.println(parser.toString());    
    WhatsUpInfo wui = parser.getWhatsUp();
    System.out.println(wui.toString());
  }
}
