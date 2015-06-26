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
 *        file: RpmParser.java
 *
 *  Created on: Aug 15, 2012
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.system.rpm;

import gov.llnl.lc.logging.CommonLogger;
import gov.llnl.lc.parser.Parser;
import gov.llnl.lc.system.CommandLineArguments;
import gov.llnl.lc.system.CommandLineExecutor;
import gov.llnl.lc.system.CommandLineResults;
import gov.llnl.lc.system.SoftwareComponent;
import gov.llnl.lc.time.TimeStamp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

/**********************************************************************
 * Describe purpose and responsibility of RpmParser
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Aug 15, 2012 8:28:12 AM
 **********************************************************************/
public class RpmParser implements Parser, CommonLogger
{
  private static final String NEW_LINE = System.getProperty("line.separator");
  static final String COLON              = ":";
  
  static final int NORMAL_STATE = 0;
  static final int DESCRIPTION_STATE = 1;
  static final int NULL_STATE = 13;

  static final String NAME_PREFIX        = "Name";
  static final String VERSION_PREFIX     = "Version";
  static final String RELEASE_PREFIX     = "Release";
  static final String RELOCATIONS_PREFIX = "Relocations";
  static final String VENDOR_PREFIX = "Vendor";
  static final String INSTALL_PREFIX     = "Install Date";
  static final String BUILD_PREFIX       = "Build Date";
  static final String GROUP_PREFIX       = "Group";
  static final String SRPM_PREFIX        = "Source RPM";
  static final String SIZE_PREFIX        = "Size";
  static final String SIGNATURE_PREFIX   = "Signature";
  static final String PACKAGER_PREFIX    = "Packager";
  static final String SUMMARY_PREFIX     = "Summary";
  static final String DESCRIPTION_PREFIX = "Description";
  static final String BHOST_PREFIX       = "Build Host";
  static final String LICENSE_PREFIX     = "License";
  
  protected String FileName;
  protected TimeStamp FileTimeStamp;
  protected int linesParsed;
  protected SoftwareComponent RpmInfo;
  
  protected void initParser()
  {
    // get rid of instance data
    FileName = "";
    linesParsed = 0;
  }

