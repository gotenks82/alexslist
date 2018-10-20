function getNotifications() {
    $.ajax({
        type: 'GET',
        url: "/myNotifications",
        contentType: "application/json",
        success: function (result) {
            $.each(result.notifications, function (idx, notification) {
                $("#notifications").append("<div class=\"alert alert-success alert-dismissable fade\">\n" +
                    " <strong>" + notification + "</strong><button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\">\n" +
                    "    <span aria-hidden=\"true\">&times;</span>\n" +
                    "  </button></div>");
                $("#fade").addClass("in");
            });
        }
    });
}
