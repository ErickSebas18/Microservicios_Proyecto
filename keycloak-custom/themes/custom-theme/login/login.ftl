<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bienvenido a ProjecTask</title>
    <link rel="stylesheet" href="${url.resourcesPath}/css/custom-style-login.css">
</head>
<body>
    <div id="kc-container">
        <div id="kc-logo">
            <img src="${url.resourcesPath}/img/PLogo.png" alt="Logo">
            <p> ProjecTask </p>
        </div>
        <div id="kc-form-wrapper">
            <h1>Bienvenido a ProjecTask</h1>
            <p class="kc-description">Por favor ingrese sus credenciales para acceder al sistema</p>
            <#if message?has_content>
                <div class="kc-error">${message.summary}</div>
            </#if>
            <form id="kc-form-login" action="${url.loginAction}" method="post">
                <div class="kc-input-group">
                    <label for="username">Usuario</label>
                    <input type="text" id="username" name="username" placeholder="Tu usuario" required autofocus>
                </div>
                <div class="kc-input-group">
                    <label for="password">Contraseña</label>
                    <input type="password" id="password" name="password" placeholder="Tu contraseña" required>
                </div>
                <div class="kc-button-group">
                    <button type="submit">Ingresar</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
