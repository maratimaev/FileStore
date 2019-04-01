<#import "parts/common.ftl" as c>
<#import "parts/commonLogin.ftl" as l>

<@c.page>
    <#if message??>
        <div class="alert alert-danger" role="alert">
            ${message}
        </div>
    </#if>
    <div class="mb-3"><b>Add new user</b></div>
    <#--${(message)!}-->
    <@l.login "/registration" "Create" true/>
</@c.page>
