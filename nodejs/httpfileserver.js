/**
    1. For every request, return a file given as command line parameter while starting the server
    2. Start the http server on a port given as command line parameter
    3. Use streams
*/

var http = require('http');
var fs = require('fs');

var server = http.createServer(function (request, response) {
    var src = fs.createReadStream(process.argv[3]);
    src.pipe(response);
});
server.listen(process.argv[2]);
