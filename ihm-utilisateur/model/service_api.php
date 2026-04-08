<?php
/**
 * Récupère les données depuis l'API Java via une requête HTTP GET
 * @param string $endpoint Le nom de la ressource (plats ou utilisateurs)
 * @return array Le tableau de données décodé depuis le JSON
 */
function get_data_from_api($endpoint) {
    // URL de ton API GlassFish
    $url = "http://localhost:8080/service-plats-utilisateurs-1.0-SNAPSHOT/api/" . $endpoint;

    $ch = curl_init($url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, array('Accept: application/json'));

    $response = curl_exec($ch);
    $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close($ch);

    if ($httpCode !== 200) {
        return [];
    }

    return json_decode($response, true);
}
?>