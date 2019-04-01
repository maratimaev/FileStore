<#import "parts/common.ftl" as c>

<@c.page>
    <div>
        <b><u>List of users</u></b>
    </div>

    <div class="form-group mt-3">
        <table class="table table-sm">
            <thead>
            <tr>
                <th>Name</th>
                <th>Role</th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
                <tr>
                    <td>${user.username}</td>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td><a href="/manageUsers/${user.id}">Edit</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
    <div>
        <a href="/upload" class="btn btn-light btn-block" role="button">Back to Upload</a>
    </div>
</@c.page>
