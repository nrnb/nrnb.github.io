<?php

include_once("csp_helper.php");

$u = "drpico@gmail.com";
$p = "pi314159265";

$ss = new Google_Spreadsheet($u,$p);
$ss->useSpreadsheet("Collaboration Tracking");
$ss->useWorksheet("Sheet1");

$ss2 = new Google_Spreadsheet($u,$p);
$ss2->useSpreadsheet("NRNB Subproject Stages");
$ss2->useWorksheet("Sheet1");

$rowset = 'projectstage!=""'; # only rows with stage info

$rows = $ss->getRows($rowset);

if ($rows){
    foreach ($rows as $row){

	$stage = $row['projectstage'];
	$stage2 = array();

	## skip if no digits
	$stage = str_replace(",","",$stage);
	$len = strlen($stage);
	if ($len < 1)
		continue;
        ## else extract digits and prepare cell writes
	elseif ($len == 1){
		array_push($stage2, $stage . "1");

	} elseif ($len > 1) {
		array_push($stage2, substr($stage,0,1) . "2");
		for ($i=1; $i<$len-1; $i++) {
			array_push($stage2, substr($stage,$i,1) . "1");
			array_push($stage2, substr($stage,$i,1) . "2");
		}	
		array_push($stage2, substr($stage,$len-1,1) . "1");
	}
		
	#print_r($stage2);	

	## get or add row to Stages and write cells
	$subid = "CSP-".$row['subprojectstatus'];
	$nrnb = $row['nrnbcontact'];
	$title = $row['projecttitle'];

	$uprow = array
        (
                'projecttitle' => $title,
                'nrnbcontact' => $nrnb,                      
		'subprojectstatus' => $subid
        );      

        foreach ($stage2 as $st){
		$col = "st".$st;
		$uprow[$col] = $st;
        }                                                            
                                                                            

        ## UPDATE GOOGLE DOC                                                 

	$rowcheck = $ss2->getRows("subprojectstatus=\"$subid\"");
	if ($rowcheck) {
        	if ($ss2->updateRow($uprow, "subprojectstatus=\"$subid\""))              
                	echo "Data successfully updated for subproject ID: $subid\n";
       	 	else                                                                
         	       echo "Error, unable to update data for subproject ID: $subid\n";
	} else {
		if ($ss2->addRow($uprow))
			echo "Data successfully added for subproject ID: $subid\n";
		else
			echo "Error, unable to add data for subproject ID: $subid\n";
	}
    }

}
