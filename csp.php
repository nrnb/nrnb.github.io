<html>
<head>
<style type="text/css">
        body
        {
            font-family: Arial, Sans-Serif;
            font-size: 13px;
        }
	#inputArea
	{
	    font-family: Arial, Sans-Serif;
	    font-size: 13px;
	    background-color: #d6e5f4;
	    padding: 20px;
	    width:520px;
	}
	#inputArea input, #inputArea textarea
	{
	    font-family: Arial, Sans-Serif;
	    font-size: 13px;
	    margin-bottom: 5px;
	    display: block;
	    padding: 4px;
	    width: 500px;
	}
	#inputArea input.radio, #inputArea input.checkbox 
	{ 
	    display:inline-block; 
	    width: 20px;
	    margin: 5px 0 0 5px;
	}

	#inputArea label
	{
	    font-weight: bold;
	}	
        .activeField
        {
            background-image: none;
            background-color: #ffffff;
            border: solid 1px #33677F;
        }
        .idle
        {
	    border: solid 1px #85b1de;
	    background-repeat: repeat-x;
	    background-position: top;
        }
	.ro
	{
            border: solid 1px #fff;
            background-repeat: repeat-x;
            background-position: top;
        }
    </style>
</head>

<body>
<div id="inputArea" style="float:left;background-color:#fff;">

<?php
     
include_once("csp_helper.php");

$u = "drpico@gmail.com";
$p = "pi314159265";
 
$ss = new Google_Spreadsheet($u,$p);
$ss->useSpreadsheet("Collaboration Tracking");
$ss->useWorksheet("Sheet1");

