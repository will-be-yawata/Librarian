$(function () {
    layui.use(['table','layer'], function() {
        var $ = layui.jquery
            ,table = layui.table;
        table.render({
            elem: '#loglist'
            ,url:'/adm/log'
            ,method:'post'
            ,id:'loglist'
            ,cols: [[
                 {field:'id', width:80, title: 'ID', sort: true}
                ,{field:'librarian_num', width:120, title: '管理员工号', sort: true}
                ,{field:'action', width:80, title: '动作', sort: true}
                ,{field:'action_time', width:240, title: '操作时间', sort: true}
                ,{field:'book_id', width:85, title: '图书ID', sort: true}
            ]]
            ,parseData:function(res){
                console.log(res);
                return res;
            }
            ,request:{limitName:'size'}
            ,response:{dataName:'logs'}
            ,page:true
            ,limit:6
            ,limits:[5,6,8,12]
            ,even:true
        });
    });
});