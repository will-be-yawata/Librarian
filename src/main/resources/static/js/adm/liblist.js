$(function () {
    layui.use(['table','layer','form'], function() {
        var $ = layui.jquery
            ,table = layui.table
            ,layer=layui.layer
            ,form = layui.form;
        table.render({
            elem: '#liblist'
            ,url:'/adm/liblist'
            ,method:'post'
            ,id:'liblist'
            ,cols: [[
                 {field:'num', width:80, title: '工号', sort: true}
                ,{field:'name', width:150, title: '姓名', sort: true}
                ,{field:'password', width:150, title: '密码', sort: true}
                ,{field:'status', width:90, title: '状态', sort: true
                    ,templet: function(d){
                    return '<input type="checkbox" '+(d.status===1?'checked=""':'')+' lay-skin="switch" data-num="'+d.num+'" lay-filter="status" lay-text="ON|OFF">';
                    }
                }
            ]]
            ,request:{limitName:'size'}
            ,response:{dataName:'librarians'}
            ,page:true
            ,limit:6
            ,limits:[5,6,8,12]
            ,even:true
        });
        //监听指定开关
        form.on('switch(status)', function(){
            var sw=$(this);
            var num=sw.data("num")
            var checked=this.checked
            $.ajax({
                url:"/adm/openLib/"+num+"/"+checked,
                success:function(data){
                    if(data==null || data<=0){
                        sw.prop("checked",!checked);
                        form.render('checkbox');
                        layer.alert("操作失败");
                    }
                },
                error:function(e){
                    console.log(e);
                    layer.alert("修改失败");
                }
            })
        });
    });
});