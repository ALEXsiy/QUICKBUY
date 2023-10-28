<!DOCTYPE html>
<html>
<head>
    <title>QUICKbuy </title>
</head>
<body>
<h1>QUICKbue</h1><hr>
<h4>Подробная информация о товаре</h4>
<#list images as img>
    <img src="/images/${img.id}" height="60px"/><br><br>
</#list>
<b>Название товара:</b>${product.title}<br>
<b>Описание товара:</b>${product.description}<br>
<b>Цена товара:</b>${product.price}<br>
<b>Город товара:</b>${product.city}<br>
<b>Автор товара:</b><a href="/user/${product.user.id}" >${product.user.name}</a><br>
</body>
</html>