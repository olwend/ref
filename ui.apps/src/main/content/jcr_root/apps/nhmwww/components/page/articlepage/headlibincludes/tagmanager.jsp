<%@page session="false"%>
<%@page import="uk.ac.nhm.core.componentHelpers.ArticleHelper"%>
<%@page import="java.util.Map" %>
<%@include file="/libs/foundation/global.jsp" %>
<%
	ArticleHelper helper = new ArticleHelper(resource, slingRequest);

	Map<String, String> hubTagMap = helper.getHubTag();
%>

<!-- WR-1074 - add Google Optimise for A/B testing -->
<!-- Initialise the dataLayer -->
<%if(hubTagMap != null) { %>
<script>dataLayer = ['primaryHubTag':'<%=hubTagMap.get("title")%>'];</script>
<%} else { %>
<script>dataLayer = ['primaryHubTag':''];</script>
<%} %>

<!-- Page hiding snippet -->
<style>.async-hide { opacity: 0 !important} </style>
<script>(function(a,s,y,n,c,h,i,d,e){s.className+=' '+y;h.start=1*new Date;
h.end=i=function(){s.className=s.className.replace(RegExp(' ?'+y),'')};
(a[n]=a[n]||[]).hide=h;setTimeout(function(){i();h.end=null},c);h.timeout=c;
})(window,document.documentElement,'async-hide','dataLayer',4000,
{'GTM-5D4H9KX':true});</script>
<!-- End page hiding snippet -->

<!-- Google Analytics Snippet - No Pageview Fired -->
<script>
(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
})(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

ga('create', 'UA-57477456-1', 'auto');
ga('require', 'linker');
ga('linker:autoLink', ['secpay.com','nhmimages.com','nhm.ac.uk'] );
ga('require', 'GTM-5D4H9KX');
</script>
<!-- End Google Analytics -->

<!-- Google Tag Manager -->
<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
})(window,document,'script','dataLayer','GTM-5TDGNT');</script>
<!-- End Google Tag Manager -->
<!-- END WR-1074 -->