<#macro post action value valueName buttonName>
    <form action="${action}" method="POST">
        <input type="hidden" value="${value}" name="${valueName}">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary" type="submit">${buttonName}</button>
    </form>
</#macro>
