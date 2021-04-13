package com.fortune.util;

import com.fortune.datastructures.DCELSite;

public class SiteEvent extends Event {
    private final DCELSite site;
    public SiteEvent(DCELSite site) {
        super(site.getCoordinates());
        this.site = site;
    }

    public DCELSite getSite() {
        return site;
    }
}
