<#import "parts/common.ftl" as c>
<#import "parts/post.ftl" as p>

<@c.page>
    <#if infoMessagesToCurrentUser??>
        <div class="form-group mt-3">
            <#--<table class="table table-sm">-->
            <#--<tbody>-->
            <#list infoMessagesToCurrentUser as message>
                <div class="form-group mt-1">
                    <div class="row">
                        <div class="col-md-auto">
                            <i>${message.msg}</i>
                        </div>
                        <div class="col-md-auto">
                            <td><@p.post "/okMsg" "${message.msgUuid}" "msgUuid" "Ok"/></td>
                        </div>
                    </div>
                </div>
            <#else> No files
            </#list>
            <#--</tbody>-->
            <#--</table>-->
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
            <#else> No files
            </#list>
        </div>
        <br>
    </#if>
    <div>
        <b>Upload to File store</b>
    </div>
    <div class="form-group mt-3">
        <form method="POST" enctype="multipart/form-data" id="fileUploadForm">
            <div class="form-group">
                <div class="custom-file">
                    <div class="col-sm-10">
                        <input type="file" id="customFile" name="uploadfile"/>
                        <label class="custom-file-label" for="customFile">Choose file</label>
                    </div>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Upload</button>
            </div>

        </form>
    </div>
    <div>
        ${(message)!}
    </div>
    <br>
</@c.page>