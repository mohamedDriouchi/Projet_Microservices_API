<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Catalogue des Plats</title>
    <style>
        table { border-collapse: collapse; width: 80%; margin: 20px auto; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #f4b400; color: white; }
    </style>
</head>
<body>
<h1 style="text-align:center;">Nos Plats Disponibles</h1>
<table>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Description</th>
        <th>Prix</th>
    </tr>
    <?php foreach ($plats as $p): ?>
        <tr>
            <td><?= htmlspecialchars($p['id']) ?></td>
            <td><?= htmlspecialchars($p['nom']) ?></td>
            <td><?= htmlspecialchars($p['description']) ?></td>
            <td><?= htmlspecialchars($p['prix']) ?> €</td>
        </tr>
    <?php endforeach; ?>
</table>
<p style="text-align:center;"><a href="index.php?action=utilisateurs">Voir les Utilisateurs</a></p>
</body>
</html>