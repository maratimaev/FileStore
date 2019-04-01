<#import "parts/common.ftl" as c>
<#import "parts/post.ftl" as p>

<@c.page>
    <div>
        <b><u>My files</u></b>
    </div>

    <div class="form-group mt-3">
        <table class="table table-sm">
            <thead>
            <tr>
                <th>File name</th>
                <th>Size</th>
                <th>Delete file</th>
            </tr>
            </thead>
            <tbody>
            <#list userfiles as file>
                <tr>
                    <td><a href=${file.url}>${file.filename}</a></td>
                    <td><i>${file.fileSize}</i></td>
                    <td><@p.post "/files" "${file.tmpFilename}" "tmpFilename" "Delete"/></td>
                </tr>
            <#else> No files
            </#list>
            </tbody>
        </table>
    </div>

    <div>
        <b><u>Shared files</u></b>
    </div>
    <div class="form-group mt-3">
        <table class="table table-sm">
            <thead>
            <tr>
                <th>File name</th>
                <th>Size</th>
                <th>Owner</th>
            </tr>
            </thead>
            <tbody>
            <#list sharedfiles as file>
                <tr>
                    <td><#if file.url??><a href=${file.url}>${file.filename}</a><#else>${file.filename}</td></#if>
                    <td><i>${file.fileSize}</i></td>
                    <td><span>${file.userView.username}</span></td>
                </tr>
            <#else> Nothing shared from other users
            </#list>
            </tbody>
        </table>
    </div>
    <div>
        <a href="/upload" class="btn btn-light btn-block" role="button">Back to Upload</a>
    </div>

</@c.page>