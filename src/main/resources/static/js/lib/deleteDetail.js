$(function () {
    var ids=window.location.search.substr(1).split('&');
    console.log(ids);
    layui.use(['table','layer'], function() {
        var $ = layui.jquery
            ,table = layui.table
            ,layer=layui.layer;
        table.render({
            elem: '#borrowlist'
            ,url:'/lib/delDetail'
            ,method:'post'
            ,where:{ids:ids}
            ,id:'borrowlist'
            ,cols: [[
                {type:'checkbox'}
                ,{field:'id', width:80, title: 'ID', sort: true}
                ,{field:'bname', width:150, title: '书名', sort: true}
                ,{field:'num', width:110, title: '学号', sort: true}
                ,{field:'sname', width:80, title: '姓名', sort: true}
                ,{field:'creationtime',width:240, title: '申请日期', sort: true}
                ,{field:'deadline', width:240, title: '截止日期', sort: true}
                ,{field:'status', width:80, title: '状态', sort: true}
                ,{fixed: 'right',title:'操作',toolbar:'#borrowlistBar',width:150}
            ]]
            ,parseData:function(res){
                for (let i = 0; i < res.borrows.length; i++) {
                    res.borrows[i]["bname"]=res.borrows[i]["book"]["name"];
                    res.borrows[i]["num"]=res.borrows[i]["student"]["num"];
                    res.borrows[i]["sname"]=res.borrows[i]["student"]["name"];
                    var status=res.borrows[i].status;
                    if(status===0){
                        res.borrows[i].status="未处理";
                    }else if(status===1){
                        res.borrows[i].status="已通过";
                    }else{
                        res.borrows[i].status="未通过";
                    }
                }
                return res;
            }
            ,request:{limitName:'size'}
            ,response:{dataName:'borrows'}
            ,page:true
            ,limit:6
            ,limits:[5,6,8,12]
            ,even:true
            ,defaultToolbar: ['filter', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
                title: '提示'
                ,layEvent: 'LAYTABLE_TIPS'
                ,icon: 'layui-icon-tips'
            }]
        });
        table.on('tool(borrows)', function(obj){
            console.log(obj)
            if(obj.event === 'del'){
                layer.confirm("确定归还了吗？(操作不可逆)",function(index){
                    $.ajax({
                        url:"/lib/giveback/"+obj.data.id,
                        success:function(data){
                            console.log(data);
                            if(data!=null && data>0){
                                layer.alert("删除成功");
                                layer.close(index);
                                obj.del();
                            }
                        },
                        error:function(e){
                            layer.alert("删除失败");
                            console.log(e);
                        }
                    })
                });
            } else if(obj.event === 'send'){
                //通过消息队列通知学生速速归还
            }
        });
    });
});