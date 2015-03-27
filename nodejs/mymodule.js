/**
    1. Modularized code
    2. Create a module to return a list of files in the given directory filtered by given extension
*/
var fs = require('fs');
var path = require('path');

module.exports = function(dirname, extension, callback) {
    var filteredFiles = [];

    extension = '.' + extension;
    
    fs.readdir(dirname,
              function(err, files){
                if (err) {
                    console.log('An error occurred while reading directory');
                    return callback(err, null);
                }
                    
                for (var i = 0; i < files.length; i++) {
                    if (path.extname(files[i]) === extension) {
                        filteredFiles.push(files[i]);
                    }
                }
                callback(null, filteredFiles);
            });

}
