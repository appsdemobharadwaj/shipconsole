<%/*========================================================================+
|  DESCRIPTION                                                              |
|    JSP For Getting Estimated Transaction Ranges                           |                                                 
|    Version - 1                                                            |       
|    Author: Suman Gunda                                                    |
|    Create On: 15/02/2016
|   HISTORY
|	Date	         Resource           Modification
                          |
+===========================================================================*/%>

<%@page import="com.aasc.erp.util.*"%>
<%@page import="com.aasc.erp.model.*"%>
<%@page import="java.util.logging.*"%>
<%@page import="java.util.*"%>
<%! private static Logger logger=AascLogger.getLogger("aascAjaxEstimatedTransactionRange"); %>
<%

    String customerType = request.getParameter("customerType");
    String transactionRange = request.getParameter("transactionRange");
    String durationType = request.getParameter("durationType");
    String transactionCount = request.getParameter("transactionCount");
    logger.info("customer type:::::"+customerType);
    logger.info("transaction coutn:::"+transactionCount);
    
    AascUserControlDAO aascUserControlDAO = new AascOracleUserControlDAO();
    String responseValues = "@@@";
    if(transactionCount != null && !"".equalsIgnoreCase(transactionCount) && !"null".equalsIgnoreCase(transactionCount)){
        
        double fee = aascUserControlDAO.getTotalFeeOnTransactionCount(customerType, transactionCount);
        responseValues = responseValues + fee;
    
    }else{
        LinkedList list = aascUserControlDAO.getPricingDetails(customerType,transactionRange,durationType);
        Iterator it = list.iterator();
        while(it.hasNext()){
            responseValues = responseValues + it.next() ;
        }
    }
    logger.info("response:::::"+responseValues);
    if(responseValues.equals("-1"))
    {
    out.write("***");
    }
    else
    {
    out.write(responseValues);
    }

%>
