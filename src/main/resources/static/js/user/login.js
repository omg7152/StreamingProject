$(".login_btn").click(function () {
    var user_id = $("#user_id").val();
    var user_password = $("#user_password").val();

    if (user_id == null || user_id == "") {
        $("#error_message").text("아이디를 입력해 주세요.");
        $("#error_message").show();
    } else if (user_password == null || user_password == "") {
        $("#error_message").text("비밀번호를 입력해 주세요.");
        $("#error_message").show();
    } else {
        $("#error_message").hide();
        var param = {}
        param["id"] = user_id;
        param["password"] = user_password;

        $.ajax({
            url: "/api/user/login_check",
            type: "POST",
            contentType: "application/json",
            dataType: "text",
            data: JSON.stringify(param),
            success: function (data) {
                const jobj = JSON.parse(data);
                if(jobj["redirect"]){
                    window.location.href = jobj["redirect"].toString();
                }
            },
            error: function (error) {
                $("#error_message").text("아이디 또는 비밀번호가 일치하지 않습니다.");
                $("#error_message").show();
            }
        })
    }
});
