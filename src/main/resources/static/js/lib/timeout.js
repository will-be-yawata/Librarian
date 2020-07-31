$(function () {
    layui.use(['table','layer'], function() {
        var $ = layui.jquery
            ,table = layui.table
            ,layer=layui.layer;
        table.render({
            elem: '#timeoutlist'
            ,url:'/lib/timeout'
            ,method:'post'
            ,id:'timeoutlist'
            ,cols: [[
                 {field:'id', width:80, title: 'ID', sort: true}
                ,{field:'bname', width:150, title: '书名', sort: true}
                ,{field:'num', width:110, title: '学号', sort: true}
                ,{field:'sname', width:80, title: '姓名', sort: true}
                ,{field:'creationtime',width:240, title: '申请日期', sort: true}
                ,{field:'deadline', width:240, title: '截止日期', sort: true}
                ,{field:'mStatus',width:80,title:'状态'}
            ]]
            ,parseData:function(res){
                for (let i = 0; i < res.timeouts.length; i++) {
                    res.timeouts[i]["bname"]=res.timeouts[i]["book"]["name"];
                    res.timeouts[i]["num"]=res.timeouts[i]["student"]["num"];
                    res.timeouts[i]["sname"]=res.timeouts[i]["student"]["name"];
                    switch (res.timeouts[i]["status"]) {
                        case 0:
                            res.timeouts[i]["mStatus"]="未处理";
                            break;
                        case 1:
                            res.timeouts[i]["mStatus"]="已通过";
                            break;
                        case 2:
                            res.timeouts[i]["mStatus"]="未通过";
                            break;
                    }
                }
                return res;
            }
            ,request:{limitName:'size'}
            ,response:{dataName:'timeouts'}
            ,page:true
            ,limit:12
            ,limits:[5,6,8,12,15]
            ,even:true
        });
    });

});