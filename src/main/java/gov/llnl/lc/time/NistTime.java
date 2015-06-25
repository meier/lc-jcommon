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
 *        file: NistTime.java
 *
 *  Created on: Jun 29, 2011
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.time;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**********************************************************************
 * Describe purpose and responsibility of NistTime
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Jun 29, 2011 4:03:20 PM
 **********************************************************************/
public class NistTime
{
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~           Static Finals                                         !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~           Class Variables                                       !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~           Instance Variables                                    !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  private String TimeString = "not initialized";
  protected JFrame Frame; 
  protected JLabel TimeLabel;
  protected NistTime NT;

  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~           Constructors                                          !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

  //-------------------------------------------------------------------------
  // Method:
  //    NistTime
  /**
  * No argument constructor for class 'NistTime'.  A NistTime object is created
  * by getting the current time from the NIST time server over port 13.
  *
  * @see #getCurrentTime
  *
  * @throws   Class_name  Description_of_exception_thrown__Delete_if_none
  **/
  public NistTime()
  {
    super();
    getCurrentTime();
    NT = this;
  }

  /**
   * @return Returns the Time String in the format provided by NIST.
   */
  public String getNistTimeString()
  {
    return TimeString;
  }
  /**
   * @return Returns the timeString.
   */
  public String getTimeString()
  {
//    return TimeString;
    return UtcDate.getDateFromNistString(TimeString).toString();
  }
  /**
   * @return Returns the timeString.
   */
  public void showTimeFrame()
  {
    Frame =  new JFrame("Time Check"); 
    Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    TimeLabel = new JLabel();
    Container content = Frame.getContentPane();  
    content.add(TimeLabel, BorderLayout.CENTER);
    getCurrentTime();
    TimeLabel.setText(getTimeString());

    JButton button = new JButton("update time");

    ActionListener listener = new ActionListener()
    {
      public void actionPerformed(ActionEvent actionEvent)
      {
           NT.updateTimeFrame();
           System.out.println("ButtonAction: (" + actionEvent.getActionCommand()+ ")");
      }
    };

    button.addActionListener(listener);

    content.add(button, BorderLayout.SOUTH);
          
    Frame.pack();
    Frame.setVisible(true);
    
  }
  public void updateTimeFrame()
  {
    getCurrentTime();
    TimeLabel.setText(getTimeString());
          
    Frame.pack();
    Frame.setVisible(true);
    
  }
  /**
   * @return Returns the timeString.
   */
  private String getCurrentTime()
  {
//    Simply connect to the NIST server over port 13, the DAYTIME service, and just read the response.
//    You don't even have to send a request; connecting to the port is the request.
//    The response that comes back is a blank line, followed by the date and time on the second line. 
//
//    The format of the time returned follows, both in its symbolic form and with a real value.
//    The most important pieces of information are at the start, including the Modified Julian Date,
//    two digits each for year, month, and day, along with the time in hours, minutes, and seconds. 

//    JJJJJ YR-MO-DA HH:MM:SS TT L H msADV UTC(NIST) OTM
//    52453 02-06-28 07:41:36 50 0 0 872.9 UTC(NIST) *
    String message = "missing";
    BufferedReader reader = null;
    try
    {
//      System.setSecurityManager(null);
      Socket socket = new Socket("nist1-sj.ustiming.org", 123);
      InputStream is = socket.getInputStream();
      InputStreamReader isr = new InputStreamReader(is);
      reader = new BufferedReader(isr);
      reader.readLine(); // skip blank line
      String msg = reader.readLine();
      if(msg != null)
        message = msg;
    }
    catch (MalformedURLException e)
    {
      System.err.println("Malformed: " + e);
    }
    catch (IOException e)
    {
      System.err.println("I/O Exception: " + e);
    }
    finally
    {
      if (reader != null)
      {
        try
        {
          reader.close();
        }
        catch (IOException ignored)
        {
        }
      }
    }
    TimeString = new String(message);
    return TimeString;
  }
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~           Property Methods                                      !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~           Event Methods                                         !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  /*~~~           General Methods                                       !!!*/
  /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
  
public static void main(String[] args)
{
  new NistTime().showTimeFrame();
  System.out.println("The current time is (" + new NistTime().getTimeString() + ")");
//  System.exit(0);
}

}
