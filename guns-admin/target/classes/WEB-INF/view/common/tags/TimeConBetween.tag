<div class="input-group">
    <div class="input-group-btn">
        <button data-toggle="dropdown" class="btn btn-white dropdown-toggle"
                type="button">
            @if(isNotEmpty(required)){
            <span style="color: red; float: left;margin-top: 3px;height: 16px; padding: 0 2px 0 2px;">*</span>
            @}
            ${name}
        </button>
    </div>

        <input type="text" style="width: 50%" class="form-control layer-date"
               onclick="laydate({istime: ${isTime}, format: '${pattern}',max:$('#${endId}').val()})"  name="${beginId}" id="${beginId}" placeholder="开始时间"
               value="${beginValue,dateFormat="yyyy-MM-dd HH:mm:ss"}"/>
        <input type="text" style="width: 50%" class="form-control layer-date" name="${endId}"
               onclick="laydate({istime: ${isTime}, format: '${pattern}',min:$('#${beginId}').val()})" id="${endId}" placeholder="结束时间"
               value="${endValue,dateFormat="yyyy-MM-dd HH:mm:ss"}"/>
</div>