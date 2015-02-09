<%@include file="/apps/nhmwww/components/global.jsp"%>
	<div class="main-section">
		<cq:include path="title" resourceType="nhmwww/components/functional/title" />
            <div class="row"><!-- ROW 2-->             
                <div class="large-12 medium-12 columns" id="searchresults">   
		           <script>
                        (function() {
                            var cx = '007743546316542420495:tzeq1x_vp5c';
                            var gcse = document.createElement('script');
                            gcse.type = 'text/javascript';
                            gcse.async = true;
                            gcse.src = (document.location.protocol == 'https:' ? 'https:' : 'http:') + '//www.google.com/cse/cse.js?cx=' + cx;
                            var s = document.getElementsByTagName('script')[0];
                            s.parentNode.insertBefore(gcse, s);
                        })();
                    </script>
                    <gcse:search></gcse:search>
		        </div><!-- END ROW 2-->
       		</div>
		<cq:include path="par" resourceType="foundation/components/parsys" />
	</div>