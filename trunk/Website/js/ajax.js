// holds an instance of XMLHttpRequest
var xmlHttp = createXmlHttpRequestObject();
var showErrors = true;

// creates an XMLHttpRequest instance
function createXmlHttpRequestObject() {
  // will store the reference to the XMLHttpRequest object
  var xmlHttp;
  // this should work for all browsers except IE6 and older
  try {
    // try to create XMLHttpRequest object
    xmlHttp = new XMLHttpRequest();
  } catch(e) {
    // assume IE6 or older
    var XmlHttpVersions = new Array("MSXML2.XMLHTTP.6.0",
                                    "MSXML2.XMLHTTP.5.0",
                                    "MSXML2.XMLHTTP.4.0",
                                    "MSXML2.XMLHTTP.3.0",
                                    "MSXML2.XMLHTTP",
                                    "Microsoft.XMLHTTP");
    // try every prog id until one works
    for (var i=0; i<XmlHttpVersions.length && !xmlHttp; i++) {
      try { 
        // try to create XMLHttpRequest object
        xmlHttp = new ActiveXObject(XmlHttpVersions[i]);
      } catch (e) {}
    }
  }
  // return the created object or display an error message
  if (!xmlHttp)
    alert("Error creating the XMLHttpRequest object.");
  else 
    return xmlHttp;
}

// function called when the state of the HTTP request changes
function handleRequestStateChangeUN() {
  // when readyState is 4, we are ready to read the server response
  if (xmlHttp.readyState == 4)  {
    // continue only if HTTP status is "OK"
    if (xmlHttp.status == 200) {
      try {
        // do something with the response from the server
        handleServerResponseUN();
      } catch(e) {
        // display error message
        if (showErrors) alert("Error reading the response: " + e.toString());
      }
    } else {
      // display status message
      if (showErrors) alert("There was a problem retrieving the data:\n" + 
            xmlHttp.statusText);
    }
  }
}

// handles the response received from the server
function handleServerResponseUN(){
  // retrieve the server's response packaged as an XML DOM object
  var xmlResponse = xmlHttp.responseXML;
  // catching potential errors with IE and Opera
  if (!xmlResponse || !xmlResponse.documentElement)
    throw("Invalid XML structure:\n" + xmlHttp.responseText);
 
  // catching potential errors with Firefox
  var rootNodeName = xmlResponse.documentElement.nodeName;
  if (rootNodeName == "parsererror") 
    throw("Invalid XML structure:\n" + xmlHttp.responseText);
  // getting the root element (the document element)
  xmlRoot = xmlResponse.documentElement;
  // testing that we received the XML document we expect
  if (rootNodeName != "response" || !xmlRoot.firstChild)
    throw("Invalid XML structure:\n" + xmlHttp.responseText);
  // the value we need to display is the child of the root <response> element
	validResponse = xmlRoot.getElementsByTagName("valid");
	userExists = xmlRoot.getElementsByTagName("userexists");
	exists = userExists.item(0).firstChild.data;

	success = validResponse.item(0).firstChild.data;
	if (success == "true"){
		if (exists == "true") {
			document.getElementById("usernameError").innerHTML = "This username already exists";
			document.getElementById("usernameValid").innerHTML = "";
		} else {
			document.getElementById("usernameValid").innerHTML = "This username is available!";
			document.getElementById("usernameError").innerHTML = "";
		}
	}
}

function checkUsernameExists(){
	username = document.regForm.username.value;
	if (xmlHttp && username) {
	    // try to connect to the server
		try {
		  var rand = randomString(16);
		  xmlHttp.open("GET", "ajax.php?action=checkUsername&username=" + username + "&rand=" + rand, true);
		  xmlHttp.onreadystatechange = handleRequestStateChangeUN;
		  xmlHttp.send(null);
		} catch (e) {
			// Display error?
		}
	}
}

function handleRequestStateChangeEM() {
  // when readyState is 4, we are ready to read the server response
  if (xmlHttp.readyState == 4)  {
    // continue only if HTTP status is "OK"
    if (xmlHttp.status == 200) {
      try {
        // do something with the response from the server
        handleServerResponseEM();
      } catch(e) {
        // display error message
        if (showErrors) alert("Error reading the response: " + e.toString());
      }
    } else {
      // display status message
      if (showErrors) alert("There was a problem retrieving the data:\n" + 
            xmlHttp.statusText);
    }
  }
}

// handles the response received from the server
function handleServerResponseEM(){
  // retrieve the server's response packaged as an XML DOM object
  var xmlResponse = xmlHttp.responseXML;
  // catching potential errors with IE and Opera
  if (!xmlResponse || !xmlResponse.documentElement)
    throw("Invalid XML structure:\n" + xmlHttp.responseText);
 
  // catching potential errors with Firefox
  var rootNodeName = xmlResponse.documentElement.nodeName;
  if (rootNodeName == "parsererror") 
    throw("Invalid XML structure:\n" + xmlHttp.responseText);
  // getting the root element (the document element)
  xmlRoot = xmlResponse.documentElement;
  // testing that we received the XML document we expect
  if (rootNodeName != "response" || !xmlRoot.firstChild)
    throw("Invalid XML structure:\n" + xmlHttp.responseText);
  // the value we need to display is the child of the root <response> element
	validResponse = xmlRoot.getElementsByTagName("valid");
	emailExists = xmlRoot.getElementsByTagName("emailexists");
	exists = emailExists.item(0).firstChild.data;

	success = validResponse.item(0).firstChild.data;
	if (success == "true"){
		if (exists == "true") {
			document.getElementById("emailError").innerHTML = "This email address has already been registered.";
			document.getElementById("emailValid").innerHTML = "";
		} else {
			document.getElementById("emailValid").innerHTML = "You have entered a valid email.";
			document.getElementById("emailError").innerHTML = "";
		}
	}
}

function checkEmailExists(){
	email = document.regForm.email1.value;
	if (xmlHttp && email) {
	    // try to connect to the server
		try {
		  var rand = randomString(16);
		  xmlHttp.open("GET", "ajax.php?action=checkEmail&email=" + email + "&rand=" + rand, true);
		  xmlHttp.onreadystatechange = handleRequestStateChangeEM;
		  xmlHttp.send(null);
		} catch (e) {
			// Display error?
		}
	}
}

function randomString(string_length) {
	var chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz";
	var randomstring = '';
	for (var i=0; i<string_length; i++) {
		var rnum = Math.floor(Math.random() * chars.length);
		randomstring += chars.substring(rnum,rnum+1);
	}
	return randomstring;
}