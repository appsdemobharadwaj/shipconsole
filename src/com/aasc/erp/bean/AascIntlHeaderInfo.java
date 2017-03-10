/*
 * @(#)AascIntlHeaderInfo.java        28/01/2015
 * Copyright (c) 2014-2015 Apps Associates Pvt. Ltd.
 * All rights reserved.
 */
package com.aasc.erp.bean;

/**
 * Bean Class for Header Fields of  International shipments.
 * Module with getXXX() and setXXX() methods. 
 * @author 	Y Pradeep
 * @version - 1
 ========================================================================================
     Date       Resource                 Change history
 ------------------------------------------------------------------------------------------
 * 18/12/2014   Y Pradeep        Modified orderNumber from int to String.
 * 28/01/2015   Y Pradeep        Added for International Shipping.
 * 27/10/2015   G S Shekar       Added Terms of Trade for DHL
 **/
public class AascIntlHeaderInfo {
    private String orderNumber = ""; //indicates order number for international shipments
    private int shipmentId; //indicates shipment Id for international shipments
    private String voidFlag = ""; // indicates cancellation of a delivery for international shipments
    private String intlPayerType = ""; //indicates payment type for international shipments
     private String intlAccountNumber = ""; //indicates International shipment account number
      private String intlMaskAccountNumber = ""; //indicates International shipment account number
    private String intlCountryCode = ""; //indicates country code for international shipments
    private String intlTermsOfSale = ""; //indicates Terms of sale for international shipments
    private String intlTotalCustomsValue = ""; //indicates total customs value for international shipments
    private double intlFreightCharge; //indicates freight charges need to be paid by the customer for international shipments.
    private double intlInsuranceCharge; //indicates insurance charges for international shipments. 
    private double intlTaxMiscellaneousCharge; //indicates tax miscellaneous charges for international shipments
    private String intlPurpose = ""; //indicates Commercial Invoice purpose for international shipments
    private String intlSedNumber = ""; //indicates International Shipper Export Declaration Number
    private String intlSedType = ""; //indicates International Shipper Export Declaration Type
    private String intlExemptionNumber = ""; //indicates International Exemption Number
    private String intlDocument = ""; //indicates International document
    private String intlNafta = ""; //indicates International NAFTA document
    private String intlComments = ""; //indicates comments for international shipments
    private String intlCustomerInvoiceNumber = ""; //indicates Customer Invoice Number for international shipments
    private String intlPurchaseOrderNumber = ""; //indicates Customer Purchase order Number for international shipments
    private double intlDiscount; //indicates Discount allowed for international shipments
    private String intlDeclarationStmt = ""; //indicates Declaration for international shipments
    private String intlShipFromTaxId = ""; //indicates Ship From Tax Id for international shipments
    private String intlShipToTaxId = ""; //indicates Ship To Tax Id for international shipments
    private String intlExportDate = ""; // indicates Export Date for international shipments
    private String intlBlanketPeriodBeginDate = ""; // indicates Blanket period Begin Date for international shipments
    private String intlBlanketPeriodEndDate = ""; // indicates Blanket period End Date for international shipments
    private String sedPointOfOrigin = "";
    private String sedModeOfTransport = "";
    private String sedExportDate = "";
    private String sedExportingCarrier = "";
    private String sedInBondCode = "";
    private String sedEntryNumber = "";
    private String sedLoadingPier = "";
    private String sedPortOfExport = "";
    private String sedPortOfUnloading = "";
    private String sedCarrierIdentificationCode = "";
    private String sedContainerized = "";
    private String sedHazardiousMaterial = "";
    private String sedRoutedExportTransaction = "";
    private String sedLicenceExceptionCode = "";
    private String sedECCN = "";
    private String sedLicenceNumber = "";
    private String sedLicenceDate = "";
    private String intlCurrencyCode = "";
    private String intlInvoiceDate = "";
    private String intlExportCarrier = "";
    AascAddressInfo soldToAddressInfo;
    AascAddressInfo sedAddressInfo;
    AascAddressInfo forwardAgentInfo;
    AascAddressInfo intermediateConsigneeInfo;
    private String sedPartiesToTran = "";
    private String commercialInv = "";
    private String usCertOrigin = "";
    private String naftaCertOrigin = "";
    private String shippersExportDecl = "";
    private String soldToTaxId = "";
    private String isShipToSameAsSoldTo = "";
    private String shipFromAttention = "";
    private String shipFromPhone = "";
    private String soldToAttention = "";
    private String soldToPhone = "";
    private String invoiceCurrencyCode = "";
    private int invoiceValue = 0;
    private int carrierCode = 0;
    private String packingListEnclosed;
    private int shippersLoadAndCount;
    private String bookingConfirmationNumber;
    private String declarationStmt;
    private String generateCI;
    private String importerName;
    private String importerCompName;
    private String importerPhoneNum;
    private String importerAddress1;
    private String importerAddress2;
    private String importerCity;
    private String importerState;
    private String importerPostalCode;
    private String importerCountryCode;
    private String impIntlSedNumber;
    private String impIntlSedType;
    private String recIntlSedNumber;
    private String recIntlSedType;
    private String brokerName;
    private String brokerCompName;
    private String brokerPhoneNum;
    private String brokerAddress1;
    private String brokerAddress2;
    private String brokerCity;
    private String brokerState;
    private String brokerPostalCode;
    private String brokerCountryCode;
    private String termsOfTrade = ""; //Shiva added for DHL    
    //Mahesh Added these two fields for Stamps.com International shipments header data
    private String stampsLicenseNumber = "";
    private String stampsCertificateNumber = "";
    private String otherDescribe = "";

