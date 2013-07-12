package org.acme.sfdc.mvc;

import org.acme.sfdc.SfdcApiHelper;
import org.acme.sfdc.dto.SFDCAuthorization;
import org.acme.sfdc.mvc.SFDCOAuthController;
import org.apache.amber.oauth2.client.request.OAuthClientRequest;
import org.apache.amber.oauth2.client.response.OAuthAuthzResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SFDCOAuthController
{

   private static final Logger LOG = Logger.getLogger(SFDCOAuthController.class.getSimpleName());

   // TODO: Put these in a properties file
   private static String CALLBACK_URL = "https://codenvy-zazarie.appspot.com/authorize_sfdc";

   private static String SFDC_CLIENT_ID =
      "3MVG9A2kN3Bn17hvaearcHEUAs7jYVBcKsNQ8JUt1P.Z.i86xJspN_ijYiJ5pHD9Fj_ZfVAZxrueQ0USr_01m";

   private static String SFDC_CLIENT_SECRET = "6989414199582572543";

   private static String SFDC_SCOPE = "api id web refresh_token";

   @ResponseBody
	 @RequestMapping(value = "/authorize_sfdc", method = RequestMethod.GET)
	 public String authorize(ModelMap model, HttpServletRequest req, HttpServletResponse resp) {
      LOG.info("Landed inside /OAuthTestServlet servlet...");

      // Step 2 of the oauth dance
      if (req.getParameter("code") != null)
      {

         try
         {

            OAuthAuthzResponse oar = OAuthAuthzResponse.oauthCodeAuthzResponse(req);

            String code = oar.getCode();
            
            String body = SfdcApiHelper.buildOauthBody(CALLBACK_URL, code, SFDC_CLIENT_ID, SFDC_CLIENT_SECRET);
            
            LOG.info("body is: " + body);
            
            SFDCAuthorization auth = SfdcApiHelper.fetchAccessToken(body, "https://login.salesforce.com/services/oauth2/token");
            
            LOG.info("Auth token is: " + auth.getAccessToken());
            
            req.getSession().setAttribute("sfdcSession", auth);
            
            // Redirect back to index
        	  resp.sendRedirect("/");

         }
         catch (Exception e)
         {
            LOG.severe("Error in processing oauth: " + e);
         }
      }

      try
      {

         // TODO: Add hardcoded values to property file
         OAuthClientRequest rRequest =
            OAuthClientRequest.authorizationLocation("https://login.salesforce.com/services/oauth2/authorize")
               .setClientId(SFDC_CLIENT_ID)
               .setRedirectURI(CALLBACK_URL).setResponseType("code")
               .setScope(SFDC_SCOPE).setParameter("duration", "permanent").setState("teststate").buildQueryMessage();

         LOG.info("URL for salesforce oauth is:: " + rRequest.getLocationUri());

         resp.sendRedirect(rRequest.getLocationUri());

      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
      
      return "OK";
}
}