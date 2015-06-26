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
 *        file: BinList
 *
 *  Created on: Jan 3, 2012
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.util;

import gov.llnl.lc.logging.CommonLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**********************************************************************
 * A collection of collections.  This utility class can be used to group
 * objects into "bins", using the provided key string.  A typical use
 * might be;
 * <pre>
          sbna = new ArrayList<SBN_Node>(Arrays.asList(o_nodes.getSubnNodes()));
          BinList <SBN_Node> psbL = new BinList <SBN_Node>();
          // using the SBN_Nodes, bin the nodes according to their number of ports
          for(SBN_Node sn: sbna)
          {
            psbL.add(sn, "(nodes with " + Short.toString(sn.num_ports) + " ports)");
          }
          System.err.println(psbL.toString());
 * </pre>
 * You can add objects with a "key" string.  The keys should be unique and
 * represent a bin (implemented with an ArrayList).
 * <p>
 * This container does not support
 * removing objects*.  To accomplish removal, just create a new BinList that
 * is a subset of this BinList without the objects you wish removed.  There are two
 * duplicate() methods which can be used for this purpose.  <code>* The BinList is index
 * and key based, and once created can only grow (deletes would require re-indexing).
 * <p>
 * Since this is implemented as an ArrayList of ArrayLists, you can get bins (an ArrayList)
 * by their index.  You can get the index with the key.  Therefore most methods can be
 * used with a key or an index.
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Nov 17, 2011 4:42:58 PM
 **********************************************************************/
public class BinList<T> implements CommonLogger, Iterable<ArrayList<T>>
{
  /** a list of a list of <T>s **/
  private java.util.ArrayList <ArrayList<T>> Bin_List = new java.util.ArrayList<ArrayList<T>>();

  /** a map from the key string to the array index **/
  private  Map<String, Integer> lookupString = new HashMap<String, Integer>();

  private static <T, E> T getKeyByValue(Map<T, E> map, E value)
  {
    for (Entry<T, E> entry : map.entrySet())
    {
        if (value.equals(entry.getValue()))
        {
            return entry.getKey();
        }
    }
    return null;
}

  public synchronized String getKey( Integer index)
  {
    return getKeyByValue(lookupString, index);
}

  /************************************************************
   * Method Name:
   *  BinList
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   * @param   describe the parameters if any
   *
   ***********************************************************/
  public BinList()
  {
    super();
    // TODO Auto-generated constructor stub
  }
  
  public synchronized BinList<T> duplicate(boolean excludeMaxBin)
  {
    // clone this binList, conditionally without the largest bin
    if(excludeMaxBin)
      return duplicate(getMaxBinKey());
    return duplicate(null);
  }
  
  public synchronized BinList<T> duplicate(String excludeKey)
  {
    // clone this binList, excluding a bin with the provided Key
    String key = excludeKey;
    boolean bExcludeKey = false;
    Integer Ndex = null;
    Integer index = 0;
    BinList<T> rtnList = new BinList<T>();
    
    if(key != null)
    {
      Ndex = lookupString.get(key); 
      if(Ndex != null)
      {
        bExcludeKey = true;
      }
    }
    
    for(ArrayList<T> o: Bin_List)
    {
      if((index.equals(Ndex)) && bExcludeKey)
      {
        // skip this bin
      }
      else
      {
        rtnList.addBin(o, getKey(index));
      }
      index++;
    }
    return rtnList;
  }
  
  public synchronized boolean add(T node, String key)
  {
    // search the lookup map to get the index of the bin that
    // matches the key
    // 
    // if no match, create a new bin and populate it
    if((node != null) && (Bin_List != null))
    {
      // does the key already exist?
      Integer Ndex = lookupString.get(key);
      
      // if a matching bin exists, just add it and return
      if((Bin_List.size() != 0) && (Ndex != null))
      {
        return Bin_List.get(Ndex.intValue()).add(node);
      }
      
      // otherwise create the bin, and add it
      // the bin that will get this node
      ArrayList<T> pBin = new ArrayList<T>();
      pBin.add(node);
      
      // provide the lookupString
      lookupString.put(key, new Integer(Bin_List.size()));
      return Bin_List.add(pBin);
    }
    else
    {
      logger.severe("Cant add NULL to the BinList");
    }
    return false;
  }

