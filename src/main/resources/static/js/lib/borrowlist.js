$(function () {
    layui.use(['table','layer'], function() {
        var $ = layui.jquery
            ,table = layui.table
            ,layer=layui.layer;
        table.render({
            elem: '#borrowlist'
            ,url:'/lib/getBorrows'
            ,method:'post'
            ,id:'borrowlist'
            ,where:{status:-1}
            ,cols: [[
                 {field:'id', width:80, title: 'ID', sort: true}
                ,{field:'bname', width:150, title: '书名', sort: true}
                ,{field:'num', width:110, title: '学号', sort: true}
                ,{field:'sname', width:80, title: '姓名', sort: true}
                ,{field:'creationtime',width:240, title: '申请日期', sort: true}
                ,{field:'deadline', width:240, title: '截止日期', sort: true}
                ,{field:'status',width:280,title:'操作',templet:function(d){
                    //d.status:0-未处理、1-已通过、2-未通过
                    return btnsStr(d.status,d.id);
                    }}
            ]]
            ,parseData:function(res){
                for (let i = 0; i < res.borrows.length; i++) {
                    res.borrows[i]["bname"]=res.borrows[i]["book"]["name"];
                    res.borrows[i]["num"]=res.borrows[i]["student"]["num"];
                    res.borrows[i]["sname"]=res.borrows[i]["student"]["name"];
                }
                return res;
            }
            ,request:{limitName:'size'}
            ,response:{dataName:'borrows'}
            ,page:true
            ,limit:12
            ,limits:[5,6,8,12,15]
            ,even:true
            ,done: function(){
                var mBtns=$(".mBtns");
                for (let i = 0; i < mBtns.length; i++) {
                    var id=$(mBtns[i]).data("id");
                    $('.mBtns[data-id="'+id+'"]>button:eq(1)').unbind("click").bind("click",function(){
                        var mid=$(this).data("id");
                        $.ajax({
                            url:"/lib/updateStatus/"+mid+"/1",
                            success:function(data){
                                if(data) {
                                    $('.mBtns[data-id="' + mid + '"]>button:eq(1)').removeClass("layui-btn-primary").unbind("click");
                                    $('.mBtns[data-id="' + mid + '"]>button:eq(0)').removeClass("layui-btn-warm").addClass("layui-btn-disabled").unbind("click");
                                    $('.mBtns[data-id="' + mid + '"]>button:eq(2)').removeClass("layui-btn-primary").addClass("layui-btn-disabled").unbind("click");
                                }else{
                                    layer.alert("修改失败");
                                }
                            },
                            error:function(e){
                                console.log(e);
                                layer.alert("操作失败");
                            }
                        });
                    })
                    $('.mBtns[data-id="'+id+'"]>button:eq(2)').unbind("click").bind("click",function(){
                        var mid=$(this).data("id");
                        $.ajax({
                            url:"/lib/updateStatus/"+mid+"/2",
                            success:function(data){
                                if(data) {
                                    $('.mBtns[data-id="' + mid + '"]>button:eq(2)').removeClass("layui-btn-primary").addClass("layui-btn-danger").unbind("click");
                                    $('.mBtns[data-id="' + mid + '"]>button:eq(0)').removeClass("layui-btn-warm").addClass("layui-btn-disabled").unbind("click");
                                    $('.mBtns[data-id="' + mid + '"]>button:eq(1)').removeClass("layui-btn-primary").addClass("layui-btn-disabled").unbind("click");
                                }else{
                                    layer.alert("修改失败");
                                }
                            },
                            error:function(e){
                                console.log(e);
                                layer.alert("操作失败");
                            }
                        });
                    })
                }
            }
        });
    });

});
function btnsStr(status,id){
    //d.status:0-未处理、1-已通过、2-未通过
    if(status==0){
        return '<div class="mBtns" data-id="'+id+'"><button type="button" class="layui-btn layui-btn-warm layui-btn-sm" data-id="'+id+'">处理中</button>' +
            '<button type="button" class="layui-btn layui-btn-primary layui-btn-sm" data-id="'+id+'">已通过</button>' +
            '<button type="button" class="layui-btn layui-btn-primary layui-btn-sm" data-id="'+id+'">未通过</button></div>';
    }else if(status==1){
        return '<div><button type="button" class="layui-btn layui-btn-disabled layui-btn-sm">未处理</button>' +
            '<button type="button" class="layui-btn layui-btn-sm">已通过</button>' +
            '<button type="button" class="layui-btn layui-btn-disabled layui-btn-sm">未通过</button></div>';
    }else{
        return '<div><button type="button" class="layui-btn layui-btn-disabled layui-btn-sm">未处理</button>' +
            ' <button type="button" class="layui-btn layui-btn-disabled layui-btn-sm">已通过</button>' +
            '<button type="button" class="layui-btn layui-btn-danger layui-btn-sm">未通过</button></div>';
    }
}