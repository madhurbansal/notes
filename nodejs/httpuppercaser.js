/**
    1. For a string received through http post request, return the same string capitalized
    2. Start the http server on a port given as command line parameter
    3. Use streams
*/
var map = require('through2-map');
var http = require('http');

var server = http.createServer(function(req, res){
    if (req.method != 'POST')
        return res.end('send me a POST\n');
    req.pipe(map(function(chunk){
          return chunk.toString().toUpperCase();  
    }))
    .pipe(res);
});
server.listen(process.argv[2]);
