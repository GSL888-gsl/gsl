// 从localStorage取出登录者信息
var userObj;
$(function () {
        var userStr=localStorage.getItem("dddd");
        if (""==userStr||userStr==undefined){
            location.href="login.html";
            return ;
        }
 userObj=JSON.parse(userStr);

$(function(){
    // 显示登录者头像
    if (""!=userObj.photoUrl && undefined!=userObj.photoUrl&&"null"!=userObj.photoUrl){
        $("#showPhoto").attr('src',userObj.photoUrl);
    }


    // 重置密码功能实现
    $("#repassBtn").click(function(){
        var info={id:userObj.id, oldpass:$("#oldPass").val(), newpass :$("#newPass").val()}
        console.log(JSON.stringify(info));
        $.ajax({
            url:aaa,
            type:'PUT',
            data:JSON.stringify(info),
            contentType : 'application/json;charset=UTF-8' ,
            dataType : 'json' ,
            success : function(reqData) {
                alert(reqData.msg);
            }
        });
    });
});
    // 更新头像-FileInput 初始化,更换uploadUrl？？？？？？？？？？？
    var oFileInput = new FileInput();
    oFileInput.Init("userphoto", bbb+ "?id=" + userObj.id,);

}) ;
//初始化FileInput
var FileInput = function () {
    var oFile = new Object();

    //初始化fileinput控件（第一次初始化）
    oFile.Init = function(ctrlName, uploadUrl) {
        var control = $('#' + ctrlName);

        //初始化上传控件的样式
        control.fileinput({
            language: 'zh', //设置语言
            uploadUrl: uploadUrl, //上传的地址
            allowedFileExtensions : ['jpg', 'png','gif'],
            maxFileSize : 2048,			// 以kb为单位
            maxFilesNum: 1,

            showUpload: true, //是否显示上传按钮
            showCaption: false,//是否显示标题
            browseClass: "btn btn-primary", //按钮样式
            //dropZoneEnabled: false,//是否显示拖拽区域
            //minImageWidth: 50, //图片的最小宽度
            //minImageHeight: 50,//图片的最小高度
            //maxImageWidth: 1000,//图片的最大宽度
            //maxImageHeight: 1000,//图片的最大高度
            //maxFileSize: 0,//单位为kb，如果为0表示不限制文件大小
            //minFileCount: 0,
            enctype: 'multipart/form-data',
            previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
        });

        //导入文件上传完成之后的事件？？？？？？？？？？？？？？？？
        $("#userphoto").on("fileuploaded", function (event, data, previewId, index) {
            $("#updatePhoto").modal("hide");
            console.log(data) ;
            var responseData = data.response ;
            if(responseData.errCode === 0) {
                bootbox.alert('上传成功');
                // 清除文件上传预览框
                $(event.target).fileinput('clear') ;
                // 刷新头像？？？？？？？？？
                $("#showPhoto").attr('src',responseData.data);

                // 更新数据存储？？？？？？？？？？？？？？
                userObj.photoUrl=responseData.data;
                localStorage.setItem("dddd",JSON.stringify(userObj));

                // 刷新index页面头像显示
                $("#showLoginPhoto", window.parent.document).attr("src", responseData.data);

            }
        }).on("fileerror" , function(event , data , msg){
            console.log(msg) ;
        }) ;
    }
    return oFile;
};
