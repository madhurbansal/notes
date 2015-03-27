/**
    TCP server to respond with current time in the format
        YYYY-MM-DD HH:MM
*/

function appendZero(num) {
    var appendedNum = num.toString();
    if (appendedNum.length < 2) {
        appendedNum = '0' + appendedNum;
    }
    return appendedNum;
}



var net = require('net');

var server = net.createServer(function (socket) {
    var currentDate = new Date();
    var year = currentDate.getFullYear();
    var month = currentDate.getMonth() + 1;
    var day = currentDate.getDate();
    var hours = currentDate.getHours();
    var minute = currentDate.getMinutes();
    var dateToPrint = year + '-' + appendZero(month) + '-' + appendZero(day) + ' ' + appendZero(hours) + ':' + appendZero(minute) + '\n';
    
    socket.end(dateToPrint);
});
server.listen(process.argv[2]);
