<#import "parts/common.ftl" as c>
<#import "parts/post.ftl" as p>

<@c.page>
    <div>
        <b><u>Users files</u></b>
    </div>
    <div class="form-group mt-3">
        <table class="table table-sm">
            <thead>
            <tr>
                <th>File name</th>
                <th>Size</th>
                <th>User name</th>
                <th>Delete file</th>
            </tr>
            </thead>
            <tbody>
            <#list allfiles as file>
                <tr>
                    <td><a href=${file.url}>${file.filename}</a></td>
                    <td><i>${file.fileSize}</i></td>
                    <td>${file.userView.username}</td>
                    <td><@p.post "/admin" "${file.tmpFilename}" "tmpFilename" "Delete"/></td>
                </tr>
            <#else> No files
            </#list>
            </tbody>
        </table>
    </div>
    <div>
        <a href="/upload" class="btn btn-light btn-block" role="button">Back to Upload</a>
    </div>
</@c.page>