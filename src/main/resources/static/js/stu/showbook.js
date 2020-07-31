$(function () {
    var category_id=window.location.search.substr(1);
    layui.use(['layer','laypage'], function() {
        var $ = layui.jquery
            ,layer=layui.layer
            ,laypage=layui.laypage;
        $.ajax({
            url:"/stu/showbookcount/"+category_id,
            success:function(data){
                if(data!=null){
                    laypage.render({
                        elem: 'pageable'
                        ,count: data
                        ,limits:[6,12,18,24]
                        ,limit:12
                        ,layout: ['count', 'prev', 'page', 'next', 'limit', 'refresh', 'skip']
                        ,jump: function(obj){
                            $.ajax({
                                url:"/stu/showbook/"+category_id,
                                type:"post",
                                data:{page:obj.curr,size:obj.limit},
                                success:function(data){
                                    console.log(data);
                                    if(data!=null){
                                        var bookListRow=$(".bookListRow");
                                        bookListRow.html("");
                                        var bookList="";
                                        for (let i = 0; i < data.length; i++) {
                                            bookList+='<div class="layui-col-md2 bookItem" data-id="'+data[i].id+'">' +
                                                '<img src="/static/images/'+data[i].img+'" width="100%"/>' +
                                                '<div class="layui-form-item">' +
                                                '<p>《'+data[i].name+'》</p><em style="font-size: 12px;color:#bbb;">'+data[i].author+'</em>' +
                                                '</div></div>';
                                        }
                                        bookListRow.html(bookList);
                                        $(".bookItem").unbind("click").bind("click",function(){
                                            window.location.href="/stu/bookDetail/"+$(this).data("id");
                                        });
                                    }
                                },
                                error:function(e){
                                    console.log(e);
                                    layer.alert("查询失败");
                                }
                            })
                        }
                    });
                }
            },
            error:function(e){
                console.log(e);
                layer.alert("查询错误");
            }
        });
    });
});