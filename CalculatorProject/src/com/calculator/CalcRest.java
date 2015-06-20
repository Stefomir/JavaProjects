package com.calculator;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.calculator.BinaryTree;
import com.calculator.Calculator;
import com.calculator.MySql;

@Path("/calc")
public class CalcRest {

	  @POST @Path("/{idUser}/{strInput}")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String responseResult(@PathParam("idUser") int idUser, @PathParam("strInput") String strInput){
		  String strError = "";
		  strInput = strInput.replaceAll("l","/");
			// Creating object for calculating and passing it the operation value
			Calculator calc = new Calculator(strInput);
			strError = calc.preprocessor();
			// check if there is an error and displays it
			if (!strError.equals("")) {
				return "Error: " + strError;
				//throw new Exception(strError);
				//sendError(request, response, strInput, strError);
			} else
			// check if there is an error and displays it
			if (calc.getInfix().equals("NA")) {
				return "Error: " + "Wrong parameters!";
				//throw new Exception("Wrong Parameters!");
				//sendError(request, response, strInput, "Wrong Parameters!");
			} else {
				strError = calc.infix2postfix();
				// check if there is an error and displays it
				if (!strError.equals("")) {
					return "Error: " + strError; 
					
					//throw new Exception(strError);
					//sendError(request, response, strInput, strError);
				} else {
					// Building tree object for calculation
					BinaryTree eTree;
					try {
						eTree = calc.buildExpressionTree();
					} catch (Exception e) {
						// check if there is an error and displays it
						return "Error: " + "Invalid math operation";
						//throw new Exception("Invalid math operation!");
						//sendError(request, response, strInput,
							//	"Invalid math operacia!");
						//return;
					}
					String strTrac;
					// Retrieves the result of the calculation
					try{
					strTrac = Double.toString(calc.evalExpressionTree(eTree));
					}catch(Exception e){
						return "Error: " + e.getMessage();
					}
					// check if there is an error and displays it
					if (strTrac.equals("Infinity")) {
						return "Error: " + "You cant divide to zero!";
						//throw new Exception("You cant divide to zero!");
						//sendError(request, response, strInput,
							//	"You cant divide to zero!");
						//return;
					}
					// Forming String with the original request + result operation
					String endResult = calc.getOriginal() + " = " + strTrac;
					
					MySql sql = new MySql();
					// Saving the finished result
					sql.insertHistoryCalculate(Integer.toString(idUser), endResult);
					List<String> mylist =sql.selectHistoryCalculate(Integer.toString(idUser));
					endResult = "";	  
					for(int i = 0;i < mylist.size(); i++)
							  
							  endResult += mylist.get(i) + "\n";
					
					sql.closeAllConections();
					return endResult;
					//sendError(request, response, endResult, "");

				}

			}
	  }
	  
	  @POST @Path("clearHistory/{idUser}")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String clearHistory(@PathParam("idUser") String idUser) {
		  
		  MySql sql = new MySql();
		  sql.delUserHistory(idUser);
		    
		  return "";
	  }
	
	  @POST
	  @Path("/{strUsername}/{strPassword}/{strFullname}/{strEmail}")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String createUser(@PathParam("strUsername") String strUsername, @PathParam("strPassword") String strPassword, @PathParam("strFullname") String strFullname, @PathParam("strEmail") String strEmail){
		  
		// check if the typed name is real
			try {
				Integer.parseInt(strFullname);
				
			return "Error: " + "You can't type number for your Full Name!";
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			// Check for valid email
			try {
				String emailregex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
				Boolean b = strEmail.matches(emailregex);
				if (b == false) return "Error: " + "Invalid Email";
									
			} catch (Exception e) {
				e.printStackTrace();
			}

			MySql sql = new MySql();
			// check if there is another user registered with the same name and
			// email
			if (sql.DublicateUsernameEmail(strUsername, strEmail)) return "Error: " + "There is another person with the same Name and Email!";
			
			sql.insertUserInfo(strUsername, strPassword, strFullname, strEmail);
			return "";
	  }
}
