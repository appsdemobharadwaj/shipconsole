package com.aasc.erp.model;

import com.aasc.erp.bean.AascGetLocInfo;


/*
 * @(#)AascGetLocDAO.java        05/09/2007
 * Copyright (c) 2005-2006 Apps Associates Pvt. Ltd.
 * All rights reserved.
 *
 * ====================================================================
 *
 * This interface is used to retrive the Inventory Organization Information
 *
 */

/**
 * AascGetLocDAO.
 * @author Eshwari
 * @version 2.0
 * 
 * */
public interface AascGetLocDAO
{
    public AascGetLocInfo getLocationInfo(int clientId);

}
