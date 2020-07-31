layui.use(['form', 'layedit', 'laydate','upload'], function() {
    var form = layui.form
        ,layer = layui.layer
        ,$ = layui.jquery;
    form.render();
    form.verify({
        num:[
            /^\d{4}$/
            ,'工号为4位纯数字'
        ],
        name: function(value){
            if(value.length <=1){
                return '姓名至少是两个字';
            }
        },
        password: function(value){
            if(value.length <4 || value.length>16){
                return '密码为4-16位';
            }
        }
    });
})