/**
    1. If an http request is detailed time, respond should be a json object parsing the time string received
    2. Start the server on a port given as command line parameter
    3. HINT : node -pe "require('url').parse('/test?q=1', true)"
    4. Create two endpoints one for parsetime (to give time as key-value pairs in json), and another for unixtime to give time as a big integer in json
*/

var http = require('http');
var url = require('url');

var server = http.createServer(function(req, res){
    res.writeHead(200, { 'Content-Type': 'application/json' });
    var urlObj = url.parse(req.url, true);
    var dateRec = new Date(urlObj.query['iso']);
//    console.log(dateRec);
    
    var preparedRes = '';
    if (urlObj.pathname === '/api/parsetime') {
        preparedRes = JSON.stringify({'hour':dateRec.getHours(), 'minute':dateRec.getMinutes(), 'second':dateRec.getSeconds()});  
    }
    
    if (urlObj.pathname === '/api/unixtime') {
        preparedRes = JSON.stringify({'unixtime':dateRec.getTime()});
    }
    
//    console.log(preparedRes);
    res.end(preparedRes);
    
});

server.listen(process.argv[2]);



/*var http = require('http')
var url = require('url')

function parsetime (time) {
  return {
    hour: time.getHours(),
    minute: time.getMinutes(),
    second: time.getSeconds()
  }
}

function unixtime (time) {
  return { unixtime : time.getTime() }
}

var server = http.createServer(function (req, res) {
  var parsedUrl = url.parse(req.url, true)
  var time = new Date(parsedUrl.query.iso)
  var result

  if (/^\/api\/parsetime/.test(req.url))
    result = parsetime(time)
  else if (/^\/api\/unixtime/.test(req.url))
    result = unixtime(time)

  if (result) {
    res.writeHead(200, { 'Content-Type': 'application/json' })
    res.end(JSON.stringify(result))
  } else {
    res.writeHead(404)
    res.end()
  }
})
server.listen(Number(process.argv[2]))*/