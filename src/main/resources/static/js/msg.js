// 聊天窗口处理

function msg(event) {
    var obj = JSON.parse(event.data);
    var html = '<p><b>【系统】'+ CurentTime() +'：';
    if(obj.type === 'addRoom'){
         html += '</b>有新的用户加入，用户ID：'+ obj.userId +'，现有人数'+ obj.userList +'</p>';
        $('#room_msg').append(html);

    } else if(obj.type === undefined){
        $('#userDetail').append('用户ID：'+obj.sessionId);

    } else if(obj.type === 'roomSequence'){
        if(obj.msg.sequenceUserId === sessionStorage.sessionKey){
            $('#robLandlord').removeAttr('disabled');
        }
        html += '</b>【顺序操作】用户ID：'+ obj.msg.sequenceUserId +'</p>';
        $('#room_msg').append(html);
    }

    else {
        html += '</b>'+ event.data +'</p>';
        $('#room_msg').append(html);
    }

    $("#room_msg").getNiceScroll().resize();
    setPos();
}

function setPos() {
    var div = document.getElementById('room_msg');
    div.scrollTop = div.scrollHeight;
}

function CurentTime() {
    var now = new Date();

    var year = now.getFullYear();       //年
    var month = now.getMonth() + 1;     //月
    var day = now.getDate();            //日

    var hh = now.getHours();            //时
    var mm = now.getMinutes();          //分

    var clock = year + "-";

    if(month < 10)
        clock += "0";

    clock += month + "-";

    if(day < 10)
        clock += "0";

    clock += day + " ";

    if(hh < 10)
        clock += "0";

    clock += hh + ":";
    if (mm < 10) clock += '0';
    clock += mm;
    return(clock);
}