<#import "parts/common.ftl" as c>

<@c.page>
    <div class="form-group mt-3">
        Users count : ${usercount}
    </div>
    <div class="form-group mt-3">
        <b><u>Users statistics</u></b>
    </div>
    <div class="form-group mt-3">
        <table class="table table-sm">
            <thead>
            <tr>
                <th>User name</th>
                <th>Files count</th>
            </tr>
            </thead>
            <tbody>
            <#list statistics as stat>
                <tr>
                    <td>${stat.userView.username}</td>
                    <td>${stat.countFiles}</td>
                </tr>
            <#else> No users
            </#list>
            </tbody>
        </table>
    </div>

    <br>
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
                <th>Count of downloads</th>
            </tr>
            </thead>
            <tbody>
            <#list allfiles as file>
                <tr>
                    <td><span>${file.filename}</span></td>
                    <td><i>${file.fileSize}</i></td>
                    <td>${file.userView.username}</td>
                    <td>${file.downloadCount}</td>
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