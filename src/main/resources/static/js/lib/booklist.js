$(function () {
    layui.use(['form','table','layer'], function() {
        var $ = layui.jquery
            ,table = layui.table
            ,layer=layui.layer
            ,form=layui.form;
        table.render({
            elem: '#booklist'
            ,url:'/book/getBooks'
            ,method:'post'
            ,id:'booklist'
            ,cols: [[
                {type:'checkbox'}
                ,{field:'id', width:80, title: 'ID', sort: true}
                ,{field:'isbn', width:150, title: 'ISBN', sort: true}
                ,{field:'name', width:150, title: '书名', sort: true}
                ,{field:'price', width:80, title: '价格', sort: true}
                ,{field:'author',width:160, title: '作者', sort: true}
                ,{field:'published', width:200, title: '出版社', sort: true}
                ,{field:'category_name', width:80, title: '分类', sort: true}
                ,{fixed: 'right',title:'操作',toolbar:'#booklistBar',width:150}
            ]]
            ,parseData:function(res){
                for (let i = 0; i < res.books.length; i++) {
                    res.books[i]["category_name"]=res.books[i]["category"]["name"];
                }
                return res;
            }
            ,request:{limitName:'size'}
            ,response:{dataName:'books'}
            ,page:true
            ,limit:6
            ,limits:[5,6,8,12]
            ,even:true
            ,toolbar: '#booklistToolbar'
        });
        //头工具栏事件
        table.on('toolbar(books)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id);
            switch(obj.event){
                case 'delAll':
                    var books=checkStatus.data;
                    if(books.length>0){
                        var ids=[];
                        for (let i = 0; i < books.length; i++) {
                            ids.push(books[i].id);
                        }
                        layer.confirm('你确定要删除选中项吗？', function(index) {
                            $.ajax({
                                url: "/lib/deletePre",
                                type: "post",
                                contentType: 'application/json;charset=UTF-8',
                                data: JSON.stringify(ids),
                                success: function (data) {
                                    if (data.status === -1) {
                                        layer.alert(data.message);
                                    } else if (data.status === 0) {
                                        var delId=data.delId        //被删除的book_id
                                            ,unDelId=data.unDelId   //不可删除的book_id
                                            ,n=data.n;              //实际删除条数
                                        layer.confirm('删除情况<br/>' +
                                            '已删除书的id:'+delId+'<br/>' +
                                            '未删除书的id:'+unDelId+'<br/>' +
                                            '实际删除条数:'+n,{
                                            btn:unDelId.length>0?['算了','去看看']:['确定']
                                            ,btn1: function (ind) {
                                                table.reload('booklist',{});
                                                layer.close(ind);
                                            }
                                            ,btn2:function(){
                                                var ids = "";
                                                if (unDelId != null && unDelId.length > 0) {
                                                    ids += unDelId[0];
                                                    for (var i = 1; i < unDelId.length; i++) {
                                                        ids += "&" + unDelId[i];
                                                    }
                                                    window.location.href = "/lib/delDetail?" + ids;
                                                }
                                            }
                                        });
                                    } else {
                                        layer.alert("发生了未知错误");
                                    }
                                    layer.close(index);
                                },
                                error: function (e) {
                                    layer.alert("删除失败");
                                    console.log(e);
                                }
                            });
                        });
                    }else{
                        layer.alert('抱歉！你至少要选中一个！');
                    }
                    break;
                case 'screen':
                    layer.open({
                        type: 1
                        ,title: '筛选' //不显示标题栏
                        ,closeBtn: false
                        ,shade: 0.8
                        ,shadeClose:true
                        ,skin:'layui-layer-molv'
                        ,id: 'LAY_layuipro' //设定一个id，防止重复弹出
                        ,btn: ['搜索', '取消']
                        ,btnAlign: 'c'
                        ,moveType: 1 //拖拽模式，0或者1
                        ,content: $("#screenModelForm")
                        ,yes:function(index,layero){
                            table.reload(
                                'booklist'
                                ,{
                                    page: {curr: 1}
                                    // ,where: {book:JSON.stringify(form.val('bookscreen'))}
                                    ,where:form.val('bookscreen')
                                }
                                ,'data');
                            layer.close(index);
                        }
                    });
                    break;
                //自定义头工具栏右侧图标 - 提示
                case 'LAYTABLE_TIPS':
                    layer.alert('这是工具栏右侧自定义的一个图标按钮');
                    break;
            };
        });
        table.on('tool(books)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                layer.confirm('你确定要删除吗？', function(index){
                    $.ajax({
                        url:"/lib/deletePre",
                        type:"post",
                        contentType: 'application/json;charset=UTF-8',
                        data:JSON.stringify([obj.data.id]),
                        success:function(data){
                            if(data.status===-1){
                                layer.alert(data.message);
                            }else if(data.status===0){
                                if(data.n>0){
                                    obj.del();
                                    layer.alert("删除成功");
                                }else{
                                    layer.confirm('删除失败，该书籍被借阅',{
                                        btn:['算了','去看看']
                                        ,btn2:function(){
                                            var unDelId=data.unDelId;
                                            var ids="";
                                            if(unDelId!=null && unDelId.length>0){
                                                ids+=unDelId[0];
                                                for(var i=1;i<unDelId.length;i++){
                                                    ids+="&"+unDelId[i];
                                                }
                                                window.location.href="/lib/delDetail?"+ids;
                                            }
                                        }
                                        ,btn1:function(ind){
                                            layer.close(ind);
                                        }
                                    })
                                }
                            }else{
                                layer.alert("发生了未知错误");
                            }
                            layer.close(index);
                        },
                        error:function(e){
                            layer.alert("删除失败");
                            console.log(e);
                        }
                    });
                });
            } else if(obj.event === 'edit'){
                var id=obj.data.id;
                window.location.href="/lib/updateBook/"+id;
            }
        });
        //自定义验证规则
        form.verify({
            isbn:[
                /^\d{0,13}$/
                ,'ISBN为纯数字，且不超过13位'
            ]
        });
    });
});