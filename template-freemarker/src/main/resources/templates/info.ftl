<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8" />
    <title>Hello World!</title>

</head>

<body>

    <p>

        welcome ${name}  to freemarker!

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

<br>
    <div>
        <img src="/img/timg.jpg">
    </div>



</body>
</html>