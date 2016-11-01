<get_details.php>
<?php
 
/*
 * Following code will list all the items
 */
 
// array for JSON response
$response = array();
 
// include db connect class
//require_once __DIR__ . '/db_connect.php';
 
// connecting to db
//$db_con = new DB_CONNECT();

//require_once __DIR__ . '/db_config.php';
/*
 * All database connection variables
 */
 
define('DB_USER', "root"); // db user
define('DB_PASSWORD', "pass123"); // db password (mention your db password here)
define('DB_DATABASE', "testDB"); // database name
define('DB_SERVER', "localhost"); // db server

$conn = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD, DB_DATABASE) or die(mysql_error());

 
// get all products from products table
$result = mysqli_query($conn, "SELECT * FROM departments") or die(mysql_error());
 
// check for empty result
if (mysqli_num_rows($result) > 0) 
{
    // looping through all results
    // products node
    $response["departments"] = array();
 
    while ($row = mysqli_fetch_array($result)) 
	{
        // temp user array
        $dept = array();
		$dept["dept_id"] = $row["dept_id"];
        $dept["dept_name"] = $row["dept_name"];
        $dept["dept_phone"] = $row["dept_phone"];
        $dept["dept_email"] = $row["dept_email"];
 
        // push single item into final response array
        array_push($response["departments"], $dept);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
	
	//close connection
	mysqli_close($conn);
	
} 
else 
{
    // no items found
    $response["success"] = 0;
    $response["message"] = "No departments found";
 
    // echo no users JSON
    echo json_encode($response);
	
	//close connection
	mysqli_close($conn);
}
?>