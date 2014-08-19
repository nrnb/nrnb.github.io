<style type="text/css">
        body
        {
            font-family: Arial, Sans-Serif;
            font-size: 13px;
        }
tr:nth-child(even) {
  background-color: #AACCFF;
}


tr:nth-child(odd) {
  background-color: white;
}
    </style>
</head>

<body>
<table border="0px">

<?php
     
include_once("csp_helper.php");

$u = "drpico@gmail.com";
$p = "pi314159265";
 
$ss = new Google_Spreadsheet($u,$p);
$ss->useSpreadsheet("Collaboration Tracking");
$ss->useWorksheet("Sheet1");

//headers
echo "<tr>";
$head = $ss->getColumnNames();
foreach ($head as $colname) {
	echo "<th>$colname</th>";
}
echo "</tr>";

//GET admin set
$view = $_GET["view"]; 

$rowset = ""; //means "show all;
if ($view == "Ongoing" || $view == "Completed" ||$view == "Canceled" ||$view == "Unknown" )
	$rowset = "projectstatus=$view";

//retrieve data
$rows = $ss->getRows($rowset);

if ($rows){

foreach ($rows as $row){
	echo "<tr>";
	$status = $row['projectstatus'];
	$title = $row['projecttitle'];
	$nrnbcontact = $row['nrnbcontact'];
	$email = str_replace(' ', '', $row['email']);
	$nrnbemail = str_replace(' ', '', $row['nrnbemail']);
	$subject = "Update Requested Regarding Your NRNB Collaboration: $title";
	$subid = $row['subprojectstatus'];
	$body = "This automatic email is sent to you based on the information submitted to http://nrnb.org regarding your collaboration with $nrnbcontact on the project: $title.%0D%0D

One of the primary roles of NRNB is to engage in a wide variety of collaboration and service projects around Cytoscape and other network biology tools. In our first few years as an organization, we have established close to 100 such collaborations, spanning 14 countries and 11 states -- including one with you! In preparation for our annual report and future funding renewal applications, we need YOUR help in justifying our existence.%0D%0D 

Please take 5 minutes to update the status of your collaboration. We have prepared a custom form to collect a short paragraph and any new publication or grant numbers related to our work together. And if you have any general comments about impact of our collaboration on your research/career goals, please include those as well. %0D%0D

Project Title: $title%0D
Collaborator: $nrnbcontact (cc'd)%0D%0D

Link to custom update form:%0D 
http://nrnb.org/csp.php?id=$subid%0D%0D

Collaborations are at the core of NRNB, providing a critical feedback loop in the development and dissemination of network biology tools. They clearly enrich our work and we hope they benefit you directly as well.%0D%0D

Thanks!%0D
 - Alex%0D%0D

-----------------------------------%0D
Alexander Pico, PhD%0D
NRNB executive director%0D
apico@gladstone.ucsf.edu%0D
415-734-2741%0D
-----------------------------------
";

	$pubs = $row['pubmedids'];
	$ifpubs = "";
	if (strlen($pubs) > 7){
	  $splitpubs = split(',', $pubs);
	  $publinks = "";
	  foreach($splitpubs as $p){
	  	$publinks .= "http://www.ncbi.nlm.nih.gov/pubmed/$p ";
	  }
	  $ifpubs = "Publication(s): $publinks";
	}
	$subject2 = "NRNB Collaborator, Your Feedback Is Requested (<2 minutes)";
	$body2 = "Dear Collaborator,%0D%0D

Four years ago we launched the National Resource for Network Biology (http://nrnb.org), a NIH P41 resource comprised of multiple groups working on developing network biology technologies and offering training and a broad set of collaborations.  In that time we've:%0D%0D

 * published 80 papers%0D
 * held 119 training events and 26 courses across 14 countries%0D
 * worked with 59 Google Summer of Code students%0D
 * established 149 collaborations%0D
 * and enhanced the Cytoscape developer and user communities with projects like the Cytoscape App Store%0D%0D

Now we are up for renewal and we need your help.  We want your feedback on your experience collaborating with an NRNB group. We've prepare a super simple, single question survey. It'll take less than 2 minutes!%0D%0D

http://nrnb.org/outreach.html#feedback%0D%0D

NRNB collaborator: $nrnbcontact%0D
Project title: $title%0D 
$ifpubs%0D%0D

Did we help develop a new method or tool? Did we help apply a network tool toward a useful end? Did we contribute significantly to a publication? Did we further the goals of your research?%0D%0D 

Thanks for you time!%0D
 - Alex%0D%0D

----------------------------------------%0D
Alexander Pico, PhD%0D
NRNB Executive Director%0D
Bioinformatics Assoc. Director%0D
Gladstone Institutes%0D
http://nrnb.org%0D
http://gladstoneinstitutes.org%0D
----------------------------------------
";

        foreach ($row as $field){
                if (strlen($field) > 200)
                        $field = substr($field, 0, 200) ."...";
                echo "<td>$field</td>";
        }
        if (isValidEmail($email) && isValidEmail($nrnbemail)){
          if ($subid > 0 && $status == "Ongoing")
                echo "<td><a href=\"mailto:$email?cc=$nrnbemail&Subject=$subject&Body=$body\">[send update request]</a></td>";
          else                                                                                                                                            
                echo "<td><a href=\"mailto:$email?cc=$nrnbemail&Subject=$subject\">[send email]</a></td>";                                              
	  echo "<td><a href=\"mailto:$email?Subject=$subject2&Body=$body2\">[send testimonial request]</a></td>"; 
	}
        else                                                                                                                                            
                echo "<td>email missing</td><td>email missing</td>";                                                                                                          
                                                                                                                                                        
        echo "</tr>";    

}
		

} //end if($rows)

else echo "No data found for row search: $rowset";

 function isValidEmail($e){
	$emails = explode(',', $e);
	foreach ($emails as $each){
		$check = preg_match("^[_a-z0-9-]+(\.[_a-z0-9-]+)*@[a-z0-9-]+(\.[a-z0-9-]+)*(\.[a-z]{2,3})$^", $each);
		if (!$check)
			return FALSE;
	}
	return TRUE;
}
 
?>


</table>
</body></html>
