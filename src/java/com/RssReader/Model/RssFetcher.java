/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.RssReader.Model;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.RssReader.FeedBean.Feed;

/**
 *
 * @author hongzhang
 */
public class RssFetcher {
    
    public Feed fetchFeed(String _url) 
            throws MalformedURLException, IOException, IllegalArgumentException, FeedException {
       
        //fetch rss feed given by an url
        URL url = new URL(_url);
        SyndFeedInput syndFeedInput = new SyndFeedInput();
        SyndFeed syndFeed = null;
        XmlReader xmlReader = new XmlReader(url);
        syndFeed = syndFeedInput.build(xmlReader);
        
        Feed feed = new Feed( syndFeed.getTitle(), syndFeed.getEntries(), syndFeed.getLink(), syndFeed.getImage().getUrl());   
        return feed;
    }
    
    
}
