
<%       
/*==========================================================================+
|  DESCRIPTION                                                              |
|    
|    26/05/2015    Suman G      Added code to fix #2772                     |
+===========================================================================*/%>

<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.math.*" %>
<%    
  try{
  String filename = "aascOrderImportReport.csv";
  LinkedList errorList=(LinkedList)session.getAttribute("errorList");
  Iterator it=errorList.iterator();
    
//  System.out.println("list size:::::"+errorList.size());
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
            writer.append("D-Duplicate");
	    writer.append('\n');
 
	    writer.append("Ordernumber");
	    writer.append(',');
	    writer.append("Error Description");
            writer.append(',');
	    writer.append("Status");
            writer.append(',');
	    writer.append("Import ID");
            writer.append(',');
            
            writer.append(',');
	    
            writer.append(',');
            writer.append(',');
	    writer.append("E-Error");
            writer.append('\n');
 
 
	    //generate whatever data you want
 
try{ 
 while (it.hasNext()) {
  HashMap<String, ?> map1 = (HashMap<String, ?>)it.next();
        if((String)map1.get("ORDER_NUMBER") != null){
            writer.append((String)map1.get("ORDER_NUMBER"));
            writer.append(',');
            writer.append((String)map1.get("ERROR_CODE"));
            writer.append(','); 
            writer.append((String)map1.get("STATUS"));
            writer.append(',');     
            String impor_id=((BigDecimal)map1.get("IMPORT_ID")).toString();    
            writer.append(impor_id);
        }else{
        writer.append("");
        writer.append(',');
        writer.append("");
        writer.append(',');
        writer.append("Successfully Imported Orders");
        
        }
        writer.append(',');
        writer.append('\n');
  }
  }catch(Exception e){
  
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
