<?php
/**
 * Created by PhpStorm.
 * User: Noor
 * Date: 4/12/2019
 * Time: 1:40 PM
 */
$db = new mysqli("localhost","root","134687952a","firstDB");

    if(isset($_REQUEST["Operation"])){
        $operation = $_REQUEST ["Operation"];
        switch ($operation){
            case "Login":
                $acctNum = $_REQUEST['accNum'];
                    $pass = $_REQUEST['pass'];
                $loginQuery = "Select * from users where AccountNumber =" .$acctNum." and Password =".$pass;
                $result = $db->query($loginQuery);
                if($result->num_rows==0){
                    echo"Failed";
                }
                else {
                    echo "Success";
                }
                break;
                case "ChangePass":
                $acctNum = $_REQUEST['accNum'];
                $newPassword = $_REQUEST['newPass'];
                $changePassQuery = "Update users set Password =".$newPassword." where AccountNumber =".$acctNum;
                $result = $db->query($changePassQuery);
                if($result){
                    echo "Password Changed";
                }
                else {
                    echo "Failed";
                }
                break;
            case "withdraw":
                $Amount = $_REQUEST['Amount'];
                $acctNum = $_REQUEST['accNum'];
                $getBalance = "Select Balance from users where AccountNumber =" . $acctNum;
                $result = $db->query($getBalance);
                $row = $result->fetch_row();
                if ($row[0] > $Ammount) {
                    $withdraw = "Update users set Balance =" . ($row[0] - $Ammount) . " where AccountNumber =" . $acctNum;
                    $result = $db->query($ddd);
                    if ($result) {
                        echo "$Ammount Subbed from Balance";
                    } else {
                        echo "$Ammount Scant be subbed from Balance";
                    }

                } else {
                    echo "amount < balance ";
                }
                break;
            case "deposit":
                $Amount = $_REQUEST['Amount'];
                $acctNum = $_REQUEST['accNum'];
                $getBalance = "Select Balance from users where AccountNumber =" . $acctNum;
                $result = $db->query($getBalance);
                $row = $result->fetch_row();
                $depositQuery = "Update users set Balance =" . ($row[0] + $Ammount) . " where AccountNumber =" . $acctNum;
                $result = $db->query($ddd);
                if ($result) {
                    echo "$Ammount Added to the Balance";
                } else {
                    echo "$Ammount couldnt be Added to the Balance";
                }
                break;
            case "getBalance":
                $acctNum = $_REQUEST['accNum'];
                $getBalance = "Select Balance from users where AccountNumber =".$acctNum;
                $result = $db->query($getBalance);
                if($result){
                    $row = $result->fetch_row();
                    echo "$row[0]";
                }
                else {
                    echo "Failed";
                }
                break;
        }
    }