  public synchronized ArrayList<T> newBin(String key)
  {
    if((key != null) && (Bin_List != null))
    {
      // return an existing bin, if already exists
      ArrayList<T> pBin = getBin(key);
      if(pBin == null)
      {
        pBin = new ArrayList<T>();
        
        // provide the lookupString
        lookupString.put(key, new Integer(Bin_List.size()));
        Bin_List.add(pBin);
      }
      return pBin;
    }
    return null;
  }

  public synchronized boolean addBin(String key)
  {
    // same as newBin, except returns true or false
    //
    // search the lookup map to get the index of the bin that
    // matches the key
    // 
    // if no match, create a new bin empty bin
    // if a match exists, then don't add a duplicate bin
    if((key != null) && (Bin_List != null))
    {
      if(newBin(key) != null)
        return true;
    }
    else
    {
      logger.severe("Cant add NULL to the BinList");
    }
    return false;
  }

  public synchronized boolean addBin(ArrayList<T> nodeList)
  {
    return addBin(nodeList, "UNKNOWN_KEY");
  }

  public synchronized boolean addBin(ArrayList<T> nodeList, String key)
  {
    // provide the lookupString
    lookupString.put(key, new Integer(Bin_List.size()));
    return Bin_List.add(nodeList);
  }

  public synchronized boolean add(T node)
  {
    return false;
  }

  public synchronized boolean containsKey(String key)
  {
    // return true if the BinList contains this key
    if(Bin_List != null)
    {
      // does the key already exist?
      Integer Ndex = lookupString.get(key);
      
      // if a matching bin exists, return it
      if((Bin_List.size() != 0) && (Ndex != null))
      {
        return true;
      }
    }
    return false;
  }

  public synchronized ArrayList<T> getBin(String key)
  {
    // return the bin that corresponds to this key
    if(Bin_List != null)
    {
      // does the key already exist?
      Integer Ndex = lookupString.get(key);
      
      // if a matching bin exists, return it
      if((Bin_List.size() != 0) && (Ndex != null))
      {
        return Bin_List.get(Ndex.intValue());
      }
    }
    return null;
  }

  public synchronized ArrayList<T> getBin(int index)
  {
    // returns a copy of the Bin at the ArrayList index
    if((index >= 0) && (Bin_List != null) && (Bin_List.size()>index))
    {
      return Bin_List.get(index);
    }
    return null;
  }

  public synchronized ArrayList<T> getMaxBin()
  {
    // returns the bin with the most elements in it
    if((Bin_List != null) && (Bin_List.size()>0))
    {
      int Max = 0;
      ArrayList<T> rtnBin = null;
      
      for(ArrayList<T> o: Bin_List)
      {
        if(Max < o.size())
        {
          Max = o.size();
          rtnBin = o;
        }
      }
      return rtnBin;
    }
    return null;
  }

  public synchronized String getMaxBinKey()
  {
    String key = null;
    // returns the key 
    if((Bin_List != null) && (Bin_List.size()>0))
    {
      int Max = 0;
      int index = 0;
      
      for(ArrayList<T> o: Bin_List)
      {
        if(Max < o.size())
        {
          Max = o.size();
          key = getKey(index);
         }
        index++;
      }
    }
    return key;
  }

  public synchronized ArrayList<T> getArrayList(int index)
  {
    // returns the n'th element in each bin
    if((index >= 0) && (Bin_List != null))
    {
      // build up a new array
      java.util.ArrayList <T> rtn = new java.util.ArrayList <T>();
      for(ArrayList <T> b: Bin_List)
      {
        if(b.size() > index)
          rtn.add(b.get(index));
      }
      return rtn;
    }
    return null;
  }

  public synchronized int size()
  {
    return Bin_List.size();
  }
  
  public synchronized boolean isEmpty()
  {
    return Bin_List.isEmpty();
  }
  
  public synchronized java.util.ArrayList <Long> getBinSizes()
  {
    java.util.ArrayList <Long> binSizes = new java.util.ArrayList<Long>();
    Long binSize = 0L;

      /* iterate through the enum, and build a string */
    if(!isEmpty())
      for (int j = 0; j < Bin_List.size(); j++)
      {
        if(Bin_List.get(j) != null)
        {
          binSize = new Long(Bin_List.get(j).size());
        }
        binSizes.add(binSize);
      }
   return binSizes; 
  }
  
