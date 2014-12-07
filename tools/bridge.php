<html>
  <head>
    <meta charset="utf-8" />
    <title>MAPPBuilder Bridge</title>
  </head>

  <body>
    <h1>MAPPBuilder Bridge</h1>
<script type="text/javascript">
function submitform()
{
    document.getElementById("results").innerHTML = "...";	
    document.forms["form"].submit();
}
</script>
    <form id="form" action="bridge.php"  target="return" method="POST">

<?php
//http://webservice.bridgedb.org/Yeast/attributeSearch/replicative
//http://webservice.bridgedb.org/Yeast/xrefs/T/GO:0001302?dataSource=D
//http://webservice.bridgedb.org/Yeast/attributes/D/S000006339?attrName=Symbol

//Defaults
$org = "Yeast";
$ds1 = "T|GeneOntology"; //note: use pipe to separate code from name
$ds2 = "D|SGD"; //note: use pipe to separate code from name
$id = "GO:0032807";

if(!empty($_POST["org"]))
	$org = $_POST["org"];
if(!empty($_POST["ds1"]))
	$ds1 = $_POST["ds1"];
if(!empty($_POST["ds2"]))
	$ds2 = $_POST["ds2"];
if(!empty($_POST["id"]))
	$id = $_POST["id"];

$ds1Arr = explode("|", $ds1);
$ds1a = $ds1Arr[0];
$ds1b = $ds1Arr[1];

$ds2Arr = explode("|", $ds2);
$ds2a = $ds2Arr[0];
$ds2b = $ds2Arr[1];

//Initialize Form Fields
print("
Species:&nbsp;&nbsp;
<select name=\"org\">
  <option selected>$org</option>
  <option>Human</option>
  <option>Mouse</option>
  <option>Yeast</option>
</select>
<br/>
From:&nbsp;&nbsp;
<select name=\"ds1\">
  <option value=$ds1 selected>$ds1b</option>
  <option value=\"T|GeneOntology\">GeneOntology</option>
  <option value=\"D|SGD\">SGD</option>
  <option value=\"En|Ensembl\">Ensembl</option>
  <option value=\"L|EntrezGene\">EntrezGene</option>
</select>
&nbsp;&nbsp;
<input type=\"text\" name=\"id\" size=\"22\" value=$id>
<br/>
To:&nbsp;&nbsp;
<select name=\"ds2\">
  <option value=$ds2 selected>$ds2b</option>
  <option value=\"T|GeneOntology\">GeneOntology</option>
  <option value=\"D|SGD\">SGD</option>
  <option value=\"En|Ensembl\">Ensembl</option>
  <option value=\"L|EntrezGene\">EntrezGene</option>
</select>
<br/><br/>
<button style=\"font: 14px Arial; background: lightblue\"  onclick=\"submitform()\">Submit</button>
</form>
<hr>
<div id=\"results\">
");

$url = "http://webservice.bridgedb.org/$org/xrefs/$ds1a/$id?dataSource=$ds2a";
//print $url;
$getresult = file_get_contents($url);

$array = explode("\n", $getresult);
//print_r($array);
$i = 0;
foreach ($array as $key => $val){
$valArr = explode("\t", $val);
$valArr0 = $valArr[0];
$url2 = "http://webservice.bridgedb.org/$org/attributes/$ds2a/$valArr0?attrName=Symbol";
//print $url2;
$getresult2 = file_get_contents($url2);
$array2 = explode("\n", $getresult2);
//print_r($array2);
print "<pre>";
	$j = 0;
	foreach ($array2 as $key2 => $val2){
		$val2Arr = explode("\t", $val2);
		print("$val2Arr[0]\t$valArr[0]\t$valArr[1]");
		if (++$j == sizeof($array2)-1) break;
	}
	if (++$i == sizeof($array)-1) break;
print "</pre>";
}	

?>

</div>
  </body>

</html>
