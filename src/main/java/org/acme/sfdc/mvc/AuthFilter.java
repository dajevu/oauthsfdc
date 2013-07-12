package org.acme.sfdc.mvc;

import org.acme.sfdc.WebUtil;
import org.acme.sfdc.dto.SFDCAuthorization;
import org.springframework.stereotype.Service;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;
import java.util.Date;

@Service
public class AuthFilter implements Filter
{
   private static final Logger LOG = Logger.getLogger(AuthFilter.class.getSimpleName());
   
   public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException 
   {
   
     if (response instanceof HttpServletResponse && request instanceof HttpServletRequest) {
      HttpServletRequest httpRequest = (HttpServletRequest) request;
      HttpServletResponse httpResponse = (HttpServletResponse) response;

      // skip auth for oauth process
      if (httpRequest.getRequestURI().startsWith("/authorize_sfdc")) {
        LOG.info("Skipping auth check during auth flow");
        filterChain.doFilter(request, response);
        return;
      }
      
      // if sfdc session isn't available, initiate oauth process
      if (httpRequest.getSession().getAttribute("sfdcSession") == null) {
        LOG.info("No sfdcSession found...redirectring to authorize_sfdc");

			  httpResponse.sendRedirect(WebUtil.buildUrl(httpRequest, "/authorize_sfdc"));
        return;
        
		  } else {
        LOG.info("sfdcSession found in session. No need to reauthorize");
        SFDCAuthorization auth =  (SFDCAuthorization) (httpRequest.getSession().getAttribute("sfdcSession"));
        
        long now = (new Date()).getTime();
        long issued = Long.valueOf(auth.getIssuedAt());
        long expires =  new Date(issued + (3600000l * 2)).getTime();
        
        LOG.info("Now is: " + now + " expires: " + expires);
        
        // session has expired
        if (now >= expires) {
          LOG.info("accessToken has expired");
          
          // instead of bothering with refresh token, we'll simply clear out the session and start over to keep thinsgs simple
          
          httpRequest.getSession().invalidate();
          httpResponse.sendRedirect(WebUtil.buildUrl(httpRequest, "/authorize_sfdc"));

          return; 
        } 
        
      }
     }
     
     filterChain.doFilter(request, response);
     
   }
   
   @Override
   public void destroy()
   {
      
   }

   @Override
   public void init(FilterConfig arg0) throws ServletException
   {
      // TODO Auto-generated method stub
      
   }
  
}