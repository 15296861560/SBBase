function post() {
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:'application/json',
        data: JSON.stringify({//将json对象转换成字符串
            "parentId":questionId,
            "content":commentContent,
            "type":1
        }),
        success: function (response) {
            if (response.code == 200) {
                $("#comment_section").hidden;
            }else {
                alert(response.message);
            }
            console.log(response);

        },
        dataType: "json"
    });

}