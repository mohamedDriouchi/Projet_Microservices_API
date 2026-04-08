<?php
require_once 'model/service_api.php';

/**
 * Action : Afficher la liste des plats
 */
function platsAction() {
    $plats = get_data_from_api('plats');
    require 'view/liste_plats.php';
}

/**
 * Action : Afficher la liste des utilisateurs
 */
function utilisateursAction() {
    $utilisateurs = get_data_from_api('utilisateurs');
    require 'view/liste_utilisateurs.php';
}
?>