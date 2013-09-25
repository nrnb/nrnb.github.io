<html>
<style>
label textarea{
 vertical-align: top;
}
</style>
<body>

<form id="pubpost" name="pubpost" method="post" action="">
<h2>NRNB Publication Posting Tool</h2>

<label>Post to
<select name="blog">
<option value="picopubtest">picopubtest</option>
<option value="cytoscape-publications">cytoscape-publications</option>
<option value="wikipathways">wikipathways</option>
<option value="netbiopub">netbiopub</option>
</select>
</label>
<br />
<label>Pubmed ID
<input type="text" name="pmid" id="pmid" size="12" value="18651794" />
</label>
<br />
<label>Image URL
<input type="text" name="image" id="image" size="150" value="http://www.ncbi.nlm.nih.gov/corecgi/tileshop/tileshop.fcgi?p=PMC3&id=240897&s=15&r=1&c=2" />
</label>
<br />
<label>Extra message
<textarea name="extra" id="extra" cols="100" rows="5"><b>Cytoscape cited!</b></textarea>
</label>
<br />
<label>Tags (comma-separated)
<input type="text" name="tags" id="tags" size="50" value="cytoscape-app,tool,resource,applied" />
</label>
<br />
<label>Publish in 
<input type="text" name="date" id="date" size="2" value="2" />
 days (0 = publish now)
</label>
<br />
<br />
<input type="submit" name="submit" id="submit" value="Submit" />
</form>

<?php
include('pubmedapi.php');

if (isset($_POST["submit"])){

$blogname = $_POST["blog"];
$pmid = $_POST["pmid"];
$pubimage = $_POST["image"];
$extra = $_POST["extra"];
$date = $_POST["date"];
$tags = $_POST["tags"];

//Process date 
$pubdate = gmdate('Y-m-d H:i:s', strtotime("+".$date." day")) . " GMT";

//Perform PUBMED lookup
$pma = new PubMedAPI();
$pma_results = $pma->query_pmid($pmid);

$title = $pma_results[0]["title"];
$authors = implode(", ", $pma_results[0]["authors"]);
$year = $pma_results[0]["year"];
$journal = $pma_results[0]["journalabbrev"];
$issue = $pma_results[0]["issue"];
$vol = $pma_results[0]["volume"];

// check for returned data or msg user to try again
$publink = "http://www.ncbi.nlm.nih.gov/pubmed/$pmid";
$pubcaption = "<p>$title<br /><a href=\"$publink\">$authors ($year) $journal $issue($vol).</a></p>$extra";

// Start a session, load the library
session_start();
require_once('tumblroauth.php');

// Define the needed keys
$consumer_key = "AvTd3aL9BsMwVqz0XTmUSy8QsZBrMwwXhDH0P9BgsNNKxzdx6U";
$consumer_secret = "gcdDozJpI2nF5nluEcozTZpWMvc0u75JQWjuebD8DMYDLt6oFt";

// Once the user approves your app at Tumblr, they are sent back to this script.
// This script is passed two parameters in the URL, oauth_token (our Request Token)
// and oauth_verifier (Key that we need to get Access Token).
// We'll also need out Request Token Secret, which we stored in a session.

// Create instance of TumblrOAuth.
// It'll need our Consumer Key and Secret as well as our Request Token and Secret
$tum_oauth = new TumblrOAuth($consumer_key, $consumer_secret, $_SESSION['request_token'], $_SESSION['request_token_secret']);

// Ok, let's get an Access Token. We'll need to pass along our oauth_verifier which was given to us in the URL. 
$access_token = $tum_oauth->getAccessToken($_REQUEST['oauth_verifier']);

// We're done with the Request Token and Secret so let's remove those.
unset($_SESSION['request_token']);
unset($_SESSION['request_token_secret']);

// Make sure nothing went wrong.
if (200 == $tum_oauth->http_code) {
  // good to go
} else {
  die("Unable to authenticate. <a href=\"connect.php\">Try again?</a>");
}

// What's next?  Now that we have an Access Token and Secret, we can make an API call.

// Any API call that requires OAuth authentiation will need the info we have now - (Consumer Key,
// Consumer Secret, Access Token, and Access Token secret).

// You should store the Access Token and Secret in a database, or if you must, a Cookie in the user's browser.
// Never expose your Consumer Secret.  It should stay on your server, avoid storing it in code viewable to the user.

// I'll make the /user/info API call to get some baisc information about the user

// Start a new instance of TumblrOAuth, overwriting the old one.
// This time it will need our Access Token and Secret instead of our Request Token and Secret
$tum_oauth = new TumblrOAuth($consumer_key, $consumer_secret, $access_token['oauth_token'], $access_token['oauth_token_secret']);

// Make an API call with the TumblrOAuth instance.  There's also a post and delete method too.
//$userinfo = $tum_oauth->get('http://api.tumblr.com/v2/user/info');

// You don't actuall have to pass a full URL,  TukmblrOAuth will complete the URL for you.
// This will also work: $userinfo = $tum_oauth->get('user/info');

// POST
$url = "http://api.tumblr.com/v2/blog/$blogname.tumblr.com/post";
$params = array(
	"type" => "photo",
	"state" => "queue",
	"publish_on" => $pubdate,
	"source" => file_get_contents($pubimage),
	"data" => array(file_get_contents($pubimage)),	
	"link" => $publink,
	"tags" => $tags,
	"caption" => $pubcaption
	);   

$post = $tum_oauth->post($url, $params);

// Check for an error.
if (201 == $tum_oauth->http_code) {
  $postid = (float) $post->response->id;
  echo "<br /><hr>$pubcaption<hr><br />
	<br />Sucessfully posted to <b>$blogname</b> queue! <a href=\"http://tumblr.com/blog/$blogname/queue\" target=\"_blank\">View queue</a> or <a href=\"connect.php\">Submit another post</a>";
} else {
  die("Unable to post to tumblr. <a href=\"connect.php\">Try reconnecting...</a>");
}


// find primary blog.  Display its name.
//$screen_name = $userinfo->response->user->name;
//for ($fln=0; $fln<count($userinfo->response->user->blogs); $fln=$fln+1) {
//        if ($userinfo->response->user->blogs[$fln]->primary==true) {
//                echo("Your primary blog's name: " .($userinfo->response->user->blogs[$fln]->title));
//                break;
//        }
//}
//echo("<br/>");
//echo("Your user name (the part before tumblr.com of your primary blog): ".$userinfo->response->user->name);

// And that's that.  Hopefully it will help.

} #end if SUBMIT
?>

