<#import "parts/common.ftl" as c>

<@c.page>
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