<%
	String check = (String) session.getAttribute("step3done");
	if (check == null || !check.equals("yes"))
	{
	   response.sendRedirect("step3.jsp?msg=Please complete this step");
	}
	else
	{
%>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Super Resolution</title>

        <!-- CSS -->
        <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
		<link rel="stylesheet" href="assets/css/form-elements.css">
        <link rel="stylesheet" href="assets/css/style.css">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

        <!-- Favicon and touch icons -->
        <link rel="shortcut icon" href="assets/ico/favicon.png">
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">

    </head>

    <body>

		<!-- Top menu -->
		<nav class="navbar navbar-inverse navbar-no-bg" role="navigation">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#top-navbar-1">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a href="index.jsp" style='color: white; font-size: 26px; font-weight: bold; margin-top: 50px;' >Internship (2020)</a>
				</div>
				<!-- Collect the nav links, forms, and other content for toggling -->
				<div class="collapse navbar-collapse" id="top-navbar-1">
					<ul class="nav navbar-nav navbar-right">
						<li>
							<span class="li-text" style='font-weight: bold; color: white;'>
								Xonlabs
							</span> 
						</li>
					</ul>
				</div>
			</div>
		</nav>

        <!-- Top content -->
        <div class="top-content">
        	
            <div class="inner-bg">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-8 col-sm-offset-2 text">
                            <h1><strong>Super</strong> Resolution</h1>
                            <div class="description">
                            	<p>
	                            	The goal of this project, is to recover a high resolution image from a low resolution input, <br/>or simple put "enhance!".  
                            	</p>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3 form-box">
                        	
			                    <fieldset>
		                        	<div class="form-top">
		                        		<div class="form-top-left">
		                        			<h3>Step 4 / 4</h3>
		                            		<p>Run the Algorithm</p>
											<%
											   String msg = request.getParameter("msg");
											%>
											<%
											   if (msg != null) {
											%>
											<div class="alert alert-success alert-dismissable">
												<a href="#" class="close" data-dismiss="alert"
													aria-label="close">&times;</a>
												<%=msg%>.
											</div>
											<%
											   }
											%>
		                            		
		                        		</div>
		                        		<div class="form-top-right">
		                        			<i class="fa fa-image"></i> 
		                        		</div>
		                            </div>
			                            <div class="form-bottom">
		                            		<form action='step4' method=post enctype = "multipart/form-data">
						                        <div class="form-group">
						                        	<label>Input Image File</label>
						                        	<input type="file" name="file" required="required" placeholder="Input Image File" class="form-control" >
						                        </div>
						                        <button id='prev' type="button" class="btn btn-previous">Previous</button>
						                        <button type="submit" class="btn btn-next">Run Algorithm</button>
						                    </form>
					                    </div>
			                    </fieldset>
		                    <br/>
		                    <a href='reset' style='float: right; color: white;' class='btn btn-danger btn-xs'>Reset Session</a>
                        </div>
                    </div>
                </div>
            </div>
            
        </div>


        <!-- Javascript -->
        <script src="assets/js/jquery-1.11.1.min.js"></script>
        <script src="assets/bootstrap/js/bootstrap.min.js"></script>
        <script src="assets/js/jquery.backstretch.min.js"></script>
        <script src="assets/js/retina-1.1.0.min.js"></script>
        <script src="assets/js/scripts.js"></script>
        
        <!--[if lt IE 10]>
            <script src="assets/js/placeholder.js"></script>
        <![endif]-->

		<script>
			$(document).ready(function()
			{
				$('#prev').click(function(){
				    history.back(1);
				});
			});
		</script>
		
    </body>

</html>
<% } %>