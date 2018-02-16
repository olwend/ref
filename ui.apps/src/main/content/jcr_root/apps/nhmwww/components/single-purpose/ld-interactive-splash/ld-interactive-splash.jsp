
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page session="false" import="uk.ac.nhm.core.componentHelpers.HelperBase"%><%
%>
<cq:defineObjects />


<%  HelperBase helper = new HelperBase(); %>

  
            <div id="bigsplash-interactive">
                <div class="wrap">
                    <div class="c"></div>
                    <div class="c"></div>
                    <div class="c"></div>
                    <div class="c"></div>
                    <div class="c"></div>
                    <div class="c"></div>
                    <div class="c"></div>
                    <div class="c"></div>
                    <div class="c"></div>
                    <div class="c"></div>
                </div>
            </div>

            <div class="bigsplash-container"></div>
            <div class="bigsplash-text">
                <div class="row cf">
                    <div class="bigsplash-text--title-container">
                        <h1 class="bigsplash-text--title">Life in the dark</h1>
                    </div>

                    <!-- CTA tab -->
                    <div class="bigsplash-text--cta-container">
                        <span onclick="torch()">Turn the light off</span>
                    </div>

                </div>
          

<cq:includeClientLib categories="nhmwww.ld-interactive-splash"/>
