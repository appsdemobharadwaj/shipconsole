
<%       
/*==========================================================================+
|  DESCRIPTION                                                              |
|    
|   @Author: Suman Gunda
|   @Date:   22/12/2015 
+===========================================================================*/%>

<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
<%    
  try{
  String filename = "aascShipToUploadReport.csv";
  LinkedList errorList=(LinkedList)session.getAttribute("shipToErrorList");
  Iterator it=errorList.iterator();
  
 // String filepath = "\\";   
  String applicationPath = application.getRealPath("/");
  System.out.println("applicationPath"+applicationPath);
  response.setContentType("APPLICATION/OCTET-STREAM");   
  response.setHeader("Content-Disposition","attachment; filename=\"" + filename + "\"");
  
  FileWriter writer = new FileWriter(applicationPath+filename);
 
	    writer.append("Error Report:");
            writer.append(',');
            
            writer.append(',');
            
            writer.append(',');
            
            writer.append(',');
            
            writer.append(',');
            
            writer.append(',');
            writer.append("Reference:");
            writer.append(',');
            writer.append("E-Error");
	    writer.append('\n');
 
	    writer.append("Customer Name");
	    writer.append(',');
      writer.append("Location Name");
	    writer.append(',');
	    writer.append("Error Description");
            writer.append(',');
	    writer.append("Status");
            writer.append(',');
	    writer.append("Upload ID");
            writer.append(',');
            
            writer.append(',');
	    
            writer.append(',');
            writer.append(',');
	    writer.append(',');
            writer.append('\n');
 
 
	    //generate whatever data you want
 
// logger.info("size::::"+errorList.size());
 if(errorList.size() != 0){
 while (it.hasNext()) {
  HashMap<String, ?> map1 = (HashMap<String, ?>)it.next();
        
            writer.append((String)map1.get("CUSTOMER_NAME"));
            writer.append(',');
            writer.append((String)map1.get("LOCATION_NAME"));
            writer.append(',');
            writer.append((String)map1.get("ERROR_CODE"));
            writer.append(','); 
            writer.append((String)map1.get("STATUS"));
            writer.append(',');     
            String impor_id=((BigDecimal)map1.get("UPLOAD_ID")).toString();    
            writer.append(impor_id);
        
        writer.append(',');
        writer.append('\n');
  }
  }else{
            writer.append("");
            writer.append(',');
            writer.append("");
            writer.append(',');
            writer.append("Ship To Locations Uploaded Successfully");
            writer.append(',');
            writer.append('\n');
        }
  
  
 
 
	    writer.flush();
	    writer.close();
  
  
 FileInputStream fileInputStream=null;
  try{
   fileInputStream= new FileInputStream(applicationPath + filename);  
  }
  catch(Exception e){
  e.printStackTrace();
  }
  int i;   
  while ((i=fileInputStream.read()) != -1) {  
    out.write(i);   
  }   
  fileInputStream.close();   
  
  
  try{
 
    		File file = new File(applicationPath+filename);
 
    		if(file.delete()){
    			System.out.println(file.getName() + " is deleted!");
    		}else{
    			System.out.println("Delete operation is failed.");
    		}
 
    	}catch(Exception e){
 
    		e.printStackTrace();
 
    	}
 }
 catch(Exception e){
 e.printStackTrace();
 } 
%>   
