<%@ include file="/apps/nhmwww/components/page/imagepage/headlibincludes/globalinclude.jsp" %>

<script>
// WR-1042 - YouTube API was loading quicker than page DOM, causing a "YT.Player is not a constructor" error and stopping Discover captions from being displayed on-click.
// Moving the YouTube API function into the headlibs allows the API to load quicker and resolves the issues.
	onYouTubeIframeAPIReady();
</script>