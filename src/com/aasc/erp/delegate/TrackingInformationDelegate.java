package com.aasc.erp.delegate;

import com.aasc.erp.bean.AascProfileOptionsBean;
import com.aasc.erp.bean.AascShipMethodInfo;
import com.aasc.erp.bean.AascTrackingOrderInfo;
import com.aasc.erp.model.AascDAOFactory;
import com.aasc.erp.model.AascProfileOptionsDAO;
import com.aasc.erp.model.AascShipMethodDAO;
import com.aasc.erp.model.AascTrackingOrderInfoDAO;

import java.util.LinkedList;

import javax.servlet.http.HttpSession;

/**
 * TrackingInformationDelegate class is delegate class for Tracking Information.    16/01/2015
 * @version   1
 * @author    Y Pradeep
 * History
 *
 * 16-Jan-2015  Y Pradeep       Added this file for TrackingInformationAction class.
 * 19-Jan-2015  Y Pradeep       Added javadoc comments.
 * 20-Jan-2015  Y Pradeep       Modified retun type of getOrderInfo() method. Modified auther and version, also removed commented code.
 * 14-Mar-2015  Eshwari M       Modified getTrackingOrderInfo() to return info instead of int 
 */
public class TrackingInformationDelegate {
    
    private int returnStatus = 1; 
    AascDAOFactory aascDAOFactory ;
    AascTrackingOrderInfoDAO aascTrackingOrderInfoDAO;
    AascProfileOptionsBean aascProfileOptionsBean = null;
    AascShipMethodInfo aascShipmethodInfo = null;
    AascTrackingOrderInfo aascTrackingOrderInfo = null;    
    
    public TrackingInformationDelegate() {
        aascDAOFactory = AascDAOFactory.getAascDAOFactory(1); // this method returns the object of the AascDAOFactory class
        aascTrackingOrderInfoDAO = aascDAOFactory.getAascTrackingOrderInfoDAO();
    }

    /** This method is used get the profile options details and setting them in session.
     * @param sessionList
     * @param session.
     */
    public void getProfileOptionsBean(LinkedList sessionList, HttpSession session){
        
        AascProfileOptionsDAO aascProfileOptionsDAO = aascDAOFactory.getAascProfileOptionsDAO();
        aascProfileOptionsBean = aascProfileOptionsDAO.getProfileOptionsBean(sessionList);

        session.setAttribute("ProfileOptionsInfo", aascProfileOptionsBean);
    }

    /** This method is used to get Ship Method details.
     * @param sessionList
     * @return aascShipmethodInfo object type of AascShipMethodInfo class.
     */
    public AascShipMethodInfo getShipMethodInfo(LinkedList sessionList){
    
        AascShipMethodDAO aascShipMethodDAO = aascDAOFactory.getAascShipMethodDAO();
        aascShipmethodInfo = aascShipMethodDAO.getShipMethodInfo(sessionList);
        
        return aascShipmethodInfo;
    }

    /** This method is used to get Order information from databse.
     * @param inputNumber
     * @param queryTimeOutInt
     * @param clientId
     * @param locationId
     * @return returnStatus of type int.
     */
    public AascTrackingOrderInfo getTrackingOrderInfo(String inputNumber, int clientId, int locationId){
    
        aascTrackingOrderInfo = aascTrackingOrderInfoDAO.getTrackingOrderInfo(inputNumber, clientId, locationId);
        return aascTrackingOrderInfo;
    }

    /** THis method is used to get Order names from database.
     * @param inputNumber
     * @param queryTimeOutInt
     * @param clientId
     * @param locationId
     * @return aascTrackingOrderInfo object type of AascTrackingOrderInfo class.
     */
    public AascTrackingOrderInfo getOrderNames(String inputNumber, int clientId, int locationId){
    
        aascTrackingOrderInfo = aascTrackingOrderInfoDAO.getOrderNames(inputNumber, clientId, locationId);
        returnStatus = aascTrackingOrderInfo.getReturnStatus();
        
        return aascTrackingOrderInfo;
    }

    /** This method is used to Order details from database.
     * @param inputNumberList
     * @param queryTimeOutInt
     * @param clientId
     * @param locationId
     * @return aascTrackingOrderInfo of type AascTrackingOrderInfo.
     */
    public AascTrackingOrderInfo getOrderInfo(String inputNumberList,  int clientId, int locationId){
    
        aascTrackingOrderInfo = aascTrackingOrderInfoDAO.getOrderInfo(inputNumberList, clientId, locationId);
        
        return aascTrackingOrderInfo;
    }
}
