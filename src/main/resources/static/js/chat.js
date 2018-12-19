$(document).ready(function(){
    var urlPrefix ='ws://localhost:8080/chat-room/';
    var ws = null;
    var username = $.cookie("username");
    var avatar = $.cookie("avatar");
    var url = urlPrefix + username;
    ws = new WebSocket(url);
    ws.onopen = function () {
        console.log("建立 websocket 连接...");
    };
    ws.onmessage = function(event){
        $('.people-choice ul').append("<li>\n" +
            "<img src=\"imgs/"+ $.parseJSON(event.data).avatar +".png\" alt=\"\">\n" +
            "<p class=\"people-name\">"+ $.parseJSON(event.data).nickname +"</p>\n" +
            "</li>")
        //服务端发送的消息
        console.log(username, $.parseJSON(event.data).username)
        if (username === $.parseJSON(event.data).username) {
            $('.chat-content').append(
                "<div class='my-chat-container clearfix'>"+
                "<img src='../imgs/"+ avatar +".png' alt=''>"+
                "<p class='my-content'>"+$.parseJSON(event.data).msg+"</p>"+
                "</div>"
            )
        } else {
            $('.chat-content').append(
                "<div class='other-chat-container clearfix'>"+
                "<img src='../imgs/"+ $.parseJSON(event.data).avatar +".png' alt=''>"+
                "<p class='other-content'>"+$.parseJSON(event.data).msg+"</p>"+
                "</div>"
            )
        }
        $(".online-count").text($.parseJSON(event.data).onlineCount);
        $(".chat-content").scrollTop($(".chat-content")[0].scrollHeight);
    };
    ws.onclose = function(event){
        $(".online-count").text(parseInt($(".online-count").text()) - 1)
        console.log("关闭 websocket 连接...");
    }
    //客户端发送消息到服务器
    $('.send-btn').click(function(){
        if($(".w-e-text").text()!="") {
            var msg = $('.w-e-text').text();
            if(ws){
                ws.send(msg);
            }
        }
        $(".w-e-text").text("");
    });
    // 退出聊天室
    $('#user_exit').click(function(){
        if(ws){
            ws.close();
        }
    });
    //使输入框具有按回车发送的功能
    $(".w-e-text").keyup(function(){
        if(event.keyCode==13){
            $(".send-btn").click();
        }
    })
    // 执行搜索
    search();
    //左侧用户点击效果
    $(".people-choice").find("li").click(function(){
        $(".people-choice").find("li").not(this).removeClass("active");
        $(this).addClass("active");
    })
})

function search(){
    var searchTimer;
    $(".search-input").focus(function(){
        searchTimer=window.setInterval(function(){
            var tag=$(".search-input").val();
            $(".people-choice").find("li").each(function(){
                if($(this).children(".people-name").text().indexOf(tag)!=-1){
                    $(this).css({
                        display:"block"
                    })
                }
                else{
                    $(this).css({
                        display:"none"
                    })
                }
            })
            var i=0;
            $(".people-choice").find("li").each(function(){
                if($(this).css("display")=="block"){
                    i++;
                }
            })
            if(i==0){
                $(".no-result").css({
                    display:"block"
                })
            }
            else{
                $(".no-result").css({
                    display:"none"
                })
            }
        },300);
    })
    $(".search-input").blur(function(){
        window.clearInterval(searchTimer);
    })
}