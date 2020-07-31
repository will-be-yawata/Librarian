function transLeft() {
    $(".trans1").removeClass("trans1").addClass("trans1Temp");
    $(".trans3").removeClass("trans3").addClass("trans1");
    $(".trans2").removeClass("trans2").addClass("trans3");
    $(".trans1Temp").removeClass("trans1Temp").addClass("trans2");
}

function transRight() {
    $(".trans1").removeClass("trans1").addClass("trans1Temp");
    $(".trans2").removeClass("trans2").addClass("trans1");
    $(".trans3").removeClass("trans3").addClass("trans2");
    $(".trans1Temp").removeClass("trans1Temp").addClass("trans3");
}

function verifyStu() {
    var num = $(".formStu>input[name='num']").val();
    var password = $(".formStu>input[name='password']").val();
    if (num.length == 0) {
        $(".formStu>.prompt").html("请填写学号");
        return false;
    }
    if (password.length == 0) {
        $(".formStu>.prompt").html("请填写密码");
        return false;
    }
    if (num.length != 10) {
        $(".formStu>.prompt").html("学号的长度应为10位");
        return false;
    }
    return true;
}

function verifyLib() {
    var num = $(".formLib>input[name='num']").val();
    var password = $(".formLib>input[name='password']").val();
    if (num.length == 0) {
        $(".formLib>.prompt").html("请填写工号");
        return false;
    }
    if (password.length == 0) {
        $(".formLib>.prompt").html("请填写密码");
        return false;
    }
    if (num.length != 4) {
        $(".formLib>.prompt").html("工号的长度应为4位");
        return false;
    }
    return true;
}

function verifyAdm() {
    var num = $(".formAdm>input[name='num']").val();
    var password = $(".formAdm>input[name='password']").val();
    if (num.length == 0) {
        $(".formAdm>.prompt").html("请填写工号");
        return false;
    }
    if (password.length == 0) {
        $(".formAdm>.prompt").html("请填写密码");
        return false;
    }
    if (num.length != 4) {
        $(".formAdm>.prompt").html("工号的长度应为4位");
        return false;
    }
    return true;
}