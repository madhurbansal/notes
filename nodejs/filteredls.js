/**
    1. Modularized code
    2. Create a module to return a list of files in the given directory filtered by given extension
    3. Use the above module to print the list of files by passing on the parameters received from command line for directory name and file extension to filter upon
*/

var mymodule = require('./mymodule');
mymodule(process.argv[2],
         process.argv[3], 
        function(err, filesAr){
            if (err) {
                console.log("An error occurred ");
            }
            for(var i = 0; i<filesAr.length; i++) {
                console.log(filesAr[i]);
            }    
        }
    );