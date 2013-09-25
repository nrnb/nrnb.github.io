<html>
<body>
<h2>NRNB Collaborations</h2>

<table >
<tr><td><td><b>Project Title<td><b>Investigator<td><b>NRNB Contact</b></tr>
<?php

$url = "https://spreadsheets.google.com/feeds/list/t4GxaAbYL-dqlxi3iTy-b7w/od6/public/values/";

$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, $url);
curl_setopt($ch, CURLOPT_HEADER, 0);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
$xml = curl_exec($ch);
curl_close($ch);

//$doc = new DOMDocument();
//$doc->loadXML($xml);

//$entries = $doc->getElementsByTagName("gsx:projecttitle");

//if ($entries->length > 0) {
//	foreach($entries as $e) {
//		echo $e;
//	}
//}

$items = element_set('entry', $xml);
$c = 1;
foreach($items as $i){
	$content = value_in('gsx:projecttitle', $i);
	$name = value_in('gsx:fullnamesanddegreese.g.johne.smithmdphd', $i);
	$contact = value_in('gsx:nrnbcontact', $i);
	echo "<tr><td>".$c++."<td>$content<td>$name<td>$contact</tr>";
}

$title = value_in('title', $xml);
$link = value_in('link', $xml);


function value_in($element_name, $xml, $content_only = true) {
    if ($xml == false) {
        return false;
    }
    $found = preg_match('#<'.$element_name.'(?:\s+[^>]+)?>(.*?)'.
            '</'.$element_name.'>#s', $xml, $matches);
    if ($found != false) {
        if ($content_only) {
            return $matches[1];  //ignore the enclosing tags
        } else {
            return $matches[0];  //return the full pattern match
        }
    }
    // No match found: return false.
    return false;
}

function element_set($element_name, $xml, $content_only = false) {
    if ($xml == false) {
        return false;
    }
    $found = preg_match_all('#<'.$element_name.'(?:\s+[^>]+)?>' .
            '(.*?)</'.$element_name.'>#s',
            $xml, $matches, PREG_PATTERN_ORDER);
    if ($found != false) {
        if ($content_only) {
            return $matches[1];  //ignore the enlosing tags
        } else {
            return $matches[0];  //return the full pattern match
        }
    }
    // No match found: return false.
    return false;
}
?>

</table>

</body>
</html>
