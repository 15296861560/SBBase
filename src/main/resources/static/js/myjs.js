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
                if(response.code==2003){//登陆错误则弹窗提示是否登陆
                    var isAccepted = confirm(response.message);
                    if (isAccepted){//跳转到登陆地址
                        window.open("https://github.com/login/oauth/authorize?client_id=784cb00fadf73dfb5f43&redirect_uri=http://localhost:8887/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }

                }else {
                    alert(response.message);

                }
            }
            console.log(response);

        },
        dataType: "json"
    });

}