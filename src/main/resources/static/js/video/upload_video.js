var fileName = "";
// 확장자
var ext = "";
// 파일 사이즈(단위 :MB)
var fileSize = 0;

//드래그앤드랍
$("#fileDrop").on("dragenter", function (e) {
    e.preventDefault();
    e.stopPropagation();
}).on("dragover", function (e) {
    e.preventDefault();
    e.stopPropagation();
}).on("dragleave", function (e) {
    e.preventDefault();
    e.stopPropagation();
}).on("drop", function (e) {
    e.preventDefault();
    var files = e.originalEvent.dataTransfer.files;
    if (files != null) {
        if (files.length < 1) {
            alert("폴더 업로드 불가");
            return;
        }
        selectFile(files)
    } else {
        alert("ERROR");
    }
});

function selectFile(fileObject) {
    var files = null;

    if (fileObject != null) {
        // 파일 Drag 이용하여 등록시
        files = fileObject;
    } else {
        // 직접 파일 등록시
        files = $("#uploadFile")[0].files;
    }

    // 다중파일 등록
    if (files != null) {
        // 파일 이름
        fileName = files[0].name;
        // 확장자
        ext = fileName.split("\.")[-1];
        // 파일 사이즈(단위 :MB)
        fileSize = files[0].size / 1024 / 1024;

        if ($.inArray(ext, ['exe', 'bat', 'sh', 'java', 'jsp', 'html', 'js', 'css', 'xml']) >= 0) {
            // 확장자 체크
            alert("등록 불가 확장자");
        } else if (fileSize > 1000) {
            // 파일 사이즈 체크
            alert("용량 초과\n업로드 가능 용량 : 1000 MB");
        } else {
            uploadFile(files[0]);
        }
    } else {
        alert("ERROR");
    }
}

function uploadFile(file) {

    // 등록할 파일을 formData로 데이터 입력
    var form = $('#uploadForm');
    var formData = new FormData(form[0]);
    formData.append('file', file);

    $.ajax({
        url: "api/video/upload",
        data: formData,
        type: 'POST',
        enctype: 'multipart/form-data',
        processData: false,
        contentType: false,
        dataType: 'text',
        cache: false,
        success: function (data) {
            const jobj = JSON.parse(data);
            alert(jobj["videoUrl"].toString());
        },
        error: function (error) {
        }
    });
}