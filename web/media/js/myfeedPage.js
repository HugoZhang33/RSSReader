/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function() {
    
    //
    $("#urlRemind").css("color", "#F84F4F");
    $("#urlRemind").css("font-weight", "300");
    $("#urlRemind").css("font-size", "18px");
    if ($("#urlRemind").text() !== "") {
        // when user input wrong RSS feed url, show this error message for few seconds.
        setTimeout(function() { 
            $("#urlRemind").remove();
        }, 4000);
    } else {
        $("#urlRemind").remove();
    }
    
    
    //set the color of Favorite button
    var feedUrl = $("#favorite").attr("title");
    
    $("#feedCategory button").each(function() {
        if ($(this).val() === feedUrl)
            $("#favorite").prev().css("color", "#F84F4F");
    });
    
    
    //show or hide the left navigation bar
    $(".fui-list").click(function() {
        
        if ($(".secondary").css("display") === "none")
            $(".container").css("width", "75%");
        else 
            $(".container").css("width", "100%");
        
        $(".secondary").toggle("slow");
        
    });
    
    
    //toggle between feed content and feed category
    $("#feedsButton").click(function() {
       
        if ($("#feedCategory").css("display") === "block") {
            
            $("#categoriesButton").css("color", "#B4B4C3");
            $("#feedsButton").css("color", "#333337");
            
            $("#feedContent").toggle();
            $("#feedCategory").toggle();
        }
        
    });
    $("#categoriesButton").click(function() {
       
        if ($("#feedContent").css("display") === "block") {
            
            $("#feedsButton").css("color", "#B4B4C3");
            $("#categoriesButton").css("color", "#333337");
            
            $("#feedContent").toggle();
            $("#feedCategory").toggle();
        }
        
    });
    
    
    // subscribe or unsubscribe a feed
    $("#favorite").click(function() {
        
        var title = $("#feedContent p").first().text();
        var feedUrl = $(this).attr("title");
        var logoUrl = $(this).next().text();
        
        $.post(
            "./UpdateFeed",
            {"title":title, "feedUrl":feedUrl, "logoUrl":logoUrl},
            function(data) {                
                if (data === "1") {
                    //insert new feed
                    $("#favorite").prev().css("color", "#F84F4F");  
                    var html = "<div class=\"col-xs-3\">" +
                               "<div class=\"tile\">" +
                               "<img src=\"" + logoUrl +"\" alt=\"\" class=\"feedLogo \">" +
                               "<form action=\"RssDispatcher\" method=\"POST\">" +
                               "<button type=\"submit\" name=\"feedUrl\" value=\"" + feedUrl + "\" class=\"btn btn-primary btn-large btn-block\" >Get Feed</button>" +
                               "</form>" +
                               "</div>" +
                               "</div>";
                    $("#feedCategory").append(html); // append new feed into feed category
                    
                } else if(data === "2") {
                    //delete feed
                    $("#favorite").prev().css("color", "##DADADA");
                    $("#feedCategory button").each(function() {
                        if ($(this).val() === feedUrl)
                            $(this).parent().parent().parent().remove(); // remove feed from feed category
                    });
                }else {
                    //alert error message
                    alert("fail to do, please try again");
                }
            });        
        
    });   
    
    
});

