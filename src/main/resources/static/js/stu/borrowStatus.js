$(function () {
    layui.use(['table','layer'], function() {
        var $ = layui.jquery
            ,table = layui.table
            ,layer=layui.layer;
        table.render({
            elem: '#borrowlist'
            ,url:'/stu/borrowStatus'
            ,method:'post'
            ,id:'borrowlist'
            ,cols: [[
                 {field:'id', width:80, title: 'ID', sort: true}
                ,{field:'bname', width:150, title: '书名', sort: true}
                ,{field:'creationtime',width:240, title: '申请日期', sort: true}
                ,{field:'deadline', width:240, title: '截止日期', sort: true}
                ,{field:'status',width:120,title:'操作',templet:function(d){
                    //d.status:0-未处理、1-已通过、2-未通过
                    return btnsStr(d.status);
                    }}
            ]]
            ,parseData:function(res){
                for (let i = 0; i < res.borrows.length; i++) {
                    res.borrows[i]["bname"]=res.borrows[i]["book"]["name"];
                    res.borrows[i]["num"]=res.borrows[i]["student"]["num"];
                }
                return res;
            }
            ,request:{limitName:'size'}
            ,response:{dataName:'borrows'}
            ,page:true
            ,limit:12
            ,limits:[5,6,8,12,15]
            ,even:true
        });
    });
});
function btnsStr(status){
    //0-未处理、1-已通过、2-未通过
    switch (status) {
        case 0:
            return '<button type="button" class="layui-btn layui-btn-primary">处理中</button>';
        case 1:
            return '<button type="button" class="layui-btn">已通过</button>';
        case 2:
            return '<button type="button" class="layui-btn layui-btn-danger">未通过</button>';
    }
    return '';
}