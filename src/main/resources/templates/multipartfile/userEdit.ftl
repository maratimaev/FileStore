<#import "parts/common.ftl" as c>

<@c.page>
    <#if message??>
        <div class="alert alert-danger" role="alert">
            ${message}
        </div>
    </#if>
    <div>
        <b><u>Change user roles</u></b>
    </div>

    <div class="form-group mt-3">
        <form action="/manageUsers" method="post">
            <div class="col-sm-6">
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon1">Username</span>
                    </div>
                    <input type="text" class="form-control" name="username" value="${user.username}" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1">
                </div>
            </div>
            <div class="col-sm-6">
                <#list roles as role>
                    <div>
                        <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
                    </div>
                </#list>
                <input type="hidden" value="${user.id}" name="userId">
                <input type="hidden" value="${_csrf.token}" name="_csrf">
                <button type="submit" class="btn btn-primary">Save</button>
            </div>
        </form>
    </div>
    <div>
        <a href="/manageUsers" class="btn btn-light btn-block" role="button">Back to Manage users</a>
    </div>
</@c.page>
