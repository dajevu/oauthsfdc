package org.acme.sfdc.mvc;

import org.acme.sfdc.SfdcApiHelper;
import org.acme.sfdc.dto.Accounts;
import org.acme.sfdc.dto.SFDCAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class HomeController
{

	private static final Logger LOG = Logger.getLogger(HomeController.class.getSimpleName());

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
 
      SFDCAuthorization auth = (SFDCAuthorization) request.getSession().getAttribute("sfdcSession");
 
      LOG.info("auth in home is: " + auth.getInstanceUrl());
      
        
      try
      {
         Accounts accounts = SfdcApiHelper.getAccounts(auth.getAccessToken(), auth.getInstanceUrl());
         
         model.addAttribute("accounts", accounts);
      }
      catch (Exception e)
      {
         LOG.severe(e.getMessage());
      }
     
      
      return "main";
  }
}