/**
 * [orderBy description]
 * @param  {[type]} source [description]
 * @param  {[type]} orders [description]
 * @param  {[type]} type  {asc, desc}  [description]
 * @return {[type]}        [description]
 */
function orderBy(source, orders, type) {

    if (source instanceof Array && orders instanceof Array && orders.length > 0) {

        var ordersc = orders.concat([]);
        var sorttype = type || 'asc';
        var results = [];
        var totalSum = {};

        function grouporder(source, orders, totalSum) {

            source.sort(function(a, b) {
                var convertA = a[orders[0]];
                var convertB = b[orders[0]];
                if (typeof convertA == 'string' && typeof convertB == 'string') {
                    if (sorttype.toUpperCase() == 'ASC') {
                        return convertA.localeCompare(convertB);
                    } else {
                        return convertB.localeCompare(convertA);
                    }
                } else {
                    if (sorttype.toUpperCase() == 'ASC') {
                        return convertA - convertB;
                    } else {
                        return convertB - convertA;
                    }
                }

            });

            var groupmap = new Map();
            source.forEach((item) => {
                if (groupmap.has(item[orders[0]])) {
                    groupmap.get(item[orders[0]]).push(item);
                } else {
                    groupmap.set(item[orders[0]], []);
                    groupmap.get(item[orders[0]]).push(item);
                }
            });

            orders.shift();

            for (let [key, val] of groupmap) {

                totalSum[key] = {};
                totalSum[key].name = key;
                totalSum[key].value = val.length;
                if (orders.length === 0) {
                    results = results.concat(val);
                } else {
                    totalSum[key].children = {};
                    var orderscopy = orders.concat([]);
                    grouporder(val, orderscopy, totalSum[key].children);
                }
            }
        }

        grouporder(source, ordersc, totalSum);

        return {
            results: results,
            totalSum: totalSum
        };
    } else {
        return source;
    }
}