    public AascIntlHeaderInfo() {
    }

    /** method to retreive Shipment Id.
     * @return shipmentId int
     */
    public int getShipmentId() {
        return shipmentId;
    }

    /** method to set Shipment Id.
     * @param shipmentId int
     */
    public void setShipmentId(int shipmentId) {
        this.shipmentId = shipmentId;
    }

    /** method to get the value of void flag. 
     * which indicates cancellation of shipment of that delivery
     * @return voidFlag String
     */
    public String getVoidFlag() {
        return voidFlag;
    }

    /** method to set the value of void flag.
     * which indicates cancellation of shipment of that delivery
     * @param voidFlag String
     */
    public void setVoidFlag(String voidFlag) {
        this.voidFlag = voidFlag;
    }

    /** method to retreive payment type of international shipments.
     *@return intlPayerType String
     */
    public String getIntlPayerType() {
        return intlPayerType;
    }

    /** method to set payment type of international shipments.
     *@param intlPayerType String
     */
    public void setIntlPayerType(String intlPayerType) {
        this.intlPayerType = intlPayerType;
    }

    /** method to retreive international shipment account number of customer.
     * @return intlAccountNumber String
     */
    public String getIntlAccountNumber() {
        return intlAccountNumber;
    }

    /** method to set international shipment account number of customer.
     * @param intlAccountNumber String
     */
    public void setIntlAccountNumber(String intlAccountNumber) {
        this.intlAccountNumber = intlAccountNumber;
    }
    
   public String getIntlMaskAccountNumber() {
        return intlMaskAccountNumber;
    }

    public void setIntlMaskAccountNumber(String intlMaskAccountNumber) {
        this.intlMaskAccountNumber = intlMaskAccountNumber;
    }

    /** method to retreive international shipment Country Code of customer.
     * @return intlCountryCode String
     */
    public String getIntlCountryCode() {
        return intlCountryCode;
    }

    /** method to set international shipment Country Code of customer.
     * @param intlCountryCode String
     */
    public void setIntlCountryCode(String intlCountryCode) {
        this.intlCountryCode = intlCountryCode;
    }

    /** method to retreive Terms of sale for international shipments.
     * @return intlTermsOfSale String
     */
    public String getIntlTermsOfSale() {
        return intlTermsOfSale;
    }

    /** method to set Terms of sale for international shipments.
     * @param intlTermsOfSale String
     */
    public void setIntlTermsOfSale(String intlTermsOfSale) {
        this.intlTermsOfSale = intlTermsOfSale;
    }

    /** method to retreive total customs value for international shipments.
     * @return intlTotalCustomsValue String
     */
    public String getIntlTotalCustomsValue() {
        return intlTotalCustomsValue;
    }

    /** method to set total customs value for international shipments.
     * @param intlTotalCustomsValue String
     */
    public void setIntlTotalCustomsValue(String intlTotalCustomsValue) {
        this.intlTotalCustomsValue = intlTotalCustomsValue;
    }

    /** method to retreive freight cost of international shipment.
     * @return intlFreightCharge double.
     */
    public double getIntlFreightCharge() {
        return intlFreightCharge;
    }

