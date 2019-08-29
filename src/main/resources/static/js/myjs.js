
// 提交回复
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId,1,content);

}

function comment2target(targetId, type,content) {

    if (!content){
        alert("不能回复空内容");
        return;
    }

    $.ajax({
        type: "POST",
        url: "/comment",
        contentType:'application/json',
        data: JSON.stringify({//将json对象转换成字符串
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
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

        },
        dataType: "json"
    });

}

function comment(e) {
    var commentId=e.getAttribute("data-id");
    var content = $("#input-"+commentId).val();

    comment2target(commentId,2,content);


}

// 展开二级评论
function collapseComment(e) {
    var id=e.getAttribute("data-id");
    var comments=$("#comment-"+id);
    if (comments.hasClass("in")){//如果展开则折叠
        comments.removeClass("in");
        e.classList.remove("active");
    } else {//如果折叠则展开
        var subCommentContainer=$("#comment-"+id);
        if (subCommentContainer.children().length!=1){
            // 展开二级评论
            comments.addClass("in");
            e.classList.add("active");
        } else {
            $.getJSON( "/comment/"+id, function( data ) {
                $.each( data.data.reverse(), function(index,comment ) {
                    //头像
                    var avatarElement=$("<img/>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl
                    });
                    var mediaLeftElement=$("<div/>",{
                        "class":"media-left"
                    });
                    mediaLeftElement.append(avatarElement);

                    //主体
                    var mediaBodyElement=$("<div/>",{
                        "class":"media-body"
                    });
                    //评论者名字
                    var mediaBottomElement=$("<h4/>",{
                        "class":"media-bottom",
                        "html":comment.user.name
                    });
                    //内容
                    var contentElement=$("<div/>",{
                        "html":comment.content
                    });
                    //时间
                    var menuElement=$("<div/>",{
                        "class":"menu"
                    });
                    var timeElement=$("<span/>",{
                        "class":"pull-right",
                        "html":moment(comment.gmtCreate).format('YYYY-MM-DD')
                    });

                    //评论框
                    var commentElement=$("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    });
                    menuElement.append(timeElement);
                    mediaBodyElement.append(mediaBottomElement);
                    mediaBodyElement.append(contentElement);
                    mediaBodyElement.append(menuElement);

                    commentElement.append(mediaLeftElement);//头像
                    commentElement.append(mediaBodyElement);//主体
                    subCommentContainer.prepend(commentElement);
                });

                // 展开二级评论
                comments.addClass("in");
                e.classList.add("active");
            });

        }
    }
}


//展示标签
function showTags() {
    // 显示标签列表
    $("#select-tags").show();
}

//隐藏标签
function hiddenTags() {
    $("#select-tags").hide();
}

//选择标签
function selectTag(e) {
    var value=e.getAttribute("data-tag");
    var previous = $("#tag").val();

    //需要添加的标签还未添加的时候才添加标签
    if (previous.indexOf(value)==-1){
        if (previous){
            $("#tag").val(previous+","+value);
        } else {
            $("#tag").val(value);
        }
    }
}

