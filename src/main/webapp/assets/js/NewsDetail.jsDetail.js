$(function () {
    var temp=localStorage.getItem("currentNews");
    if (temp!=null && temp!=""){
        obj=JSON.parse(temp);
        $("#name").val(obj.name);
        $("#password").val(obj.password);
        $("#regDatetime").val(obj.regDatetime);
        $("#id").val(obj.id);
        if(null != obj.photo &&"" != obj.photo){
            $("#showPhoto").attr('src', obj.photo)
        }
    }
})