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
 *        file: OSM_SoftwareElement.java
 *
 *  Created on: Aug 14, 2012
 *      Author: meier3
 ********************************************************************/
package gov.llnl.lc.system;

import java.io.Serializable;

/**********************************************************************
 * Describe purpose and responsibility of OSM_SoftwareElement
 * <p>
 * @see  related classes and interfaces
 *
 * @author meier3
 * 
 * @version Aug 14, 2012 5:04:24 PM
 **********************************************************************/
public class SoftwareComponent implements Serializable
{
  /**  describe serialVersionUID here **/
  private static final long serialVersionUID = 6662798614949270380L;

  /* this information comes primarily from the rpm */
  
  private String Name;
  private String VersionReleaseName;
  private String Version;
  private String Release;
  private String Size;
  private String Summary;
  private String Description;
  private String BuildDate;
  private String InstallDate;
  private String Contact;
  private String License;
  private String Other;
  /************************************************************
   * Method Name:
   *  getName
   **/
  /**
   * Returns the value of name
   *
   * @return the name
   *
   ***********************************************************/
  
  public String getName()
  {
    return Name;
  }
  /************************************************************
   * Method Name:
   *  setName
   **/
  /**
   * Sets the value of name
   *
   * @param name the name to set
   *
   ***********************************************************/
  public void setName(String name)
  {
    Name = name;
  }
  /************************************************************
   * Method Name:
   *  getVersionReleaseName
   **/
  /**
   * Returns the value of versionReleaseName
   *
   * @return the versionReleaseName
   *
   ***********************************************************/
  
  public String getVersionReleaseName()
  {
    return VersionReleaseName;
  }
  /************************************************************
   * Method Name:
   *  setVersionReleaseName
   **/
  /**
   * Sets the value of versionReleaseName
   *
   * @param versionReleaseName the versionReleaseName to set
   *
   ***********************************************************/
  public void setVersionReleaseName(String versionReleaseName)
  {
    VersionReleaseName = versionReleaseName;
  }
  /************************************************************
   * Method Name:
   *  getVersion
   **/
  /**
   * Returns the value of version
   *
   * @return the version
   *
   ***********************************************************/
  
  public String getVersion()
  {
    return Version;
  }
  /************************************************************
   * Method Name:
   *  setVersion
   **/
  /**
   * Sets the value of version
   *
   * @param version the version to set
   *
   ***********************************************************/
  public void setVersion(String version)
  {
    Version = version;
  }
  /************************************************************
   * Method Name:
   *  getRelease
   **/
  /**
   * Returns the value of release
   *
   * @return the release
   *
   ***********************************************************/
  
  public String getRelease()
  {
    return Release;
  }
  /************************************************************
   * Method Name:
   *  setRelease
   **/
  /**
   * Sets the value of release
   *
   * @param release the release to set
   *
   ***********************************************************/
  public void setRelease(String release)
  {
    Release = release;
  }
  /************************************************************
   * Method Name:
   *  getSummary
   **/
  /**
   * Returns the value of summary
   *
   * @return the summary
   *
   ***********************************************************/
  
  public String getSummary()
  {
    return Summary;
  }
  /************************************************************
   * Method Name:
   *  setSummary
   **/
  /**
   * Sets the value of summary
   *
   * @param summary the summary to set
   *
   ***********************************************************/
  public void setSummary(String summary)
  {
    Summary = summary;
  }
  /************************************************************
   * Method Name:
   *  getDescription
   **/
  /**
   * Returns the value of description
   *
   * @return the description
   *
   ***********************************************************/
  
  public String getDescription()
  {
    return Description;
  }
  /************************************************************
   * Method Name:
   *  SoftwareComponent
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   * @param   describe the parameters if any
   *
   * @param name
   * @param versionReleaseName
   * @param version
   * @param release
   * @param size
   * @param summary
   * @param description
   * @param buildDate
   * @param installDate
   * @param contact
   * @param license
   * @param other
   ***********************************************************/
  public SoftwareComponent(String name, String versionReleaseName, String version, String release,
      String size, String summary, String description, String buildDate, String installDate,
      String contact, String license, String other)
  {
    super();
    Name = name;
    VersionReleaseName = versionReleaseName;
    Version = version;
    Release = release;
    Size = size;
    Summary = summary;
    Description = description;
    BuildDate = buildDate;
    InstallDate = installDate;
    Contact = contact;
    License = license;
    Other = other;
  }
  /************************************************************
   * Method Name:
   *  getSize
   **/
  /**
   * Returns the value of size
   *
   * @return the size
   *
   ***********************************************************/
  
