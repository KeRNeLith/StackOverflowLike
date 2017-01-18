/*
Ref. starting point
http://stackoverflow.com/questions/7195844/writing-text-on-div-using-javascript

Naming conventions to make programming easy :)
TIP: Use CamelCases for names after prefix
<form>  id's prefix frm____  
<text><input> prefix txt___ 
<radio>  rad___
<checkbox> chk__
*/

function writeComment(e) {
    // check to see if they didn't fill in anything
    if ($.trim(document.frmComment.txtComment.value).length == 0) {
        alert('Please enter  some text.');
        document.frmComment.txtComment.value = ''; // clear any spaces 
        $('#txtComment').focus();        
        return; // exit function
    }


    // required to hide first, in order to show later
    $("#comment").hide();
    $("#txtComment").hide();
    $("#txtCommentHere").hide();

    var s = document.frmComment.txtComment.value;

    $("#txtComment").fadeIn(1000);

    // write out to to textarea, with delay of 1,000ms (1 second)
    document.getElementById('txtCommentHere').innerHTML = s;
    $("#txtCommentHere").fadeIn(1000);

    // write out to to <div id="comment"> , with delay of 2,000ms (2 second)
    document.getElementById('comment').innerHTML = s;
    $("#comment").fadeIn(2000);


    return false;
}

function writeLorem(e) {
    var s; // string

    if ($('#chkLorem').is(':checked')) {

        // required to hide first, in order to show later
        $("#comment").hide();
        $("#txtCommentHere").hide();

        s = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum in posuere nisi. Cras mi eros, blandit eget eros in, blandit vulputate sem. Morbi in tincidunt magna, sed tincidunt turpis. Integer. ';

        document.frmComment.txtComment.value = s;

        // write out to to textarea, with delay of 1,000ms (1 second)
        document.getElementById('txtCommentHere').innerHTML = s;
        $("#txtCommentHere").fadeIn(1000);

        // write out to to <div id="comment"> , with delay of 2,000ms (2 second)
        document.getElementById('comment').innerHTML = s;
        $("#comment").fadeIn(2000);


    } else {

        document.frmComment.txtComment.value = ' ';

        $('#txtComment').focus();


    } // end if

} // end function