<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8" />
    <title>layout页面</title>
</head>
<body>
<#include "/header.ftl" >



<p>

    welcome ${name}  to complex freemarker!

</p>





<p>性别：

<#if gender==0>

    女

<#elseif gender==1>

    男

<#else>

    保密

</#if>

</p>





<h4>我的好友：</h4>

<#list friends as item>

姓名：${item.name} , 年龄${item.age}

<br>

</#list>





<#include "/footer.ftl" >
</body>
</html>