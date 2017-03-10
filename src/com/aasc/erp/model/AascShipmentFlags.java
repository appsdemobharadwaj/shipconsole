/*
 * @(#)AascShipmentFlags.java        08/03/2006
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
package com.aasc.erp.model;

import com.aasc.erp.bean.AascShipmentHeaderInfo;
import com.aasc.erp.util.AascLogger;

import java.util.logging.Logger;


/**
 AascShipmentFlags sets all the flags to disable or enable the Buttons, DropDownList of aascShipments.jsp.
 */
public class

/* @author Swapna Soni.
 @version 2.
 */

/* ========================================================================================
 Date        Resource       Change history
 ------------------------------------------------------------------------------------------
 02/05/2006  Swapna Soni    Code cleanup
 09/05/2006  Swapna Soni    Change the value of the saveDisabledFlag and clearDisabledFlag in else block
 10/06/2009  Madhavi        Modified logging code
 05/03/2015    Sanjay & Khaja Added code for new UI changes.
 ========================================================================================*/
AascShipmentFlags {
    private String voidDisableFlag = 
        ""; // holds the value of the voidDisableFlag 
    private String printDisableFlag = 
        ""; // holds the value of the printDisableFlag 
    private String saveDisableFlag = 
        ""; // holds the value of the saveDisableFlag 
    private String clearDisableFlag = 
        ""; // holds the value of the clearDisableFlag 
    private String printerDisableFlag = 
        ""; // holds the value of the printerDisableFlag 
    private String templateDisableFlag = 
        ""; // holds the value of the templateDisableFlag 
    private String tpDisable = 
        ""; // holds the value of the third party disable value
    private String readOnlyFlag = ""; // holds the value of the readOnlyFlag 
    private String selectDisableFlag = 
        ""; // holds the value of the selectDisableFlag 
    private String fedexDisable = ""; // holds the value of the fedexDisable
    private static Logger logger = 
        AascLogger.getLogger("AascShipmentFlags"); // this method returns the object of the logger;

    //added

    String disableFlag = "";
    String editShip = 
        "disabled"; // the flag to enable or disable editing of ship method 
    String shipButtonFlag = 
        ""; // the flag which enables or disables ship buton.
    String ShipReadonlyFlag = "";
    String FlagUPSReadonly = "";
    String AddButtonFlag = "";
    String ShipDisableFlag = "";


public AascShipmentFlags(){
    
}
    /**
     * This is constructor.Depending upon the condition  it sets the value of the various flags .
     * @param ShipmentHeaderInfo Object .
     **/
    public AascShipmentFlags(AascShipmentHeaderInfo ShipmentHeaderInfo) {
        logger.info("Entered AascShipmentFlags method");
        try {
            if (nullStringToSpace(ShipmentHeaderInfo.getShipFlag()).equalsIgnoreCase("Y") && 
                !(nullStringToSpace(ShipmentHeaderInfo.getVoidFlag()).equalsIgnoreCase("Y"))) {
                logger.info("ShipFlag is Y and VoidFlag is N");
                saveDisableFlag = "DISABLED";
                readOnlyFlag = "READONLY";
                selectDisableFlag = "DISABLED";
                clearDisableFlag = "DISABLED";
                printDisableFlag = "";
                voidDisableFlag = "";
            } else if (nullStringToSpace(ShipmentHeaderInfo.getShipFlag()).equalsIgnoreCase("Y") && 
                       (nullStringToSpace(ShipmentHeaderInfo.getVoidFlag()).equalsIgnoreCase("Y"))) {
                logger.info("ShipFlag is Y and VoidFlag is Y");
                saveDisableFlag = "DISABLED";
                printDisableFlag = "DISABLED";
                voidDisableFlag = "DISABLED";
                clearDisableFlag = "DISABLED";
                printerDisableFlag = "DISABLED";
                templateDisableFlag = "DISABLED";
            } else {
                logger.info("inside the else block");
                saveDisableFlag = "";
                printDisableFlag = "DISABLED";
                voidDisableFlag = "DISABLED";
                clearDisableFlag = "";
                printerDisableFlag = "DISABLED";
                templateDisableFlag = "DISABLED";
            }
            if (nullStringToSpace(ShipmentHeaderInfo.getCarrierPaymentMethod()).equalsIgnoreCase("THIRD PARTY BILLING")) {
                tpDisable = "";
            } else {
                tpDisable = "DISABLED";
            }
            if (nullStringToSpace(ShipmentHeaderInfo.getShipMethodMeaning()).startsWith("FDX")) {
                fedexDisable = "";
            } else {
                fedexDisable = "DISABLED";
            }
            logger.info("ShipmentHeaderInfo.getManualTrackingFlag() in ShipmentFlags=" + 
                        ShipmentHeaderInfo.getManualTrackingFlag());
            if (nullStringToSpace(ShipmentHeaderInfo.getManualTrackingFlag()).equalsIgnoreCase("Y")) {
                voidDisableFlag = "DISABLED";
            }
            logger.info("the value of the void disabled flag in the ShipmentFlag class =" + 
                        voidDisableFlag);
            if (!(nullStringToSpace(ShipmentHeaderInfo.getShipFlag()).equalsIgnoreCase("Y")) && 
                !(nullStringToSpace(ShipmentHeaderInfo.getCustomerName()).equalsIgnoreCase(""))) {
                saveDisableFlag = "";
                clearDisableFlag = "";
            }
        } // closing the try block
        catch (Exception e) {
            logger.severe("Exception::"+e.getMessage());
        } // closing the catch block
    } // closing the constructor    

    /**
     * nullStringToSpace() method is used when the string is null it replaces with space.
     * @param object of type Object
     * @return object of type String.
     */
    public String nullStringToSpace(Object object) {
        String spcStr = "";

        if (object == null) {
            return spcStr;
        } else {
            return object.toString();
        }
    }

    /**
     * This method returns the saveDisableFlag value.
     * @return saveDisableFlag String.
     */
    public String getSaveDisableFlag() {
        return saveDisableFlag;
    }

    /**
     * This method returns the readOnlyFlag value.
     * @return readOnlyFlag String.
     */
    public String getReadOnlyFlag() {
        return readOnlyFlag;
    }

    /**
     * This method returns the printDisableFlag value.
     * @return printDisableFlag String.
     */
    public String getPrintDisableFlag() {
        return printDisableFlag;
    }

    /**
     * This method returns the voidDisableFlag value.
     * @return voidDisableFlag String.
     */
    public String getVoidDisableFlag() {
        return voidDisableFlag;
    }

    /**
     * This method returns the clearDisableFlag value.
     * @return clearDisableFlag String.
     */
    public String getClearDisableFlag() {
        return clearDisableFlag;
    }

    /**
     * This method returns the printerDisableFlag value.
     * @return printerDisableFlag String.
     */
    public String getPrinterDisableFlag() {
        return printerDisableFlag;
    }

    /**
     * This method returns the templateDisableFlag value.
     * @return templateDisableFlag String.
     */
    public String getTemplateDisableFlag() {
        return templateDisableFlag;
    }

    /**
     * This method returns the tpDisable value.
     * @return tpDisable String.
     */
    public String getTpDisable() {
        return tpDisable;
    }

    /**
     * This method returns the selectDisableFlag value.
     * @return selectDisableFlag String.
     */
    public String getSelectDisableFlag() {
        return selectDisableFlag;
    }

    /**
     * This method returns the fedexDisable value.
     * @return fedexDisable String.
     */
    public String getFedexDisable() {
        return fedexDisable;
    }

    public void setDisableFlag(String disableFlag) {
        this.disableFlag = disableFlag;
    }

    public String getDisableFlag() {
        return disableFlag;
    }

    public void setEditShip(String editShip) {
        this.editShip = editShip;
    }

    public String getEditShip() {
        return editShip;
    }

    public void setShipButtonFlag(String shipButtonFlag) {
        this.shipButtonFlag = shipButtonFlag;
    }

    public String getShipButtonFlag() {
        return shipButtonFlag;
    }


    public String zeroTospc(int valueInt) {

        if (valueInt == 0) {
            return "";
        } else {
            return (String.valueOf(valueInt));
        }
    }

    public void setShipReadonlyFlag(String shipReadonlyFlag) {
        this.ShipReadonlyFlag = shipReadonlyFlag;
    }

    public String getShipReadonlyFlag() {
        return ShipReadonlyFlag;
    }

    public void setFlagUPSReadonly(String flagUPSReadonly) {
        this.FlagUPSReadonly = flagUPSReadonly;
    }

    public String getFlagUPSReadonly() {
        return FlagUPSReadonly;
    }

    public void setAddButtonFlag(String addButtonFlag) {
        this.AddButtonFlag = addButtonFlag;
    }

    public String getAddButtonFlag() {
        return AddButtonFlag;
    }

    public void setShipDisableFlag(String shipDisableFlag) {
        this.ShipDisableFlag = shipDisableFlag;
    }

    public String getShipDisableFlag() {
        return ShipDisableFlag;
    }
}// end of class AascShipmentFlags
