package com.aasc.erp.model;

import com.aasc.erp.bean.AascCustomerAccountNumbers;

import java.util.LinkedList;

public interface AascCustAccountNumsDAO {


public String saveCustAccountNums(AascCustomerAccountNumbers aascCustomerAccountNumbers);

public LinkedList getCustAccountNums(int customerLocationid);

}
