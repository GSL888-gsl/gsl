// 从localStorage取出登录者信息
loginStr = localStorage.getItem("loginuser") ;
// if (null == loginStr || loginStr == '') {
//     location.href="../Login.html" ;
// }
// 将localStorage取出字符串转换js对象
loginUser = JSON.parse(loginStr) ;

$(function(){
    $.get(
        eee,
        function (reqDate) {
            if (reqDate.errCode==0){
                var str='<option value="0">--未选择--</option>';
                $.each(reqDate.data,function (index,item) {
                    str +='<option value="'+item.id+' ">';
                    str +=item.typename;
                    str +='</option>';
                });
                $("#search_type").html(str);
            }
        }
    )




    $("#newsTable").bootstrapTable({
        url : ddd ,
        method : 'GET' ,
        toolbar : '#toolbar' ,   // 为表格绑定工具栏
        striped: true,			// 显示为斑马线格式，奇偶行不通背景色
        showRefresh: "true",	// 显示刷新按钮
        showToggle: "true",		// 显示格式切换按钮
        showColumns: "true",	// 显示列过滤按钮
        // 分页相关 ，如果分页，服務器端返回的数据需要包含有total属性和rows属性
        pagination: true,		// 显示分页
        pageNumber: 1,			// 初始化加载第一页
        pageSize: 5,			// 每页2条数据
        pageList: [3, 5, 10],	// 可以选择的每页数量
        sidePagination: "server", //表示服务端请求分页数据

        // 提交到Server的参数列表 ，
        // 参数设定相关
        queryParamsType: "undefined",  // undefined：提交到服务器端的参数自定义
        queryParams: function(params) {
            // 参数params中自带 pageSize , pageNumber , sortName , sortOrder

            // 为params对象增加额外三个属性
            var sDate=$.trim($("#search_date").val());
            if (""!=sDate){
                sDate=sDate.replace('年','-');
                sDate=sDate.replace('月','-');
                sDate=sDate.replace('日','');
            }
            params.title = $.trim($("#search_title").val()) ;
            if ($.trim($("#search_type").val()!='0')) {
                params.typeid = $.trim($("#search_type").val());
            }
            params.dt=sDate;
            console.dir(params) ;
            return params ;
        },

        columns : [
            {checkbox : true} ,
            {field : 'id' , title : 'id' , visible : false} ,
            {title : '序号' , formatter:function (value, row, index) {
                    return index+1 ;
                }} ,
            {field : 'title' , title : '标题' ,formatter : function(value, row, index){
                    return  '<span class="showDetail" style="color:blue; cursor: pointer; ">'+ value +'</span>';
                },events : window.showDetail = {
                    'click .showDetail' : function (e , value , row , index) {
                        sessionStorage.setItem("currentNews" , JSON.stringify(row)) ;
                        window.open('NewsDetail.html') ;
                    }
                }} ,
            {field : 'pubdatetime' , title : '发布日期' } ,
            {field : 'typename' , title : '新闻类别' } ,
            {field : 'comefrom' , title : '新闻来源' } ,
            {
                title : '操作' ,
                formatter: operateFormatter,
                events: operateEvent ,
            }
        ] ,
    });


    // 搜索按钮动作
    $("#btn_query").click(function(){
        $("#newsTable").bootstrapTable('refresh') ;
    });

    $("#btn_add").click(function(){
        window.open('AddNews.html') ;
    });

    //工具栏删除按钮的动作
    $("#btn_delete").click(function () {
        var rows = $("#newsTable").bootstrapTable('getSelections');
        // console.log(rows);
        //判断选中的行数
        if (rows.length==0){
            alert("请选中要删除的新闻");
            return;
        }
        //
        bootbox.confirm("确认删除本新闻?" , function(isSure){
           console.log(isSure);
            if (isSure) {
                //ajax请求
                var upData="";
                $.each(rows,function (index,item) {
                    upData +="id="+item.id + "&";
                });
                upData =upData.substr(0,upData.length-1);
                console.log(upData);
                $.ajax({
                    url:fff+"?"+upData,
                    type:'DELETE',
                    dataType:'json',
                    success:function(reqData){
                        console.log(reqData);
                        alert(reqData.msg);
                        if (0==reqData.errCode){
                            $("#newsTable").bootstrapTable('refresh') ;
                        }
                    }
                })
            }
            }) ;
    })

    // 日历控件初始化
    $('#showDatetimePicker').datetimepicker({
        format: 'yyyy年mm月dd日',
        autoclose: true,
        minView:'month',
        maxView:'month',
        todayBtn : true ,
        language:'zh-CN'
    });

})

// 操作栏外观
function operateFormatter(value, row, index) {
    var del = '<button  type="button" class="remove btn btn-xs btn-danger">' +
        '<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>删除' +
        '</button>';
   /* var detail = '<button  type="button" class="detail btn btn-xs btn-info">' +
        '<span class="glyphicon glyphicon-arrow-right" aria-hidden="true"></span>详情' +
        '</button>';*/
    return del ;
    // return del + "&nbsp;&nbsp;" + detail ;

}
window.operateEvent = {
    'click .remove' : function(e , value , row , index) {
        bootbox.confirm("确认删除本新闻?" , function(data){
            if(data) {
                $.ajax({
                    url:ggg+"?id="+row.id,
                    type:'DELETE',
                    dataType:'json',
                    success:function(reqData){
                        alert(row.id);
                        console.log(reqData);
                        alert(reqData.msg);
                        if (0==reqData.errCode){
                            $("#newsTable").bootstrapTable('refresh') ;
                        }
                    }
                })
            }
        }) ;
    } ,

}
