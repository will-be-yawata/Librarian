$(function () {
    layui.use(['layer'], function() {
        var $ = layui.jquery
            , layer = layui.layer;
        $("button.borrowBtn").unbind("click").bind("click", function () {
            var id = $(this).data("id");
            $(this).addClass("layui-btn-disabled");
            $.ajax({
                url: "/stu/borrowBook/" + id,
                success: function (data) {
                    if(data!=null) {
                        if (data.status === -1) {
                            layer.alert(data.message);
                        }else if(data.status===1){
                            layer.alert(data.message);
                            window.location.href="/login";
                        }else{
                            if(data.message>0){
                                layer.alert("申请借阅成功");
                            }else{
                                layer.alert("申请借阅失败");
                            }
                        }
                    }
                },
                error: function (e) {
                    console.log(e);
                    layer.alert("申请失败");
                }
            })
        });
    });
});