function getNotifications() {
    $.ajax({
        type: 'GET',
        url: "/myNotifications",
        contentType: "application/json",
        success: function (result) {
            $.each(result.notifications, function (idx, notification) {
                $("#notifications").append("<div class=\"alert alert-success alert-dismissable\">\n" +
                    " <strong>" + notification + "</strong><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                    "    <span aria-hidden=\"true\">&times;</span>\n" +
                    "  </button></div>");
            });

            $("#notifications").on("click", function() {
                location.href = "/myTrades"
            });
        }
    });
}

function updateTrade(id, status) {
    $('.tradeContainer').css('cursor','wait');
    $.ajax({
        type:"POST",
        url: "/updateTrade",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({ id: id, status: status }),
        dataType: "json",
        success: function(){
            $('.tradeContainer').css('cursor','auto');
            location.reload();
        }
    })
}

function refreshChat(tradeId) {
    $.ajax({
        type:"GET",
        url: "/messages?tradeId="+tradeId,
        contentType: "application/json; charset=utf-8",
        success: function(data) {
            var div = $("#chatMessagesDiv");
            div.empty();
            $.each(data.messages, function (idx, message) {
                div.append("<div class=\"chatMessage\">" +
                    "<div class=\"chatMessageFrom\"><span class=\"chatMessageAuthor\">"+message.from+"</span><span class=\"chatMessageDate\">"+message.timestamp+"</span></div>"+
                    "<div class=\"chatMessageContent\">"+message.content+"</div>" +
                    "</div>");
            });
        }
    });
}

function sendMessage() {
    var message = $(".newMessage").val();
    var tradeId = $("#tradeId").val();
    $(".newMessage").val("");
    $.ajax({
        type:"POST",
        url: "/messageTrade",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify({ tradeId: tradeId, content: message }),
        dataType: "json",
        success: function() {
            refreshChat(tradeId);
            $("#chatMessagesDiv").animate({ scrollTop: $('#chatMessagesDiv').height()}, 1000);
        }
    });
}

$(document).ready(function() {
    $("#acceptTrade").on("click", function() {
        updateTrade($("#tradeId").val(), "ACCEPTED")
    });
    $("#rejectTrade").on("click", function() {
        updateTrade($("#tradeId").val(), "REJECTED")
    });
});
