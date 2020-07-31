layui.use(['form', 'layedit', 'laydate','upload'], function() {
    var form = layui.form
        ,layer = layui.layer
        ,$ = layui.jquery
        ,upload = layui.upload;
    form.verify({
        ISBN: [
            /^\d{13}$/
            ,'ISBN必须为13位纯数字'
        ]
        ,price: [
            /((^[1-9]\d*)|^0)(\.\d{0,2}){0,1}$/
            ,'价格不合法'
        ]
    });
    form.on('submit(submit)', function(data){
        return data.field != null;

    });
    var uploadInst = upload.render({
        elem: '#uploadImg'
        , url: '/lib/uploadImg'
        , before: function (obj) {
            obj.preview(function (index, file, result) {
                $('#bookImg').attr('src', result); //图片链接（base64）
            });
        }
        , done: function (res) {
            console.log(res);
            if (res.success=="false") {
                return layer.msg(res.message);
            }
            $('input[type="hidden"][name="img"][lay-verify="img"][autocomplete="off"][class="layui-input"]').val(res.message);

            return layer.msg('上传成功');
        }
        , error: function () {
            var uploadFail = $('#uploadFail');
            uploadFail.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs upload-reload">重试</a>');
            uploadFail.find('.upload-reload').on('click', function () {
                uploadInst.upload();
            });
        }
    });
})