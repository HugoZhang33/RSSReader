/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RssReader.FeedBean;

import java.beans.*;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author hongzhang
 */
public class Feed implements Serializable {
    
    private String title = null;
    private List entries = null;
    private String link = null;
    private String feedUrl = null;
    private String logoUrl = null;
        
    public Feed(String _title, List _entries, String _link, String _logoUrl) {
        title = _title;
        entries = _entries;
        link = _link;
        logoUrl = _logoUrl;
    }
    
    public Feed(String _title, String _feedUrl, String _logoUrl) {
        title = _title;
        feedUrl = _feedUrl;
        logoUrl = _logoUrl;
    }
    
    public String getTitle() {
        return title;
    }
    
    public List getEntries() {
        return entries;
    }
    
    public String getLink() {
        return link;
    }
    
    public String getFeedUrl() {
        return feedUrl;
    }
    
    public String getLogoUrl() {
        return logoUrl;
    }
}
