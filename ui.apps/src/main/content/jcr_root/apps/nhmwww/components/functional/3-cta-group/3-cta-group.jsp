<%--

  3 call to action group component.

  This is a component that will create 3 call to action buttons

--%>
<%@include file="/apps/nhmwww/components/global.jsp"%>
<%@page import="uk.ac.nhm.nhm_www.core.componentHelpers.CTAButtonHelper" %>
<%@page import="uk.ac.nhm.nhm_www.core.model.SVGImage" %>
<cq:defineObjects />
<%
	CTAButtonHelper helper = new CTAButtonHelper(cssClassSection.toLowerCase());
%>

<div class="info-actions cf" data-equalizer>
     <div class="large-8 info-actions--buttons columns" data-equalizer-watch>
         <cq:include path="ctabutton1" resourceType="nhmwww/components/functional/ctabutton" />
         <cq:include path="ctabutton2" resourceType="nhmwww/components/functional/ctabutton" />
     </div>
     <div class="large-4 columns info-tout info-tout__share" data-equalizer-watch>
         <a href="#">
             <i class="ico svg-ico" data-svg-src="/etc/designs/nhmwww/img/svg-icons/icon_l_general_share.svg" data-svg-title="icon__actions_share" data-alt="Share" data-stroke-width="4" data-fallback="img/icons/share-lg.png" data-base-color="<%= helper.getSvgColour() %>"></i>
             <h3>Invite Friends</h3>
             <p>Book your group tickets now</p>
         </a>
     </div>
 </div>