{
    "tabTip": CQ.I18n.getMessage("YouTube"),
    "id": "cfTab-Youtube",
    "xtype": "contentfindertab",
    "iconCls": "cq-cft-tab-icon youtube",
    "ranking": 11,
    "allowedPaths": [
        "/content/*",
        "/etc/scaffolding/*",
        "/etc/commerce/*",
        "/etc/workflow/packages/*"
    ],
    "items": [
        CQ.wcm.ContentFinderTab.getQueryBoxConfig({
            "id": "cfTab-YouTube-QueryBox",
            "items": [
                CQ.wcm.ContentFinderTab.getSuggestFieldConfig({"url": "/bin/wcm/contentfinder/suggestions.json/content/dam"})
            ]
        }),
        CQ.wcm.ContentFinderTab.getResultsBoxConfig({
            "itemsDDGroups": ["youtube"],
            "items": {
                "tpl":
                    '<tpl for=".">' +
                        '<div class="cq-cft-search-item" title="{videoId}">' +
                        '<div class="cq-cft-search-thumb-top" style="background-image:url(\'{[CQ.shared.HTTP.getXhrHookedURL(CQ.HTTP.externalize(CQ.shared.XSS.getXSSValue(CQ.HTTP.encodePath(values.thumbnail))))]}\');"></div>' +
                        '<div class="cq-cft-search-text-wrapper">' +
                        '<div class="cq-cft-search-title">{values.title}</div>' +
                        '<div>{values.videoId}</div>' +
                        '</div>' +
                        '<div class="cq-cft-search-separator"></div>' +
                        '</div>' +
                        '</tpl>',
                "itemSelector": CQ.wcm.ContentFinderTab.DETAILS_ITEMSELECTOR
            },
            "tbar": [ CQ.wcm.ContentFinderTab.REFRESH_BUTTON ]
        },{
            "url": "/bin/wcm/contentfinder/youtube/view.json"
        }, {
            "reader": new CQ.Ext.data.JsonReader({
                "totalProperty": "results",
                "root": "hits",
                "fields": [
                    "name", "path", "thumbnail", "title", "excerpt", "videoId"
                ],
                "id": "videoId"
            }),
            "baseParams": { }
        })
    ]
}