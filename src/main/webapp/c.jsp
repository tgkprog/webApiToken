


<%!
int cnt =0;
%>

<% 

String s = application.getInitParameter("a1");
out.print (s + "  " + (cnt++) + " at " + (new java.util.Date()) ); 
String u  = request.getParameter("u");
out.print ("<br>user " + u + "."); 
if(u!= null){
session.setAttribute("user", u);
out.print ( " set "   ); 

}


%>

<br>