  public synchronized Long getMaxBinSize()
  {
    Long Max = 0L;
    ArrayList <Long> sizes = getBinSizes();
    for(Long s: sizes)
       Max = Max < s ? s : Max; 
    return Max;
  }
  
  public synchronized Long getTotalBinSizes()
  {
    Long Total = 0L;
    ArrayList <Long> sizes = getBinSizes();
    for(Long s: sizes)
       Total += s; 
    return Total;
  }
  
  public synchronized java.util.Set <String> getKeys()
  {
    return lookupString.keySet();
  }
  

  /************************************************************
   * Method Name:
   *  getAllBins
   **/
  /**
   * Returns the value of bin_List
   *
   * @return the bin_List
   *
   ***********************************************************/
  
  public synchronized java.util.ArrayList<ArrayList<T>> getAllBins()
  {
    return Bin_List;
  }

  /************************************************************
   * Method Name:
   *  getUniqueBins
   **/
  /**
   * Returns an ArrayList of bins which only contain a single
   * object.
   *
   * @return the unique bins
   *
   ***********************************************************/
  
  public synchronized java.util.ArrayList<ArrayList<T>> getUniqueBins()
  {
    return getBinsWithSize(1);
  }

  /************************************************************
   * Method Name:
   *  getBinsWithSize
   **/
  /**
   * Returns an ArrayList of bins which contain the specified number
   * of objects.
   *
   * @return the bins
   *
   ***********************************************************/
  
  public synchronized java.util.ArrayList<ArrayList<T>> getBinsWithSize(int binSize)
  {
    // create a new bin_list of bins whose size exactly matches
    java.util.ArrayList <ArrayList<T>> Size_List = new java.util.ArrayList<ArrayList<T>>();
    
    for(ArrayList<T> o: Bin_List)
      if(o.size() == binSize)
        Size_List.add(o);
    return Size_List;
  }

  /************************************************************
   * Method Name:
   *  getBinsWithLargerSize
   **/
  /**
   * Returns an ArrayList of bins which contain more than the
   * specified number of objects.
   *
   * @return the bins
   *
   ***********************************************************/
  
  public synchronized java.util.ArrayList<ArrayList<T>> getBinsWithLargerSize(int binSize)
  {
    // create a new bin_list of bins whose size is greater than
    java.util.ArrayList <ArrayList<T>> Size_List = new java.util.ArrayList<ArrayList<T>>();
    
    for(ArrayList<T> o: Bin_List)
      if(o.size() > binSize)
        Size_List.add(o);
    return Size_List;
  }

  /************************************************************
   * Method Name:
   *  getBinsWithSmallerSize
   **/
  /**
   * Returns an ArrayList of bins which contain less than the
   * specified number of objects.
   *
   * @return the bins
   *
   ***********************************************************/
  
  public synchronized java.util.ArrayList<ArrayList<T>> getBinsWithSmallerSize(int binSize)
  {
    // create a new bin_list of bins whose size is less than
    java.util.ArrayList <ArrayList<T>> Size_List = new java.util.ArrayList<ArrayList<T>>();
    
    for(ArrayList<T> o: Bin_List)
      if(o.size() < binSize)
        Size_List.add(o);
    return Size_List;
  }

  /************************************************************
   * Method Name:
   *  toString
  **/
  /**
   * A simple string representation of the BinList
   *
   * @see java.lang.Object#toString()
   *
   * @return  a list of key/value pairs where the key represents the
   *          bin and the value is the size of the bin.
   ***********************************************************/
  
  @Override
  public String toString()
  {
    StringBuffer sbuff = new StringBuffer();
    String key = null;
    int ndex = 0;
    
    for(ArrayList<T> b: Bin_List)
    {
      if(ndex != 0)
        sbuff.append(", ");
      key = getKeyByValue(lookupString, new Integer(ndex++));
      sbuff.append(key + "=" + b.size());
    }
    return sbuff.toString();
  }

  /************************************************************
   * Method Name:
   *  iterator
  **/
  /**
   * Describe the method here
   *
   * @see java.lang.Iterable#iterator()
   *
   * @return  describe the value returned
   ***********************************************************/
  
  @Override
  public Iterator<ArrayList<T>> iterator()
  {
    // TODO Auto-generated method stub
    return Bin_List.iterator();
  }
}
