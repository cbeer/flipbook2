<%@ page import="edu.stanford.dlss.flipbook.PagesMetadataJSON" %>

<%
  String id = request.getParameter("id");
  String json = null;

  if (id == null && !id.matches("\\w{11}")) {
    response.sendRedirect("id-invalid.jsp");
  } else {
  	PagesMetadataJSON metadata = new PagesMetadataJSON(id);
  	json = metadata.getJSON();
  }
%>

<html>
  <head>
    <title>Stanford University BookReader</title>
    <link rel="stylesheet" type="text/css" href="../css/BookReader.css"/>
    <link rel="stylesheet" type="text/css" href="../css/BookReaderDemo.css"/>
    <link rel="stylesheet" type="text/css" href="../css/BookReaderEmbed.css"/>
  </head>

  <body style="background-color: #939598;">
    <div id="logo-sulair"></div>
    <div id="BookReader">
      Stanford University BookReader <br/>
      <noscript>
        <p>The BookReader requires JavaScript to be enabled. Please check that your browser supports JavaScript and that it is enabled in the browser settings.</p>
      </noscript>
    </div>

    <script type="text/javascript" src="../js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="../js/jquery-ui-1.8.5.custom.min.js"></script>

    <script type="text/javascript" src="../js/dragscrollable.js"></script>
    <script type="text/javascript" src="../js/jquery.colorbox-min.js"></script>
    <script type="text/javascript" src="../js/jquery.ui.ipad.js"></script>
    <script type="text/javascript" src="../js/jquery.bt.min.js"></script>

    <script type="text/javascript">
      <% if (json.equalsIgnoreCase("Invalid JSON")) { %>
        var flipbookJSON = "<%= json %>";
      <% } else { %>
        var flipbookJSON = <%= json %>;
      <% } %>
    </script>

    <script type="text/javascript" src="../js/BookReader.js"></script>
    <script type="text/javascript" src="../js/BookReaderSU.js"></script>
    <script type="text/javascript" src="../js/BookReaderJSSimple.js"></script>
  </body>
</html>
