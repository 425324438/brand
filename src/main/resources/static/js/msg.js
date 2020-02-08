// 聊天窗口处理

function msg(event) {
    var obj = JSON.parse(event.data);
    var html = '<p><b>【系统】'+ CurentTime() +'：';
    switch (obj.type) {
        //用户加入房间
        case "addRoom" :
            html += '</b>有新的用户加入，用户ID：'+ obj.userId +'，现有人数'+ obj.userList +'</p>';
            $('#room_msg').append(html);
            break;
        //房间操作消息
        case "roomSequence":
            if(obj.msg.sequenceUserId === sessionStorage.sessionKey){
                $('#robLandlord').removeAttr('disabled');
            }
            html += '</b>【顺序操作】用户ID：'+ obj.msg.sequenceUserId +'</p>';
            $('#room_msg').append(html);
            break;
        //用户发送消息
        case "room_user_msg":
            html = '';
            html = '<p><b>【房间】'+ CurentTime() +'：';
            html += '</b>'+ event.data.msg +'</p>';
            $('#room_msg').append(html);
            break;
        //用户登录 刷新用户ID
        case undefined:
            $('#userDetail').append('用户ID：'+obj.sessionId);
            break;
        default:

            break;
    }
    $("#room_msg").getNiceScroll().resize();
    setPos();
}


function sendMsg() {
    var val = $('#msg_Text').val();
    var event = {
        data : {
            type : "room_user_msg",
            msg: val
        }
    };
    // msg(event);
    websocket.send(JSON.stringify(event));
    $('#msg_Text').val('');
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