    /** method to set freight cost for international shipment .
     * @param intlFreightCharge double
     */
    public void setIntlFreightCharge(double intlFreightCharge) {
        this.intlFreightCharge = intlFreightCharge;
    }

    /** method to retreive Insurance charges of international shipment.
     * @return intlInsuranceCharge double.
     */
    public double getIntlInsuranceCharge() {
        return intlInsuranceCharge;
    }

    /** method to set Insurance charges for international shipment .
     * @param intlInsuranceCharge double
     */
    public void setIntlInsuranceCharge(double intlInsuranceCharge) {
        this.intlInsuranceCharge = intlInsuranceCharge;
    }

    /** method to retreive Tax Miscellaneous charges of international shipment.
     * @return intlTaxMiscellaneousCharge double.
     */
    public double getIntlTaxMiscellaneousCharge() {
        return intlTaxMiscellaneousCharge;
    }

    /** method to set Tax Miscellaneous charges for international shipment .
     * @param intlTaxMiscellaneousCharge double
     */
    public void setIntlTaxMiscellaneousCharge(double intlTaxMiscellaneousCharge) {
        this.intlTaxMiscellaneousCharge = intlTaxMiscellaneousCharge;
    }

    /** method to retreive Commercial Invoice purpose for international shipments.
     *@return intlPurpose String
     */
    public String getIntlPurpose() {
        return intlPurpose;
    }

    /** method to set Commercial Invoice purpose for international shipments.
     *@param intlPurpose String
     */
    public void setIntlPurpose(String intlPurpose) {
        this.intlPurpose = intlPurpose;
    }

    /** method to retreive  International Shipper Export Declaration Number.
     *@return intlSedNumber String
     */
    public String getIntlSedNumber() {
        return intlSedNumber;
    }

    /** method to set  International Shipper Export Declaration Number.
     *@param intlSedNumber String
     */
    public void setIntlSedNumber(String intlSedNumber) {
        this.intlSedNumber = intlSedNumber;
    }

    /** method to retreive International Shipper Export Declaration Type.
     *@return intlSedType String
     */
    public String getIntlSedType() {
        return intlSedType;
    }

    /** method to set International Shipper Export Declaration Type.
     *@param intlSedType String
     */
    public void setIntlSedType(String intlSedType) {
        this.intlSedType = intlSedType;
    }

    /** method to retreive International Exemption Number.
     *@return intlExemptionNumber String
     */
    public String getIntlExemptionNumber() {
        return intlExemptionNumber;
    }

    /** method to set International Exemption Number.
     *@param intlExemptionNumber String
     */
    public void setIntlExemptionNumber(String intlExemptionNumber) {
        this.intlExemptionNumber = intlExemptionNumber;
    }

    /** method to retreive International Document.
     *@return intlDocument String
     */
    public String getIntlDocument() {
        return intlDocument;
    }

    /** method to set International Document.
     *@param intlDocument String
     */
    public void setIntlDocument(String intlDocument) {
        this.intlDocument = intlDocument;
    }

    /** method to retreive International NAFTA Document.
     *@return intlNafta String
     */
    public String getIntlNafta() {
        return intlNafta;
    }

    /** method to set International NAFTA Document.
     *@param intlNafta String
     */
    public void setIntlNafta(String intlNafta) {
        this.intlNafta = intlNafta;
    }

    /** method to retreive comments for International shipments.
     *@return intlComments String
     */
    public String getIntlComments() {
        return intlComments;
    }

    /** method to set comments for International shipments.
     *@param intlComments String
     */
    public void setIntlComments(String intlComments) {
        this.intlComments = intlComments;
    }

    /** method to retreive customer invoice number of International shipments.
     *@return intlCustomerInvoiceNumber String
     */
    public String getIntlCustomerInvoiceNumber() {
        return intlCustomerInvoiceNumber;
    }

    /** method to set customer invoice number for International shipments.
     *@param intlCustomerInvoiceNumber String
     */
    public void setIntlCustomerInvoiceNumber(String intlCustomerInvoiceNumber) {
        this.intlCustomerInvoiceNumber = intlCustomerInvoiceNumber;
    }

    /** method to retreive customer Purchase Order number of International shipments.
     *@return intlPurchaseOrderNumber String
     */
    public String getIntlPurchaseOrderNumber() {
        return intlPurchaseOrderNumber;
    }

