$(function () {

    $("#QuestionSubmitBtn").click(function () {
        var id = $("#question_id").val();
        var content = $("#comment_content").val();
        console.log("question_id = " + id + "    content = " + content);

        $.ajax({
            type: "POST",
            url: "/comment",
            contentType: "application/json;charset-UTF-8",
            data:JSON.stringify({
                "parentId":id,
                "content":content,
                "type":1
            }),
            success: function (response) {
                console.log(response.code);
                if(response.code == 200){
                    $(".QuestionComment").hide();
                }else{
                    if(response.code == 2003){
                        var isAccepted = confirm(response.message);
                        if(isAccepted){
                            window.open("https://github.com/login/oauth/authorize?client_id=8caeaa72c420ca956371&redirect_uri=http://localhost:9090/callback&scope=user&state=1");
                            window.localStorage.setItem("closable","true");
                        }
                    }else {
                        alert(response.message);
                    }
                }
            },
            dataType: "json"
        });
    });

});