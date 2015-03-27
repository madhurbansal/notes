
/**
    1. Take as command line parameter 3 URLs
    2. Using HTTP GET receive complete response of all three in parallel
    3. Only when response of all 3 are complete print them out in the same order on the console
    4. Do not use any third party nodejs library like after, parallel etc..
    5. making sure async requests are accounted for is a key to learn Nodejs.
*/
var http = require('http');
var resData = ['', '', ''];
var resComplete = [false, false, false];

var getURLData = function(response, resIndex) {
    response.setEncoding("utf8");
    response.on('data', function(data){
       resData[resIndex] = resData[resIndex] + data; 
    });

    response.on('error', console.error);
    response.on('end', function() {
//        console.log("done response - " + resIndex);
        resComplete[resIndex] = true;
    });
    
}

http.get(process.argv[2], function(response){
    getURLData(response, 0);
});

http.get(process.argv[3], function(response){
    getURLData(response, 1);
});

http.get(process.argv[4], function(response){
    getURLData(response, 2);
});

var flagCheck = setInterval(function(){
//    console.log("checking for completeness ...")
    if (resComplete[0] && resComplete[1] && resComplete[2]) {
        clearInterval(flagCheck);
        console.log(resData[0]);
        console.log(resData[1]);
        console.log(resData[2]);
    }
}, 100);


/**
    HTTP GET to print the response received from a given url as a command line parameter
*/

/*http.get(process.argv[2],
        function(response) {
            response.setEncoding("utf8");
//            response.on('data',
//                       function(data){
//                        console.log(data);
//            });
//            response.on('data', console.log);
    
            response.on('data', function(data){
               resData = resData + data; 
            });
    
            response.on('error', console.error);
            response.on('end', function() {
//                console.log(' ================= done ====================');
                console.log(resData.length);
                console.log(resData);
            });
    
});*/