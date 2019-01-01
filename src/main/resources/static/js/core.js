
$(function (e) {
    $(window).bind('beforeunload', function(){
        //退出房间
        outRoom();
        //页面刷新是 清空
        sessionStorage.clear();
        return true;
    });
});

getRoomList();
/**
 * 获取房间列表
 */
function getRoomList() {
    $.ajax({
        type : 'get',
        url :    '/room/roomList/',
        dataType:'json',
        success : function(data){
            console.log(data);
            if(data != null && data.status === 0){
                var roomIds = data.data;
                for( index in roomIds){
                    if(!isNaN(index)){
                        var id = roomIds[index].roomId;
                        var nmae = roomIds[index].roomName;
                        $('#roomList').append('<li><a class="addRoom" onclick="addRoom(this)" ><span data-id="'+ id +'">'+ nmae +'</span></a></li>');
                    }
                }
            }
        }
    });
}

/**
 * 创建房间
 */
function createRoom() {
    var name = $('#roomName').val();
    $.ajax({
        type : 'POST',
        url :    '/room/createRoom/'+sessionStorage.sessionKey,
        data:{
            roomName : name
        },
        dataType:'json',
        success : function(data){
            console.log(data);
            if(data.status === 0){
                sessionStorage.roomKey = data.data.roomId;
                layer.msg('创建房间成功，房间名称：'+ data.data.roomName , { icon:1});
                $('#addRoomName').html(data.data.roomName);
            } else {
                layer.msg(data.msg , { icon:2});
            }
        }
    });
}

/**
 * 加入房间
 * @param _this
 */
function addRoom(_this) {
    if(sessionStorage.roomKey != null){
        layer.msg("请先退出当前房间！");
        return false;
    }
    //RoomID
    var id = $(_this).find('span').data('id');
    var roomName = $(_this).find('span').html();
    console.log(id);

    $.ajax({
        type : 'POST',
        url :    '/room/addRoom/'+id+'/'+sessionStorage.sessionKey,
        data:{
            roomName : '小地主'
        },
        dataType:'json',
        success : function(data){
            console.log(data);
            if(data.status === 0){
                layer.msg('加入房间成功，房间名称：'+ roomName , { icon:1});
                sessionStorage.roomKey = id;
                $('#addRoomName').html(roomName);
            }
            else {
                layer.msg(data.msg , { icon:2});
            }
            //TODO 主页面显示自己加入的房间
        }
    });
}

/**
 * 退出房间
 */
function outRoom() {

    $.ajax({
        type : 'POST',
        url :    '/room/outRoom/'+sessionStorage.roomKey+'/'+sessionStorage.sessionKey,
        dataType:'json',
        success : function(data){
            console.log(data);
            //TODO 主页面显示自己加入的房间
        }
    });
}

/**
 * 发牌
 */
function licensing() {
    $.ajax({
        type : 'POST',
        url :    '/brand/licensing/'+sessionStorage.roomKey+'/'+sessionStorage.sessionKey,
        data:{
            roomName : '小地主'
        },
        dataType:'json',
        success : function(data){
            console.log(data);
        }
    });
}