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
 *        file: Query.java
 *
 *  Created on: Jul 16, 2012
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.search;

/**********************************************************************
 * A collection of strings that represent criteria for searching.  This
 * is roughly modeled after a google search, where the most primitive
 * type of search is based on discovering matches for all the words in
 * the search string (AllWords).  You can specify the OR equivalent in
 * the AnyWord string, and you can specify the - equivalent in the
 * ExcludeWords string.  If the result MUST match a string exactly, then
 * you must use the ExactPhrase member.  Finally, the scope of the
 * search can be limited to specific types.  Using the QueryTypes field
 * restricts the results to Objects that satisfy this type(s), and can
 * potentially improve search performance.
 * 
 * Any or all of these fields can be empty strings.  A completely empty
 * query would be meaningless, and would return an empty result set. 
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Jul 16, 2012 9:42:22 AM
 **********************************************************************/
public class Query
{
  /** the result should contain all of the words in this string **/
  private String AllWords;

  /** the result should contain 1 or more words in this string **/
  private String AnyWord;

  /** the result should not contains any of the words in this string **/
  private String ExcludeWords;

  /** the result should contain this exact string **/
  private String ExactPhrase;

  /** the results should be limited to these types **/
  private String QueryTypes;
}