    /** method to set customer Purchase Order number for International shipments.
     *@param intlPurchaseOrderNumber String
     */
    public void setIntlPurchaseOrderNumber(String intlPurchaseOrderNumber) {
        this.intlPurchaseOrderNumber = intlPurchaseOrderNumber;
    }

    /** method to retreive Discount of international shipment.
     * @return intlDiscount double.
     */
    public double getIntlDiscount() {
        return intlDiscount;
    }

    /** method to set Discount for international shipment .
     * @param intlDiscount double
     */
    public void setIntlDiscount(double intlDiscount) {
        this.intlDiscount = intlDiscount;
    }

    /** method to retreive Declaration Statement  of International shipments.
     *@return intlDeclarationStmt String
     */
    public String getIntlDeclarationStmt() {
        return intlDeclarationStmt;
    }

    /** method to set Declaration Statement for International shipments.
     *@param intlDeclarationStmt String
     */
    public void setIntlDeclarationStmt(String intlDeclarationStmt) {
        this.intlDeclarationStmt = intlDeclarationStmt;
    }

    /** method to retreive ship from tax id(shipper) of International shipments.
     *@return intlShipFromTaxId String
     */
    public String getIntlShipFromTaxId() {
        return intlShipFromTaxId;
    }

    /** method to set ship from tax id for International shipments.
     *@param intlShipFromTaxId String
     */
    public void setIntlShipFromTaxId(String intlShipFromTaxId) {
        this.intlShipFromTaxId = intlShipFromTaxId;
    }

    /** method to retreive Ship To TaxId(Customer) of International shipments.
     *@return intlShipToTaxId String
     */
    public String getIntlShipToTaxId() {
        return intlShipToTaxId;
    }

    /** method to set Ship To TaxId for International shipments.
     *@param intlShipToTaxId String
     */
    public void setIntlShipToTaxId(String intlShipToTaxId) {
        this.intlShipToTaxId = intlShipToTaxId;
    }

    /**
     Method to set the ExportDate.
     @param intlExportDate Date.
     */
    public void setIntlExportDate(String intlExportDate) {
        this.intlExportDate = intlExportDate;
    }

    /**
     Method to retreive Export Date.
     @return intlExportDate Date.
     */
    public String getIntlExportDate() {
        return intlExportDate;
    }

    /**
     Method to set the Blanket Period Begin Date.
     @param intlBlanketPeriodBeginDate Date.
     */
    public void setIntlBlanketPeriodBeginDate(String intlBlanketPeriodBeginDate) {
        this.intlBlanketPeriodBeginDate = intlBlanketPeriodBeginDate;
    }

    /**
     Method returns Blanket Period Begin Date.
     @return intlBlanketPeriodBeginDate Date.
     */
    public String getIntlBlanketPeriodBeginDate() {
        return intlBlanketPeriodBeginDate;
    }

    /**
     Method to set the Blanket Period End Date.
     @param intlBlanketPeriodEndDate String.
     */
    public void setIntlBlanketPeriodEndDate(String intlBlanketPeriodEndDate) {
        this.intlBlanketPeriodEndDate = intlBlanketPeriodEndDate;
    }

    /**
     Method returns Blanket Period End Date.
     @return intlBlanketPeriodEndDate String.
     */
    public String getIntlBlanketPeriodEndDate() {
        return intlBlanketPeriodEndDate;
    }

    public String getSedPointOfOrigin() {
        return sedPointOfOrigin;
    }

    public void setSedPointOfOrigin(String sedPointOfOrigin) {
        this.sedPointOfOrigin = sedPointOfOrigin;
    }

    public String getSedModeOfTransport() {
        return sedModeOfTransport;
    }

    public void setSedModeOfTransport(String sedModeOfTransport) {
        this.sedModeOfTransport = sedModeOfTransport;
    }

    public String getSedExportDate() {
        return sedExportDate;
    }

    public void setSedExportDate(String sedExportDate) {
        this.sedExportDate = sedExportDate;
    }

    public String getSedExportingCarrier() {
        return sedExportingCarrier;
    }

    public void setSedExportingCarrier(String sedExportingCarrier) {
        this.sedExportingCarrier = sedExportingCarrier;
    }

    public String getSedInBondCode() {
        return sedInBondCode;
    }

    public void setSedInBondCode(String sedInBondCode) {
        this.sedInBondCode = sedInBondCode;
    }

