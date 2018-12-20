$(document).ready(function(){
    var urlPrefix ='ws://localhost:8080/chat-room/';
    var ws = null;
    var username = $.cookie("username");
    var avatar = $.cookie("avatar");
    var url = urlPrefix + username;
    ws = new WebSocket(url);
    ws.onopen = function (event) {
        console.log("建立 websocket 连接...");
    };
    ws.onmessage = function(event){
        //服务端发送的消息
        console.log($.parseJSON(event.data))
        if ($.parseJSON(event.data).type === 1) {
            if (username === $.parseJSON(event.data).username) {
                if ($.parseJSON(event.data).to !== "") {
                    $('.chat-content').append(
                        "<div class='my-chat-container clearfix'>"+
                        "<img src='../imgs/"+ $.parseJSON(event.data).avatar +".png' alt=''>"+
                        "<div class='my-container'>"+
                        "<div class='sayer-data'>"+
                        "<span class='say-time'>"+ $.parseJSON(event.data).currentTime +"</span>"+
                        "<span class='sayer-name'>"+ $.parseJSON(event.data).nickname +"</span>"+
                        "</div>"+
                        "<p class='my-content'>" + "你对" + $.parseJSON(event.data).to + "悄悄说:" + $.parseJSON(event.data).msg+"</p>"+
                        "</div>"+
                        "</div>"
                    )
                } else {
                    $('.chat-content').append(
                        "<div class='my-chat-container clearfix'>"+
                        "<img src='../imgs/"+ $.parseJSON(event.data).avatar +".png' alt=''>"+
                        "<div class='my-container'>"+
                        "<div class='sayer-data'>"+
                        "<span class='say-time'>"+ $.parseJSON(event.data).currentTime +"</span>"+
                        "<span class='sayer-name'>"+ $.parseJSON(event.data).nickname +"</span>"+
                        "</div>"+
                        "<p class='my-content'>"+$.parseJSON(event.data).msg+"</p>"+
                        "</div>"+
                        "</div>"
                    )
                }
            } else {
                console.log($.parseJSON(event.data));
                if ($.parseJSON(event.data).to === "") {
                    $('.chat-content').append(
                        "<div class='other-chat-container clearfix'>"+
                        "<img src='../imgs/"+ $.parseJSON(event.data).avatar +".png' alt=''>"+
                        "<div class='other-container'>"+
                        "<div class='sayer-data'>"+
                        "<span class='sayer-name'>"+ $.parseJSON(event.data).nickname +"</span>"+
                        "<span class='say-time'>"+ $.parseJSON(event.data).currentTime +"</span>"+
                        "</div>"+
                        "<p class='other-content'>"+$.parseJSON(event.data).msg+"</p>"+
                        "</div>"+
                        "</div>"
                    )
                } else if ($.parseJSON(event.data).to === username) {
                    $('.chat-content').append(
                        "<div class='other-chat-container clearfix'>"+
                        "<img src='../imgs/"+ $.parseJSON(event.data).avatar +".png' alt=''>"+
                        "<div class='other-container'>"+
                        "<div class='sayer-data'>"+
                        "<span class='sayer-name'>"+ $.parseJSON(event.data).nickname +"</span>"+
                        "<span class='say-time'>"+ $.parseJSON(event.data).currentTime +"</span>"+
                        "</div>"+
                        "<p class='other-content'>" + $.parseJSON(event.data).from + "悄悄对你说:" + $.parseJSON(event.data).msg+"</p>"+
                        "</div>"+
                        "</div>"
                    )
                }

            }
            $(".chat-content").scrollTop($(".chat-content")[0].scrollHeight);
        } else if ($.parseJSON(event.data).type === 0) {
            console.log($.parseJSON(event.data));
            $(".online-count").text($.parseJSON(event.data).onlineCount);
            $('.people-choice ul').text("");
            $.parseJSON(event.data).users.forEach(function(user) {
                $('.people-choice ul').append("<li username='" + user.username +  "' onclick='onlinePeopleClick(this)'>\n" +
                    "<img src=\"imgs/"+ user.avatar +".png\" alt=\"\">\n" +
                    "<p class=\"people-name\">"+ user.nickname +"</p>\n" +
                    "<div class='online-status'>" +
                    "<div class=\"status-circle\"></div>" +
                    "<div class=\"status-words\">online</div>" +
                    "</div>" +
                    "</li>")
            })

        }
        // 执行搜索
        search();
    };
    ws.onclose = function(){
        $(".online-count").text(parseInt($(".online-count").text()) - 1)
        console.log("关闭 websocket 连接...");
    }
    //客户端发送消息到服务器
    $('.send-btn').click(function(){
        if($(".w-e-text").text()!="") {
            var data = {
                msg: $('.w-e-text').text(),
                from: username,
                to: $(".to-people-value").text()
            }
            if(ws){
                ws.send(JSON.stringify(data));
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
})

function search(){
    var searchTimer;
    $(".search-input").on("focus", function() {})
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

function onlinePeopleClick(_this) {
    if ($(_this).hasClass("active")) {
        $(_this).removeClass("active")
        $(".to-people-value").text("");
    } else {
        $(".people-choice").find("li").not(_this).removeClass("active");
        $(_this).addClass("active");
        $(".to-people-value").text(_this.getAttribute("username"));
    }
}