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

        var html =  ' id='+ this.id +'<br/>' +
                    ' type='+ this.type +'<br/>' +
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