    public String getSedLoadingPier() {
        return sedLoadingPier;
    }

    public void setSedLoadingPier(String sedLoadingPier) {
        this.sedLoadingPier = sedLoadingPier;
    }

    public String getSedPortOfExport() {
        return sedPortOfExport;
    }

    public void setSedPortOfExport(String sedPortOfExport) {
        this.sedPortOfExport = sedPortOfExport;
    }

    public String getSedPortOfUnloading() {
        return sedPortOfUnloading;
    }

    public void setSedPortOfUnloading(String sedPortOfUnloading) {
        this.sedPortOfUnloading = sedPortOfUnloading;
    }

    public String getSedCarrierIdentificationCode() {
        return sedCarrierIdentificationCode;
    }

    public void setSedCarrierIdentificationCode(String sedCarrierIdentificationCode) {
        this.sedCarrierIdentificationCode = sedCarrierIdentificationCode;
    }

    public String getSedContainerized() {
        return sedContainerized;
    }

    public void setSedContainerized(String sedContainerized) {
        this.sedContainerized = sedContainerized;
    }

    public String getSedHazardiousMaterial() {
        return sedHazardiousMaterial;
    }

    public void setSedHazardiousMaterial(String sedHazardiousMaterial) {
        this.sedHazardiousMaterial = sedHazardiousMaterial;
    }

    public String getSedRoutedExportTransaction() {
        return sedRoutedExportTransaction;
    }

    public void setSedRoutedExportTransaction(String sedRoutedExportTransaction) {
        this.sedRoutedExportTransaction = sedRoutedExportTransaction;
    }

    public String getSedLicenceExceptionCode() {
        return sedLicenceExceptionCode;
    }

    public void setSedLicenceExceptionCode(String sedLicenceExceptionCode) {
        this.sedLicenceExceptionCode = sedLicenceExceptionCode;
    }

    public String getSedECCN() {
        return sedECCN;
    }

    public void setSedECCN(String sedECCN) {
        this.sedECCN = sedECCN;
    }

    public String getSedLicenceNumber() {
        return sedLicenceNumber;
    }

    public void setSedLicenceNumber(String sedLicenceNumber) {
        this.sedLicenceNumber = sedLicenceNumber;
    }

    public String getSedLicenceDate() {
        return sedLicenceDate;
    }

    public void setSedLicenceDate(String sedLicenceDate) {
        this.sedLicenceDate = sedLicenceDate;
    }

    public String getIntlCurrencyCode() {
        return intlCurrencyCode;
    }

    public void setIntlCurrencyCode(String intlCurrencyCode) {
        this.intlCurrencyCode = intlCurrencyCode;
    }

    public String getIntlInvoiceDate() {
        return intlInvoiceDate;
    }

    public void setIntlInvoiceDate(String intlInvoiceDate) {
        this.intlInvoiceDate = intlInvoiceDate;
    }

    public String getIntlExportCarrier() {
        return intlExportCarrier;
    }

    public void setIntlExportCarrier(String intlExportCarrier) {
        this.intlExportCarrier = intlExportCarrier;
    }

    public AascAddressInfo getSoldToAddressInfo() {
        return soldToAddressInfo;
    }

    public void setSoldToAddressInfo(AascAddressInfo soldToAddressInfo) {
        this.soldToAddressInfo = soldToAddressInfo;
    }

    public AascAddressInfo getSedAddressInfo() {
        return sedAddressInfo;
    }

    public void setSedAddressInfo(AascAddressInfo sedAddressInfo) {
        this.sedAddressInfo = sedAddressInfo;
    }

    public String getSedPartiesToTran() {
        return sedPartiesToTran;
    }

    public void setSedPartiesToTran(String sedPartiesToTran) {
        this.sedPartiesToTran = sedPartiesToTran;
    }

    public String getCommercialInv() {
        return commercialInv;
    }

    public void setCommercialInv(String commercialInv) {
        this.commercialInv = commercialInv;
    }

    public String getUsCertOrigin() {
        return usCertOrigin;
    }

    public void setUsCertOrigin(String usCertOrigin) {
        this.usCertOrigin = usCertOrigin;
    }

    public String getNaftaCertOrigin() {
        return naftaCertOrigin;
    }

    public void setNaftaCertOrigin(String naftaCertOrigin) {
        this.naftaCertOrigin = naftaCertOrigin;
    }

