<#macro login path buttonName isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-1 col-form-label"> Username: </label>
            <div class="col-sm-5">
                <input type="text" name="username" class="form-control" placeholder="User name"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-1 col-form-label"> Password: </label>
            <div class="col-sm-5">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-1 col-form-label"> Email: </label>
                <div class="col-sm-5">
                    <input type="email" name="email" class="form-control" placeholder="some@some.ru"/>
                </div>
            </div>
        <#else><a href="/registration">Add new user</a>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary" type="submit">${buttonName}</button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary" type="submit">Sign Out</button>
    </form>
</#macro>
