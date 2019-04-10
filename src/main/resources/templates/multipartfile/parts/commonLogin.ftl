<#macro login path buttonName isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-1 col-form-label"> Username: </label>
            <div class="col-sm-5">
                <input type="text" id ="username" name="username" class="form-control" placeholder="User name"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-1 col-form-label"> Password: </label>
            <div class="col-sm-5">
                <input type="password" id="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>
        <#if isRegisterForm>
            <div class="form-group row">
                <label class="col-sm-1 col-form-label"> Email: </label>
                <div class="col-sm-5">
                    <input type="email" id="email" name="email" class="form-control" placeholder="some@some.ru"/>
                </div>
            </div>
        <#else><a href="/registration" id="add_new_user">Add new user</a>
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary" id="submit_button" type="submit">${buttonName}</button>
    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button class="btn btn-primary" type="submit">Sign Out</button>
    </form>
</#macro>
