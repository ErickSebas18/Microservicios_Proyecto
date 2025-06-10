<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Actualizar contraseña</title>
    <link rel="stylesheet" href="${url.resourcesPath}/css/custom-style.css">
</head>
<body>
    <div id="kc-container">
        <div id="kc-logo">
            <img src="${url.resourcesPath}/img/PLogo.png" alt="Logo">
            <p> ProjecTask </p>
        </div>
        <div id="kc-form-wrapper">
            <h1>Actualizar contraseña</h1>
            <p class="kc-description">Debe establecer una nueva contraseña para continuar</p>

            <#if message?has_content>
                <div class="kc-error">${message.summary}</div>
            </#if>

            <form id="kc-update-password-form" action="${url.loginAction}" method="post">
                <div class="kc-input-group">
                    <label for="password-new">Nueva contraseña</label>
                    <input type="password" id="password-new" name="password-new" required autofocus>
                </div>

                <div class="kc-input-group">
                    <label for="password-confirm">Confirmar nueva contraseña</label>
                    <input type="password" id="password-confirm" name="password-confirm" required>
                </div>

                <div class="kc-button-group">
                    <button type="submit">Actualizar</button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
