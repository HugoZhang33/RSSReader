/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function() {
    
    //show or hide error message of login
    if ($("#megOfLogin").text() !== "") {
        $("#megOfLogin").show(); 
    } else {
        $("#megOfLogin").hide();
    }
    
    //sign up button
    $("#signup").click(function() {
        var userName = $("#userName").val();
        var password = $("#password").val();
        
        $.post(
            "./Register",
            {"userName":userName,"password":password},
             function(data){ 
                
                if (data === "1") {
                    // successful registeration
                    window.location.href = "RssDispatcher";
                } else {
                    // fail to register a new account, show the error message
                    $("#megOfLogin").hide();
                    $("#megOfRegister").show();      
                }
            },
            "text" );
    });
    
});

