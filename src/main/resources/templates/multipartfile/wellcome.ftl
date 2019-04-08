<#import "parts/common.ftl" as c>

<@c.page>
    <h5>Hello, <#if username??>${username}<#else>Guest</#if>!</h5>
    <div>This is simple file store</div>
</@c.page>
