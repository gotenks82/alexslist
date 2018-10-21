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

$(document).ready(function() {
    $("#acceptTrade").on("click", function() {
        updateTrade($("#tradeId").val(), "ACCEPTED")
    });
    $("#rejectTrade").on("click", function() {
        updateTrade($("#tradeId").val(), "REJECTED")
    });
});
