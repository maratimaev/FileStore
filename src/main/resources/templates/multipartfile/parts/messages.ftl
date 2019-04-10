<#import "post.ftl" as p>

<#macro messages>
    <#if infoMessagesToCurrentUser??>
        <div class="form-group mt-3">
            <#list infoMessagesToCurrentUser as message>
                <div class="form-group mt-1">
                    <div class="row">
                        <div class="col-md-auto">
                            <i>${message.msg}</i>
                        </div>
                        <div class="col-md-auto">
                            <@p.post "/okMsg" "${message.msgUuid}" "msgUuid" "Ok"/>
                        </div>
                    </div>
                </div>
            <#else>
            </#list>
        </div>
        <br>
    </#if>
    <#if accessMessagesToCurrentUser??>
        <div class="form-group mt-3">
            <#list accessMessagesToCurrentUser as message>
                <div class="form-group mt-1">
                    <div class="row">
                        <div class="col-md-auto">
                            <i>${message.msg}</i>
                        </div>
                        <div class="col-md-auto">
                            <@p.post "/acceptMsg" "${message.msgUuid}" "msgUuid" "Accept"/>
                        </div>
                        <div class="col-md-auto">
                            <@p.post "/declineMsg" "${message.msgUuid}" "msgUuid" "Decline"/>
                        </div>
                    </div>
                </div>
            <#else>
            </#list>
        </div>
        <br>
    </#if>
</#macro>