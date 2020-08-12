var timerHander ;
$(function(){
    // 从localStorage取出登录者信息

    var userStr=localStorage.getItem("dddd");
    if (""==userStr||userStr==undefined){
        location.href="login.html";
        return ;
    }
    var userObj=JSON.parse(userStr);
    $("#showLoginUser").text(userObj.name);
    if (""!=userObj.photoUrl && undefined!=userObj.photoUrl&&"null"!=userObj.photoUrl){
        $("#showLoginPhoto").attr('src',userObj.photoUrl);
    }
    // 注销按钮动作


    // treeview设置
    // $.getJSON(url , 回调函数) 是使用ajax的get请求方式，请求结果是一个json字符串
    $.getJSON('json/menu.json', function (data) {
        $("#tree").treeview({
            data: data,
            selectedIcon: "glyphicon glyphicon-menu-right",
            selectedBackColor: '#FF7F24',
            collapseIcon : '' ,  // 子项展开时去掉前面默认的 + 图标
            expandIcon : '' ,    // 子项未展开时去掉前面默认 + 图标
            onNodeSelected: function (event, data) {
                var navHtml = '' ;
                if(data.text.indexOf('首页') >= 0) {
                    navHtml = "<li>首页</li>" ;
                } else {
                    // 根据当前选中节点的父节点
                    var parent = $("#tree").treeview("getNode", data.parentId);
                    navHtml = "<li>" + parent.text + "</li>" +
                        "<li>" + data.text + "</li>" ;
                }
                $("#breadcrumb").html(navHtml);
                $("#contentFrame").attr("src", data.href);
            }
        })
    })

    timerHander = setInterval(function(){
        $("#showLoginTime").text(getDateStr(new Date())) ;
    }, 1000) ;
})
window.onunload = function() {
    clearInterval(timerHander) ;
}
