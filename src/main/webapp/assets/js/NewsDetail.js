
var parentDom = window.opener.document ;
console.log(parentDom) ;
var imgs = new Array();
$(function(){
    var aStr = sessionStorage.getItem("currentNews") ;
    // if(aStr == '' || aStr == null) {
    //     window.close() ;
    //     return ;
    // }
    var aObj = JSON.parse(aStr) ;
    sessionStorage.removeItem("currentActivity") ;
    console.log(aObj) ;
    $("#id").val(aObj.id) ;
    $("#title").val(aObj.title) ;
    $("#newstype").val(aObj.typename) ;
    $("#editor").val(aObj.content) ;
    $("#comefrom").val(aObj.comefrom) ;

    function sendFile($summernote ,file) {
        var formData = new FormData();
        formData.append("file",file) ;
        $.ajax({
            type : 'post' ,
            url : hhh ,
            data : formData ,
            //如果提交data是FormData类型，那么下面三句一定需要加上
            cache : false ,
            processData : false,
            contentType : false ,
            success : function (data) {
                console.log(data) ;
                if(data.errCode == 0) {
                    $('#editor').summernote('insertImage', data.data);  //直接插入路径就行，filename可选
                    // imgs.push(data.data.substr(data.data.lastIndexOf('/') + 1)) ;
                } else {
                    bootbox.alert('图片插入失败！') ;
                }
            } ,
            error : function (data) {
                bootbox.alert('上传失败') ;
            }
        })
    }
    $(function(){
        // $.get(
        //     eee,
        //     function (reqDate) {
        //         if (reqDate.errCode==0){
        //             var str='<option id="aaaa"></option>';
        //             // var str='';
        //             $.each(reqDate.data,function (index,item) {
        //                 str +='<option value="'+item.id+' ">';
        //                 str +=item.typename;
        //                 str +='</option>';
        //             });
        //             $("#newstype").html(str);
        //         }
        //     }
        // );
        $summernote = $("#editor").summernote({
            lang : 'zh-CN' ,
            height:300,
            toolbar : [
                <!--字体工具-->
                ['fontname', ['fontname']], //字体系列
                ['style', ['bold', 'italic', 'underline', 'clear']], // 字体粗体、字体斜体、字体下划线、字体格式清除
                ['font', ['strikethrough', 'superscript', 'subscript']], //字体划线、字体上标、字体下标
                ['fontsize', ['fontsize']], //字体大小
                ['color', ['color']], //字体颜色

                <!--段落工具-->
                // ['style', ['style']],//样式
                // ['para', ['ul', 'ol', 'paragraph']], //无序列表、有序列表、段落对齐方式
                // ['height', ['height']], //行高

                <!--插入工具-->
                ['table',['table']], //插入表格
                ['hr',['hr']],//插入水平线
                ['link',['link']], //插入链接
                ['picture',['picture']], //插入图片
                // ['video',['video']], //插入视频

                <!--其它-->
                ['fullscreen',['fullscreen']], //全屏
                ['codeview',['codeview']], //查看html代码
                ['undo',['undo']], //撤销
                ['redo',['redo']], //取消撤销
                ['help',['help']], //帮助
            ],
            callbacks: {
                onImageUpload: function (files) {
                    sendFile($summernote ,files[0]);
                },
            }
        }) ;
        // 添加按钮
        $("#btn_addSure").click(function(){
            // var content = {
            //     typeid : $("#newstype").val() ,
            //     title : $("#addForm input[name=title]").val() ,
            //     content : $("#editor").summernote('code') ,
            //     comefrom : $("#addForm input[name=comefrom]").val() ,
            //     // imgs : imgs.join(',')
            // };
            // imgs.length = 0;

            var info={id:aObj.id, content:$("#editor").summernote('code') , comefrom :$("#addForm input[name=comefrom]").val() }
            console.log(info);
            $.ajax({
                url:jjj,
                type:'PUT',
                data:JSON.stringify(info),
                contentType : 'application/json;charset=UTF-8' ,
                dataType : 'json' ,
                success : function(reqData) {
                    if(reqData.errCode == 0) {
                        bootbox.confirm('修改成功，是否继续？',function(reqData) {
                            if(reqData) {
                                reset();
                            } else {
                                var pTable = parentDom.getElementById("newsTable") ;
                                $(pTable).bootstrapTable('refresh') ;
                                window.close() ;
                            }
                        })
                    } else {
                        bootbox.alert(data.errMsg + ",稍后再试！") ;
                    }
                }
            });
            // $.post(
            //     iii ,
            //     content ,
            //     function(data) {
            //         if(data.errCode == 0) {
            //             bootbox.confirm('添加成功，是否继续？',function(data) {
            //                 if(data) {
            //                     reset();
            //                 } else {
            //                     var pTable = parentDom.getElementById("newsTable") ;
            //                     $(pTable).bootstrapTable('refresh') ;
            //                     window.close() ;
            //                 }
            //             })
            //         } else {
            //             bootbox.alert(data.errMsg + ",稍后再试！") ;
            //         }
            //     }
            // );
        }) ;
        $("#btn_addCancel").click(function(){
            reset();
        })
        $('#addDatetimepicker').datetimepicker({
            format: 'yyyy年mm月dd日',
            autoclose: true,
            minView:'month',
            maxView:'month',
            todayBtn : true ,
            language:'zh-CN'
        });
    });
})

function reset() {
    $("#editor").summernote('code' , '')
    $("#addForm")[0].reset() ;
    // imgs.length = 0;
}

