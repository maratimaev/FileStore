<#include "security.ftl">
<#import "commonLogin.ftl" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">File store</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/upload">Upload</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/files">Files</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/changegroup">Share files</a>
            </li>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/admin">All files</a>
                </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/manageUsers">Manage users</a>
                </li>
            </#if>
            <#if isAnalyst>
                <li class="nav-item">
                    <a class="nav-link" href="/statistics">Statistics</a>
                </li>
            </#if>
        </ul>

        <div class="navbar-text mr-3">${name}</div>
        <@l.logout />
    </div>
</nav>