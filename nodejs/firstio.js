/*
    1. Use an sync (NOT async) operation to calculate the number of lines present in a file
    2. The name of the file to be read is a command line parameter
*/

var fs = require('fs');
var buf = fs.readFileSync(process.argv[2]); // this file read is in sync, as not async
var contents = buf.toString();
//console.log(contents);
console.log(contents.split('\n').length - 1);