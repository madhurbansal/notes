/**
    1. print the sum of a list of numbers given as command line parameters
    2. list can be of arbitrary length
    3. Ignore strings which cannot be converted to number while doing the sum
*/

//console.log(process.argv);
var length = process.argv.length;
var sum = 0;
var num = 0;
for (var i = 2; i < length; i++) {
    num = 1 * process.argv[i]; // trick to convert a string to number, multiply by a numeral 1
    if (!isNaN(num)) {
        sum += num;        
    }
    // result += (!isNaN(Number(process.argv[i])) // another answer !!
//    console.log((1*process.argv[i]));
}
console.log(sum);