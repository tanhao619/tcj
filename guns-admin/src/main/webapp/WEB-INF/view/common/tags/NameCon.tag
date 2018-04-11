@/*
    名称查询条件标签的参数说明:

    name : 查询条件的名称
    id : 查询内容的input框id
    value : 查询内容的input框id
@*/
<div class="input-group">
    <div class="input-group-btn">
        <button data-toggle="dropdown" class="btn btn-white dropdown-toggle"
                type="button">${name}
        </button>
    </div>
    <input class="form-control" id="${id}" name="${id}"
           @if(isNotEmpty(type)){
           type="${type}"
           @}
           @if(isNotEmpty(value)){
           value="${tool.dateType(value)}"
           @}
           placeholder="${placeholder!}"
           maxlength="${maxlength!}"
    />
</div>