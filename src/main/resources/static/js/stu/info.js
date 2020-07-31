layui.use(['form', 'layer'], function() {
    var form = layui.form
        ,layer = layui.layer
        ,$ = layui.jquery;
    form.verify({
        password: function(value){
            if(value.length < 4 || value.length > 16){
                return '密码在4-16个字符之间';
            }
        },
        repassword:function(value){
            if(value.length< 4|| value.length>16){
                return '确认密码在4-16个字符之间';
            }
        }
    });
    form.on('submit(updatePassword)', function(data){
        console.log(data);
        var password=data.field.password;
        var repassword=data.field.repassword;
        if(password!==repassword) {
            layer.alert("密码与确认密码不一致");
            return false;
        }
        $.ajax({
            url:"/stu/updatePassword",
            type:"post",
            data:{password:password},
            success:function(data){
                switch (data.status) {
                    case 0:
                        if(data.message>0){
                            layer.alert("修改成功");
                        }else{
                            layer.alert("修改失败");
                        }
                        break;
                    case 1:
                        layer.alert(data.message);
                        break;
                    case -1:
                        layer.alert(data.message);
                        window.location.href="/login";
                        break;
                    default:
                        layer.alert("发生未知错误");
                        break;
                }
            },
            error:function(e){
                console.log(e);
                layer.alert("提交请求失败");
                return false;
            }
        });
        return false;
    });
})