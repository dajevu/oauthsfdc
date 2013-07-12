<%@ page session="false"%>

<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%> 

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Salesforce OAuth Example</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

  <link href="css/bootstrap.min.css" rel="stylesheet">
  <link href="css/bootstrap-responsive.min.css" rel="stylesheet">
  <link href="css/style.css" rel="stylesheet">

  <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
  <!--[if lt IE 9]>
    <script src="js/html5shiv.js"></script>
  <![endif]-->

  <!-- Fav and touch icons -->
  <link rel="apple-touch-icon-precomposed" sizes="144x144" href="img/apple-touch-icon-144-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="114x114" href="img/apple-touch-icon-114-precomposed.png">
  <link rel="apple-touch-icon-precomposed" sizes="72x72" href="img/apple-touch-icon-72-precomposed.png">
  <link rel="apple-touch-icon-precomposed" href="img/apple-touch-icon-57-precomposed.png">
  <link rel="shortcut icon" href="img/favicon.png">
  
  <script type="text/javascript" src="js/jquery.min.js"></script>
  <script type="text/javascript" src="js/bootstrap.min.js"></script>
  <script type="text/javascript" src="js/scripts.js"></script>

  <style>
    ol li {
      margin-bottom: 10px !important;
    }
  </style>
</head>

<body>
<div class="container-fluid">
  <div class="row-fluid">
    <div class="span12">
      <div class="hero-unit" style="padding: 30px !important">
        <h1>
          Salesforce OAuth Demo
        </h1>
      </div>
      <div class="row-fluid">
        <div class="span12">
          <table class="table table-striped">  
                <thead>  
                  <tr>  
                    <th>Account Name</th>  
                    <th>Description</th>  
                    <th>Type</th>
                    <th># of Employees</th>
                  </tr>  
                </thead>  
                <tbody>  
                  <!-- scroll through the list of accounts -->
                  
                  <c:forEach items="${accounts.records}" var="record">
                      <tr>  
                        <td nowrap>${record.name}</td>
                        <td>${record.description}</td>
                        <td>${record.type}</td>
                        <td>${record.numberOfEmployees}</td>
                      </tr>           
                  </c:forEach>  
                  
                </tbody>  
           </table>  
        </div>
      </div>
    </div>
  </div>
</div>
</body>

</html>