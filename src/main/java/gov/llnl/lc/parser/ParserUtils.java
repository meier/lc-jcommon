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
 *        file: ParserUtils.java
 *
 *  Created on: Jun 17, 2011
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**********************************************************************
 * Describe purpose and responsibility of ParserUtils
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Jun 17, 2011 3:19:41 PM
 **********************************************************************/
public class ParserUtils
{
  static final String ZEROS_REGEX = "0+";    // one or more zeros

  public static long convertHexStringToLong(String hexString)
  {
    // hmmm, check out Long.decode()
    // is this function necessary??
    long val = 0L;
    if((hexString != null) && (hexString.length()>0))
    {
    // optional prefix of 0x permitted (strip off)
    //  (does not handle colons)
        String hexVal = hexString.trim();
        int ndex = 0;
        if(hexVal.startsWith("0x"))
          ndex = 2;
        
        hexVal = hexVal.substring(ndex);
        val = Long.parseLong(hexVal, 16);
    }
    return val;
  }
  
  public static int convertHexStringToInt(String hexString)
  {
    // hmmm, check out Integer.decode()
    // is this function necessary??
    int val = 0;
    if((hexString != null) && (hexString.length()>0))
    {
    // optional prefix of 0x permitted (strip off)
    //  (does not handle colons)
        String hexVal = hexString.trim();
        int ndex = 0;
        if(hexVal.startsWith("0x"))
          ndex = 2;
        
        hexVal = hexVal.substring(ndex);
        val = Integer.parseInt(hexVal, 16);
    }
    return val;
  }
  

  public static String getStringValue(String prefix, String body)
  {
    // return string from prefix to end of line
    return getStringValue(prefix, body, null);
  }

  public static String getStringValue(String prefix, String body, String postfix)
  {
    // return string from the prefix to postfix
    
    // if prefix is null, then use the start
    // if postifx is null, then use the end
    
    // if either prefix or postfix is specified, but not found, return empty string
    
    String stringValue = "";
    int startIndex = 0;
    int endIndex = 0;
    
    if ((body != null) && (body.length() > 0)) {
      if ((prefix != null) && ((startIndex = body.indexOf(prefix)) < 0))
        return stringValue;

      if ((postfix != null)
          && ((endIndex = body.indexOf(postfix, startIndex)) < 0))
        return stringValue;

      startIndex = ((prefix == null) ? 0 : (startIndex + prefix.length()));

      endIndex = (postfix == null) ? body.length() : endIndex;

      if (endIndex > startIndex) {
        String tempVal = body.substring(startIndex, endIndex);
        if (tempVal != null)
          stringValue = tempVal.trim();
      }
    }
    return stringValue;
  }

  public static int getIntValue(String prefix, String body)
  {
    return getIntValue(prefix, body, null);
  }

  public static int getIntValue(String prefix, String body, String postfix)
  {
    // return int between prefix and postfix
    String stringValue = getStringValue(prefix, body, postfix);
    return (stringValue.length() > 0 ? Integer.parseInt(stringValue): 0);
  }

  public static Matcher getMatching(String regex, String line)
  {
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(line);

    if (matcher.find())
      return matcher;
    
    return null;
  }

  public static String getMatchingString(String regex, String line)
  {
    Matcher matcher = getMatching(regex, line);

    if (matcher != null)
      return matcher.group();
    
    return null;
  }

  public static String trimLeadingZeros(String line)
  {
    Matcher matcher = getMatching(ZEROS_REGEX, line);

    // if starts with one or more zeros, trim this string
    if ((matcher != null) && (matcher.start() == 0))
      return line.substring(matcher.end());
    
    return line;
  }


}
