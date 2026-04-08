<?php
ini_set('display_errors', 1);
error_reporting(E_ALL);

require_once 'controller/controller.php';

$action = isset($_GET['action']) ? $_GET['action'] : 'plats';

if ($action === 'plats') {
    platsAction();
} elseif ($action === 'utilisateurs') {
    utilisateursAction();
} else {
    header('Status: 404 Not Found');
    echo '<h1>Page non trouvée</h1>';
}