  public String getSize()
  {
    return Size;
  }
  /************************************************************
   * Method Name:
   *  setSize
   **/
  /**
   * Sets the value of size
   *
   * @param size the size to set
   *
   ***********************************************************/
  public void setSize(String size)
  {
    Size = size;
  }
  /************************************************************
   * Method Name:
   *  setDescription
   **/
  /**
   * Sets the value of description
   *
   * @param description the description to set
   *
   ***********************************************************/
  public void setDescription(String description)
  {
    Description = description;
  }
  /************************************************************
   * Method Name:
   *  getBuildDate
   **/
  /**
   * Returns the value of buildDate
   *
   * @return the buildDate
   *
   ***********************************************************/
  
  public String getBuildDate()
  {
    return BuildDate;
  }
  /************************************************************
   * Method Name:
   *  setBuildDate
   **/
  /**
   * Sets the value of buildDate
   *
   * @param buildDate the buildDate to set
   *
   ***********************************************************/
  public void setBuildDate(String buildDate)
  {
    BuildDate = buildDate;
  }
  /************************************************************
   * Method Name:
   *  getInstallDate
   **/
  /**
   * Returns the value of installDate
   *
   * @return the installDate
   *
   ***********************************************************/
  
  public String getInstallDate()
  {
    return InstallDate;
  }
  /************************************************************
   * Method Name:
   *  setInstallDate
   **/
  /**
   * Sets the value of installDate
   *
   * @param installDate the installDate to set
   *
   ***********************************************************/
  public void setInstallDate(String installDate)
  {
    InstallDate = installDate;
  }
  /************************************************************
   * Method Name:
   *  getContact
   **/
  /**
   * Returns the value of contact
   *
   * @return the contact
   *
   ***********************************************************/
  
  public String getContact()
  {
    return Contact;
  }
  /************************************************************
   * Method Name:
   *  setContact
   **/
  /**
   * Sets the value of contact
   *
   * @param contact the contact to set
   *
   ***********************************************************/
  public void setContact(String contact)
  {
    Contact = contact;
  }
  /************************************************************
   * Method Name:
   *  getLicense
   **/
  /**
   * Returns the value of license
   *
   * @return the license
   *
   ***********************************************************/
  
  public String getLicense()
  {
    return License;
  }
  /************************************************************
   * Method Name:
   *  setLicense
   **/
  /**
   * Sets the value of license
   *
   * @param license the license to set
   *
   ***********************************************************/
  public void setLicense(String license)
  {
    License = license;
  }
  /************************************************************
   * Method Name:
   *  getOther
   **/
  /**
   * Returns the value of other
   *
   * @return the other
   *
   ***********************************************************/
  
  public String getOther()
  {
    return Other;
  }
  /************************************************************
   * Method Name:
   *  setOther
   **/
  /**
   * Sets the value of other
   *
   * @param other the other to set
   *
   ***********************************************************/
  public void setOther(String other)
  {
    Other = other;
  }

  
  
  /************************************************************
   * Method Name:
   *  SoftwareComponent
  **/
  /**
   * Describe the constructor here
   *
   * @see     describe related java objects
   *
   * @param   describe the parameters if any
   *
   ***********************************************************/
  public SoftwareComponent()
  {
    super();
    // TODO Auto-generated constructor stub
  }

  /************************************************************
   * Method Name:
   *  toString
  **/
  /**
   * Describe the method here
   *
   * @see java.lang.Object#toString()
  
   * @param   describe the parameters
   *
   * @return  describe the value returned
   * @return
   ***********************************************************/
  
  @Override
  public String toString()
  {
    return "SoftwareComponent [Name=" + Name + ", VersionReleaseName=" + VersionReleaseName
        + ", Version=" + Version + ", Release=" + Release + ", Size=" + Size + ", Summary="
        + Summary + ", Description=" + Description + ", BuildDate=" + BuildDate + ", InstallDate="
        + InstallDate + ", Contact=" + Contact + ", License=" + License + ", Other=" + Other + "]";
  }
 
}
