function check_id() {
    var user_id = $("#user_id").val();
    var param = {}

    if(user_id == null || user_id == ""){
        $("#id_success").hide();
        $("#id_error").hide();
        return
    }

    param["id"] = user_id;

    if (user_id != null && user_id != "") {
        $.ajax({
            url: "api/user/check_id",
            type: "POST",
            contentType: "application/json",
            dataType: "text",
            data: JSON.stringify(param),
            success: function (data) {
                const jobj = JSON.parse(data);
                if (jobj["status"].toString() == "SUCCESS") {
                    $("#id_success").text("사용가능한 아이디 입니다.");
                    $("#id_success").show();
                    $("#id_error").hide();
                } else {
                    $("#id_error").text("이미 사용중인 아이디 입니다.");
                    $("#id_error").show();
                    $("#id_success").hide();
                }
            },
            error: function (error) {
                $("#id_error").text("이미 사용중인 아이디 입니다.");
                $("#id_error").show();
                $("#id_success").hide();
            }
        })
    }
}

function insert_account() {

    var info = ["id", "password", "name", "birth", "gender", "email", "phone"];

    if ($("#year").val().length != 4) {
        $("#birth_error").text("년도를 4자리로 입력해주세요.");
        $("#birth_error").show();
        return
    }

    $("#user_birth").val($("#year").val() + $("#month").val() + $("#date").val())

    var offset;

    $(".error_text").hide();
    for (var i = 0; i < info.length; i++) {
        if ($("#user_" + info[i]).val() == null || $("#user_" + info[i]).val() == "") {
            offset = $("#user_" + info[i]).offset();
            var scroll_top = $(document).height() - $(window).height();

            if (scroll_top > offset.top - 100) {
                scroll_top = offset.top - 100;
            }

            $('html, body').animate({scrollTop: scroll_top}, 200);
            $("#" + info[i] + "_error").text("필수 정보입니다.");
            $("#" + info[i] + "_error").show();
            return
        } else {
            $("#" + info[i] + "_error").hide();
        }
    }


    var param = {}
    param["id"] = $("#user_id").val();
    param["password"] = $("#user_password").val();
    param["name"] = $("#user_name").val();
    param["birth"] = $("#user_birth").val();
    param["gender"] = $("#user_gender").val();
    param["email"] = $("#user_email").val();
    param["phone"] = $("#user_phone").val();

    $.ajax({
        url: "api/user/insert_account",
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
            $("#error_message").text("회원가입 실패");
            $("#error_message").show();
        }
    });
}