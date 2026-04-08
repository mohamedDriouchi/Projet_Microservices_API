<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Utilisateurs</title>
    <style>
        table { border-collapse: collapse; width: 80%; margin: 20px auto; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #4285f4; color: white; }
    </style>
</head>
<body>
<h1 style="text-align:center;">Liste des Abonnés</h1>
<table>
    <tr>
        <th>Nom</th>
        <th>Prénom</th>
        <th>Email</th>
        <th>Adresse de livraison</th>
    </tr>
    <?php foreach ($utilisateurs as $u): ?>
        <tr>
            <td><?= htmlspecialchars($u['nom']) ?></td>
            <td><?= htmlspecialchars($u['prenom']) ?></td>
            <td><?= htmlspecialchars($u['email']) ?></td>
            <td><?= htmlspecialchars($u['adresse']) ?></td>
        </tr>
    <?php endforeach; ?>
</table>
<p style="text-align:center;"><a href="index.php?action=plats">Retour aux Plats</a></p>
</body>
</html>