$(function () {
    layui.use(['table','layer'], function() {
        var $ = layui.jquery
            ,table = layui.table
            ,layer=layui.layer;
        table.render({
            elem: '#givebacklist'
            ,url:'/lib/giveback'
            ,method:'post'
            ,id:'givebacklist'
            ,cols: [[
                 {field:'id', width:80, title: 'ID', sort: true}
                ,{field:'bname', width:150, title: '书名', sort: true}
                ,{field:'num', width:110, title: '学号', sort: true}
                ,{field:'sname', width:80, title: '姓名', sort: true}
                ,{field:'creationtime',width:240, title: '申请日期', sort: true}
                ,{field:'deadline', width:240, title: '截止日期', sort: true}
                ,{field:'mStatus',width:80,title:'状态'}
                ,{fixed: 'right',title:'操作',toolbar:'#givebacklistBar',width:120}
            ]]
            ,parseData:function(res){
                for (let i = 0; i < res.givebacklist.length; i++) {
                    res.givebacklist[i]["bname"]=res.givebacklist[i]["book"]["name"];
                    res.givebacklist[i]["num"]=res.givebacklist[i]["student"]["num"];
                    res.givebacklist[i]["sname"]=res.givebacklist[i]["student"]["name"];
                    switch (res.givebacklist[i]["status"]) {
                        case 0:
                            res.givebacklist[i]["mStatus"]="未处理";
                            break;
                        case 1:
                            res.givebacklist[i]["mStatus"]="已通过";
                            break;
                        case 2:
                            res.givebacklist[i]["mStatus"]="未通过";
                            break;
                    }
                }
                return res;
            }
            ,request:{limitName:'size'}
            ,response:{dataName:'givebacklist'}
            ,page:true
            ,limit:12
            ,limits:[5,6,8,12,15]
            ,even:true
        });
        table.on('tool(giveback)', function(obj) {
            var data = obj.data;
            console.log(obj);
            console.log(data);
            if(obj.event === 'del'){
                $.ajax({
                    url:"/lib//giveback/"+data.id,
                    success:function(res){
                        if(res!=null && res>0){
                            layer.alert("成功归还");
                            obj.del();
                        }else{
                            layer.alert("归还失败");
                        }
                    },
                    error:function(e){
                        console.log(e);
                        layer.alert("操作失败");
                    }
                })
            }
        });
    });

});