/*
    1. Create an async operation to calculate the number of lines present in a file
    2. The name of the file to be read is a command line parameter
*/
var fs = require('fs');
fs.readFile(process.argv[2], "utf-8",
            function(err, contents){
                      if (err) throw err;
                      console.log(contents.split('\n').length-1);
                      });