  public void parseString(String rpmOutput) throws IOException
  {
    StringReader sr = new StringReader(rpmOutput);
    
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
    String scratch;
    StringBuffer desc = new StringBuffer();
    String line;  // get one line at a time
    
    SoftwareComponent rpmInfo = new SoftwareComponent();
    
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
      else if (line.startsWith(DESCRIPTION_PREFIX) || (state == DESCRIPTION_STATE) )
      {
        state = DESCRIPTION_STATE;
      }
      else
        state = NORMAL_STATE;

      switch (state) 
      {
        case NULL_STATE:
          break;
          
        case NORMAL_STATE:
          if(line.startsWith(NAME_PREFIX))
          {
            /* get the value after the colon, up to the next white space */
            String[] values = line.split(COLON);
            if(values.length > 1)
            {
              /* just want the first word, no white space */
              int endIndex = values[1].indexOf(RELOCATIONS_PREFIX);
              scratch = values[1].substring(0, endIndex);
              rpmInfo.setName(scratch.trim());
            }
          }
          
          if(line.startsWith(VERSION_PREFIX))
          {
            /* get the value after the colon, up to the next white space */
            String[] values = line.split(COLON);
            if(values.length > 1)
            {
              /* just want the first word, no white space */
              int endIndex = values[1].indexOf(VENDOR_PREFIX);
              scratch = values[1].substring(0, endIndex);
              rpmInfo.setVersion(scratch.trim());
            }
          }
          
          if(line.startsWith(RELEASE_PREFIX))
          {
            /* get the value after the colon, up to the next white space */
            String[] values = line.split(COLON);
            if(values.length > 1)
            {
              /* just want the first word, no white space */
              int endIndex = values[1].indexOf(BUILD_PREFIX);
              scratch = values[1].substring(0, endIndex);
              rpmInfo.setRelease(scratch.trim());
              rpmInfo.setVersionReleaseName(rpmInfo.getName()+ "-" + rpmInfo.getVersion() + "-" + rpmInfo.getRelease());
              /* has colons in value, brute force - can't used split */
              endIndex = line.indexOf(BUILD_PREFIX) + BUILD_PREFIX.length() +2;
              if(endIndex < line.length())
                rpmInfo.setBuildDate(line.substring(endIndex));
            }
          }
          
          if(line.startsWith(INSTALL_PREFIX))
          {
            int beginIndex = line.indexOf(INSTALL_PREFIX) + INSTALL_PREFIX.length() + 1;
            int endIndex   = line.indexOf(BHOST_PREFIX);
            scratch = line.substring(beginIndex, endIndex);
            rpmInfo.setInstallDate(scratch.trim());
          }
          
          if(line.startsWith(SIZE_PREFIX))
          {
            /* get the value after the colon, up to the next white space */
            String[] values = line.split(COLON);
            if(values.length > 1)
            {
              /* just want the first word, no white space */
              int endIndex = values[1].indexOf(LICENSE_PREFIX);
              scratch = values[1].substring(0, endIndex);
              rpmInfo.setSize(scratch.trim());
              
              if(values.length > 2)
                rpmInfo.setLicense(values[2].trim());
            }
          }
          
          if(line.startsWith(PACKAGER_PREFIX))
          {
            /* get the value after the colon, up to the next white space */
            String[] values = line.split(COLON);
            if(values.length > 1)
            {
              rpmInfo.setContact(values[1].trim());
            }
          }
            
          if(line.startsWith(SUMMARY_PREFIX))
          {
            /* get the value after the colon, up to the next white space */
            String[] values = line.split(COLON);
            if(values.length > 1)
            {
              rpmInfo.setSummary(values[1].trim());
            }
          }
           break;
          
        case DESCRIPTION_STATE:
          if(!line.startsWith(DESCRIPTION_PREFIX))
            desc.append(line);
          break;
          
          default:
            break;
      }
     }
    if(desc.length() > 1)
      rpmInfo.setDescription(desc.toString());
    RpmInfo = rpmInfo;
  }
  

  public void parseFile(File file) throws IOException
  {
    BufferedReader fr = new BufferedReader(new FileReader(file));
    setFileName(file.getAbsolutePath());
    FileTimeStamp = new TimeStamp(file.lastModified());
    
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

    public String getSummary() {
       StringBuffer stringValue = new StringBuffer();
      
       stringValue.append(this.getClass().getName() + NEW_LINE);
       stringValue.append(this.getFileName() + NEW_LINE);
       stringValue.append("Date: " + this.getFileTimeStamp() + NEW_LINE);
       stringValue.append("Lines: " +this.getLinesParsed() + NEW_LINE);
          
      return stringValue.toString();
  }
  
  public TimeStamp getFileTimeStamp()
  {
    return FileTimeStamp;
  }

  public void setFileTimeStamp(TimeStamp fileTimeStamp)
  {
    FileTimeStamp = fileTimeStamp;
  }

  
  /************************************************************
   * Method Name:
   *  getRpmInfo
   **/
  /**
   * Returns the value of rpmInfo
   *
   * @return the rpmInfo
   *
   ***********************************************************/
  
  public SoftwareComponent getRpmInfo()
  {
    return RpmInfo;
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
    RpmParser parser = new RpmParser();
    int exitStatus = 0;
    CommandLineExecutor cmdExecutor1 = new CommandLineExecutor(new CommandLineArguments("rpm -qi llnl-ldapotp-clt-jni-auth-libs-1.0.0-4.ch4.4"));    
    try
    {
      exitStatus = cmdExecutor1.runCommand();
      CommandLineResults results = cmdExecutor1.getResults();
      parser.parseString(results.getOutput());
    }
    catch (Exception ioe)
    {
      System.out.println("Parse exception: " + ioe.getMessage());
    }
    
    System.out.println(parser.toString());    
  }

}