    public String getShippersExportDecl() {
        return shippersExportDecl;
    }

    public void setShippersExportDecl(String shippersExportDecl) {
        this.shippersExportDecl = shippersExportDecl;
    }

    public String getSoldToTaxId() {
        return soldToTaxId;
    }

    public void setSoldToTaxId(String soldToTaxId) {
        this.soldToTaxId = soldToTaxId;
    }

    public String getIsShipToSameAsSoldTo() {
        return isShipToSameAsSoldTo;
    }

    public void setIsShipToSameAsSoldTo(String isShipToSameAsSoldTo) {
        this.isShipToSameAsSoldTo = isShipToSameAsSoldTo;
    }

    public String getShipFromAttention() {
        return shipFromAttention;
    }

    public void setShipFromAttention(String shipFromAttention) {
        this.shipFromAttention = shipFromAttention;
    }

    public String getShipFromPhone() {
        return shipFromPhone;
    }

    public void setShipFromPhone(String shipFromPhone) {
        this.shipFromPhone = shipFromPhone;
    }

    public String getSoldToAttention() {
        return soldToAttention;
    }

    public void setSoldToAttention(String soldToAttention) {
        this.soldToAttention = soldToAttention;
    }

    public String getSoldToPhone() {
        return soldToPhone;
    }

    public void setSoldToPhone(String soldToPhone) {
        this.soldToPhone = soldToPhone;
    }

    public String getInvoiceCurrencyCode() {
        return invoiceCurrencyCode;
    }

    public void setInvoiceCurrencyCode(String invoiceCurrencyCode) {
        this.invoiceCurrencyCode = invoiceCurrencyCode;
    }

    public int getInvoiceValue() {
        return invoiceValue;
    }

    public void setInvoiceValue(int invoiceValue) {
        this.invoiceValue = invoiceValue;
    }

    public int getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(int carrierCode) {
        this.carrierCode = carrierCode;
    }

    public AascAddressInfo getForwardAgentInfo() {
        return forwardAgentInfo;
    }

    public void setForwardAgentInfo(AascAddressInfo forwardAgentInfo) {
        this.forwardAgentInfo = forwardAgentInfo;
    }

    public AascAddressInfo getIntermediateConsigneeInfo() {
        return intermediateConsigneeInfo;
    }

    public void setIntermediateConsigneeInfo(AascAddressInfo intermediateConsigneeInfo) {
        this.intermediateConsigneeInfo = intermediateConsigneeInfo;
    }

    public void setSedEntryNumber(String sedEntryNumber) {
        this.sedEntryNumber = sedEntryNumber;
    }

    public String getSedEntryNumber() {
        return sedEntryNumber;
    }

    public void setPackingListEnclosed(String packingListEnclosed) {
        this.packingListEnclosed = packingListEnclosed;
    }

    public String getPackingListEnclosed() {
        return packingListEnclosed;
    }

    public void setShippersLoadAndCount(int shippersLoadAndCount) {
        this.shippersLoadAndCount = shippersLoadAndCount;
    }

    public int getShippersLoadAndCount() {
        return shippersLoadAndCount;
    }

    public void setBookingConfirmationNumber(String bookingConfirmationNumber) {
        this.bookingConfirmationNumber = bookingConfirmationNumber;
    }

    public String getBookingConfirmationNumber() {
        return bookingConfirmationNumber;
    }

    public void setDeclarationStmt(String declarationStmt) {
        this.declarationStmt = declarationStmt;
    }

    public String getDeclarationStmt() {
        return declarationStmt;
    }

    public void setGenerateCI(String generateCI) {
        this.generateCI = generateCI;
    }

    public String getGenerateCI() {
        return generateCI;
    }

    public void setImporterName(String importerName) {
        this.importerName = importerName;
    }

    public String getImporterName() {
        return importerName;
    }

    public void setImporterCompName(String importerCompName) {
        this.importerCompName = importerCompName;
    }

    public String getImporterCompName() {
        return importerCompName;
    }

    public void setImporterPhoneNum(String importerPhoneNum) {
        this.importerPhoneNum = importerPhoneNum;
    }

    public String getImporterPhoneNum() {
        return importerPhoneNum;
    }

    public void setImporterAddress1(String importerAddress1) {
        this.importerAddress1 = importerAddress1;
    }

    public String getImporterAddress1() {
        return importerAddress1;
    }

