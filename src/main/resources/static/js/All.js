
$(function () {

    //输入标签
    $("#input-tag").click(function () {
        var dis = $(".selectTags").css("display");
        if(dis == "block"){
            $(".selectTags").hide();
        }else {
            //默认标签第一个
            $("#language").addClass("active");

            $(".selectTags").show();
        }
    });


    if($(".input-error").val()!=null){
        alert($(".input-error").val());
    }


});

function selectTag(e) {
    var value = $(e).attr("data-tag")
    var tag = $("#input-tag").val();
    if(tag.indexOf(value) == -1){
        if(tag){
            $("#input-tag").val(tag+','+value);
        }else {
            $("#input-tag").val(value);
        }
    }
}



// 回复的方法
function message(targetId,type,content,request) {

    if(!content){
        return alert(" 亲 回复不能为空哦~~~");
    }
    $.ajax({
        type: request,
        url: "/comment",
        //url: "/comment/{id}",
        contentType: "application/json;charset-UTF-8",
        data:JSON.stringify({
            "parentId":targetId,
            "content":content,
            "type":type
        }),
        success: function (response) {
            console.log(response.code);
            if(response.code == 200){
                window.location.reload();
            }else{
                if(response.code == 2003){
                    var isAccepted = confirm(response.message);
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=8caeaa72c420ca956371&redirect_uri=http://localhost:9090/callback&scope=user&state=1");
                        window.localStorage.setItem("closable","true");
                        window.location.reload();
                    }
                }else {
                    alert(response.message);
                }
            }
        },
        dataType: "json"
    });
}

//回复问题
function messageQuestion() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    message(questionId,1,content,"post");

}

//回复一级评论
function messageComment(e) {
    var commentId = $(e).attr("data-id");
    var content = $("#input-"+commentId).val();
    message(commentId,2,content,"post");

}

// 二级回复
function commentIcon(e){
    var a =  $(e).attr("aria-expanded");
    var id =  $(e).attr("data-id");
    // 展开二级回复
    if(a == "false"){

        var subComment = $("#comment-"+id);

        if(subComment.children().length != 1){
            $(e).addClass("spanMenIconM iconM");
        }else {
            $.getJSON( "/comment/"+id, function(data) {
                // console.log("id  =  "+id);
                // console.log("data  =  "+data);
                $.each( data.data.reverse() , function(index , comment) {

                    var mediaBobyElement = $("<div/>",{
                        "class":"media-body"
                    }).append($("<h5/>", {
                        "class": "h5 media-heading"
                    }).append($("<span/>", {
                        "class": "desc-test",
                        "html":comment.user.name
                    }).append($("<span/>",{
                       "style":"font-size: 12px; color: #999",
                        "text":" • "+moment(comment.gmtModified).format("YY-MM-DD")
                    }).append($("<div/>",{
                        "class": "commentContent",
                        "html":comment.content
                    }))   )));


                    var mediaLifeElement = $("<div/>",{
                        "class":"media-left"
                    }).append($("<a/>", {
                        "href": "#"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "width": "45px",
                        "height": "45px",
                        "src": comment.user.avatarUrl
                    })));


                    var mediaElement = $("<div/>",{
                        "style":"padding: 15px 5px 5px 5px;"
                    }).append(mediaLifeElement).append(mediaBobyElement);

                    subComment.prepend(mediaElement);
                });
            });
        }
    }else {
        $(e).removeClass("spanMenIconM iconM");
    }
}

