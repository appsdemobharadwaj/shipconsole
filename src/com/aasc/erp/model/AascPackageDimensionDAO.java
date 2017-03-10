
/*
  * @(#)AascPackageDimensionInfoDAO.java
 * Copyright (c) 2014 Apps Associates Pvt. Ltd.
 * All rights reserved.
 * ====================================================================
 * This class is used to retrive the package dimensions Information
 @History
 	17/11/2014  Sunanda.k    added the following code from ShipConsoleCloud version 1.2 Sunanda.k
 */
package com.aasc.erp.model;

import com.aasc.erp.bean.AascPackageDimensionInfo;

public interface AascPackageDimensionDAO {

    /**
     * This method used for get package dimensions.
     * 
     * @param locationId int, type int, queryTimeOut int and clientId int
     * 
     * @return  AascPackageDimensionInfo
     **/
     
    public AascPackageDimensionInfo getPackageDimensionInfo(int locationId, 
                                                            int type, 
                                                            int clientId);
    
    /**
     * This method used for save package dimensions.
     * 
     * @param userId int, aascPackageDimensionInfo AascPackageDimensionInfo, queryTimeOut int and clientId int
     * 
     * @return  int
     **/
    public int saveAllPackageDimensionInfo(int userId, 
                                           AascPackageDimensionInfo aascPackageDimensionInfo, 
                                           int queryTimeOut, int clinetId);
    
   /**
    * This method used for save package dimensions.
    * 
    * @param userId int, locatinId int,aascPackageDimensionInfo AascPackageDimensionInfo, queryTimeOut int and clientId int
    * 
    * @return  AascPackageDimensionInfo
    **/
    public AascPackageDimensionInfo getSavePackageDimensionInfo(int userId, 
                                                                int locationId, 
                                                                AascPackageDimensionInfo aascPackageDimensionInfo, 
                                                                int queryTimeOut,int clientId); //added by Jagadish

     /**
      * This method used for update package dimensions.
      * 
      * @param userId int, locatinId int,aascPackageDimensionInfo AascPackageDimensionInfo, queryTimeOut int and clientId int
      * 
      * @return  AascPackageDimensionInfo
      **/

    public AascPackageDimensionInfo getUpdatePackageDimensionInfo(int userId, 
                                                                  int locationId, 
                                                                  AascPackageDimensionInfo aascPackageDimensionInfoEdit, 
                                                                  int queryTimeOut, int clientId);
    
  /**
   * This method used for get units info.
   *  
   * @return  AascPackageDimensionInfo
   **/
    public AascPackageDimensionInfo getUnitsInfo();

    /**
     * This method used for get hardous units info.
     *  
     * @return  AascPackageDimensionInfo
     **/
    public AascPackageDimensionInfo getHazardousUnitsInfo();

    /**
     * This method used for get Hazardous material info.
     * 
     * @param clientId int
     *  
     * @return  AascPackageDimensionInfo
     **/
     public AascPackageDimensionInfo getHazardousMaterialIdInfo(int clientId);

    /**
     * This method used for get package uom info.
     *  
     * @return  AascPackageDimensionInfo
     **/
     public AascPackageDimensionInfo getPackageUOMInfo();


    public AascPackageDimensionInfo getHazardousMaterialIdSEInfo(int carrierCode, int clientId);
}