    public void setImporterAddress2(String importerAddress2) {
        this.importerAddress2 = importerAddress2;
    }

    public String getImporterAddress2() {
        return importerAddress2;
    }

    public void setImporterCity(String importerCity) {
        this.importerCity = importerCity;
    }

    public String getImporterCity() {
        return importerCity;
    }

    public void setImporterState(String importerState) {
        this.importerState = importerState;
    }

    public String getImporterState() {
        return importerState;
    }

    public void setImporterPostalCode(String importerPostalCode) {
        this.importerPostalCode = importerPostalCode;
    }

    public String getImporterPostalCode() {
        return importerPostalCode;
    }

    public void setImporterCountryCode(String importerCountryCode) {
        this.importerCountryCode = importerCountryCode;
    }

    public String getImporterCountryCode() {
        return importerCountryCode;
    }

    public void setImpIntlSedNumber(String impIntlSedNumber) {
        this.impIntlSedNumber = impIntlSedNumber;
    }

    public String getImpIntlSedNumber() {
        return impIntlSedNumber;
    }

    public void setImpIntlSedType(String impIntlSedType) {
        this.impIntlSedType = impIntlSedType;
    }
//SC_UPS Shipping - Making Default sender tax id type to BUSINESS_NATIONAL

    public String getImpIntlSedType() {
        if(impIntlSedType.length() < 1){
            impIntlSedType = "BUSINESS_NATIONAL";
        }
        return impIntlSedType;
    }

    public void setRecIntlSedNumber(String recIntlSedNumber) {
        this.recIntlSedNumber = recIntlSedNumber;
    }

    public String getRecIntlSedNumber() {
        return recIntlSedNumber;
    }

    public void setRecIntlSedType(String recIntlSedType) {
        this.recIntlSedType = recIntlSedType;
    }

    public String getRecIntlSedType() {
        return recIntlSedType;
    }

    public void setBrokerName(String brokerName) {
        this.brokerName = brokerName;
    }

    public String getBrokerName() {
        return brokerName;
    }

    public void setBrokerCompName(String brokerCompName) {
        this.brokerCompName = brokerCompName;
    }

    public String getBrokerCompName() {
        return brokerCompName;
    }

    public void setBrokerPhoneNum(String brokerPhoneNum) {
        this.brokerPhoneNum = brokerPhoneNum;
    }

    public String getBrokerPhoneNum() {
        return brokerPhoneNum;
    }

    public void setBrokerAddress1(String brokerAddress1) {
        this.brokerAddress1 = brokerAddress1;
    }

    public String getBrokerAddress1() {
        return brokerAddress1;
    }

    public void setBrokerAddress2(String brokerAddress2) {
        this.brokerAddress2 = brokerAddress2;
    }

    public String getBrokerAddress2() {
        return brokerAddress2;
    }

    public void setBrokerCity(String brokerCity) {
        this.brokerCity = brokerCity;
    }

    public String getBrokerCity() {
        return brokerCity;
    }

    public void setBrokerState(String brokerState) {
        this.brokerState = brokerState;
    }

    public String getBrokerState() {
        return brokerState;
    }

    public void setBrokerPostalCode(String brokerPostalCode) {
        this.brokerPostalCode = brokerPostalCode;
    }

    public String getBrokerPostalCode() {
        return brokerPostalCode;
    }

    public void setBrokerCountryCode(String brokerCountryCode) {
        this.brokerCountryCode = brokerCountryCode;
    }

    public String getBrokerCountryCode() {
        return brokerCountryCode;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setTermsOfTrade(String termsOfTrade) {
        this.termsOfTrade = termsOfTrade;
    }

    public String getTermsOfTrade() {
        return termsOfTrade;
    }

   public void setStampsLicenseNumber(String stampsLicenseNumber) {
        this.stampsLicenseNumber = stampsLicenseNumber;
    }

    public String getStampsLicenseNumber() {
        return stampsLicenseNumber;
    }

    public void setStampsCertificateNumber(String stampsCertificateNumber) {
        this.stampsCertificateNumber = stampsCertificateNumber;
    }

    public String getStampsCertificateNumber() {
        return stampsCertificateNumber;
    }

    public void setOtherDescribe(String otherDescribe) {
        this.otherDescribe = otherDescribe;
    }

    public String getOtherDescribe() {
        return otherDescribe;
    }
}
