<%@ page pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!--[if IE 9]><html class="lt-ie10" lang="en" > <![endif]-->
<html class="no-js" lang="en"
	data-useragent="Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>EpiBaby | Products</title>
<meta name="description"
	content="Documentation and reference library for ZURB Foundation. JavaScript, CSS, components, grid and more." />
<meta name="author"
	content="ZURB, inc. ZURB network also includes zurb.com" />
<meta name="copyright" content="ZURB, inc. Copyright (c) 2014" />
<link rel="shortcut icon" href="/JWeb/images/favicon.ico" />
<link rel="stylesheet" href="/JWeb/css/foundation.css" />
<script src="/JWeb/js/vendor/modernizr.js"></script>
<style>
img#productviewimage {
	margin-top: 20px;
	max-width: 100%;
	max-height: 100%;
}
img#usercommentimage {
	max-width: 100px;
	max-height: 100px;
}
h1 {
	margin-top: 20px;
}
</style>
</head>
<body>
	<jsp:include page="MenuTop.jsp" />
   	<jsp:include page="ProductView.jsp" />
	<jsp:include page="Footer.jsp" />
</body>
</html>