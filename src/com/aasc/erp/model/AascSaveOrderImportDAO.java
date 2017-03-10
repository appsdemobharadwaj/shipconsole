package com.aasc.erp.model;

import com.aasc.erp.bean.AascERPImportOrdHTBL;
import com.aasc.erp.bean.AascShipmentOrderInfo;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * AascSaveOrderImportDAO is an interface has abstract method 
      which is implemented by the  AascOracleSaveOrderImportDAO class.

 * @author 	Jagadish Jain
 * @version 1
 * @since
 * History
 *
 */
 


public interface AascSaveOrderImportDAO {

    
    /**
    * saveImportOrders() method of AascOracleSaveOrderImport class has four parameters clientId, locationId,userId  and AascERPImportOrdHTBL
    * object and returns a Hashmap object with import id and error list.
    * This method is used to send the import order file data to the staging table where validations are perfomed. 
    * The errored out data will remain in staging table while success will be moved to shipments header table. 
    * @param int clientId, int locationId, int userId,AascERPImportOrdHTBL AascERPImportOrdHTBL
    * @return Hashmap
    */
    public HashMap saveImportOrders(int clientId, int locationId,int userId, AascERPImportOrdHTBL AascERPImportOrdHTBL);
    /**
    * getImportErrorStatus() method of AascOracleSaveOrderImport class has three parameters clientId, locationId and importId
    * and returns a LinkedList object with errored out data.
    * This method takes import id as one of the input and returns the failed records for that import id. 
    * @param int clientId, int locationId,int importId
    * @return LinkedList
    */
    public LinkedList getImportErrorStatus(int clientId, int locationId,int importId);
}