//GET sub id
if (empty($_GET)) {
	echo "This page requires a Subproject ID in order to display data. Please try again. </div></body></html>";
}
else {
$subid = $_GET["id"]; 


$msg = "Please update the following information:";

if ('POST' == $_SERVER['REQUEST_METHOD']){

        $update2 = check_input($_POST['update']);
        $feedback2 = check_input($_POST['feedback']);
        $pubmed2 = check_input($_POST['pubmed']);
        $grant2 = check_input($_POST['grant']);
        $status2 = check_input($_POST['status']);
	$st1 = check_input($_POST['stage1']);
        $st2 = check_input($_POST['stage2']);
        $st3 = check_input($_POST['stage3']);
        $st4 = check_input($_POST['stage4']);

	$stage2 = implode(",", array($st1,$st2,$st3,$st4));

	$uptime2 = date('n/j/Y G:i:s');
	$uprow = array
	(
          	'projectupdate' => $update2,
        	'collaborationfeedback' => $feedback2,
        	'pubmedids' => $pubmed2,
        	'grantids' => $grant2,
        	'projectstatus' => $status2,
		'updatetimestamp' => $uptime2,
		'projectstage' => $stage2
	);

	//UPDATE GOOGLE DOC
	if ($ss->updateRow($uprow, "subprojectstatus=$subid"))
		$msg = "<em><font color=\"green\">Data successfully updated!</font></em>";
	else 
		$msg = "<em><font color=\"red\">Error, unable to update spreadsheet data.</font></em> <br />
			Please try again or contact Alex at 
			<a href=\"mailto:apico@gladstone.ucsf.edu?Subject=Error updating subproject $subid!\">apico@gladstone.ucsf.edu</a>";
}

$rows = $ss->getRows("subprojectstatus=$subid");
if ($rows){

$names = $rows[0]['fullnamesanddegrees'];
$inst = $rows[0]['departmentandinstitution'];
$email = $rows[0]['email'];
$url = $rows[0]['projecturl'];
$nrnb = $rows[0]['nrnbcontact'];
$nrnbemail = str_replace(' ', '', $rows[0]['nrnbemail']);
$desc = $rows[0]['projectdescription'];
$title = $rows[0]['projecttitle'];
$type = $rows[0]['requesttype'];
$oritime = $rows[0]['timestamp'];
//UPDATE FIELDS
$update = $rows[0]['projectupdate'];
$uptime = $rows[0]['updatetimestamp'];
$feedback = $rows[0]['collaborationfeedback'];
$pubmed = $rows[0]['pubmedids'];
$grant = $rows[0]['grantids'];
$status = $rows[0]['projectstatus'];

$selfurl = $_SERVER['PHP_SELF'] . "?id=" . $subid;

echo "<h1>Your NRNB Collaboration</h1>";

echo "<label>Subproject ID</label>
<input name=\"subid\" type=\"text\" class=\"ro\" value=\"$subid\" readonly>";
 
echo "<label>Name</label>
<input name=\"names\" type=\"text\" class=\"ro\" value=\"$names\" readonly>";

echo "<label>Affiliation</label>
<input name=\"inst\" type=\"text\" class=\"ro\" value=\"$inst\" readonly>";

echo "<label>Project title</label>
<input name=\"title\" type=\"text\" class=\"ro\" value=\"$title\" readonly>";

echo "<label>Initial project description</label>
<textarea name=\"desc\" rows=\"8\" class=\"ro\" readonly>$desc</textarea>";

echo "<label>Start date</label>                                                                                                                    
<input name=\"oritime\" type=\"text\" class=\"ro\" value=\"$oritime\" readonly>";

echo "<label>Collaboration type</label>                                                                                                                    
<input name=\"type\" type=\"text\" class=\"ro\" value=\"$type\" readonly>";

if (isValidEmail($nrnbemail))
	echo "<label>NRNB contact <a href=\"mailto:$nrnbemail?Subject=Regarding Our NRNB Collaboration\">[send email]</a></label>";
else
	echo "<label>NRNB contact</label>";

echo "<input name=\"nrnb\" type=\"text\" class=\"ro\" value=\"$nrnb\" readonly>"; 



//UPDATE FIELDS
echo "</div><div id=\"inputArea\" style=\"float:left;margin-top:40px\">";
echo "<form method=\"post\" action=\"$selfurl\">"; 
echo "<h2>$msg</h2>";

echo "<label>Latest project update  <em>$uptime</em></label>
<textarea name=\"update\" rows=\"8\" class=\"idle\">$update</textarea>";                                                                             
echo "<label>Related publications (Pubmed IDs)</label>                                                                               
                                   <input name=\"pubmed\" type=\"text\" class=\"idle\" value=\"$pubmed\">";



echo "<label>Related grant numbers</label>                                                                                           
<input name=\"grant\" type=\"text\" class=\"idle\" value=\"$grant\">"; 

echo "<label>Feedback on collaboration with NRNB</label>                                                                                                                <textarea name=\"feedback\" rows=\"4\" class=\"idle\" >$feedback</textarea>";                                                                             
//initialize
$check1 =  $check2 =  $check3 = $check4 = "";
switch ($status) {
	case "Ongoing":
		$check1 = "checked";
		break;
        case "Completed":
                $check2 = "checked";
                break;
        case "Canceled":
                $check3 = "checked";
                break;
        case "Unknown":
                $check4 = "checked";
                break;
}

echo "<label>Project status</label><br /> 
<input class=\"radio\" type=\"radio\" name=\"status\" value=\"Ongoing\" $check1>Ongoing
<input class=\"radio\" name=\"status\" type=\"radio\" value=\"Completed\" $check2>Completed
<input class=\"radio\" name=\"status\" type=\"radio\" value=\"Canceled\" $check3>Canceled
<input class=\"radio\" name=\"status\" type=\"radio\" value=\"Unknown\" $check4>Unknown
";

echo "<br /><br /><label>Project stages</label><br />
Which stages has this project cleared <em>during the collaboration</em>. Check all that apply. <br />
But note that not all projects start at Stage 1 and aim for Stage 4. <br />
<input type=\"checkbox\" class=\"checkbox\" name=\"stage1\" value=\"1\">Stage 1 - Identified problem, biological question and target audience<br />
<input type=\"checkbox\" class=\"checkbox\" name=\"stage2\" value=\"2\">Stage 2 - Developed new method or approach; established proof of principle<br />
<input type=\"checkbox\" class=\"checkbox\" name=\"stage3\" value=\"3\">Stage 3 - Implemented solution as a software tool or techonology<br />
<input type=\"checkbox\" class=\"checkbox\" name=\"stage4\" value=\"4\">Stage 4 - Achieved broad adoption of method via your tool or technology<br />
";

//complete form and divs within php
echo "<input type=\"submit\" name=\"submit\" value=\"Save\" style=\"font-size:20px;width:100px;height:30px;padding:0px;margin-top:20px;\">
</form>
</div>";

} //end if($rows)

else echo "No data found for Subproject ID: $subid";

//if ($rows) print_r($rows);
//else echo "Error, unable to get spreadsheet data";

} //end else $_GET not empty
 
function check_input($data)
{
    	$data = trim($data);
    	$data = stripslashes($data);
    	$data = htmlspecialchars($data);

	return $data;
}

function isValidEmail($e)
{
        $emails = explode(',', $e);
        foreach ($emails as $each){
                $check = preg_match("^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,3})$^", $each);
                if (!$check)
                        return FALSE;
        }
        return TRUE;
}
 
?>


</div>
</body></html>
