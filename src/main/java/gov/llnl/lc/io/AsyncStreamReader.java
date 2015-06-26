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
 *        file: AsyncStreamReader.java
 *
 *  Created on: Aug 7, 2012
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**********************************************************************
 * Describe purpose and responsibility of AsyncStreamReader
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Aug 7, 2012 8:22:00 AM
 **********************************************************************/
public class AsyncStreamReader extends Thread
{
  private StringBuffer Buffer = null;
  private InputStream InputStream = null;
  private String ThreadId = null;
  private boolean Stop = false;
  
  private static final String NewLine = System.getProperty("line.separator");
  
  public AsyncStreamReader(InputStream inputStream, StringBuffer buffer, String threadId)
  {
    InputStream = inputStream;
    Buffer = buffer;
    ThreadId = threadId;
  } 
  
  public String getBuffer() 
  {   
    return Buffer.toString();
  }
  
  public void run()
  {
    String name = Thread.currentThread().getName();
    Thread.currentThread().setName(name + " (" + ThreadId + ")");

    try 
    {
      readCommandOutput();
    } 
    catch (Exception ex) 
    {
      //ex.printStackTrace(); //DEBUG
    }
  }
  
  private void readCommandOutput() throws IOException
  {   
    BufferedReader bufOut = new BufferedReader(new InputStreamReader(InputStream));    
    
    char c = '\0';
    int charInt;
    while ( (Stop == false) && ((charInt = bufOut.read()) != -1) )
    {
    c = (char)charInt; 
    Buffer.append(c);
    }   
    bufOut.close();
  }
  
  public void stopReading() 
  {
    Stop = true;
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
    // TODO Auto-generated method stub

  }

}
