<#import "parts/common.ftl" as c>
<#import "parts/commonLogin.ftl" as l>

<@c.page>
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
        <div class="alert alert-danger" role="alert">
            ${(Session.SPRING_SECURITY_LAST_EXCEPTION.message)!}
        </div>
    </#if>
    <#if message??>
        <div class="alert alert-${messageType}" role="alert">
            ${message}
        </div>
    </#if>
    <#if activated??>
        <div class="alert alert-${activatedType}" role="alert">
            ${activated}
        </div>
    </#if>

    <div class="mb-3"><b>Login</b></div>
    <#--${(message)!}-->
    <@l.login "/login" "Sign on" false/>
</@c.page>
