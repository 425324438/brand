//整副牌
var typeList = new Array('black', 'red', 'plum', 'square');

class Brand {
    /**
     * 牌对象
     * @param {} id 为自增ID  1 ~ 54
     * @param {*} type 黑红梅方
     * @param {} val 具体的大小  比如 2 3  4 5 6 7 ，比大小的时候用
     */
    constructor(brand){
        this.id = brand.id;
        this.type = brand.type;
        this.val = brand.val;
    }

    getType(type){
        var typeStr = null;
        switch (type) {
            case 'black':
                typeStr = '黑';
                break;
            case 'red':
                typeStr = '红';
                break;
            case 'plum':
                typeStr = '梅';
                break;
            case 'square':
                typeStr = '方';
                break;
            default:
                break;
        }
        return typeStr;
    }

    createBrandLiHtml() {

        var html =  ' 【type='+ this.type +'】' +
                    ' val='+ this.val ;

        var input = document.createElement('input');
        input.type = 'hidden';
        input.value = JSON.stringify(this);

        var brandText = document.createElement('div');
        brandText.append(html);
        brandText.appendChild(input);

        var brandDiv = document.createElement('div');
        brandDiv.className = 'brand';
        brandDiv.appendChild(brandText);

        var div = document.createElement('div');
        div.appendChild(brandDiv);

        var li = document.createElement('li');
        li.id = this.id;
        li.className = 'brandList';
        li.appendChild(div);

        return li.outerHTML;
    }
}

var sendBrand = new Array();

/**
 * 用户出牌
 */
function send() {

    var json = socketSendJson('sendBrand');
    json.Brand = sendBrand;
    json.brandType = '三代一';
    websocket.send(JSON.stringify(json));
}

function pushSendBrandArray(brand) {
    sendBrand.push(brand);
}

function removeSendBrandArray(brand) {
    var index = sendBrand.indexOf(brand);
    if (index > -1) {
        sendBrand.splice(index, 1);
    }
}

function brandsAddClick() {
    $('#brandList li').on('click',function(ev){
        var val = $(this).find("input:hidden").val();
        var className = $(this)[0].className;

        if(className.indexOf('brandList-active') !== -1){
            removeSendBrandArray(val);
        } else {
            pushSendBrandArray(val);
        }

        $(this).toggleClass("brandList-active");
        console.log(sendBrand);
    });
}