<#import "parts/common.ftl" as c>

<@c.page>
    <#if accessMessagesFromCurrentUser??>
        <div>
            <table id="MessageTable" class="table">
                <tbody>
                <#list accessMessagesFromCurrentUser as message>
                    <tr>
                        <td><i>Response from ${message.to.username} not yet received</i></td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <br>
    </#if>

    List of users
    <div class="form-group mt-3">
        <form action="/changegroup" method="post">
            <table class="table table-sm">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Grant access</th>
                    <th>Request access</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <#list users as user>
                    <tr>
                        <td>${user}</td>
                        <td>
                            <label><input type="checkbox" name="list-${user}" ${userListGroup?seq_contains(user)?string("checked", "")}>List</label>
                            <label><input type="checkbox" name="download-${user}" ${userDownloadGroup?seq_contains(user)?string("checked", "")}>Download</label>
                        </td>
                        <td>
                            <label><input type="checkbox" name="askForList-${user}">List</label>
                            <label><input type="checkbox" name="askForDownload-${user}">Download</label>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <button class="btn btn-primary" type="submit">Save</button>
        </form>
    </div>
    <div>
        <a href="/upload" class="btn btn-light btn-block" role="button">Back to Upload</a>
    </div>
</@c.page>
