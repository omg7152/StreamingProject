function changePage(url)
{
    window.location.href = url;
}

function changeIframeUrl(url)
{
    $("#content").attr('src', url);
}

function openModal(modalname){
    document.get
    $("#modal").fadeIn(300);
    $("."+modalname).fadeIn(300);
}

